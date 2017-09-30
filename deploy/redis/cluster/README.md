构建镜像
======
拷贝项目中deploy下的redis目录到私库服务器的/usr/local/eshop/redis
```Bash
cd /usr/local/eshop/redis/cluster
sudo docker build -t registry.eshop.com/redis:3.2.6-alpine .
sudo docker push registry.eshop.com/redis:3.2.6-alpine
```

启动
======

```Bash
sudo docker run -d \
 --name=redis1 \
 --net=host \
 -e REDIS_PORT=7000 \
 -e CLUSTER_NODE_TIMEOUT=5000 \
 -v /etc/localtime:/etc/localtime:ro \
 registry.eshop.com/redis:3.2.6-alpine
 
sudo docker run -d \
 --name=redis2 \
 --net=host \
 -e REDIS_PORT=7001 \
 -e CLUSTER_NODE_TIMEOUT=5000 \
 -v /etc/localtime:/etc/localtime:ro \
 registry.eshop.com/redis:3.2.6-alpine
 

sudo docker run -d  \
 --name=redis3 \
 --net=host \
 -e REDIS_PORT=7002 \
 -e CLUSTER_NODE_TIMEOUT=5000 \
 -v /etc/localtime:/etc/localtime:ro \
 registry.eshop.com/redis:3.2.6-alpine
```
搭建集群
====

```bash
gem install redis
```
通过使用 Redis 集群命令行工具 redis-trib ， 编写节点配置文件的工作可以非常容易地完成： redis-trib 位于 Redis 源码的 src 文件夹中， 它是一个 Ruby 程序， 这个程序通过向实例发送特殊命令来完成创建新集群， 检查集群， 或者对集群进行重新分片（reshared）等工作。

#预发布环境
```Bash
/usr/local/eshop/redis/redis-trib.rb create --replicas 1 192.168.100.93:7000 192.168.100.93:7001 \
192.168.100.93:7002 192.168.100.95:7000 192.168.100.95:7001 192.168.100.95:7002
```

#正式环境
```Bash
/usr/local/eshop/redis/cluster/redis-trib.rb create --replicas 1 192.168.100.90:7000 192.168.100.90:7001 \
192.168.100.90:7002 192.168.100.91:7000 192.168.100.91:7001 192.168.100.91:7002
```

参考资料
====
http://www.redis.cn/topics/cluster-tutorial.html
https://redis.io/topics/cluster-tutorial