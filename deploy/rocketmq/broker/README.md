创建镜像
======
```bash
sudo docker build -t eshop/rocketmq-broker:3.2.6 .
``` 

正式启动
======
```bash
sudo docker run --name rocketmq-broker1 -d -p 10911:10911 \
    -v /usr/local/eshop/rocketmq/broker/config1:/usr/local/rocketmq/config \
    -v /etc/localtime:/etc/localtime:ro \
    eshop/rocketmq-broker:3.2.6

sudo docker run --name rocketmq-broker2 -d -p 10922:10922 \
    -v /usr/local/eshop/rocketmq/broker/config2:/usr/local/rocketmq/config \
    -v /etc/localtime:/etc/localtime:ro \
    eshop/rocketmq-broker:3.2.6
``` 