FROM frolvlad/alpine-oraclejdk8:slim
VOLUME /tmp
ADD document-service-1.0.0.jar document-service-1.0.0.jar
RUN sh -c 'touch /document-service-1.0.0.jar'
ENV JAVA_OPTS=""
ENTRYPOINT [ "sh", "-c", "java $JAVA_OPTS -Djava.security.egd=file:/dev/./urandom -jar /document-service-1.0.0.jar" ]
