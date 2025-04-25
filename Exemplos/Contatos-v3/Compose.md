## DevOps
**Prof. Delano M. Beder (UFSCar)**

- - -

#### Exemplo de uma aplicação composta (docker compose) de 3 serviços: NodeJs (Vue.js) + Go + MySQL



1. Clone o repositório https://github.com/delanobeder/DevOps

2. Entre no diretório **Exemplos/Contatos-v3**

3. Entre no diretório **backend** e faça o **build** da imagem (***contatos-v3/backend***)

   ```bash
   $ cd backend
   $ docker build . -t contatos-v3/backend
   $ cd ..
   ```
   
4. Entre no diretório **frontend** e faça o **build** da imagem (***contatos-v3/frontend***)

   ```bash
   $ cd frontend
   $ docker build . -t contatos-v3/frontend
   $ cd ..
   ```

5. Execute a aplicação

   ```bash
   $ docker compose up
   ```

6. A aplicação estará disponível para acesso no link: http://localhost

7. Fim
