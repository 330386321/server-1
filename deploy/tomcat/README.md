构建镜像
======
```bash
sudo docker build -t registry.eshop.com/tomcat:8.0_1 .
```

正式启动
======
```bash
sudo docker run --name tomcat-rocketmq -it -d -p 9090:8080 \
    -v /usr/local/eshop/rocketmq-console:/usr/local/tomcat/webapps \
    -v /etc/localtime:/etc/localtime:ro \
    registry.eshop.com/tomcat:8.0_1
```