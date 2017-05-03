拉取镜像
======
    docker pull nginx:1.10.2-alpine


正式启动
======

    docker run --name nginx -it -d -p 80:80 -p 81:81 -p 82:82 \
        -v /usr/local/eshop/nginx/nginx.conf:/etc/nginx/nginx.conf:ro \
        -v /usr/local/media:/usr/local/media:ro \
        -v /usr/local/eshop/merchant-html:/usr/local/eshop/merchant-html:ro \
        -v /usr/local/eshop/operator-html:/usr/local/eshop/operator-html:ro \
        -v /etc/localtime:/etc/localtime:ro \
        nginx:1.10.2-alpine