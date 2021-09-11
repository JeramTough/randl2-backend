#!/bin/sh

SCRIPT_PATH=$(cd `dirname $0`; pwd)


cd "$SCRIPT_PATH" || exit
cd ../../
echo "The root path of project at $(pwd)"

echo "Starting update by svn"
svn update

echo "Starting build"
#打包parent，第一次进来打包安装就可以，这两个模块
#mvn -Dmaven.test.skip=true clean install || exit 1

#打包公共模块组件,打包dao模块组件
mvn -f'base/pom.xml' -Dmaven.test.skip=true clean install ; \
mvn -f'dao/pom.xml' -Dmaven.test.skip=true clean install

#打包项目
cd web || exit
mvn -Dmaven.test.skip=true clean package || exit 1
