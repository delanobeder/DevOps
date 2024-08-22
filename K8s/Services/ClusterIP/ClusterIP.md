## DEVOPS

**Prof. Delano M. Beder (UFSCar)**

- - -

#### Serviço ClusterIP
- - -

Este tutorial demonstra como executar uma aplicação exemplo, que contém um serviço do tipo **ClusterIP**, no Kubernetes utilizando o minikube. 

**ClusterIP:**  Expõe o serviço em um IP <u>interno do cluster</u>. Escolher esse valor torna o <u>serviço acessível somente de dentro do cluster</u>. Esse é o padrão usado se você não especificar explicitamente um tipo para um serviço. Você pode expor o Serviço à internet pública usando o **Ingress**.

Material adicional: https://kubernetes.io/docs/concepts/services-networking/service/

------



## Criar um cluster do minikube

```shell
$ minikube start

################################################
# Habilitando o Ingress
################################################

$ minikube image load k8s.gcr.io/ingress-nginx/controller:v1.9.4

$ minikube addons enable ingress
```



## Abrir o painel (_dashboard_)

Abra o painel (_dashboard_) do Kubernetes. 

```shell
$ minikube addons enable dashboard

# Deixe este comando rodando (background).
$ minikube dashboard &
```


<div style="page-break-after: always"></div>

## Criando 2 Deployments (2 Pods)

Crie o arquivo **deployment.yaml** com o conteúdo abaixo

   ```yaml
   apiVersion: apps/v1
   kind: Deployment
   metadata:
     name: nginx
     labels:
       app: web-server
   spec:
     replicas: 1
     selector:
       matchLabels:
         app: web-server
     template:
       metadata:
         labels:
           app: web-server
       spec:
         containers:
           - name: nginx
             image: nginx
             imagePullPolicy: IfNotPresent
             ports:
               - containerPort: 80
   ---
   apiVersion: apps/v1
   kind: Deployment
   metadata:
     name: httpd
     labels:
       app: web-server
   spec:
     replicas: 1
     selector:
       matchLabels:
         app: web-server
     template:
       metadata:
         labels:
           app: web-server
       spec:
         containers:
           - name: httpd
             image: httpd
             imagePullPolicy: IfNotPresent
             ports:
               - containerPort: 80
   ```
e, execute o comando abaixo:
```shell
$ kubectl apply -f deployment.yaml
```

Visualize o Deployment:

```shell
$ kubectl get deployments
```

A saída será semelhante a: 
```
NAME    READY   UP-TO-DATE   AVAILABLE   AGE
httpd   1/1     1            1           101s
nginx   1/1     1            1           101s
```

Visualize o Pod:

```shell
$ kubectl get pods
```

A saída será semelhante a:
    
```
NAME                     READY   STATUS    RESTARTS   AGE
httpd-7c5bfc95fd-ctcqb   1/1     Running   0          2m32s
nginx-65bdbf7998-l5rmn   1/1     Running   0          2m32s
```



## Criando um serviço ClusterIP

Crie o arquivo **service.yaml** com o conteúdo abaixo

   ```yaml
   apiVersion: v1
   kind: Service
   metadata:
     name: frontend
     labels:
       run: frontend
   spec:
     # type: ClusterIP
     # Default se você não especificar explicitamente um tipo para um serviço.
     ports:
       - port: 80
         targetPort: 80
         protocol: TCP
     selector:
       app: web-server
   ```
   execute o comando abaixo:
   ```shell
   $ kubectl apply -f service.yaml
   ```

<div style="page-break-after: always"></div>

Visualize o serviço que você acabou de criar:

```shell
$ kubectl get services
```

A saída será semelhante a:

```
NAME         TYPE        CLUSTER-IP      EXTERNAL-IP   PORT(S)   AGE
frontend     ClusterIP   10.98.120.129   <none>        80/TCP    3s
kubernetes   ClusterIP   10.96.0.1       <none>        443/TCP   11m
```

## Acessando o serviço -- configurando o Ingress

Crie o arquivo **ingress.yaml** com o conteúdo abaixo

```yaml
apiVersion: networking.k8s.io/v1
kind: Ingress
metadata:
  name: gateway-ingress
spec:
  rules:
  - host: k8s.local
    http:
      paths:
      - path: /
        pathType: Prefix
        backend:
          service:
            name: frontend
            port:
              number: 80
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

# The following lines are desirable for IPv6 capable hosts
::1     ip6-localhost ip6-loopback
fe00::0 ip6-localnet
ff00::0 ip6-mcastprefix
ff02::1 ip6-allnodes
ff02::2 ip6-allrouters
```





Abra um terminal e acesse http://k8s.local (via curl). Note que o mesmo comando pode apresentar 2 saídas distintas -- dependendo do Pod (nginx ou httpd) que atende a requisição

```sh
$ curl http://k8s.local
<html><body><h1>It works!</h1></body></html>

$ curl http://k8s.local
<!DOCTYPE html>
<html>
<head>
<title>Welcome to nginx!</title>
<style>
html { color-scheme: light dark; }
body { width: 35em; margin: 0 auto;
font-family: Tahoma, Verdana, Arial, sans-serif; }
</style>
</head>
<body>
<h1>Welcome to nginx!</h1>
<p>If you see this page, the nginx web server is successfully installed and
working. Further configuration is required.</p>

<p>For online documentation and support please refer to
<a href="http://nginx.org/">nginx.org</a>.<br/>
Commercial support is available at
<a href="http://nginx.com/">nginx.com</a>.</p>

<p><em>Thank you for using nginx.</em></p>
</body>
</html>
```




## Limpeza

Agora você pode remover todos os recursos criados no seu cluster:

```shell
$ kubectl delete -f ingress.yaml 
$ kubectl delete -f deployment.yaml; 
$ kubectl delete -f service.yaml
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
