FROM oysteinjakobsen/armv7-oracle-java8
VOLUME /tmp
ADD document-service-1.0.0.jar document-service-1.0.0.jar
RUN sh -c 'touch /document-service-1.0.0.jar'
ENV JAVA_OPTS=""
EXPOSE 8003
ENTRYPOINT [ "sh", "-c", "java $JAVA_OPTS -Djava.security.egd=file:/dev/./urandom -jar /document-service-1.0.0.jar" ]
