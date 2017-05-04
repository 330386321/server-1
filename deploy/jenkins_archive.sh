#!/bin/sh

DEPLOY_READY_ROOT_DIR=$1

DEPLOY_READY_FOLDER=deploy

# 该目录永远为最新的部署包存放目录
DEPLOY_READY_DIR=${DEPLOY_READY_ROOT_DIR}/${DEPLOY_READY_FOLDER}

# 当前Jenkins工作空间
WORK_SPACE=${WORKSPACE}

# 目标目录是否存在
if [ ! -e ${DEPLOY_READY_ROOT_DIR} ]
then
    echo "[ERR] ${DEPLOY_READY_ROOT_DIR} is not exist!"
    exit
fi

if [ -e ${DEPLOY_READY_DIR} ]
then
    # 当前日期
    CUR_DATE=`date +%Y%m%d-%H%M%S`

    # 备份上一次的文件（加上当前日期）
    cd ${DEPLOY_READY_ROOT_DIR}
    mv ${DEPLOY_READY_FOLDER} ${DEPLOY_READY_FOLDER}${CUR_DATE}
fi


# 创建当前部署包存放文件夹
mkdir ${DEPLOY_READY_DIR}


# 拷贝部署包
function cpJar() {
    jarType=$1
    jarName=$2

    echo "copy ${jarType}/${jarName}"

    # 创建文件夹
    mkdir "${DEPLOY_READY_DIR}/${jarName}"

    # 从Jenkins工作空间拷贝文件
    cp "${WORK_SPACE}/${jarType}/${jarName}/target/${jarName}-1.0-SNAPSHOT.jar" "${DEPLOY_READY_DIR}/${jarName}/"
}

# business
cpJar "business" "agent-api"
cpJar "business" "external-api"
cpJar "business" "member-api"
cpJar "business" "merchant-api"
cpJar "business" "operator-api"
cpJar "business" "statistics"

# service-share
cpJar "service-share" "ad-srv"
cpJar "service-share" "mall-srv"
cpJar "service-share" "operator-srv"
cpJar "service-share" "order-srv"
cpJar "service-share" "product-srv"
cpJar "service-share" "property-srv"
cpJar "service-share" "user-srv"

# service-sys
cpJar "service-sys" "cache-srv"
cpJar "service-sys" "pay-srv"
