拉取镜像
======
    docker pull vongosling/rocketmq

正式启动
======
    docker run --name rocketmq -d -p 9876:9876 -p 10911:10911  \
        -v /etc/localtime:/etc/localtime:ro \
        vongosling/rocketmq
        
        
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