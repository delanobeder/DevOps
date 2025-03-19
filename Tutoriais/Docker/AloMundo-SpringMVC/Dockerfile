FROM maven:3.8.4-openjdk-11-slim AS build-stage
WORKDIR /app
COPY pom.xml .
COPY src ./src
RUN mvn clean package -DskipTests

FROM openjdk:11-jre-slim AS production-stage
WORKDIR /app
ENV TZ=America/Sao_Paulo
COPY --from=build-stage /app/target/AloMundoMVC-0.0.1-SNAPSHOT.jar app.jar
CMD ["java", "-jar", "app.jar"]