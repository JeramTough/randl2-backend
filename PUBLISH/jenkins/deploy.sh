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

#链接名
linkName=""

#激活的配置
active="beta"

#选择发布的配置
selectedConfig=$1
echo "Selected config is $selectedConfig"
#读取配置信息
targetPath=$(awk -F '=' "/\[$selectedConfig\]/{a=1}a==1&&\$1~/targetPath/{print \$2;exit}" config.ini)
active=$(awk -F '=' "/\[$selectedConfig\]/{a=1}a==1&&\$1~/active/{print \$2;exit}" config.ini)
linkName=$(awk -F '=' "/\[$selectedConfig\]/{a=1}a==1&&\$1~/linkName/{print \$2;exit}" config.ini)

echo -e "targetPath=$targetPath\nactive=$active\nlinkName=$linkName"

isEmpty() {
  if [ ! $1 ]; then
    echo "The params can't is NULL"
    return 1
  else
    return 0
  fi
}
isEmpty "$targetPath" || exit 1
isEmpty "$linkName" || exit 1
isEmpty "$active" || exit 1

#获取jar包路径
cd "$SCRIPT_PATH" || exit
cd ../../
cd web/target || exit
jarName=$(ls *.jar)
jarPath=$(pwd)/"$jarName"

#正式发布
jar_deploy.sh -n"$linkName" -t"$targetPath" -s"$SOURCE_PATH" -j"$jarPath"

#替换激活
sed -i "s/ACTIVE=\"%s\"/ACTIVE=\"$active\"/" \
  "$targetPath"/script/runjar.sh
