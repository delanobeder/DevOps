## DEVOPS

**Prof. Delano M. Beder (UFSCar)**

- - -

#### Ingress
- - -

Material aqui apresentado é baseado no seguinte link:

Material adicional: https://minikube.sigs.k8s.io/docs/start/

------



## Criar um cluster do minikube

```shell
$ minikube start
```



## Abrir o painel (_dashboard_)

Abra o painel (_dashboard_) do Kubernetes. 

```shell
$ minikube addons enable dashboard

# Deixe este comando rodando (background).
$ minikube dashboard &
```


## Habilitando o Ingress

```shell
################################################
# Habilitando o Ingress
################################################

$ minikube image load k8s.gcr.io/ingress-nginx/controller:v1.9.4

$ minikube addons enable ingress
```



## Implantando a aplicação

Crie o arquivo **ingress-example.yaml** que cria 2 serviços simples (**echo-server**).

```yaml
kind: Pod
apiVersion: v1
metadata:
  name: foo-app
  labels:
    app: foo
spec:
  containers:
    - name: foo-app
      image: 'kicbase/echo-server:1.0'
---
kind: Service
apiVersion: v1
metadata:
  name: foo-service
spec:
  selector:
    app: foo
  ports:
    - port: 8080
---
kind: Pod
apiVersion: v1
metadata:
  name: bar-app
  labels:
    app: bar
spec:
  containers:
    - name: bar-app
      image: 'kicbase/echo-server:1.0'
---
kind: Service
apiVersion: v1
metadata:
  name: bar-service
spec:
  selector:
    app: bar
  ports:
    - port: 8080
---
```

e, execute o comando abaixo:
```shell
$ kubectl apply -f services.yaml
```

## Acessando o serviço -- configurando o Ingress

Crie o arquivo **ingress.yaml** com o conteúdo abaixo

```yaml
apiVersion: networking.k8s.io/v1
kind: Ingress
metadata:
  name: example-ingress
spec:
  rules:
    - host: k8s.local
      http:
        paths:
          - pathType: Prefix
            path: /foo
            backend:
              service:
                name: foo-service
                port:
                  number: 8080
          - pathType: Prefix
            path: /bar
            backend:
              service:
                name: bar-service
                port:
                  number: 8080
---
```
e, execute o comando abaixo:
```shell
$ kubectl apply -f ingress.yaml
```

Configure o arquivo **/etc/hosts**. Note que o IP 192.168.49.2 é o resultado da invocação do comando `minikube ip`.

```bash
127.0.0.1	localhost
127.0.1.1	mundau
192.168.49.2    k8s.local
```

Abra um terminal e acesse http://k8s.local/foo ou http://k8s.local/bar (via curl). Note que os comandos apresentam 2 saídas distintas -- dependendo do serviço (**foo-service** ou **bar-service**) que atende a requisição (roteado pelo **Ingress**)

```sh
$ curl http://k8s.local/foo
Request served by foo-app

HTTP/1.1 GET /foo

Host: k8s.local
Accept: */*
User-Agent: curl/7.81.0
X-Forwarded-For: 192.168.49.1
X-Forwarded-Host: k8s.local
X-Forwarded-Port: 80
X-Forwarded-Proto: http
X-Forwarded-Scheme: http
X-Real-Ip: 192.168.49.1
X-Request-Id: 1fd2d9cf47d01cbfa44f3869d2b3ca10
X-Scheme: http

$ curl http://k8s.local/bar
Request served by bar-app

HTTP/1.1 GET /bar

Host: k8s.local
Accept: */*
User-Agent: curl/7.81.0
X-Forwarded-For: 192.168.49.1
X-Forwarded-Host: k8s.local
X-Forwarded-Port: 80
X-Forwarded-Proto: http
X-Forwarded-Scheme: http
X-Real-Ip: 192.168.49.1
X-Request-Id: bfbf1b16fbe099f57506e29b3e133cc1
X-Scheme: http
```


## Limpeza

Agora você pode remover todos os recursos criados no seu cluster:

```shell
$ kubectl delete -f ingress.yaml 
$ kubectl delete -f services.yaml
```

Encerre o cluster do minikube:

```shell
$ minikube stop
```

(Opcional) Apague a máquina virtual (VM) do minikube:

```shell
# Opcional
$ minikube delete
```

Se você desejar utilizar o minikube novamente, você não precisa apagar a VM.