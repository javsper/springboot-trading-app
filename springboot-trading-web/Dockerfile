FROM amazoncorretto:17

LABEL org.opencontainers.image.authors="Javsper"
LABEL org.opencontainers.image.descritpion="Container image for https://github.com/javsper/springboot-trading"

VOLUME /tmp

COPY target/springboot-trading-web-0.0.1-SNAPSHOT.jar app.jar
ENTRYPOINT ["java","-jar","/app.jar"]
