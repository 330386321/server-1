FROM java:8

ENV ROCKETMQ_HOME /usr/local/rocketmq
ENV JAVA_HOME /usr/lib/jvm/java-8-openjdk-amd64
ENV PATH $ROCKETMQ_HOME/bin:$PATH

RUN mkdir -p "$ROCKETMQ_HOME"
WORKDIR $ROCKETMQ_HOME

ENV MQ_TGZ_URL https://github.com/alibaba/RocketMQ/releases/download/v3.2.6/alibaba-rocketmq-3.2.6.tar.gz

RUN groupadd -r rocketmq && useradd -r -g rocketmq rocketmq
RUN chown -R rocketmq.rocketmq /usr/local/rocketmq


RUN set -x \
	\
	&& wget -O rocketmq.tar.gz "$MQ_TGZ_URL" \
	&& tar -xvf rocketmq.tar.gz --strip-components=1 \
	&& rm rocketmq.tar.gz*

RUN cd bin && chmod +x mqadmin mqbroker mqfiltersrv mqnamesrv mqshutdown