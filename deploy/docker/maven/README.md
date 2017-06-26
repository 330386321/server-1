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
    -v ~/src/server:/usr/src/maven \
    -v /etc/docker/certs.d/registry.eshop.com:/etc/docker/certs.d/registry.eshop.com:ro \
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
```bash    
sudo docker exec -it maven mvn clean package -Dmaven.test.skip -DpushImage
```


清空无用docker镜像
======
```bash 
docker rmi $(docker images -f "dangling=true" -q)
```