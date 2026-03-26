## DevOps
**Prof. Delano M. Beder (UFSCar)**

- - -

#### Exemplo: Docker Volume
- - -
1. Abra um terminal e execute o seguinte comando:

   ```bash
   $ docker run --rm --name web1 -p 8001:80 -d nginx
   ```

2. Abra um navegador e acesse a seguinte url:

​	http://localhost:8001/

3. Volte ao terminal e execute os seguintes comandos:

   ```bash
   $ docker exec -it web1 bash
   
   Obs: os demais comandos serão executados "dentro" do container web1
   
   root@a7a76ef71672:/# cd /usr/share/nginx/html
   root@a7a76ef71672:/# echo "<html><head><title>Ola</title></head><body>Ola</body></html>" > index.html
   root@a7a76ef71672:/# exit
   ```

4. Volte ao navegador e acesse novamente a url: 

   http://localhost:8001/ 				**O conteúdo da página foi atualizado ? Por que ?**

5. Volte ao terminal e execute os seguintes comandos:

   ```bash
   $ docker stop web1
   
   $ docker run --rm --name web1 -p 8001:80 -d nginx
   ```

6. Volte ao navegador e acesse novamente a url:

   http://localhost:8001/ 				**O conteúdo da página foi atualizado ? Por que ?** 

7. Volte ao terminal e execute o seguinte comando:

   ```bash
   $ docker stop web1
   ```

<div style="page-break-after: always"></div>
- - -

1. Abra um terminal e execute os seguintes comando:

   ```bash
   $ mkdir html
   $ cd html
   $ echo "<html><head><title>Ola</title></head><body>Ola</body></html>" > index.html
   $ cd ..
   $ docker run --rm --name web1 -p 8001:80 -v ./html:/usr/share/nginx/html -d nginx
   ```
2. Abra um navegador e acesse a seguinte url:

​	http://localhost:8001/				**Qual foi o conteúdo apresentado ? Por que ?**

3. Volte ao terminal e execute os seguintes comandos:

   ```bash
   $ docker exec -it web1 bash
   
   Obs: os demais comandos serão executados "dentro" do container web1
   
   root@a7a76ef71672:/# cd /usr/share/nginx/html
   root@a7a76ef71672:/# echo "<html><head><title>Ola</title></head><body><h1>Ola</h1></body></html>" > index.html
   root@a7a76ef71672:/# exit
   ```

4. Volte ao navegador e acesse novamente a url: 

   http://localhost:8001/ 				**O conteúdo da página foi atualizado ? Por que ?**
   
5. Abra o arquivo **html/index.html** e faça algumas alterações de sua escolha

6. Volte ao navegador e acesse novamente a url:

​	http://localhost:8001/ 				**O conteúdo da página foi atualizado ? Por que ?**

7. Volte ao terminal e execute os seguintes comandos:

   ```bash
   $ docker stop web1
   
   $ docker run --rm --name web1 -p 8001:80 -v ./html:/usr/share/nginx/html -d nginx
   ```

8. Volte ao navegador e acesse novamente a url:

   http://localhost:8001/				**Qual foi o conteúdo apresentado ? Por que ?**

9. Volte ao terminal e execute o seguinte comando:

   ```bash
   $ docker stop web1
   ```