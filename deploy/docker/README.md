安装docker
======

准备工作
------
关闭防火墙
```bash
sudo systemctl disable firewalld
```
如果安装之前没有关闭防火墙，需要关闭防火墙后再清掉防火墙配置，再重启docker
```bash
sudo iptables -F;sudo iptables -X
```

通过阿里云加速安装docker
------
参考地址： https://cr.console.aliyun.com/?spm=5176.100239.blogcont29941.12.MkthiN#/accelerator


Docker的使用
======
使用docker构建基础服务和业务服务镜像，再推送到私库

构建Docker镜像
------
以构建redis镜像为例

拷贝项目中deploy下的redis目录到私库服务器的/usr/local/eshop/下
```Bash
cd /usr/local/eshop/redis/cluster
sudo docker build -t registry.eshop.com/redis:3.2.6-alpine .
```
or
```Bash
sudo docker build -t registry.eshop.com/redis:3.2.6-alpine /usr/local/eshop/redis/cluster
```

推送Docker镜像
------
```bash
sudo docker push registry.eshop.com/xxx:1.0
```