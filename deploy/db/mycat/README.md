##构建镜像
拷贝项目中deploy下的db/mycat文件夹到/usr/local/eshop/mycat
```
cd /usr/local/eshop/mycat
sudo docker build -t registry.eshop.com/xinetd-mycat:1.6-RELEASE-20161028204710 .
```

启动
======
拷贝项目中deploy/db/mycat文件夹到/usr/local/eshop/mycat
```
sudo docker run --name mycat -d -p 8066:8066 -p 9066:9066 -p 1984:1984 -p 48700:48700 \
    -v /usr/local/eshop/mycat/config/wrapper.conf:/usr/local/mycat/conf/wrapper.conf \
    -v /usr/local/eshop/mycat/config/server.xml:/usr/local/mycat/conf/server.xml \
    -v /usr/local/eshop/mycat/config/schema.xml:/usr/local/mycat/conf/schema.xml \
    -v /usr/local/eshop/mycat/xinetd/mycat_status:/etc/xinetd.d/mycat_status \
    -v /usr/local/eshop/mycat/xinetd/mycat_status_check.sh:/usr/local/bin/mycat_status_check.sh \
    -v /etc/localtime:/etc/localtime:ro \
    registry.eshop.com/xinetd-mycat:1.6-RELEASE-20161028204710
```
```
sudo docker run --name mycat -d -p 8066:8066 -p 9066:9066 -p 1984:1984 \
    -v /etc/localtime:/etc/localtime:ro \
    registry.eshop.com/xinetd-mycat:1.6-RELEASE-20161028204710
```

说明
======
xinetd/mycat_status_check.sh必须授予执行权限