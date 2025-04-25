FROM openjdk:17-alpine
VOLUME /tmp
COPY target/*.jar app.jar
COPY cidades.txt .
COPY estados.txt .
ENTRYPOINT ["java","-jar","/app.jar"]
