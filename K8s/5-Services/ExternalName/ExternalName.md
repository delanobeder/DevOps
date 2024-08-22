## DEVOPS

**Prof. Delano M. Beder (UFSCar)**

- - -

#### Serviço ExternalName
- - -

Este tutorial demonstra como executar uma aplicação exemplo, que contém um serviço do tipo **ExternalName**, no Kubernetes utilizando o minikube. 

**ClusterIP:**  Mapeia o serviço para o conteúdo do campo **externalName** (host externo) presente na configuração do serviço. O mapeamento configura o servidor DNS do seu cluster para retornar um registro CNAME com esse valor de nome de host externo. Nenhum proxy de qualquer tipo é configurado.

Material adicional: https://kubernetes.io/docs/concepts/services-networking/service/

------

## Criar um cluster do minikube e abrir o *dashboard*

```shell
$ minikube start

################################################
# Habilitando o Ingress
################################################
$ minikube image load k8s.gcr.io/ingress-nginx/controller:v1.9.4
$ minikube addons enable ingress

################################################
# Abrindo o dashboard
################################################
$ minikube addons enable dashboard
# Deixe este comando rodando (background).
$ minikube dashboard &
```

## Criando um Serviço ExternalName

Crie o arquivo **service.yaml** com o conteúdo abaixo

   ```yaml
   apiVersion: v1
   kind: Service
   metadata:
     name: frontend
   spec:
     type: ExternalName
     externalName: www.ufscar.br
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
NAME         TYPE           CLUSTER-IP   EXTERNAL-IP     PORT(S)   AGE
frontend     ExternalName   <none>       www.ufscar.br   <none>    67s
kubernetes   ClusterIP      10.96.0.1    <none>          443/TCP   16m
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



Abra um navegador e acesse http://k8s.local. Ao clicar no link, você será direcionado para a url: https://www.ufscar.br/



![image-20240817113952472](/home/delano/.config/Typora/typora-user-images/image-20240817113952472.png)


## Limpeza

Agora você pode remover todos os recursos criados no seu cluster:

```shell
$ kubectl delete -f ingress.yaml
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
