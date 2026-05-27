## DEVOPS

**Prof. Delano M. Beder (UFSCar)**

- - -

#### Multi-Node
- - -

Este tutorial mostrará como iniciar um cluster multi-node no minikube e implantar um serviço nele.

Material baseado no disponível em https://minikube.sigs.k8s.io/docs/tutorials/multi_node/

------



## Criar um cluster do minikube com 4 nós

```shell
$ minikube start --nodes 4
$ minikube addons enable dashboard
$ minikube addons enable metrics-server
# Deixe este comando rodando (background).
$ minikube dashboard &
```



![image-20240817125612997](/home/delano/.config/Typora/typora-user-images/image-20240817125612997.png)

Visualize os nós do cluster

```bash
$ kubectl get nodes
```

A saída será semelhante a: 
```
NAME           STATUS   ROLES           AGE   VERSION
minikube       Ready    control-plane   75s   v1.28.3
minikube-m02   Ready    <none>          55s   v1.28.3
minikube-m03   Ready    <none>          38s   v1.28.3
minikube-m04   Ready    <none>          23s   v1.28.3
```

<div style="page-break-after: always"></div>

## Criando 1 Deployment

## (4 Pods/Réplicas -- 1 em cada Node)



Crie o arquivo **deployment.yaml** com o conteúdo abaixo

   ```yaml
   apiVersion: apps/v1
   kind: Deployment
   metadata:
     name: hello
   spec:
     replicas: 4
     selector:
       matchLabels:
         app: hello
     template:
       metadata:
         labels:
           app: hello
       spec:
         affinity:
           # ⬇⬇⬇ Isso assegura que os pods serão implantados em diferentes nós
           podAntiAffinity:
             requiredDuringSchedulingIgnoredDuringExecution:
               - labelSelector:
                   matchExpressions: [{ key: app, operator: In, values: [hello] }]
                 topologyKey: "kubernetes.io/hostname"
         containers:
           - name: hello-from
             image: pbitty/hello-from:latest
             ports:
               - name: http
                 containerPort: 80
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
hello   4/4     4            4           7m35s
```

<div style="page-break-after: always"></div>

Visualize o Pod:

```shell
$ kubectl get pod -o="custom-columns=NAME:.metadata.name, IP:.status.podIP, NODE:.spec.nodeName"
```

A saída será semelhante a:
```
NAME                     IP           NODE
hello-5bcfdd44d6-6lxh7   10.244.0.3   minikube
hello-5bcfdd44d6-n6bq5   10.244.1.3   minikube-m02
hello-5bcfdd44d6-kttjj   10.244.2.3   minikube-m03
hello-5bcfdd44d6-2q72w   10.244.3.3   minikube-m04
```

## Criando um serviço NodePort

Crie o arquivo **service.yaml** com o conteúdo abaixo

   ```yaml
   apiVersion: v1
   kind: Service
   metadata:
     name: hello
   spec:
     type: NodePort
     selector:
       app: hello
     ports:
       - protocol: TCP
         nodePort: 30000
         port: 80
         targetPort: http
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
hello        NodePort    10.103.1.42   <none>        80:30000/TCP   13m
kubernetes   ClusterIP   10.96.0.1     <none>        443/TCP        14m
```

<div style="page-break-after: always"></div>

## Acessando o serviço

Abra um terminal e acesse http://192.168.49.2:30000 (curl), onde o IP 192.168.49.2 é o resultado da invocação do comando `minikube ip`.

Note que o mesmo comando pode apresentar saídas distintas -- dependendo do Pod/Réplica que atende a requisição

```sh
$ curl http://192.168.49.2:30000/; echo ""
Hello from hello-5bcfdd44d6-kttjj (10.244.2.3)

$ curl http://192.168.49.2:30000/; echo ""
Hello from hello-5bcfdd44d6-6lxh7 (10.244.0.3)

$ curl http://192.168.49.2:30000/; echo ""
Hello from hello-5bcfdd44d6-kttjj (10.244.2.3)

$ curl http://192.168.49.2:30000/; echo ""
Hello from hello-5bcfdd44d6-n6bq5 (10.244.1.3)

$ curl http://192.168.49.2:30000/; echo ""
Hello from hello-5bcfdd44d6-2q72w (10.244.3.3)
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

(Recomendado) Apague a máquina virtual (VM) do minikube:

```shell
# Recomendado
$ minikube delete
```

Se você desejar utilizar o minikube novamente, você não precisa apagar a VM.