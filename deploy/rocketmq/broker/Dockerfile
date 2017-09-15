FROM registry.eshop.com/rocketmq:3.2.6

ENV ROCKETMQ_HOME /usr/local/rocketmq

WORKDIR $ROCKETMQ_HOME

#COPY config/broker.p config/broker.p

CMD ["/usr/local/rocketmq/bin/mqbroker", "-c config/broker.p"]