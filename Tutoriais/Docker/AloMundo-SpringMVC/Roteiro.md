## DevOps
**Prof. Delano M. Beder (UFSCar)**

- - -

#### Exemplo da conteinerização de uma aplicação SpringMVC
- - -



1. Clone o repositório https://github.com/delanobeder/DevOps

2. Entre no diretório **AloMundo-SpringMVC**

3. Nesse diretório, crie o arquivo **Dockerfile**

   ```dockerfile
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
   ```

4. Faça o **build** da imagem **docker** de sua aplicação

   ```bash
   $ docker build . -t alomundo-springmvc
   ```

5. Execute a aplicação

   ```bash
   $ docker run --rm -p 8080:8080 alomundo-springmvc
   ```

6. Crie uma conta no dockerhub (https://hub.docker.com/)

7. Faça o ***push*** de sua imagem para o dockerhub

   ```bash
   $ docker tag alomundo-springmvc <usuario_docker_hub>/alomundo-springmvc
   $ docker login
   $ docker push <usuario_docker_hub>/alomundo-springmvc
   ```

8. Execute a aplicação (Imagem no dockerhub)

   ```bash
   $ docker run --rm -p 8080:8080 <usuario_docker_hub>/alomundo-springmvc
   ```

9. Fim



