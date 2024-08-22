## DEVOPS

**Prof. Delano M. Beder (UFSCar)**

- - -

#### Serviço NodePort
- - -

Este tutorial demonstra como executar uma aplicação exemplo, que contem um serviço do tipo **NodePort**, no Kubernetes utilizando o minikube. 

**NodePort:** Expõe o serviço no IP de cada Node em uma porta estática (o NodePort). 

Material adicional: https://kubernetes.io/docs/concepts/services-networking/service/

------

## Criar um cluster do minikube e abrir o *dashboard*

```shell
$ minikube start
$ minikube addons enable dashboard
# Deixe este comando rodando (background).
$ minikube dashboard &
```

## Criando 1 Deployment (10 Pods -- 10 réplicas)

Crie o arquivo **deployment.yaml** com o conteúdo abaixo

   ```yaml
   apiVersion: apps/v1
   kind: Deployment
   metadata:
     name: web-server
     labels:
       app: web-server
   spec:
     replicas: 10
     selector:
       matchLabels:
         app: web-server
     template:
       metadata:
         labels:
           app: web-server
       spec:
         containers:
           - name: web-server
             image: pbitty/hello-from:latest
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
NAME         READY   UP-TO-DATE   AVAILABLE   AGE
web-server   10/10   10           10          2m7s
```

Visualize o Pod:

```shell
$ kubectl get pods
```

A saída será semelhante a:
    
```
NAME                          READY   STATUS    RESTARTS   AGE
web-server-6975dddb7f-89h9h   1/1     Running   0          2m23s
web-server-6975dddb7f-9jfwv   1/1     Running   0          2m23s
web-server-6975dddb7f-9p7gp   1/1     Running   0          2m23s
web-server-6975dddb7f-bwpvw   1/1     Running   0          2m23s
web-server-6975dddb7f-c686j   1/1     Running   0          2m23s
web-server-6975dddb7f-cczpv   1/1     Running   0          2m23s
web-server-6975dddb7f-jp5cg   1/1     Running   0          2m23s
web-server-6975dddb7f-s757z   1/1     Running   0          2m23s
web-server-6975dddb7f-vvr9z   1/1     Running   0          2m23s
web-server-6975dddb7f-zmwct   1/1     Running   0          2m23s
```



## Criando um serviço NodePort

Crie o arquivo **service.yaml** com o conteúdo abaixo

   ```yaml
   apiVersion: v1
   kind: Service
   metadata:
     name: frontend
     labels:
       run: frontend
   spec:
     type: NodePort
     ports:
       - port: 80
         targetPort: 80
         nodePort: 30000
     selector:
       app: web-server
   ```
   execute o comando abaixo:
   ```shell
   $ kubectl apply -f service.yaml
   ```

Visualize o serviço que você acabou de criar:

```shell
$ kubectl get services
```

A saída será semelhante a:

```
NAME         TYPE        CLUSTER-IP    EXTERNAL-IP   PORT(S)        AGE
frontend     NodePort    10.97.236.5   <none>        80:30000/TCP   7m14s
kubernetes   ClusterIP   10.96.0.1     <none>        443/TCP        7m18s
```

## Acessando o serviço

Abra um terminal e acesse http://192.168.49.2:30000 (curl), onde o IP 192.168.49.2 é o resultado da invocação do comando `minikube ip`.

Note que o mesmo comando pode apresentar saídas distintas -- dependendo do Pod/Réplica que atende a requisição

```sh
$ curl http://192.168.49.2:30000/; echo ""
Hello from web-server-6975dddb7f-9jfwv (10.244.0.14)

$ curl http://192.168.49.2:30000/; echo ""
Hello from web-server-6975dddb7f-jp5cg (10.244.0.8)

$ curl http://192.168.49.2:30000/; echo ""
Hello from web-server-6975dddb7f-c686j (10.244.0.15)

$ curl http://192.168.49.2:30000/; echo ""
Hello from web-server-6975dddb7f-jp5cg (10.244.0.8)

$ curl http://192.168.49.2:30000/; echo ""
Hello from web-server-6975dddb7f-cczpv (10.244.0.12)
```




## Limpeza

Agora você pode remover todos os recursos criados no seu cluster:

```shell
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
