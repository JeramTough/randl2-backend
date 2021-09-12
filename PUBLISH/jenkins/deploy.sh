#!/bin/sh

SCRIPT_PATH=$(
  cd $(dirname $0)
  pwd
)
cd "$SCRIPT_PATH" || exit 1

#发布资源路径，其实就是PUBLISH文件夹
SOURCE_PATH=$(dirname "$SCRIPT_PATH")
SOURCE_PATH=$(dirname "$SOURCE_PATH")

cd ../../
ROOT_PATH=$(pwd)

#模块名数组
moduleNames="api_admin,api_sso,api_resource"

#发布路径
targetPath=""

#激活的配置
active=""

#选择发布的配置
selectedConfig=$1
echo "Selected config is $selectedConfig"
#读取配置信息
targetPath=$(awk -F '=' "/\[$selectedConfig\]/{a=1}a==1&&\$1~/targetPath/{print \$2;exit}" "$SCRIPT_PATH/config.ini")
active=$(awk -F '=' "/\[$selectedConfig\]/{a=1}a==1&&\$1~/active/{print \$2;exit}" "$SCRIPT_PATH/config.ini")
moduleNames=$(awk -F '=' "/\[$selectedConfig\]/{a=1}a==1&&\$1~/moduleNames/{print \$2;exit}" "$SCRIPT_PATH/config.ini")

echo "targetPath=$targetPath\nactive=$active"

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

#发布模块里的jar包函数
deployJar() {
  #获取jar包路径
  cd "$ROOT_PATH" || exit 1

  isEmpty "$1" || exit 1
  moduleName="$1"

  isEmpty "$2" || exit 1
  linkName="$2"

  cd "$moduleName"/target || exit
  jarName=$(ls *.jar)
  jarPath=$(pwd)/"$jarName"

  moduleTargetPath="$targetPath/$moduleName"

  #传送发布
  jar_deploy.sh -n"$linkName" -t"$moduleTargetPath" -s"$SOURCE_PATH" -j"$jarPath"

  #替换激活
  sed -i "s/ACTIVE=\"%s\"/ACTIVE=\"$active\"/" \
    "$moduleTargetPath"/script/runjar.sh

  #替换快捷方式名
  sed -i "s/JAR_NAME=\"%s\"/JAR_NAME=\"$linkName\"/" \
    "$moduleTargetPath"/script/runjar.sh

  echo "Succeed in deploying the module of $moduleName"
}

#正式发布几个模块里的
for each in $(echo $moduleNames | sed "s/,/ /g"); do
  echo "
  Start deploying the module of $each
  "
  deployJar "$each" "$each.jar"
done
