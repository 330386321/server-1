#eureka

##构建镜像
```Bash
docker build -t eshop/cache-srv:1.0-SNAPSHOT .
```

##启动容器
```Bash
docker run --name cache-srv -it -d -p 8001:8001 \
    -v /etc/localtime:/etc/localtime:ro \
    eshop/cache-srv:1.0-SNAPSHOT
```
