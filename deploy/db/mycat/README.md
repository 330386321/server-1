##构建镜像
```Bash
sudo docker build -t eshop/mycat:1.6-RELEASE-20161028204710 .
```


启动
======
```Bash
sudo docker run --name mycat -d -p 8066:8066 -p 9066:9066 -p 1984:1984 \
-v ~/mycat/config/wrapper.conf:/usr/local/mycat/conf/wrapper.conf \
-v ~/mycat/config/server.xml:/usr/local/mycat/conf/server.xml \
-v ~/mycat/config/schema.xml:/usr/local/mycat/conf/schema.xml \
-v /etc/localtime:/etc/localtime:ro \
eshop/mycat:1.6-RELEASE-20161028204710
```

schema.xml
server.xml