## DevOps
**Prof. Delano M. Beder (UFSCar)**

- - -

#### Exemplo de uma aplicação composta (docker compose) de 3 serviços

#### Livraria (SpringMVC) + Transacao (SpringMVC REST API) + MySQL

- - -



1. Clone o repositório https://github.com/delanobeder/DevOps

2. Entre no diretório **Exemplos/Livraria**

3. Entre no diretório **LivrariaMVC** e faça o **build** da imagem (***devopsufscar/livraria-mvc***)

   Obs: O arquivo **pom.xml**, presente nesse diretório, foi configurado para compilar e fazer o **build** da imagem (***devopsufscar/livraria-mvc***).

   ```bash
   $ cd LivrariaMVC
   $ mvn package
   $ cd ..
   ```

4. Entre no diretório **TransacaoAPI** e faça o **build** da imagem (***devopsufscar/transacao-api***)

   Obs: O arquivo **pom.xml**, presente nesse diretório, foi configurado para compilar e fazer o **build** da imagem (***devopsufscar/transacao-api***).

   ```bash
   $ cd TransacaoAPI
   $ mvn package
   $ cd ..
   ```

5. Execute a aplicação

   ```bash
   $ docker compose up
   ```

6. A aplicação estará disponível para acesso no link: http://localhost
   Existem 3 usuários cadastrados (login/senha):

   | login    | senha | Papel         |
   | -------- | ----- | ------------- |
   | admin    | admin | ADMIN         |
   | fulano   | 123   | USUÁRIO COMUM |
   | beltrano | 123   | USUÁRIO COMUM |

7. Fim



