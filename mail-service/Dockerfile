FROM adoptopenjdk/openjdk11:alpine-jre
MAINTAINER ITAcademy

ADD ./target/by.itacademy.mail-service.jar /app/

CMD ["java", "-Xmx200m", "-jar", "/app/by.itacademy.mail-service.jar"]

EXPOSE 8080 8089