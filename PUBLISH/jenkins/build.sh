#!/bin/sh

SCRIPT_PATH=$(cd `dirname $0`; pwd)


cd "$SCRIPT_PATH" || exit
cd ../../
echo "The root path of project at $(pwd)"

echo "Starting update by git"
#git pull

echo "Starting build"
#打包parent，第一次进来打包安装就可以，这两个模块
mvn -f'randl_parent/pom.xml' -Dmaven.test.skip=true clean install || exit 1

#打包公共模块组件,打包dao模块组件
mvn -f'randl_common/pom.xml' -Dmaven.test.skip=true clean install || exit 1
mvn -f'randl_service/pom.xml' -Dmaven.test.skip=true clean install || exit 1

#并发打包项目
mvn -f'api_admin/pom.xml' -Dmaven.test.skip=true clean package ; \
mvn -f'api_resource/pom.xml' -Dmaven.test.skip=true clean package ; \
mvn -f'api_sso/pom.xml' -Dmaven.test.skip=true clean package

