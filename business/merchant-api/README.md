#eureka

##构建镜像
```Bash
docker build -t eshop/merchant-api:1.0-SNAPSHOT .
```

##启动容器
```Bash
docker run --name merchant-api -it -d -p 2222:2222 \
    -v /etc/localtime:/etc/localtime:ro \
    eshop/merchant-api:1.0-SNAPSHOT
```
