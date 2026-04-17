FROM maven:3.8.5-openjdk-17-slim AS build-stage
WORKDIR /app
COPY pom.xml .
COPY src ./src
RUN mvn clean package -DskipTests

FROM eclipse-temurin:17-alpine-3.23
WORKDIR /app
ENV TZ=America/Sao_Paulo
COPY --from=build-stage /app/target/*.jar app.jar
COPY cidades.txt .
COPY estados.txt .
ENTRYPOINT ["java","-jar","app.jar"]
