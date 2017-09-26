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