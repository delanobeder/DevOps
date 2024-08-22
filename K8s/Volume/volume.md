## DEVOPS

**Prof. Delano M. Beder (UFSCar)**

- - -

#### Servidor Web - NGINX
- - -

```bash
################################################
# Inicializando o Minikube
################################################

$ minikube start --cpus 4 --memory 4096

################################################
# Habilitando o Ingress
################################################

$ minikube image load k8s.gcr.io/ingress-nginx/controller:v1.9.4
$ minikube addons enable ingress

################################################
# Inicializando o DashBoard
################################################

$ minikube addons enable dashboard
$ minikube dashboard &
```

Arquivo **deployment.yaml**

```yaml
apiVersion: apps/v1
kind: Deployment
metadata:
  name: frontend
  labels:
    app: frontend
spec:
  replicas: 1
  selector:
    matchLabels:
      app: frontend
  template:
    metadata:
      labels:
        app: frontend
    spec:
      containers:
        - name: frontend
          image: nginx
          imagePullPolicy: IfNotPresent
          ports:
            - containerPort: 80
```

```bash
$ kubectl apply -f deployment.yaml
```



Arquivo **service.yaml**

```yaml
apiVersion: v1
kind: Service
metadata:
  name: frontend
  labels:
    run: frontend
spec:
  ports:
    - port: 80
      targetPort: 80
      protocol: TCP
  selector:
    app: frontend
```

```bash
$ kubectl apply -f service.yaml
```

Arquivo **ingress.yaml**

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
```bash
$ kubectl apply -f ingress.yaml
```
<div style="page-break-after: always"></div>

```bash
$ minikube ip
192.168.49.2
```

Arquivo **/etc/hosts**

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

Abra um navegador web e acesse http://k8s.local

![image-20240806164402491](/home/delano/.config/Typora/typora-user-images/image-20240806164402491.png)

```bash
$ kubectl get pods
NAME                        READY   STATUS    RESTARTS   AGE
frontend-76db47485d-2hb8m   1/1     Running   0          11m
```

```bash
$ kubectl exec --stdin --tty frontend-76db47485d-2hb8m -- /bin/bash
> echo "<html><body><h1>Teste</h1></body></html>" > /usr/share/nginx/html/index.html
> exit
```

Abra um navegador web e acesse http://k8s.local

![image-20240806164637071](/home/delano/.config/Typora/typora-user-images/image-20240806164637071.png)

```bash
$ kubectl delete pod frontend-76db47485d-2hb8m
```

Abra um navegador web e acesse http://k8s.local e verifique as mudanças no index.html do container foram perdidas.

Realize  a limpeza no cluster

```bash
$ kubectl delete -f ingress.yaml
$ kubectl delete -f service.yaml
$ kubectl delete -f deployment.yaml
```

