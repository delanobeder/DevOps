## DevOps
**Prof. Delano M. Beder (UFSCar)**

- - -

#### Exemplo de uma aplicação composta de 3 serviços: NodeJs (Vue.js) + SpringMVC + MySQL

##### Roteiro de implantação no MiniKube

- - -

##### 1. Criar um cluster do minikube

```shell
$ minikube start

################################################
# Habilitando o Ingress
################################################

$ minikube image load k8s.gcr.io/ingress-nginx/controller:v1.9.4

$ minikube addons enable ingress

$ minikube addons enable dashboard

# Deixe este comando rodando (background).
$ minikube dashboard &
```
----

##### 2. Build das imagens necessárias (a partir do código-fonte)

2.1. Clone o repositório https://github.com/delanobeder/DevOps

2.2. Entre no diretório **Exemplos/Contatos**

2.3. Entre no diretório **backend** e faça o **build** da imagem (***contatos/backend***)

   Obs: O arquivo **pom.xml**, presente nesse diretório, foi configurado para compilar e fazer o **build** da imagem (***contatos/backend***).

   ```bash
   $ cd backend
   $ mvn package
   $ cd ..
   ```

2.4. Entre no diretório **frontend** e faça o **build** da imagem (***contatos/frontend***)

   ```bash
   $ cd frontend
   $ docker build . -t contatos/frontend
   $ cd ..
   ```
<div style="page-break-after: always"></div>
----
##### 3. Configuração do serviço contatos-db (MySQL)

###### 3.1. Persistência de dados do serviço

3.1.1. Crie o **PersistentVolume** (arquivo **K8s/db/pv.yaml**)

```yaml
apiVersion: v1
kind: PersistentVolume
metadata:
  name: contatos-pv-volume
  labels:
    type: local
spec:
  storageClassName: manual
  capacity:
    storage: 2Gi
  accessModes:
    - ReadWriteOnce
  hostPath:
    path: "/mnt/data/contatos"
    type: DirectoryOrCreate
```

3.1.2. Crie o **PersistentVolumeClaim** (arquivo **K8s/db/pvc.yaml**)

   ```yaml
   apiVersion: v1
   kind: PersistentVolumeClaim
   metadata:
     name: contatos-pv-claim
   spec:
     storageClassName: manual
     accessModes:
       - ReadWriteOnce
     resources:
       requests:
         storage: 2Gi
   ```

3.1.3. No diretório **K8s/db**, execute o comando abaixo:

```bash
$ kubectl create -f pv.yaml; kubectl create -f pvc.yaml
```

<div style="page-break-after: always"></div>

###### 3.2. Arquivos de configuração (variáveis de ambiente do serviço)

3.2.1.  Crie o **ConfigMap** (arquivo **K8s/db/configmap.yaml**)

```yaml
kind: ConfigMap 
apiVersion: v1 
metadata:
  name: contatos-db-configmap
data:
  database_url: jdbc:mysql://contatos-db:3306/Contatos?createDatabaseIfNotExist=true
```

3.2.2. Crie o **Secret** (arquivo **K8s/db/secret.yaml**)

```yaml
apiVersion: v1
kind: Secret
metadata:
  name: contatos-db-secret
type: kubernetes.io/basic-auth
data:
  username: cm9vdA==
  password: cm9vdA==
```

3.2.3. No diretório **K8s/db**, execute o comando abaixo:

```bash
$ kubectl apply -f configmap.yaml; kubectl apply -f secret.yaml 
```



<div style="page-break-after: always"></div>

###### 3.3. Implantação do serviço no K8s (Minikube)

3.3.1. Crie o **Deployment** (arquivo **K8s/db/deployment.yaml**)

```yaml
apiVersion: apps/v1
kind: Deployment
metadata:
  name: contatos-db
  labels:
    app: contatos-db
spec:
  selector:
    matchLabels:
      app: contatos-db
  strategy:
    type: Recreate
  template:
    metadata:
      labels:
        app: contatos-db
    spec:
      containers:
      - name: contatos-db
        image: mysql:5.7
        imagePullPolicy: IfNotPresent
        ports:
          - containerPort: 3306
        env:
          - name: MYSQL_ROOT_PASSWORD 
            valueFrom:
              secretKeyRef:
                name: contatos-db-secret
                key: password
        resources:
          requests:
            memory: "1Gi"
            cpu: "1"
          limits:
            memory: "2Gi"
            cpu: "2"
        volumeMounts:
          - name: contatos-persistent-storage
            mountPath: /var/lib/mysql
      volumes:
        - name: contatos-persistent-storage
          persistentVolumeClaim:
            claimName: contatos-pv-claim
```

<div style="page-break-after: always"></div>

3.3.2. Crie o **Service** (arquivo **K8s/db/service,yaml**)

```yaml
apiVersion: v1
kind: Service
metadata:
  name: contatos-db
  labels:
    run: contatos-db
spec:
  ports:
    - port: 3306
      targetPort: 3306
      protocol: TCP
  selector:
    app: contatos-db
```
3.3.3. No diretório **K8s/db**, execute o comando abaixo:

```bash
$ kubectl apply -f deployment.yaml; kubectl apply -f service.yaml 
```

<div style="page-break-after: always"></div>

----

##### 4. Configuração do serviço backend (SpringMVC)

###### 4.1. Implantação do serviço no K8s (Minikube)

4.1.1. Crie o **Deployment** (arquivo **K8s/backend/deployment.yaml**)

```yaml
apiVersion: apps/v1
kind: Deployment
metadata:
  name: backend
  labels:
    app: backend
spec:
  selector:
    matchLabels:
      app: backend
  template:
    metadata:
      labels:
        app: backend
    spec:
      containers:
        - name: backend
          image: contatos/backend:latest
          imagePullPolicy: IfNotPresent
          ports:
            - containerPort: 8081
          env:
            - name: MYSQL_URL
              valueFrom:
                configMapKeyRef:
                  name: contatos-db-configmap
                  key: database_url
            - name: MYSQL_USER
              valueFrom:
                secretKeyRef:
                  name: contatos-db-secret
                  key: username
            - name: MYSQL_PASSWORD 
              valueFrom:
                secretKeyRef:
                  name: contatos-db-secret
                  key: password
          resources:
            requests:
              memory: "1Gi"
              cpu: "1"
            limits:
              memory: "2Gi"
              cpu: "2"
```

<div style="page-break-after: always"></div>

4.1.2. Crie o **Service** (arquivo **K8s/backend/service,yaml**)

```yaml
apiVersion: v1
kind: Service
metadata:
  name: backend
  labels:
    run: backend
spec:
  ports:
    - port: 8081
      targetPort: 8081
      protocol: TCP
  selector:
    app: backend
```
4.1.3. No diretório **K8s/backend**, execute o comando abaixo:

```bash
$ kubectl apply -f deployment.yaml; kubectl apply -f service.yaml 
```

<div style="page-break-after: always"></div>

----

##### 5. Configuração do serviço frontend (NodeJS)

###### 5.1. Implantação do serviço no K8s (Minikube)

5.1.1. Crie o **Deployment** (arquivo **K8s/frontend/deployment.yaml**)

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
          image: contatos/frontend:latest
          imagePullPolicy: IfNotPresent
          ports:
            - containerPort: 80
          resources:
            requests:
              memory: "1Gi"
              cpu: "1"
            limits:
              memory: "2Gi"
              cpu: "2"
```

<div style="page-break-after: always"></div>

5.1.2. Crie o **Service** (arquivo **K8s/frontend/service,yaml**)

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
5.1.3. No diretório **K8s/frontend**, execute o comando abaixo:

```bash
$ kubectl apply -f deployment.yaml; kubectl apply -f service.yaml 
```

<div style="page-break-after: always"></div>
----
###### 6. Acessando a aplicação

6.1. Crie o arquivo **K8s/ingress.yaml** com o conteúdo abaixo

```yaml
apiVersion: networking.k8s.io/v1
kind: Ingress
metadata:
  name: gateway-ingress
spec:
  rules:
  - host: contatos.k8s.local
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

6.2. No diretório **K8s**, execute o comando abaixo:

```shell
$ kubectl apply -f ingress.yaml
```

6.3. Configure o arquivo **/etc/hosts**. Note que o IP 192.168.49.2 é o resultado da invocação do comando `minikube ip`.

```bash
127.0.0.1	localhost
127.0.1.1	mundau
192.168.49.2    contatos.k8s.local
```
6.4. Abra um terminal e acesse http://contatos.k8s.local