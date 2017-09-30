创建镜像
======
构建前先构建registry.eshop.com/rocketmq:3.2.6镜像
```bash
cd /usr/local/eshop/broker
sudo docker build -t registry.eshop.com/rocketmq-broker:3.2.6 .
sudo docker push registry.eshop.com/rocketmq-broker:3.2.6
``` 

开发环境启动
======
```bash
sudo docker run --name rocketmq-broker1 -d -p 10911:10911 -p 10912:10912 \
    -v /usr/local/eshop/rocketmq/broker/config1:/usr/local/rocketmq/config \
    -v /etc/localtime:/etc/localtime:ro \
    registry.eshop.com/rocketmq-broker:3.2.6

sudo docker run --name rocketmq-broker2 -d -p 10922:10922 -p 10923:10923 \
    -v /usr/local/eshop/rocketmq/broker/config2:/usr/local/rocketmq/config \
    -v /etc/localtime:/etc/localtime:ro \
    registry.eshop.com/rocketmq-broker:3.2.6
``` 

预发布环境启动
======
```bash
sudo docker run --name rocketmq-broker -d -p 10911:10911 -p 10912:10912 \
    -v /usr/local/eshop/rocketmq/broker/config:/usr/local/rocketmq/config \
    -v /etc/localtime:/etc/localtime:ro \
    registry.eshop.com/rocketmq-broker:3.2.6
``` 


正式环境启动
======
90-拷贝deploy/rocketmq/broker/config/product/broker-m1.p到/usr/local/eshop/rocketmq/broker/config，并且重命名为broker.p
91-拷贝deploy/rocketmq/broker/config/product/broker-m2.p到/usr/local/eshop/rocketmq/broker/config，并且重命名为broker.p
```bash
sudo docker run --name rocketmq-broker -d -p 10911:10911 -p 10912:10912 \
    -v /usr/local/eshop/rocketmq/broker/config:/usr/local/rocketmq/config \
    -v /etc/localtime:/etc/localtime:ro \
    registry.eshop.com/rocketmq-broker:3.2.6
``` 