## DevOps
**Prof. Delano M. Beder (UFSCar)**

- - -

#### Exemplo de uma aplicação composta (docker compose) de 3 serviços: NGINX (HTML+JS+CSS) + SpringMVC + MySQL

- - -



1. Clone o repositório https://github.com/delanobeder/DevOps

2. Entre no diretório **Exemplos/Cidades**

3. Entre no diretório **backend** e faça o **build** da imagem (***devopsufscar/cidades-v1-backend***)

   ```bash
   $ cd backend
   $ mvn package -DskipTests
   $ docker build . -t devopsufscar/cidades-v1-backend
   $ mvn clean
   $ cd ..
   ```

4. Entre no diretório **frontend** e faça o **build** da imagem (***devopsufscar/cidades-v1-frontend***)

   ```bash
   $ cd frontend
   $ docker build . -t devopsufscar/cidades-v1-frontend
   $ cd ..
   ```

5. Execute a aplicação

   ```bash
   $ docker compose up
   ```

6. A aplicação estará disponível para acesso no link: http://localhost

7. Fim
