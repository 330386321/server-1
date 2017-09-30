创建镜像
======
构建前先构建registry.eshop.com/rocketmq:3.2.6镜像
```bash
cd /usr/local/eshop/rocketmq/namesrv
sudo docker build -t registry.eshop.com/rocketmq-namesrv:3.2.6 .
sudo docker push registry.eshop.com/rocketmq-namesrv:3.2.6
``` 

正式启动
======
```bash
sudo docker run --name rocketmq-namesrv -d -p 9876:9876 \
    -v /etc/localtime:/etc/localtime:ro \
    registry.eshop.com/rocketmq-namesrv:3.2.6
``` 