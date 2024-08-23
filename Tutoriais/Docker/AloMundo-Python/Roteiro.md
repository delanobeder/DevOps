## DevOps
**Prof. Delano M. Beder (UFSCar)**

- - -

#### Exemplo da conteinerização de uma aplicação Python (Flask)
- - -



1. Clone o repositório https://github.com/delanobeder/DevOps

2. Entre no diretório **AloMundo-Python**

3. Nesse diretório, crie o arquivo **Dockerfile**

   ```dockerfile
   FROM python:3.8-slim-buster
   WORKDIR /python-docker
   ENV TZ=America/Sao_Paulo
   COPY requirements.txt requirements.txt
   RUN pip3 install -r requirements.txt
   COPY . .
   CMD [ "python3", "-m" , "flask", "run", "--host=0.0.0.0"]
   ```
   
5. Faça o **build** da imagem **docker** de sua aplicação

   ```bash
   $ docker build . -t alomundo-python
   ```

6. Execute a aplicação

   ```bash
   $ docker run --rm -p 5000:5000 alomundo-python
   ```

7. Crie uma conta no dockerhub (https://hub.docker.com/)

8. Faça o ***push*** de sua imagem para o dockerhub

   ```bash
   $ docker tag alomundo-python <usuario_docker_hub>/alomundo-python
   $ docker login
   $ docker push <usuario_docker_hub>/alomundo-python
   ```

9. Execute a aplicação (Imagem no dockerhub)

   ```bash
   $ docker run --rm -p 5000:5000 <usuario_docker_hub>/alomundo-python
   ```

10. Fim



