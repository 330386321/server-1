#!/bin/sh

#重启服务

source /etc/profile

# 部署根目录
DEPLOY_BASE_DIR="/usr/local/eshop/deploy/"
# 配置服务器地址
DEPLOY_CONFIG_SERVER="http://192.168.1.26:8080"
# 部署环境
DEPLOY_ENV=test

# --参数--
# 操作类型
OPT=$1
# 部署目标
TARGET=$2

# jar名称
JAR_NAME="${TARGET}-1.0-SNAPSHOT.jar"


# kill掉某个进程
# 第一个参数为进程过滤条件（必须保证唯一）
function killProcess() {
    # 过滤进程
    processStr=`ps aux | grep $1 | grep -v grep`

    # 字符串转为数组
    processArray=(${processStr})

    if [ ${processArray} ]
    then
        # 取出数组第二个值
        processId=${processArray[1]}
        # 强制杀掉进程
        kill -9 ${processId}
    fi
}

function waitServer() {
  echo "Waiting for server..."
  local target=$1; shift
  local wait_time=$1; shift

  server_log=~/logs/${target}/info.log; shift


  waitFile $server_log 10 || { echo "Server log file missing: '$server_log'"; return 1; }

  waitStr $server_log "${target} is started" "$wait_time"

    result=$?
    if [ ${result} = 0 ]
    then
        echo "${target} finish"
    else
        echo "${target} fail"
    fi

}

function waitStr() {
  local file="$1"; shift
  local search_term="$1"; shift
  local wait_time="${1:-5m}"; shift # 5 minutes as default timeout

  (timeout $wait_time tail -F -n0 "$file" &) | grep -q "$search_term" && return 0

  echo "Timeout of $wait_time reached. Unable to find '$search_term' in '$file'"
  return 1
}

function waitFile() {
  local file="$1"; shift
  local wait_seconds="${1:-10}"; shift # 10 seconds as default timeout

  until test $((wait_seconds--)) -eq 0 -o -f "$file" ; do sleep 1; done

  ((++wait_seconds))
}


# 启动jar
function startJar() {
    echo "start process: ${TARGET}"
    cd "${DEPLOY_BASE_DIR}${TARGET}"
    nohup java -jar ${JAR_NAME} --spring.cloud.config.profile=${DEPLOY_ENV} --spring.cloud.config.uri=${DEPLOY_CONFIG_SERVER} >/dev/null 2>&1 &

    waitServer ${TARGET} 1m

    #( tail -f -n0 ~/logs/${TARGET}/info.log & ) | grep -q "${TARGET} is started"

    #grep -m 1 "${TARGET} is started" <(tail -f -n0 ~/logs/${TARGET}/info.log)

    #tail -n0 ~/logs/${TARGET}/err.log

    #tail -f ~/logs/${TARGET}/info.log | grep -m 1 "${TARGET} is started" | { cat; echo >>~/logs/${TARGET}/info.log; }
    #tail -f ~/logs/${TARGET}/info.log | grep -m 1 "${TARGET} is started" | { xargs cat ~/logs/${TARGET}/err.log  >>~/logs/${TARGET}/info.log}

    #tail -f ~/logs/${TARGET}/info.log | awk '/${TARGET} is started/{exit 0}'

    #until tail ~/logs/${TARGET}/info.log| fgrep -q "${TARGET} is started"; do sleep 1; done

    #tail -f ~/logs/${TARGET}/info.log & #execute tail in background
    #read -sn 1 #wait for user input
    #kill %1 #kill the first background progress, i.e. tail

}

# 停止jar
function stopJar() {
    echo "stop process: ${TARGET}"
    killProcess "${JAR_NAME}"
}

# 重启jar
function restartJar() {

    # kill service
    stopJar

    # start service
    startJar
}

if [ ${OPT} = "start" ]
then
    startJar ${TARGET}
elif [ ${OPT} = "stop" ]
then
    stopJar ${TARGET}
elif [ ${OPT} = "restart" ]
then
    restartJar ${TARGET}
else
    echo "unknown operation: ${OPT}"
fi