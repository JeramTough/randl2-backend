#!/bin/bash

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
moduleNames=""

#激活的配置
active="beta"

#选择发布的配置
selectedConfig=$1
echo "Selected config is $selectedConfig"
#读取配置信息
targetPath=$(awk -F '=' "/\[$selectedConfig\]/{a=1}a==1&&\$1~/targetPath/{print \$2;exit}" config.ini)
active=$(awk -F '=' "/\[$selectedConfig\]/{a=1}a==1&&\$1~/active/{print \$2;exit}" config.ini)
moduleNames=$(awk -F '=' "/\[$selectedConfig\]/{a=1}a==1&&\$1~/moduleNames/{print \$2;exit}" "$SCRIPT_PATH/config.ini")
jdkHome=$(awk -F '=' "/\[$selectedConfig\]/{a=1}a==1&&\$1~/jdkHome/{print \$2;exit}" "$SCRIPT_PATH/config.ini")

echo -e "targetPath=$targetPath\nactive=$active"
#jdk_version.sh "$jdkHome"

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

  #设置jdk版本
#  export JAVA_HOME=/usr/local/lib/Java/jdk-11
#  export JRE_HOME=${JAVA_HOME}/jre
#  export CLASSPATH=.:${JAVA_HOME}/lib:${JRE_HOME}/lib
#  export PATH=.:${JAVA_HOME}/bin:$PATH

  for each in $(echo $moduleNames | sed "s/,/ /g"); do
    moduleTargetPath="$targetPath/$each"
    echo "
    Starting the module of $moduleTargetPath...
    "
    sh "$moduleTargetPath"/script/runjar.sh restart
  done

)
