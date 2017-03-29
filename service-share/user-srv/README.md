#eureka

##构建镜像
```Bash
docker build -t eshop/user-srv:1.0-SNAPSHOT .
```

##启动容器
```Bash
docker run --name user-srv -it -d -p 8006:8006 \
    -v /etc/localtime:/etc/localtime:ro \
    eshop/user-srv:1.0-SNAPSHOT
```
