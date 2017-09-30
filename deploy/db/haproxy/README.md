### 参考资料
https://github.com/edwardluzi/docker-keepalived-haproxy

http://blog.csdn.net/j903829182/article/details/73502756

### keepalived配置
 Keepalived is configured by environment variables as below

- `INTERFACE`:           interface to set virtual IP
- `VIRTUAL_IP`:          vip
- `VIRTUAL_MASK`:        vip mask
- `STATE`:               master or backup
- `VIRTUAL_ROUTER_ID`:   must be the same in all nodes
- `PRIORITY`:            101 on master, 100 on backups

##构建镜像
拷贝项目中deploy下的db/haproxy/build到/usr/local/eshop/haproxy/build
```bash
cd /usr/local/eshop/haproxy/build
sudo docker build -t registry.eshop.com/keepalived-haproxy:1.7.9 .
```

拷贝项目中deploy下的db/haproxy/product到/usr/local/eshop/haproxy/
docker-compose方式启动
======
主节点
------
```bash
sudo /usr/local/bin/docker-compose -f master/docker-compose.yml up -d
```
从节点
------
```bash
sudo /usr/local/bin/docker-compose -f backup/docker-compose.yml up -d
```
docker run 方式启动（以下启动方式在容器重启时会无法启动，具体原因未知，暂无解决方法，建议通过docker-compose方式启动）
======

主节点
------
```
sudo docker run --name haproxy --net=host --privileged=true --restart=always -d -p 80:80 \
    -v /usr/local/eshop/haproxy/config/haproxy.cfg:/usr/local/etc/haproxy/haproxy.cfg:ro \
    -v /etc/localtime:/etc/localtime:ro \
    -e INTERFACE=enp0s3 \
    -e STATE=MASTER \
    -e VIRTUAL_IP=192.168.100.125 \
    -e VIRTUAL_MASK=24 \
    -e VIRTUAL_ROUTER_ID=51 \
    -e PRIORITY=101 \
    registry.eshop.com/keepalived-haproxy:1.7.9
```


从节点
------
```
sudo docker run --name haproxy --net=host --privileged=true --restart=always -d -p 80:80 \
    -v /usr/local/eshop/haproxy/config/haproxy.cfg:/usr/local/etc/haproxy/haproxy.cfg:ro \
    -v /etc/localtime:/etc/localtime:ro \
    -e INTERFACE=enp0s3 \
    -e STATE=BACKUP \
    -e VIRTUAL_IP=192.168.100.115 \
    -e VIRTUAL_MASK=24 \
    -e VIRTUAL_ROUTER_ID=51 \
    -e PRIORITY=100 \
    registry.eshop.com/keepalived-haproxy:1.7.3
```
