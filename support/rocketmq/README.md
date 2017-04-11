拉取镜像
======
    docker pull vongosling/rocketmq

正式启动
======
    docker run --name rocketmq -d -p 9876:9876 -p 10911:10911  \
        -v /etc/localtime:/etc/localtime:ro \
        vongosling/rocketmq