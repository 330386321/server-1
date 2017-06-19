FROM java:8

ENV ROCKETMQ_HOME /usr/local/rocketmq
ENV JAVA_HOME /usr/lib/jvm/java-8-openjdk-amd64
ENV PATH $ROCKETMQ_HOME/bin:$PATH

RUN mkdir -p "$ROCKETMQ_HOME"
WORKDIR $ROCKETMQ_HOME

RUN groupadd -r rocketmq && useradd -r -g rocketmq rocketmq
RUN chown -R rocketmq.rocketmq /usr/local/rocketmq

COPY alibaba-rocketmq-3.2.6.tar.gz rocketmq.tar.gz


RUN set -x \
	\
	&& tar -xvf rocketmq.tar.gz --strip-components=1 \
	&& rm rocketmq.tar.gz*

RUN cd bin && chmod +x mqadmin mqbroker mqfiltersrv mqnamesrv mqshutdown