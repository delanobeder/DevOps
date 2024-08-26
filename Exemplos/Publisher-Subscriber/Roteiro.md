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

5. Crie o arquivo de configuração: **Publisher/.env**

   ```properties
   TO_NAME = <Nome do Destinatário>
   TO_ADDRESS = <Endereço de Email>
   FROM_NAME = <Nome do Remetente>
   FROM_ADDRESS = <usuario>@gmail.com
   ```

6. Crie o arquivo de configuração: **Subscriber/.env**

   ```properties
   USERNAME=<usuario>@gmail.com
   PASSWORD=<senha>
   ```

7. Execute a aplicação

   ```bash
   $ docker compose up
   ```

8. A aplicação estará disponível para acesso no link: http://localhost

9. Fim



