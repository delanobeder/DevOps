FROM eclipse-temurin:17-alpine-3.23

VOLUME /tmp
ARG JAR_FILE
COPY ${JAR_FILE} app.jar

CMD ["java","-jar","app.jar"]
