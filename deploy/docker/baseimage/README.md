构建镜像
======
拷贝项目中deploy下的docker/baseimage到/usr/local/eshop/baseimage
这个镜像是所有业务系统的基础镜像
```Bash
sudo docker build -t registry.eshop.com/java:8-alpine .
```

参考资料
------
https://github.com/opencoconut/ffmpeg