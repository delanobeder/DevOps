FROM maven:3.8.5-openjdk-17-slim AS build-stage
WORKDIR /app
COPY pom.xml .
COPY src ./src
RUN mvn clean package -DskipTests

FROM eclipse-temurin:17-alpine-3.23 AS production-stage
WORKDIR /app
ENV TZ=America/Sao_Paulo
COPY --from=build-stage /app/target/AloMundoMVC-0.0.1-SNAPSHOT.jar app.jar
CMD ["java", "-jar", "app.jar"]
