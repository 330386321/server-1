FROM openjdk:7-jre

ENV MYCAT_HOME /usr/local/mycat
ENV JAVA_HOME /usr/lib/jvm/java-7-openjdk-amd64
ENV PATH $MYCAT_HOME/bin:$PATH

RUN mkdir -p "$MYCAT_HOME"
WORKDIR $MYCAT_HOME

ENV MYCAT_TGZ_URL http://dl.mycat.io/1.6-RELEASE/Mycat-server-1.6-RELEASE-20161028204710-linux.tar.gz

RUN groupadd -r mycat && useradd -r -g mycat mycat
RUN chown -R mycat.mycat /usr/local/mycat

RUN apt-get update && apt-get install -y --no-install-recommends \
		ca-certificates \
		wget \
		supervisor \
		tcpd \
        xinetd \
        && \
        apt-get clean && \
        rm -rf /var/lib/apt/lists/

RUN set -x \
	\
	&& wget -O mycat.tar.gz "$MYCAT_TGZ_URL" \
	&& tar -xvf mycat.tar.gz --strip-components=1 \
	&& rm mycat.tar.gz*

RUN echo 'mycat_status    48700/tcp                       # mycat_status' >> /etc/services

ADD program.conf /etc/supervisor/conf.d/program.conf

EXPOSE 8066 9066 1984 48700

VOLUME ["/usr/local/mycat/conf","/usr/local/mycat/logs"]

#CMD ["./bin/mycat", "console"]
CMD ["/usr/bin/supervisord"]