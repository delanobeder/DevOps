## DevOps
**Prof. Delano M. Beder (UFSCar)**

- - -

#### Exemplo de uma aplicação composta (docker compose) de 3 serviços: NodeJs (Vue.js) + Python (Flask) + MongoDB

- - -



1. Clone o repositório https://github.com/delanobeder/DevOps

2. Entre no diretório **Exemplos/Contatos-v2**

3. Entre no diretório **backend** e faça o **build** da imagem (***devopsufscar/contatos-v2-backend***)

   ```bash
   $ cd backend
   $ docker build . -t devopsufscar/contatos-v2-backend
   $ cd ..
   ```
   
4. Entre no diretório **frontend** e faça o **build** da imagem (***devopsufscar/contatos-v2-frontend***)

   ```bash
   $ cd frontend
   $ docker build . -t devopsufscar/contatos-v2-frontend
   $ cd ..
   ```

5. Execute a aplicação

   ```bash
   $ docker compose up
   ```

6. A aplicação estará disponível para acesso no link: http://localhost

7. Fim
