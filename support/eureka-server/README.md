#eureka


##启动容器
```Bash
sudo docker run --name eureka-server -it -d -p 8888:8888 \
    -v /etc/localtime:/etc/localtime:ro \
    registry.eshop.com/eureka-server --spring.profiles.active=product1
```


##命令启动
```bash
nohup java -jar eureka-server-1.0-SNAPSHOT.jar --spring.profiles.active=test &
```
