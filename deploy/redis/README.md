拉取镜像
======
```Bash
docker pull redis:3.2.6-alpine
```


启动
======
```Bash
docker run --name redis -p 6379:6379 -d redis:3.2.6-alpine
```

清空数据
=====
删除当前数据库中的所有Key
```Bash
flushdb
```

删除所有数据库中的key
```Bash
flushall
```