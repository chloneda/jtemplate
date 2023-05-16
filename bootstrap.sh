#!/usr/bin/env bash

# 环境变量
CURRENT_DIR=$(cd $(dirname $0) && pwd -P)
PROJECT_HOME=${CURRENT_DIR}

# -------------------------------------------------------------------------------
# 脚本作者:             Chloneda（chloneda@163.com）
# 脚本说明：            Jar包 启动、停止、重启、备份、回滚、查看日志 和 进程状态 的脚本
# 使用方法：
#   1、修改基础配置(可选)：   JAVA_HOME=xxx (Java执行路径，可指定Java版本，不配置使用默认版本)
#                             JVM_OPTS=xxx (JVM参数)
#                             CUSTOM_OPTS=xxx (自定义参数，比如修改端口号)
#   2、修改Jar包名称：        JAR_NAME=xxx
#   3、修改日志路径：         LOG_FILE=xxx
#   4、启动Jar包：            ./bootstrap.sh start
#   5、查看项目日志：         ./bootstrap.sh log
# -------------------------------------------------------------------------------

# 基础配置
JAVA_HOME=
JVM_OPTS="-Xms256m -Xmx512m -Xss512k"
CUSTOM_OPTS="--spring.profiles.active=test"

# 项目配置
JAR_NAME=jtemplate.jar
LOG_FILE=${PROJECT_HOME}/logs/log_info.log
echo "PROJECT_PATH: ${PROJECT_HOME}/${JAR_NAME}"
JAVA_OUT=/dev/null

# 备份配置，不需改动
CURRENT_DATE=$(date "+%Y%m%d-%H") # 备份日期，备份及恢复时使用，备份到小时
PROJECT_BACKUP=${PROJECT_HOME}/backup
JAR_BACKUP_NAME=${JAR_NAME}-${CURRENT_DATE}

# 使用帮助
function help() {
  JAR_USAGE="Usage: sh $0 [start|stop|restart|status|log|backup|rollback]"
  echo "-------------------------------------"
  echo ""
  echo "${JAR_USAGE}"
  echo "$0 status    -查看项目运行状态"
  echo "$0 start     -启动当前项目"
  echo "$0 stop      -停止当前项目"
  echo "$0 restart   -重启当前项目"
  echo "$0 log       -查看当前项目日志"
  echo "$0 backup    -备份当前项目"
  echo "$0 rollback  -回滚最新备份项目"
  echo ""
  echo "-------------------------------------"
  exit 1
}

# 获取进程ID
function get_jar_pid() {
  echo `ps -ef | grep ${JAR_NAME} | grep -v grep | awk '{print $2}'`
}

# 查看状态
function status() {
  pid=$(get_jar_pid)
  if [ -n "${pid}" ]; then
    echo -e "${JAR_NAME} is running with pid: ${pid} !"
  else
    echo -e "${JAR_NAME} is not running !"
  fi
}

# 启动
function start() {
  pid=$(get_jar_pid)
  if [ -n "${pid}" ]; then
    echo -e "${JAR_NAME} is already running with pid: ${pid} !"
  else
    echo -e "Starting ${JAR_NAME} !"
    # JAVA_HOME 变量为 null 或 空字符串 时，重新给定默认值，使用默认 JDK 版本
    nohup ${JAVA_HOME:=java} ${JVM_OPTS} -jar ${PROJECT_HOME}/${JAR_NAME} ${CUSTOM_OPTS} > ${JAVA_OUT} 2>&1 &
    status
  fi
}

# 停止
function stop() {
  pid=$(get_jar_pid)
  if [ -n "${pid}" ]; then
    echo -e "Stoping ${JAR_NAME} !"
    kill -9 ${pid}
  else
    echo -e "${JAR_NAME} is not running !"
  fi
}

# 重启
function restart() {
  stop
  sleep 2
  start
}

# 日志
function log(){
  tail -f ${LOG_FILE} -n 500
}

# 备份
function backup() {
  [ ! -d ${PROJECT_BACKUP} ] && mkdir -p ${PROJECT_BACKUP}
  [ -f ${PROJECT_HOME}/${JAR_NAME} ] && cp -a ${PROJECT_HOME}/${JAR_NAME} ${PROJECT_BACKUP}/${JAR_BACKUP_NAME}
  echo "BACKUP PATH: ${PROJECT_BACKUP}/${JAR_BACKUP_NAME}"
}

# 回滚
function rollback() {
  LAST_BACKUP_JAR=$(ls -t ${PROJECT_BACKUP}/${JAR_NAME}*|head -1)
  # 回滚备份目录中最新日期备份的Jar包
  cp -a ${LAST_BACKUP_JAR} ${PROJECT_HOME}/${JAR_NAME}
  echo "ROLLBACK JAR NAME: ${LAST_BACKUP_JAR}"
}

# 根据输入参数，选择执行对应方法，不输入则执行使用说明
case $1 in
  start)
    start
    ;;
  stop)
    stop
    ;;
  restart)
    restart
    ;;
  log)
    log
    ;;
  status)
    status
    ;;
  backup)
    backup
    ;;
  rollback)
    rollback
    ;;
  *)
    help
    ;;
esac
exit 0
