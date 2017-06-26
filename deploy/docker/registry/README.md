一、私有仓库服务器
====
1、配置hosts
----
192.168.100.94 registry.eshop.com


2、生成证书
----
```bash
mkdir -p ~/certs
cd ~/certs
openssl genrsa -out reg.itmuch.com.key 2048
```

3、生成密钥文件
----
```bash
openssl req -newkey rsa:4096 -nodes -sha256 -keyout registry.eshop.com.key -x509 -days 365 -out registry.eshop.com.crt
```

4、拷贝证书
----
```bash
sudo mkdir -p /etc/docker/certs.d/reg.itmuch.com
sudo cp ~/certs/registry.eshop.com.crt /etc/docker/certs.d/registry.eshop.com/
```

5、重启docker
----
```bash
sudo systemctl restart docker
```

6、启动私库
----
```bash
sudo docker run -d -p 443:5000 --restart=always --name registry \
-v ~/certs:/certs \
-v ~/docker-image:/opt/docker-image \
-e STORAGE_PATH=/opt/docker-image \
-e REGISTRY_HTTP_TLS_CERTIFICATE=/certs/registry.eshop.com.crt \
-e REGISTRY_HTTP_TLS_KEY=/certs/registry.eshop.com.key \
registry:2
```

二、客户机
====
1、配置hosts
----
192.168.100.94 registry.eshop.com

2、拷贝证书
----
将私库的证书拷贝至/etc/docker/certs.d/registry.eshop.com/