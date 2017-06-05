#eureka

##构建镜像
```Bash
docker build -t eshop/eureka-server:1.0-SNAPSHOT .
```

##启动容器
```Bash
docker run --name eureka-server -it -d -p 8888:8888 \
    -v /etc/localtime:/etc/localtime:ro \
    eshop/eureka-server:1.0-SNAPSHOT
```


##命令启动
```bash
nohup java -jar eureka-server-1.0-SNAPSHOT.jar --spring.profiles.active=test &
```
