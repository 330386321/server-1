创建镜像
======
```bash
sudo docker build -t eshop/rocketmq-namesrv:3.2.6 .
``` 

正式启动
======
```bash
sudo docker run --name rocketmq-namesrv -d -p 9876:9876 \
    -v /etc/localtime:/etc/localtime:ro \
    eshop/rocketmq-namesrv:3.2.6
``` 