## DEVOPS

**Prof. Delano M. Beder (UFSCar)**

- - -

#### Servidor Web - NGINX (Volume)
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

Arquivo **pv.yaml**

```yaml
apiVersion: v1
kind: PersistentVolume
metadata:
  name: pv-volume
  labels:
    type: local
spec:
  storageClassName: manual
  capacity:
    storage: 2Gi
  accessModes:
    - ReadWriteOnce
  hostPath:
    path: "/mnt/html"
    type: DirectoryOrCreate
```

```bash
$ kubectl apply -f pv.yaml
```
<div style="page-break-after: always"></div>

Arquivo **pvc.yaml**

```yaml
apiVersion: v1
kind: PersistentVolumeClaim
metadata:
  name: pv-claim
spec:
  storageClassName: manual
  accessModes:
    - ReadWriteOnce
  resources:
    requests:
      storage: 1Gi
```

```bash
$ kubectl apply -f pvc.yaml
```

Arquivo **deployment-2.yaml**

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
          volumeMounts:
            - name: persistent-storage
              mountPath: /usr/share/nginx/html
      volumes:
        - name: persistent-storage
          persistentVolumeClaim:
          claimName: pv-claim
```

```bash
$ kubectl apply -f deployment-2.yaml
```

<div style="page-break-after: always"></div>

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

```bash
$ kubectl get pods
NAME                        READY   STATUS    RESTARTS   AGE
frontend-55b7b68766-8jhj6   1/1     Running   0          12m
```

```bash
$ kubectl exec --stdin --tty frontend-55b7b68766-8jhj6 -- /bin/bash
> echo "<html><body><h1>Teste</h1></body></html>" > /usr/share/nginx/html/index.html
> exit
```

Abra um navegador web e acesse http://k8s.local

![image-20240806164637071](/home/delano/.config/Typora/typora-user-images/image-20240806164637071.png)

```bash
$ kubectl delete pod frontend-55b7b68766-8jhj6
```

```bash
$ kubectl get pods
NAME                        READY   STATUS    RESTARTS   AGE
frontend-55b7b68766-vjdtv   1/1     Running   0          47s
```



Abra um navegador web e acesse http://k8s.local e verifique as mudanças no index.html do container não foram perdidas.



Realize  a limpeza no cluster

```bash
$ kubectl delete -f ingress.yaml
$ kubectl delete -f service.yaml
$ kubectl delete -f deployment.yaml
```
