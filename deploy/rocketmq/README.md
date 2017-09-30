创建镜像
======
拷贝项目中deploy下的rocketmq目录到/usr/local/eshop
```bash
cd /usr/local/eshop/rocketmq
sudo docker build -t registry.eshop.com/rocketmq:3.2.6 .
``` 
        
        
纯命令启动
====

```bash
cd /usr/local/eshop/rocketmq/alibaba-rocketmq/bin
``` 
1、配置broker.p
----

    第一步生成 Broker 默认配置模版 sh mqbroker -m > broker.p
    第二步修改配置文件, broker.p

2、新建日志目录
----

```bash
    mkdir /usr/local/eshop/rocketmq/log
```

4、命令授权
----
```bash
    chmod +x mqadmin mqbroker mqnamesrv mqshutdown mqfiltersrv 
```

3、分别启动namesrv、broker
----
```bash
nohup mqnamesrv 1>/usr/local/eshop/rocketmq/log/ng.log 2>/usr/local/eshop/rocketmq/log/ng-err.log &
nohup mqbroker -c broker.p >/usr/local/eshop/rocketmq/log/mq.log &
```
数据清理
=====
```bash
cd /usr/local/rocketmq/bin
sh mqshutdown broker
sh mqshutdown namesrv
--等待停止
rm -rf /root/store
mkdir /root/store
mkdir /root/store/commitlog
mkdir /root/store/consumequeue
mkdir /root/store/index
```