FROM redis:3.2.6-alpine

ADD run.sh /opt/redis.sh
RUN chmod +x /opt/redis.sh
RUN mkdir /conf && touch /conf/redis.conf

CMD ["/opt/redis.sh"]