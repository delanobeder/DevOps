## DevOps
**Prof. Delano M. Beder (UFSCar)**

- - -

#### Exemplo da conteinerização de uma aplicação NodeJs (Express)
- - -



1. Clone o repositório https://github.com/delanobeder/DevOps

2. Entre no diretório **AloMundo-NodeJS**

3. Realize a instalação das dependências da aplicação

   ```bash
   $ npm install
   ```

4. Nesse diretório, crie o arquivo **Dockerfile**

   ```dockerfile
   FROM node:16.14.0-alpine 
   WORKDIR /usr/src/app
   COPY package*.json ./
   RUN npm install
   COPY . .
   EXPOSE 3000
   # Start the application
   CMD [ "node", "app.js" ]
   ```
   
5. Faça o **build** da imagem **docker** de sua aplicação

   ```bash
   $ docker build . -t alomundo-nodejs
   ```

6. Execute a aplicação

   ```bash
   $ docker run --rm -p 3000:3000 alomundo-nodejs
   ```

7. Crie uma conta no dockerhub (https://hub.docker.com/)

8. Faça o ***push*** de sua imagem para o dockerhub

   ```bash
   $ docker tag alomundo-nodejs <usuario_docker_hub>/alomundo-nodejs
   $ docker login
   $ docker push <usuario_docker_hub>/alomundo-nodejs
   ```

9. Execute a aplicação (Imagem no dockerhub)

   ```bash
   $ docker run --rm -p 3000:3000 <usuario_docker_hub>/alomundo-nodejs
   ```

10. Fim



