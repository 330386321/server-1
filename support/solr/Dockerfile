FROM solr:6.5.1-alpine
MAINTAINER eshop

ADD lib/mmseg4j-core-1.10.0.jar /opt/solr/server/solr-webapp/webapp/WEB-INF/lib/mmseg4j-core-1.10.0.jar
ADD lib/mmseg4j-solr-2.3.2.jar /opt/solr/server/solr-webapp/webapp/WEB-INF/lib/mmseg4j-solr-2.3.2.jar

WORKDIR /opt/solr
USER root

ENTRYPOINT ["docker-entrypoint.sh"]
CMD ["solr-foreground"]