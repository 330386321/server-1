拉取代码（第一次）
======

```bash    
cd ~/src/
git clone http://218.17.157.53:18089/eshop/server.git
```

更新代码
======

```bash    
cd ~/src/server
git checkout xxx
git pull
```

创建镜像
======
```Bash
sudo docker build -t eshop/docker-maven:17-dind .
```


启动容器
======
```bash
sudo docker run -d --privileged --name docker-maven \
    --add-host registry.eshop.com:192.168.100.94 \
    -v /etc/localtime:/etc/localtime:ro \
    -v ~/src/server:/usr/src/maven \
    -v /etc/docker/certs.d/registry.eshop.com:/etc/docker/certs.d/registry.eshop.com:ro \
    -v /usr/local/lawu/apache-maven-3.5.0/conf/settings.xml:/usr/share/maven/conf/settings.xml \
    -v ~/.m2:/root/.m2 \
    -v ~/maven/docker-entrypoint.sh:/usr/local/bin/docker-entrypoint.sh \
    -v ~/maven/dockerd-entrypoint.sh:/usr/local/bin/dockerd-entrypoint.sh \
    -w /usr/src/maven \
    eshop/docker-maven:17-dind
```

镜像加速
======
进入容器进行加速配置，参考阿里云容器加速

构建docker镜像
======

1.在liunx中构建
------

非root用户，需要获取root权限
```bash
sudo -s
```

如果找不到JAVA环境，刷新系统变量
```bash
source /etc/profile
```

2.在docker容器中构建
------
```bash    
sudo docker exec -it docker-maven mvn clean package -Dmaven.test.skip -DpushImage
```


清空无用docker镜像
======
```bash 
sudo docker rmi $(sudo docker images -f "dangling=true" -q)
```

删除none镜像
======
```bash
[ $(sudo docker inspect -f "{{.ID}}:{{.RepoTags}}" $(sudo docker images -q) | grep "\[\]" | wc -l) -gt 0 ] && (sudo docker rmi $(sudo docker inspect -f "{{.ID}}:{{.RepoTags}}" $(sudo docker images -q) | grep "\[\]" | cut -d ":" -f 2))
```

删除已经停止运行的容器
======
```bash
sudo docker rm $(sudo docker ps -a -q)
```

清除无用的挂载目录
======
```bash
[ $(sudo docker volume ls -qf dangling=true | wc -l) -gt 0 ] && (sudo docker volume rm $(sudo docker volume ls -qf dangling=true))
```