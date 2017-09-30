构建镜像
======
拷贝项目中deploy下的solr文件夹到/usr/local/eshop
```Bash
cd /usr/local/eshop/solr
sudo docker build -t registry.eshop.com/solr:6.5.1-alpine .
sudo docker push registry.eshop.com/solr:6.5.1-alpine
```

solr单节点部署
====
```bash
sudo docker run --name solr -t -d -p 8983:8983 \
    -v /etc/localtime:/etc/localtime:ro \
    -v /usr/local/eshop/solr/config/solr:/opt/solr/server/solr \
    registry.eshop.com/solr:6.5.1-alpine
```

solr集群部署
====

创建znode节点
------

#预发布环境
```Bash
sudo docker exec -it zookeeper /opt/zookeeper/bin/zkCli.sh -server 192.168.100.93:2181 create /solr "solr"
```

#正式环境
```Bash
sudo docker exec -it zookeeper /opt/zookeeper/bin/zkCli.sh -server 192.168.100.90:2181 create /solr "solr"
```

创建并启动solr节点
======

第一个solr节点
------

#预发布环境
```Bash
sudo docker run --name solr --net=host -t -d -p 8983:8983 -m 1g \
    --add-host web93.lovelawu.com:192.168.100.93 \
    -v /etc/localtime:/etc/localtime:ro \
    registry.eshop.com/solr:6.5.1-alpine \
    -c -z "192.168.100.93:2181,192.168.100.95:2181/solr" -force
```

#正式环境
```Bash
sudo docker run --name solr --net=host -t -d -p 8983:8983 -m 1g \
    --add-host lvs90.lovelawu.com:192.168.100.90 \
    -v /etc/localtime:/etc/localtime:ro \
    registry.eshop.com/solr:6.5.1-alpine \
    -c -z "192.168.100.90:2181,192.168.100.91:2181/solr" -force
```

通过第一个节点上传配置到zookeeper
------
拷贝deploy/solr/cloud到/usr/local/eshop/solr/cloud
上传文件到solr容器
```bash
sudo docker cp /usr/local/eshop/solr/cloud solr:/opt
```

#预发布环境
```Bash
sudo docker exec -it solr /opt/solr/bin/solr \
    zk -z 192.168.100.93:2181/solr \
    -upconfig -n solr -d /opt/cloud
```

#正式环境
```Bash
sudo docker exec -it solr /opt/solr/bin/solr \
    zk -z 192.168.100.90:2181/solr \
    -upconfig -n solr -d /opt/cloud
```

第二个solr节点
------

#预发布环境
```Bash
sudo docker run --name solr --net=host -t -d -p 8983:8983 -m 1g \
    --add-host web95.lovelawu.com:192.168.100.95 \
    -v /etc/localtime:/etc/localtime:ro \
    registry.eshop.com/solr:6.5.1-alpine \
    -c -z "192.168.100.93:2181,192.168.100.95:2181/solr" -force
```

#正式环境
```Bash
sudo docker run --name solr --net=host -t -d -p 8983:8983 -m 1g \
    --add-host kingshard91.lovelawu.com:192.168.100.91 \
    -v /etc/localtime:/etc/localtime:ro \
    registry.eshop.com/solr:6.5.1-alpine \
    -c -z "192.168.100.90:2181,192.168.100.91:2181/solr" -force
```

清空索引
======
<delete><query>*:*</query></delete><commit/>