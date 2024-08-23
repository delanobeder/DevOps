## DevOps
**Prof. Delano M. Beder (UFSCar)**

- - -

#### Exemplo de uma aplicação composta (docker compose) de 3 serviços

#### Publisher (SpringMVC) + Subscriber (SpringMVC) + RabbitMQ para envio de emails via SMTP Gmail

- - -



1. Clone o repositório https://github.com/delanobeder/DevOps

2. Entre no diretório **Exemplos/Publisher-Subscriber**

3. Entre no diretório **Publisher** e faça o **build** da imagem (***ps/publisher***)

   Obs: O arquivo **pom.xml**, presente nesse diretório, foi configurado para compilar e fazer o **build** da imagem (***ps/publisher***).

   ```bash
   $ cd Publisher
   $ mvn package clean
   $ cd ..
   ```

4. Entre no diretório **Subscriber** e faça o **build** da imagem (***ps/subscriber***)

   Obs: O arquivo **pom.xml**, presente nesse diretório, foi configurado para compilar e fazer o **build** da imagem (***ps/subscriber***).

   ```bash
   $ cd Subscriber
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



