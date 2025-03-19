FROM maven:3.8.5-openjdk-17-slim AS build-stage
WORKDIR /app
COPY pom.xml .
COPY src ./src
RUN mvn clean package -DskipTests

FROM tomcat:10.1.39-jdk17 AS production-stage
WORKDIR /app
ENV TZ=America/Sao_Paulo
COPY --from=build-stage /app/target/AloMundoJSP.war /usr/local/tomcat/webapps/ROOT.war
EXPOSE 8080
CMD ["catalina.sh", "run"]