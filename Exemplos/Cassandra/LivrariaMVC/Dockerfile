FROM openjdk:17-alpine
VOLUME /tmp
ARG JAR_FILE
COPY ${JAR_FILE} app.jar
ENTRYPOINT ["java", "-Ddatastax-java-driver.basic.request.timeout=180s", "-jar", "/app.jar"]
