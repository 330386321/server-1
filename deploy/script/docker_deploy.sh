#!/bin/sh

# --参数--
# 操作类型
OPT=$1
# 部署目标
TARGET=$2

# 清除none的docker镜像
function clear_images() {
    # 待清理镜像ID
    local images=`sudo docker inspect -f "{{.ID}}:{{.RepoTags}}" $(sudo docker images -q) | grep "\[\]" | cut -d ":" -f 2`
    
    # 待清理镜像数量
    local images_num=`echo "${images}" | wc -l`
    
    if [[ ${images} && ${images_num} -gt 0 ]]
    then
        # 清理镜像
        sudo docker rmi ${images}
    fi
}

# 清理无效的docker挂载
function clear_volumes() {
    # 待清理镜像ID
    local volumes=`sudo docker volume ls -qf dangling=true`
    
    # 待清理镜像数量
    local volumes_num=`echo "${volumes}" | wc -l`
    
    if [[ ${volumes} && ${volumes_num} -gt 1 ]]
    then
        # 清理镜像
        sudo docker volume rm ${volumes}
    fi
}

# 清理docker环境
function clear() {
    # 清理docker镜像
    clear_images
    
    # 清理docker挂载
    clear_volumes
}

# 停止服务，重新拉取镜像，启动服务
function deploy() {
    # 指定的当前环境
    local service_name=$1
    
    if [ ${service_name} ]
    then
        sudo /usr/local/bin/docker-compose -f /usr/local/eshop/docker-compose.yml stop ${service_name}
        sudo /usr/local/bin/docker-compose -f /usr/local/eshop/docker-compose.yml rm ${service_name} << EOF
y
EOF
    else
        sudo /usr/local/bin/docker-compose -f /usr/local/eshop/docker-compose.yml down
    fi
    sudo /usr/local/bin/docker-compose -f /usr/local/eshop/docker-compose.yml pull ${service_name}
    clear
    sudo /usr/local/bin/docker-compose -f /usr/local/eshop/docker-compose.yml up -d ${service_name}
}

# 重启服务
function restart() {
    # 指定的当前环境
    local service_name=$1
    
    sudo /usr/local/bin/docker-compose -f /usr/local/eshop/docker-compose.yml restart ${service_name}
}

if [ ${OPT} = "deploy" ]
then
    deploy ${TARGET}
elif [ ${OPT} = "restart" ]
then
    restart ${TARGET}
elif [ ${OPT} = "clear" ]
then
    clear ${TARGET}
else
    echo "unknown operation: ${OPT}"
fi