#!/bin/bash

SCRIPT_PATH=$(
  cd $(dirname $0)
  pwd
)
cd "$SCRIPT_PATH" || exit

#选择发布的配置
selectedConfig=$1
echo "Selected config is $selectedConfig"

#读取配置信息
jdkHome=$(awk -F '=' "/\[$selectedConfig\]/{a=1}a==1&&\$1~/jdkHome/{print \$2;exit}" "$SCRIPT_PATH/config.ini")
#配置jdk版本
jdk_version.sh "$jdkHome"

cd ../../
echo "The root path of project at $(pwd)"

echo "Starting update by git"
#git pull

echo "Starting build"
#打包parent，第一次进来打包安装就可以，这两个模块
mvn8 -f'randl_parent/pom.xml' -Dmaven.test.skip=true clean install || exit 1

#打包公共模块组件,打包dao模块组件
mvn8 -f'randl_common/pom.xml' -Dmaven.test.skip=true clean install || exit 1
mvn8 -f'randl_service/pom.xml' -Dmaven.test.skip=true clean install || exit 1

#并发打包项目
mvn8 -f'api_admin/pom.xml' -Dmaven.test.skip=true clean package ; \
mvn8 -f'api_resource/pom.xml' -Dmaven.test.skip=true clean package ; \
mvn8 -f'api_sso/pom.xml' -Dmaven.test.skip=true clean package

