拉取镜像
======
    docker pull nginx:1.10.2-alpine


正式启动
======

测试环境
----
```bash
docker run --name nginx -it -d -p 80:80 \
    -v /usr/local/eshop/nginx/nginx_test.conf:/etc/nginx/nginx.conf:ro \
    -v /usr/local/media:/usr/local/media:ro \
    -v /usr/local/eshop/html:/usr/local/eshop/html:ro \
    -v /etc/localtime:/etc/localtime:ro \
    nginx:1.10.2-alpine        
```

预生产环境
----

```bash
docker run --name nginx -it -d -p 80:80 \
    -v /usr/local/eshop/nginx/nginx_pp.conf:/etc/nginx/nginx.conf:ro \
    -v /usr/local/eshop/media:/usr/local/eshop/media:ro \
    -v /usr/local/eshop/html:/usr/local/eshop/html:ro \
    -v /etc/localtime:/etc/localtime:ro \
    nginx:1.10.2-alpine
```
重庆测试环境
----

```bash
docker run --name nginx -it -d -p 80:80 \
    -v /usr/local/eshop/nginx/nginx_cqtest.conf:/etc/nginx/nginx.conf:ro \
    -v /usr/local/eshop/media:/usr/local/eshop/media:ro \
    -v /usr/local/eshop/html:/usr/local/eshop/html:ro \
    -v /etc/localtime:/etc/localtime:ro \
    nginx:1.10.2-alpine
```

开发环境
----
```bash
docker run --name nginx -it -d -p 80:80 \
    -v /usr/local/eshop/nginx/nginx_dev.conf:/etc/nginx/nginx.conf:ro \
    -v /usr/local/media:/usr/local/media:ro \
    -v /usr/local/eshop/html:/usr/local/eshop/html:ro \
    -v /etc/localtime:/etc/localtime:ro \
    nginx:1.10.2-alpine
```

HTTPS
======
生成证书

可以通过以下步骤生成一个简单的证书：
首先，进入你想创建证书和私钥的目录，例如：

$ cd /usr/local/nginx/cer

创建服务器私钥：
$ openssl genrsa  -out server.key 1024

创建签名请求的证书（CSR）：
$ openssl req -new -key server.key -out server.csr

最后标记证书使用上述私钥和CSR：
$ openssl x509 -req -days 365 -in server.csr -signkey server.key -out server.crt

预生产环境
----

```bash
docker run --name nginx_https -it -d -p 80:80 -p 81:81 \
    -v /usr/local/eshop/nginx/cer:/etc/nginx/cer:ro \
    -v /usr/local/eshop/nginx/nginx_pp_https.conf:/etc/nginx/nginx.conf:ro \
    -v /usr/local/eshop/media:/usr/local/eshop/media:ro \
    -v /usr/local/eshop/html:/usr/local/eshop/html:ro \
    -v /etc/localtime:/etc/localtime:ro \
    nginx:1.10.2-alpine
```