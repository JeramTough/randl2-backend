#!/bin/sh

SCRIPT_PATH=$(
  cd $(dirname $0)
  pwd
)

cd "$SCRIPT_PATH" || exit 1

#发布资源路径
SOURCE_PATH=$(dirname "$SCRIPT_PATH")
SOURCE_PATH=$(dirname "$SOURCE_PATH")

#发布路径
targetPath=""

#模块名数组
MODULE_NAMES="api_admin,api_sso,api_resource"

#激活的配置
active="beta"

#选择发布的配置
selectedConfig=$1
echo "Selected config is $selectedConfig"
#读取配置信息
targetPath=$(awk -F '=' "/\[$selectedConfig\]/{a=1}a==1&&\$1~/targetPath/{print \$2;exit}" config.ini)
active=$(awk -F '=' "/\[$selectedConfig\]/{a=1}a==1&&\$1~/active/{print \$2;exit}" config.ini)

echo -e "targetPath=$targetPath\nactive=$active"

isEmpty() {
  if [ ! $1 ]; then
    echo "The params can't is NULL"
    return 1
  else
    return 0
  fi
}
isEmpty "$targetPath" || exit 1
isEmpty "$active" || exit 1




#执行脚本，并且让jenkins不杀进程
(
  set -e
  export BUILD_ID=dontKillMe
  export JENKINS_NODE_COOKIE=dontKillMe

  for each in $(echo $MODULE_NAMES | sed "s/,/ /g"); do
    moduleTargetPath="$targetPath/$each"
    echo "
    Starting the module of $moduleTargetPath...
    "
    sh "$moduleTargetPath"/script/runjar.sh restart
  done

)

