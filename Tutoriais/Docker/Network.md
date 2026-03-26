## DevOps
**Prof. Delano M. Beder (UFSCar)**

- - -

#### Exemplo: Docker Network
- - -

1. Abra um terminal e execute os seguintes comandos:

   ```bash
   $ docker run --rm --name web1 -p 8001:80 -d nginx
   
   $ docker run --rm --name web2 -p 8002:80 -d nginx
   
   $ docker network create myNetwork
   
   $ docker network connect myNetwork web1
   
   $ docker network connect myNetwork web2
   ```
   


2. Abra um navegador e acesse as seguintes urls:

​		http://localhost:8001/

​		http://localhost:8002/

3. Volte ao terminal e execute o seguinte comando:

   ```bash
   $ docker exec -it web1 bash
   
   Obs: os demais comandos serão executados "dentro" do container web1
   
   root@a7a76ef71672:/# apt update
   root@a7a76ef71672:/# apt install iputils-ping
   root@a7a76ef71672:/# ping web2
   root@a7a76ef71672:/# exit
   ```

4. Por fim, execute o seguinte comando:

   ```bash
   $ docker stop web1 web2
   ```

   

   

