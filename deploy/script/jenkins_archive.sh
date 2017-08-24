#!/bin/sh

# 打包任务的工作空间，自定义参数
PACKAGE_WORK_SPACE=$1

# 存放目录，当前工作空间
DEPLOY_READY_ROOT_DIR=${WORKSPACE}

DEPLOY_READY_FOLDER=deploy

# 该目录永远为最新的部署包存放目录
DEPLOY_READY_DIR=${DEPLOY_READY_ROOT_DIR}/${DEPLOY_READY_FOLDER}


if [ ! -e ${DEPLOY_READY_DIR} ]
then
    # 创建当前部署包存放文件夹
    mkdir ${DEPLOY_READY_DIR}
fi

# 删除可能存在的旧文件
rm -fr ${DEPLOY_READY_DIR}/*



# 拷贝部署包
function cpJar() {
    jarType=$1
    jarName=$2

    echo "copy ${jarType}/${jarName}"

    # 创建文件夹
    mkdir "${DEPLOY_READY_DIR}/${jarName}"

    # 从Jenkins工作空间拷贝文件
    cp "${PACKAGE_WORK_SPACE}/${jarType}/${jarName}/target/${jarName}-1.0-SNAPSHOT.jar" "${DEPLOY_READY_DIR}/${jarName}/"
}

# business
cpJar "business" "agent-api"
cpJar "business" "external-api"
cpJar "business" "member-api"
cpJar "business" "merchant-api"
cpJar "business" "operator-api"
cpJar "business" "jobs"

# service-share
cpJar "service-share" "ad-srv"
cpJar "service-share" "mall-srv"
cpJar "service-share" "operator-srv"
cpJar "service-share" "order-srv"
cpJar "service-share" "product-srv"
cpJar "service-share" "property-srv"
cpJar "service-share" "user-srv"
cpJar "service-share" "statistics-srv"
cpJar "service-share" "agent-srv"

# service-sys
cpJar "service-sys" "cache-srv"