FROM maven:3.6-jdk-11-slim

LABEL maintainer="Bora Köksel bora.koeksel@gmail.com"

ADD ./target/*.jar app.jar

EXPOSE 8761

ENV JAVA_OPTS=""

ENTRYPOINT [ "sh", "-c", "java $JAVA_OPTS -Djava.security.egd=file:/dev/./urandom -jar app.jar" ]