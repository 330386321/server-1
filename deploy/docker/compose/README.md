安装
======
第一种方式
------
下载：https://github.com/docker/compose/releases/download/1.14.0/docker-compose-Linux-x86_64
拷贝至：/usr/local/bin/docker-compose

第二种方式
------
```bash
curl -L https://github.com/docker/compose/releases/download/1.14.0/docker-compose-`uname -s`-`uname -m` > /usr/local/bin/docker-compose
```

获取权限
=====
```bash
sudo chmod +x /usr/local/bin/docker-compose
```

参考资料
=====
https://docs.docker.com/compose/install/

构建网络
=====
创建网络
-----
通过服务编排创建网络,会以当前文件夹为前缀

加入网络
-----
```bash
docker run --name nginx -it -d -p 80:80 \
    --network=eshop_eshopNet \
    -v /etc/localtime:/etc/localtime:ro \
    nginx:1.10.2-alpine
```

启动服务
=====
基础服务
-----
config-server
```bash
docker run -d -p 8080:8080 --privileged --name config-server \
    -v /usr/local/eshop/config:/root/config:ro \
    -e spring.profiles.active=native,dev \
    registry.eshop.com/config-server
```

eureka-server
```bash
docker run -d -p 8888:8888 --privileged --name eureka-server \
    -e spring.profiles.active=native,dev \
    registry.eshop.com/eureka-server
```

业务服务
-----
使用服务编排，第一次启动

root用户
```bash
docker-compose -f ~/compose/dev/23/docker-compose.yml up -d
```

非root用户
```bash
sudo /usr/local/bin/docker-compose -f ~/compose/dev/23/docker-compose.yml up -d
```

更新服务

```bash
sudo /usr/local/bin/docker-compose -f ~/compose/dev/23/docker-compose.yml down
```

或者

```bash
sudo /usr/local/bin/docker-compose -f ~/compose/dev/23/docker-compose.yml stop
sudo /usr/local/bin/docker-compose -f ~/compose/dev/23/docker-compose.yml rm
sudo /usr/local/bin/docker-compose -f ~/compose/dev/23/docker-compose.yml pull
sudo /usr/local/bin/docker-compose -f ~/compose/dev/23/docker-compose.yml up
```
之后再清除无用的镜像和挂载
