拉取镜像
======
```Bash
sudo docker pull registry.cn-hangzhou.aliyuncs.com/acs-sample/zookeeper:3.4.8
```


启动单节点
======
```Bash
sudo docker run -d --name=zk --net=host \
    -e SERVER_ID=1 \
    -e ADDITIONAL_ZOOKEEPER_1=server.1=192.168.1.22:2888:3888 \
    -e ADDITIONAL_ZOOKEEPER_2=clientPort=2181  \
    -v /etc/localtime:/etc/localtime:ro \
    registry.cn-hangzhou.aliyuncs.com/acs-sample/zookeeper:3.4.8
```

正式环境启动
======
在90运行
```Bash
sudo docker run -d \
 --name=zookeeper \
 --net=host \
 -e SERVER_ID=1 \
 -e ADDITIONAL_ZOOKEEPER_1=server.1=192.168.100.90:2888:3888 \
 -e ADDITIONAL_ZOOKEEPER_2=server.2=192.168.100.91:2888:3888 \
 -e ADDITIONAL_ZOOKEEPER_3=clientPort=2181 \
 -v /etc/localtime:/etc/localtime:ro \
 registry.cn-hangzhou.aliyuncs.com/acs-sample/zookeeper:3.4.8
```

在91运行
```Bash
sudo docker run -d \
 --name=zookeeper \
 --net=host \
 -e SERVER_ID=2 \
 -e ADDITIONAL_ZOOKEEPER_1=server.1=192.168.100.90:2888:3888 \
 -e ADDITIONAL_ZOOKEEPER_2=server.2=192.168.100.91:2888:3888 \
 -e ADDITIONAL_ZOOKEEPER_3=clientPort=2181 \
 -v /etc/localtime:/etc/localtime:ro \
registry.cn-hangzhou.aliyuncs.com/acs-sample/zookeeper:3.4.8
```

预发布环境启动
======
```Bash
sudo docker run -d \
 --name=zookeeper \
 --net=host \
 -e SERVER_ID=1 \
 -e ADDITIONAL_ZOOKEEPER_1=server.1=192.168.100.93:2888:3888 \
 -e ADDITIONAL_ZOOKEEPER_2=server.2=192.168.100.95:2888:3888 \
 -e ADDITIONAL_ZOOKEEPER_3=clientPort=2181 \
 -v /etc/localtime:/etc/localtime:ro \
 registry.cn-hangzhou.aliyuncs.com/acs-sample/zookeeper:3.4.8
```

```Bash
sudo docker run -d \
 --name=zookeeper \
 --net=host \
 -e SERVER_ID=2 \
 -e ADDITIONAL_ZOOKEEPER_1=server.1=192.168.100.93:2888:3888 \
 -e ADDITIONAL_ZOOKEEPER_2=server.2=192.168.100.95:2888:3888 \
 -e ADDITIONAL_ZOOKEEPER_3=clientPort=2181 \
 -v /etc/localtime:/etc/localtime:ro \
registry.cn-hangzhou.aliyuncs.com/acs-sample/zookeeper:3.4.8
```

查看节点状态
======
```Bash
echo stat |nc 127.0.0.1 2181
```

参考资料
======
https://yq.aliyun.com/articles/30328?spm=a2c1q.8351553.0.0.FuUppI