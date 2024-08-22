## DEVOPS

**Prof. Delano M. Beder (UFSCar)**

- - -

#### Serviço LoadBalancer
- - -

Este tutorial demonstra como executar uma aplicação exemplo, que contem um serviço do tipo **LoadBalancer**, no Kubernetes utilizando o minikube. 

**LoadBalancer:** Expõe o serviço externamente usando um balanceador de carga externo. O Kubernetes não oferece diretamente um componente de balanceamento de carga; você deve fornecer um, ou pode integrar seu cluster Kubernetes a um provedor de nuvem (Google cloud, AWS, etc).

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
web-server-6975dddb7f-8rq62   1/1     Running   0          43s
web-server-6975dddb7f-g2xwv   1/1     Running   0          43s
web-server-6975dddb7f-gdgn4   1/1     Running   0          43s
web-server-6975dddb7f-hwn9t   1/1     Running   0          43s
web-server-6975dddb7f-l5sxs   1/1     Running   0          43s
web-server-6975dddb7f-l8l88   1/1     Running   0          43s
web-server-6975dddb7f-lfpzp   1/1     Running   0          43s
web-server-6975dddb7f-mbmct   1/1     Running   0          43s
web-server-6975dddb7f-mldrj   1/1     Running   0          43s
web-server-6975dddb7f-zxcdg   1/1     Running   0          43s
```



## Criando um serviço LoadBalancer

Crie o arquivo **service.yaml** com o conteúdo abaixo

   ```yaml
   apiVersion: v1
   kind: Service
   metadata:
     name: frontend
     labels:
       run: frontend
   spec:
     type: LoadBalancer
     ports:
       - port: 80
         targetPort: 80
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
NAME         TYPE           CLUSTER-IP      EXTERNAL-IP   PORT(S)        AGE
frontend     LoadBalancer   10.111.77.227   <pending>     80:30610/TCP   109s
kubernetes   ClusterIP      10.96.0.1       <none>        443/TCP        21m
```

## Acessando o serviço

Abra um terminal e execute o seguinte comando:

```sh
$ minikube tunnel
[sudo] senha para delano:          
Status:	
	machine: minikube
	pid: 126857
	route: 10.96.0.0/12 -> 192.168.49.2
	minikube: Running
	services: [frontend]
    errors: 
		minikube: no errors
		router: no errors
		loadbalancer emulator: no errors
```


Abra um novo terminal e visualize novamente o serviço:

```shell
$ kubectl get services
```

Um IP externo foi atribuído ao serviço e a saída será semelhante a:

```
NAME         TYPE           CLUSTER-IP       EXTERNAL-IP      PORT(S)        AGE
frontend     LoadBalancer   10.101.170.247   10.101.170.247   80:32104/TCP   115s
kubernetes   ClusterIP      10.96.0.1        <none>           443/TCP        30m
```

<div style="page-break-after: always"></div>

Abra um novo terminal e acesse http://10.101.170.247 (curl), onde o IP 10.101.170.247 é o EXTERNAL_IP apresentado no comando `kubectl get services`.

Note que o mesmo comando pode apresentar saídas distintas -- dependendo do Pod/Réplica que atende a requisição

```sh
$ curl http://10.101.170.247/; echo ""
Hello from web-server-6975dddb7f-jmczt (10.244.0.43)

$ curl http://10.101.170.247/; echo ""
Hello from web-server-6975dddb7f-6dwdw (10.244.0.38)

$ curl http://10.101.170.247/; echo ""
Hello from web-server-6975dddb7f-hjg52 (10.244.0.42)

$ curl http://10.101.170.247/; echo ""
Hello from web-server-6975dddb7f-6r84m (10.244.0.41)

$ curl http://10.101.170.247/; echo ""
Hello from web-server-6975dddb7f-frjk2 (10.244.0.34)
```




## Limpeza

Feche o terminal em que foi executado o comando `minikube tunnel`.

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
