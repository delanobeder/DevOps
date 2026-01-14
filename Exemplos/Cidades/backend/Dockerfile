FROM eclipse-temurin:17-alpine-3.23
VOLUME /tmp
COPY target/*.jar app.jar
COPY cidades.txt .
COPY estados.txt .
ENTRYPOINT ["java","-jar","/app.jar"]
