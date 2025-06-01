## DEVOPS

**Prof. Delano M. Beder (UFSCar)**

- - -

#### Hello Minikube
- - -

Este tutorial demonstra como executar uma aplicação exemplo no Kubernetes utilizando o minikube. 

Material baseado no disponível em https://kubernetes.io/pt-br/docs/tutorials/hello-minikube/

------



## Criar um cluster do minikube

```shell
$ minikube start
```



## Abrir o painel (_dashboard_)

Abra o painel (_dashboard_) do Kubernetes. 

```shell
# Deixe este comando rodando (background).
$ minikube dashboard &
```



## Criando um Deployment



Um **Pod** do **Kubernetes** consiste em um ou mais contêineres agrupados para fins de administração e gerenciamento de rede. O **Pod** deste tutorial possui apenas um contêiner. Um **Deployment** do **Kubernetes** verifica a integridade do seu **Pod** e reinicia o contêiner do **Pod** caso este seja finalizado. **Deployments** são a maneira recomendada de gerenciar a criação e escalonamento dos **Pods**.

1. Modo imperativo

   Usando o comando `kubectl create` para criar um Deployment que gerencia um Pod.

   O Pod executa um contêiner baseado na imagem do Docker disponibilizada.

    ```shell
    $ kubectl create deployment hello-node --image=registry.k8s.io/echoserver:1.4
    ```

<div style="page-break-after: always"></div>

2. Modo declarativo

   Crie o arquivo **deployment.yaml** com o conteúdo abaixo

   ```yaml
   apiVersion: apps/v1
   kind: Deployment
   metadata:
     labels:
       app: hello-node
     name: hello-node
   spec:`
     replicas: 1
     selector:
       matchLabels:
         app: hello-node
     template:
       metadata:
         labels:
           app: hello-node
       spec:
         containers:
           - image: crccheck/hello-world:latest
             imagePullPolicy: IfNotPresent
             name: echoserver
   ```
   e, execute o comando abaixo:
   ```shell
   $ kubectl apply -f deployment.yaml
   ```




3. Visualize o Deployment:

    ```shell
    $ kubectl get deployments
    ```

    A saída será semelhante a:

    ```
    NAME         READY   UP-TO-DATE   AVAILABLE   AGE
    hello-node   1/1     1            1           1m
    ```

4. Visualize o Pod:

    ```shell
    $ kubectl get pods
    ```

    A saída será semelhante a:

    ```
    NAME                          READY     STATUS    RESTARTS   AGE
    hello-node-5f76cf6ccf-br9b5   1/1       Running   0          1m
    ```

<div style="page-break-after: always"></div>

5. Visualize os eventos do cluster:

    ```shell
    $ kubectl get events
    ```
6. Visualize a configuração do `kubectl`:

    ```shell
    $ kubectl config view
    ```

## Criando um Serviço



Por padrão, um Pod só é acessível utilizando o seu endereço IP interno no cluster Kubernetes. Para disponiblilizar o contêiner `hello-node` fora da rede virtual do Kubernetes, você deve expor o Pod como um Service do Kubernetes.

1. Modo imperativo
    Exponha o Pod usando o comando `kubectl expose`:

    ```shell
    $ kubectl expose deployment hello-node --type=LoadBalancer --port=8080
    ```

    O parâmetro `--type=LoadBalancer` indica que você deseja expor o seu serviço fora do cluster Kubernetes.

    A aplicação dentro da imagem de teste escuta apenas na porta TCP 8080. Se você usou `kubectl expose` para expor uma porta diferente, os clientes não conseguirão se conectar a essa outra porta.

2. Modo declarativo

   Crie o arquivo **service.yaml** com o conteúdo abaixo

   ```yaml
   apiVersion: v1
   kind: Service
   metadata:
     labels:
       app: hello-node
     name: hello-node
   spec:
     type: LoadBalancer
     ports:
     - nodePort: 31123
       port: 8000
       protocol: TCP
       targetPort: 8000
     selector:
       app: hello-node
   ```
   execute o comando abaixo:
   ```shell
   $ kubectl apply -f service.yaml
   ```

<div style="page-break-after: always"></div>

3. Visualize o serviço que você acabou de criar:

    ```shell
    $ kubectl get services
    ```

    A saída será semelhante a:

    ```
    NAME         TYPE           CLUSTER-IP      EXTERNAL-IP   PORT(S)          AGE
    hello-node   LoadBalancer   10.100.87.121   <pending>     8000:31123/TCP   21s
    kubernetes   ClusterIP      10.96.0.1       <none>        443/TCP          23m
    ```

    Em provedores de nuvem que fornecem serviços de balanceamento de carga para o Kubernetes, um IP externo seria provisionado para acessar o serviço. No minikube, o tipo `LoadBalancer` torna o serviço acessível por meio do comando `minikube service`.
    
1. Execute o comando a seguir:

    ```shell
    $ minikube service hello-node
    ```

    Este comando abre uma janela do navegador que serve o seu aplicativo e exibe o retorno da requisição ao aplicativo.

## Habilitando Complementos (addons)

A ferramenta minikube inclui um conjunto integrado de addons que podem ser habilitados, desabilitados e executados no ambiente Kubernetes local.

1. Liste os *addons* suportados atualmente:

    ```shell
    $ minikube addons list
    ```

    A saída será semelhante a:

    ```
    addon-manager: enabled
    dashboard: enabled
    default-storageclass: enabled
    efk: disabled
    freshpod: disabled
    gvisor: disabled
    helm-tiller: disabled
    ingress: disabled
    ingress-dns: disabled
    logviewer: disabled
    metrics-server: disabled
    nvidia-driver-installer: disabled
    nvidia-gpu-device-plugin: disabled
    registry: disabled
    registry-creds: disabled
    storage-provisioner: enabled
    storage-provisioner-gluster: disabled
    ```

2. Habilite um ***addon***, por exemplo, `metrics-server`:

    ```shell
    $ minikube addons enable metrics-server
    ```

    A saída será semelhante a:

    ```
    The 'metrics-server' addon is enabled
    ```
3. Visualize o Pod e o Service que você acabou de criar:

    ```shell
    $ kubectl get pod,svc -n kube-system
    ```

    A saída será semelhante a:

    ```
    NAME                                   READY   STATUS    RESTARTS   AGE
    pod/coredns-5dd5756b68-sps44           1/1     Running   0          6m26s
    pod/etcd-minikube                      1/1     Running   0          6m39s
    pod/kube-apiserver-minikube            1/1     Running   0          6m39s
    pod/kube-controller-manager-minikube   1/1     Running   0          6m40s
    pod/kube-proxy-dtd9m                   1/1     Running   0          6m27s
    pod/kube-scheduler-minikube            1/1     Running   0          6m39s
    pod/metrics-server-7c66d45ddc-t5bx5    0/1     Running   0          52s
    pod/storage-provisioner                1/1     Running   0          6m38s
    
    NAME                     TYPE        CLUSTER-IP      EXTERNAL-IP   PORT(S)                  AGE
    service/kube-dns         ClusterIP   10.96.0.10      <none>        53/UDP,53/TCP,9153/TCP   6m39s
    service/metrics-server   ClusterIP   10.100.226.74   <none>        443/TCP                  52s
    ```


4. Verifique a saida do `metrics-server`:

    ```shell
    $ kubectl top pods
    ```

    The output is similar to:

    ```
    NAME                         CPU(cores)   MEMORY(bytes)   
    hello-node-ccf4b9788-4jn97   1m           6Mi             
    ```

    Se você obter a mensagem abaixo, espere um pouco e tente novamente:

    ```
    error: Metrics API not available
    ```

    <div style="page-break-after: always"></div>

5. Desabilite o ***addon*** `metrics-server`:

    ```shell
    $ minikube addons disable metrics-server
    ```

    A saída será semelhante a:

    ```
    metrics-server was successfully disabled
    ```

## Limpeza

Agora você pode remover todos os recursos criados no seu cluster:

```shell
$ kubectl delete service hello-node
$ kubectl delete deployment hello-node
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
