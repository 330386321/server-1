构建镜像
======
    docker build -t eshop/solr:5.3.2 .


正式启动
======
    docker run --name solr -t -d -p 8983:8983 \
        -v /etc/localtime:/etc/localtime:ro \
        -v /usr/local/eshop/solr/config/solr:/opt/solr/server/solr \
        eshop/solr:5.3.2