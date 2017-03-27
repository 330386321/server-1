#eureka

##构建镜像
```Bash
docker build -t eshop/test-api:1.0-SNAPSHOT .
```

##启动容器
```Bash
docker run --name test-api -it -d -p 3333:3333 \
    -v /etc/localtime:/etc/localtime:ro \
    eshop/test-api:1.0-SNAPSHOT
```
