拉取镜像
======
```Bash
docker pull mysql:5.7.18
```


启动
======
```Bash
sudo docker run --name mysql -e MYSQL_ROOT_PASSWORD=123456 -d -p 3306:3306 -v ~/db:/var/lib/mysql mysql:5.7.18
```