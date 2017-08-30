创建镜像
======
```bash
sudo docker build -t eshop/rocketmq-broker:3.2.6 .
``` 

正式启动
======
```bash
sudo docker run --name rocketmq-broker -d -p 10911:10911 \
    -v ~/rocketmq/broker/config:/usr/local/rocketmq/config \
    -v /etc/localtime:/etc/localtime:ro \
    eshop/rocketmq-broker:3.2.6
``` 