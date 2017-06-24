#eureka

##构建镜像
```Bash
docker build -t eshop/user-srv:1.0-SNAPSHOT .
```

##启动容器
```Bash
sudo docker run --name user-srv -it -d -p 8006:8006 \
    -v /etc/localtime:/etc/localtime:ro \
    registry.eshop.com/user-srv --spring.profiles.active=product 
```
