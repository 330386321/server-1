拉取镜像
======
    docker pull nginx:1.10.2-alpine


正式启动
======

测试环境
----
```bash
docker run --name nginx -it -d -p 80:80 -p 81:81 -p 82:82 \
    -v /usr/local/eshop/nginx/nginx_test.conf:/etc/nginx/nginx.conf:ro \
    -v /usr/local/media:/usr/local/media:ro \
    -v /usr/local/eshop/html:/usr/local/eshop/html:ro \
    -v /etc/localtime:/etc/localtime:ro \
    nginx:1.10.2-alpine        
```

预生产环境
----

```bash
docker run --name nginx -it -d -p 80:80 -p 81:81 \
    -v /usr/local/eshop/nginx/nginx_pp.conf:/etc/nginx/nginx.conf:ro \
    -v /usr/local/eshop/media:/usr/local/eshop/media:ro \
    -v /usr/local/eshop/html:/usr/local/eshop/html:ro \
    -v /etc/localtime:/etc/localtime:ro \
    nginx:1.10.2-alpine
```