## DevOps
**Prof. Delano M. Beder (UFSCar)**

- - -

#### Exemplo da conteinerização de uma aplicação SpringMVC
- - -



1. Clone o repositório https://github.com/delanobeder/DevOps

2. Entre no diretório **AloMundo-SpringMVC**

3. Realize a compilação/empacotamento da aplicação

   ```bash
   $ mvn package
   ```

4. Nesse diretório, crie o arquivo **Dockerfile**

   ```dockerfile
   FROM ibmjava:jre
   RUN mkdir /opt/app
   ENV TZ=America/Sao_Paulo
   COPY target/AloMundoMVC-0.0.1-SNAPSHOT.jar /opt/app/release.jar
   CMD ["java", "-jar", "/opt/app/release.jar"]
   ```

5. Faça o **build** da imagem **docker** de sua aplicação

   ```bash
   $ docker build . -t alomundo-springmvc
   ```

6. Execute a aplicação

   ```bash
   $ docker run --rm -p 8080:8080 alomundo-springmvc
   ```

7. Crie uma conta no dockerhub (https://hub.docker.com/)

8. Faça o ***push*** de sua imagem para o dockerhub

   ```bash
   $ docker tag alomundo-springmvc <usuario_docker_hub>/alomundo-springmvc
   $ docker login
   $ docker push <usuario_docker_hub>/alomundo-springmvc
   ```

9. Execute a aplicação (Imagem no dockerhub)

   ```bash
   $ docker run --rm -p 8080:8080 <usuario_docker_hub>/alomundo-springmvc
   ```

10. Fim



