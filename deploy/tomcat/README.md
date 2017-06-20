构建镜像
======
    docker build -t eshop/tomcat:8.0_1 .

正式启动
======

    docker run --name tomcat-rocketmq -it -d -p 9090:8080 \
        -v /usr/local/eshop/rocketmq-console:/usr/local/tomcat/webapps \
        -v /etc/localtime:/etc/localtime:ro \
        eshop/tomcat:8.0_1