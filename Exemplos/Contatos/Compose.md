## DevOps
**Prof. Delano M. Beder (UFSCar)**

- - -

#### Exemplo de uma aplicação composta (docker compose) de 3 serviços: NodeJs (Vue.js) + SpringMVC + MySQL

- - -



1. Clone o repositório https://github.com/delanobeder/DevOps

2. Entre no diretório **Exemplos/Contatos**

3. Entre no diretório **backend** e faça o **build** da imagem (***contatos/backend***)

   Obs: O arquivo **pom.xml**, presente nesse diretório, foi configurado para compilar e fazer o **build** da imagem (***contatos/backend***).

   ```bash
   $ cd backend
   $ mvn package
   $ cd ..
   ```

4. Entre no diretório **frontend** e faça o **build** da imagem (***contatos/frontend***)

   ```bash
   $ cd frontend
   $ docker build . -t contatos/frontend
   $ cd ..
   ```

5. Execute a aplicação

   ```bash
   $ docker compose up
   ```

6. A aplicação estará disponível para acesso no link: http://localhost

7. Fim
