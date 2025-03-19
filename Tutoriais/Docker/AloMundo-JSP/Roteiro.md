## DevOps
**Prof. Delano M. Beder (UFSCar)**

- - -

#### Exemplo da conteinerização de uma aplicação JSP
- - -



1. Clone o repositório https://github.com/delanobeder/DevOps

2. Entre no diretório **AloMundo-JSP**

3. Nesse diretório, crie o arquivo **Dockerfile**

   ```dockerfile
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
   ```

4. Faça o **build** da imagem **docker** de sua aplicação

   ```bash
   $ docker build . -t alomundo-jsp
   ```

5. Execute a aplicação

   ```bash
   $ docker run --rm -p 8080:8080 alomundo-jsp
   ```

6. Crie uma conta no dockerhub (https://hub.docker.com/)

7. Faça o ***push*** de sua imagem para o dockerhub

   ```bash
   $ docker tag alomundo-springmvc <usuario_docker_hub>/alomundo-jsp
   $ docker login
   $ docker push <usuario_docker_hub>/alomundo-jsp
   ```

8. Execute a aplicação (Imagem no dockerhub)

   ```bash
   $ docker run --rm -p 8080:8080 <usuario_docker_hub>/alomundo-jsp
   ```

9. Fim



