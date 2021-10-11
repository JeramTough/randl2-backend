#!/bin/bash
# 该脚本为Linux下启动java程序的脚本
# date: 2021/10/12

SCRIPT_PATH=$(
  cd $(dirname $0) || exit
  pwd
)
  cd "$SCRIPT_PATH" || exit

# jar包存放路径,上一级的目录
JAR_PATH=$(dirname "$SCRIPT_PATH")

#激活的springboot配置
ACTIVE="%s"

# jar包名称
JAR_NAME="%s"

#配置JDK版本路径
JDK_HOME="%s"

# 日志输出文件
LOG_FILE=logs
# java虚拟机启动参数
#JAVA_OPTS="-Xms512m -Xmx512m -XX:MetaspaceSize=512m -XX:MaxMetaspaceSize=1024m -XX:ParallelGCThreads=4 -XX:+PrintGCDateStamps -XX:+PrintGCDetails -XX:+HeapDumpOnOutOfMemoryError -Dfile.encoding=utf-8"
JAVA_OPTS="-Dspring.profiles.active=$ACTIVE -Dfile.encoding=utf-8 --add-opens java.base/java.lang=ALL-UNNAMED"
# 是否马上输出日志，auto则碰到启动成功标记，自动关闭日志
IS_PRINT="auto"

#设置jdk版本
if [ -f "$SET_JDK_VERSION_FILE_PATH" ]; then
  sh "$SET_JDK_VERSION_FILE_PATH"
fi

#设置环境变量
#export JENKINS_HOME=/developer/Environment/Jenkins/JenkinsHome/

jar_options.sh -n"$JAR_NAME" -p"$JAR_PATH" -l"$LOG_FILE" -g"$IS_PRINT" -v"$JDK_HOME" "$1" "$JAVA_OPTS"
