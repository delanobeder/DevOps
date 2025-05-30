## DevOps
**Prof. Delano M. Beder (UFSCar)**

- - -

#### Exemplo de uma aplicação composta (docker compose) de 3 serviços: NodeJs (Vue.js) + MongoDB

- - -



1. Clone o repositório https://github.com/delanobeder/DevOps

2. Entre no diretório **Exemplos/ToDoList**

3. Entre no diretório **app**app e faça o **build** da imagem (***devopsufscar/todolist-app***)

   ```bash
   $ cd app
   $ docker build . -t devopsufscar/todolist-app
   $ cd ..
   ```

4. Execute a aplicação

   ```bash
   $ docker compose up
   ```

5. A aplicação estará disponível para acesso no link: http://localhost:5000

7. Fim
