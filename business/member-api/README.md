#eureka

##构建镜像
```Bash
docker build -t eshop/member-api:1.0-SNAPSHOT .
```

##启动容器
```Bash
docker run --name member-api -it -d -p 1111:1111 \
    -v /etc/localtime:/etc/localtime:ro \
    eshop/member-api:1.0-SNAPSHOT
```
