拉取镜像
======
    docker pull nginx:1.10.2-alpine


正式启动
======

    docker run --name nginx -it -d -p 80:80 \
        -v /usr/local/eshop/nginx/nginx.conf:/etc/nginx/nginx.conf:ro \
        -v /usr/local/media:/usr/local/media:ro \
        -v /etc/localtime:/etc/localtime:ro \
        nginx:1.10.2-alpine