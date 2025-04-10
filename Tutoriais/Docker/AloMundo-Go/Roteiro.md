## DevOps
**Prof. Delano M. Beder (UFSCar)**

- - -

#### Exemplo da conteinerização de uma aplicação Go
- - -



1. Clone o repositório https://github.com/delanobeder/DevOps

2. Entre no diretório **AloMundo-Go**

3. Nesse diretório, crie o arquivo **Dockerfile**

   ```dockerfile
   FROM golang:1.20
   
   # Set destination for COPY
   WORKDIR /app
   
   # Download Go modules
   COPY . ./
   RUN go mod download
   
   # Build
   RUN CGO_ENABLED=0 GOOS=linux go build -o /alomundo-go
   
   EXPOSE 9090
   
   # Run
   CMD ["/alomundo-go"]
   ```

4. Faça o **build** da imagem **docker** de sua aplicação

   ```bash
   $ docker build . -t alomundo-go
   ```

5. Execute a aplicação

   ```bash
   $ docker run --rm -p 9090:9090 alomundo-go
   ```

6. Crie uma conta no dockerhub (https://hub.docker.com/)

7. Faça o ***push*** de sua imagem para o dockerhub

   ```bash
   $ docker tag alomundo-go <usuario_docker_hub>/alomundo-go
   $ docker login
   $ docker push <usuario_docker_hub>/alomundo-go
   ```

8. Execute a aplicação (Imagem no dockerhub)

   ```bash
   $ docker run --rm -p 9090:9090 <usuario_docker_hub>/alomundo-go
   ```

9. Fim



