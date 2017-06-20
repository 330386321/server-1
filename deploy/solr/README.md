构建镜像
======

```Bash
sudo docker build -t eshop/solr:6.5.1-alpine .
```

创建znode节点
------

```Bash
sudo docker exec -it zookeeper /opt/zookeeper/bin/zkCli.sh -server 192.168.100.90:2181 create /solr "solr"
```

创建并启动solr节点
======

第一个solr节点
------
```Bash
sudo docker run --name solr --net=host -t -d -p 8983:8983 -m 1g \
    --add-host lvs90.lovelawu.com:192.168.100.90 \
    -v /etc/localtime:/etc/localtime:ro \
    eshop/solr:6.5.1-alpine \
    -c -z "192.168.100.90:2181,192.168.100.91:2181/solr" -force
```

通过第一个节点上传配置到zookeeper
------
```Bash
sudo docker cp ~/solr/cloud containId:/opt

sudo docker exec -it solr /opt/solr/bin/solr \
    zk -z 192.168.100.90:2181/solr \
    -upconfig -n solr -d /opt/cloud
```

第二个solr节点
------

```Bash
sudo docker run --name solr --net=host -t -d -p 8983:8983 -m 1g \
    --add-host kingshard91.lovelawu.com:192.168.100.91 \
    -v /etc/localtime:/etc/localtime:ro \
    eshop/solr:6.5.1-alpine \
    -c -z "192.168.100.90:2181,192.168.100.91:2181/solr" -force
```