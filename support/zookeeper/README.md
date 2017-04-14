拉取镜像
======
```Bash
docker pull registry.cn-hangzhou.aliyuncs.com/acs-sample/zookeeper:3.4.8
```


启动单节点
======
```Bash
docker run -d --name=zk --net=host \
    -e SERVER_ID=1 \
    -e ADDITIONAL_ZOOKEEPER_1=server.1=192.168.1.22:2888:3888 \
    -e ADDITIONAL_ZOOKEEPER_2=clientPort=2181  \
    -v /etc/localtime:/etc/localtime:ro \
    registry.cn-hangzhou.aliyuncs.com/acs-sample/zookeeper:3.4.8
```

启动双节点
======
```Bash
docker run -d \
 --name=zookeeper1 \
 --net=host \
 -e SERVER_ID=1 \
 -e ADDITIONAL_ZOOKEEPER_1=server.1=192.168.0.93:2888:3888 \
 -e ADDITIONAL_ZOOKEEPER_2=server.2=192.168.0.93:2889:3889 \
 -e ADDITIONAL_ZOOKEEPER_3=clientPort=2181 \
 registry.cn-hangzhou.aliyuncs.com/acs-sample/zookeeper:3.4.8
```

```Bash
docker run -d \
 --name=zookeeper2 \
 --net=host \
 -e SERVER_ID=2 \
 -e ADDITIONAL_ZOOKEEPER_1=server.1=192.168.0.93:2888:3888 \
 -e ADDITIONAL_ZOOKEEPER_2=server.2=192.168.0.93:2889:3889 \
 -e ADDITIONAL_ZOOKEEPER_3=clientPort=2182 \
registry.cn-hangzhou.aliyuncs.com/acs-sample/zookeeper:3.4.8
```

查看节点状态
======
```Bash
echo stat |nc 127.0.0.1 2181
```