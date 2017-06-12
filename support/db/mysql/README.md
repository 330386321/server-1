拉取镜像
======
```Bash
docker pull mysql:5.7.18
```


启动
======
```Bash
sudo docker run --name mysql -e MYSQL_ROOT_PASSWORD=123456 -d -p 3306:3306 \
-v ~/mysql/data:/var/lib/mysql \
-v ~/mysql/conf.d:/etc/mysql/conf.d \
-v /etc/localtime:/etc/localtime:ro \
mysql:5.7.18
```

MySQL主从配置
======

1、连接主节点MySQL容器
------

2、登录MySQL
------
```Bash
mysql -u root -p
```

3、创建账户并授权
------
```Bash
grant replication slave on *.* to 'eshopsync'@'%' identified by 'eshop@master';
```

4、查看主服务器状态
------
```Bash
show master status;

+------------------+----------+--------------+------------------+-------------------+
| File             | Position | Binlog_Do_DB | Binlog_Ignore_DB | Executed_Gtid_Set |
+------------------+----------+--------------+------------------+-------------------+
| mysql-bin.000001 |      442 |              |                  |                   |
+------------------+----------+--------------+------------------+-------------------+
```

5、连接从接到MySQL容器并登录
------
这里的 master_log_file='mysql-bin.000001', master_log_pos与主服务器中的master status一致
```Bash
change master to master_host='192.168.100.206',master_port=3306,master_user='eshopsync',master_password='eshop@master',master_log_file='mysql-bin.000003', master_log_pos=442;
```

6、启动从服务器复制功能
------
```Bash
start slave;
```