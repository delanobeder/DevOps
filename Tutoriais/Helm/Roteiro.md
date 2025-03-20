## DevOps
**Prof. Delano M. Beder (UFSCar)**

- - -

#### Tutorial Helm
- - -

1. Execute o comando abaixo

   ```bash
   $ helm create hello-world

2. Entre no diretório **hello-world** e remova os seguintes arquivos do diretório **templates**:

   - tests (diretório)

   - hpa.yaml

   - ingress.yaml

   - NOTES.txt

   - serviceaccount.yaml

3. Ou seja, mantenha apenas os seguintes arquivos no diretório **templates**:

   - _helpers.tpl
   - deployment.yaml
   - service.yaml

4. Atualize o arquivo **templates/deployment.yaml** com o seguinte conteúdo:

   ```yaml
   apiVersion: apps/v1
   kind: Deployment
   metadata:
     name: {{ include "hello-world.fullname" . }}
     labels:
       app.kubernetes.io/name: {{ include "hello-world.name" . }}
       helm.sh/chart: {{ include "hello-world.chart" . }}
   spec:
     replicas: {{ .Values.replicaCount }}
     selector:
       matchLabels:
         app.kubernetes.io/name: {{ include "hello-world.name" . }}
     template:
       metadata:
         labels:
           app.kubernetes.io/name: {{ include "hello-world.name" . }}
       spec:
         containers:
           - name: {{ .Chart.Name }}
             image: "{{ .Values.image.repository }}:{{ .Values.image.tag }}"
             imagePullPolicy: {{ .Values.image.pullPolicy }}
             ports:
               - name: http
                 containerPort: 80
                 protocol: TCP
   ```
   
5. Atualize o arquivo **templates/service.yaml** com o seguinte conteúdo:

   ```yaml
   apiVersion: v1
   kind: Service
   metadata:
     name: {{ include "hello-world.fullname" . }}
     labels:
       app.kubernetes.io/name: {{ include "hello-world.name" . }}
       helm.sh/chart: {{ include "hello-world.chart" . }}
   spec:
     type: {{ .Values.service.type }}
     ports:
       - port: {{ .Values.service.port }}
         targetPort: http
         nodePort: 30000
         protocol: TCP
         name: http
     selector:
       app.kubernetes.io/name: {{ include "hello-world.name" . }}
   ```
   
6. Por fim, atualize o arquivo **values.yaml** com o seguinte conteúdo:

   ```yaml
   replicaCount: 1
   image:
     repository: "nginxdemos/hello"
     tag: "latest"
     pullPolicy: IfNotPresent
   service:
     type: NodePort
     port: 80
   ```

7. Entre no diretório **hello-world** e execute o seguinte comando:

   ```bash
   $ cd hello-world
   $ helm lint .
   ==> Linting .
   [INFO] Chart.yaml: icon is recommended
   
   1 chart(s) linted, 0 chart(s) failed
   ```
<div style="page-break-after: always"></div>

8. No mesmo diretório, execute o seguinte comando:

   ```bash
   $ helm template .
   ---
   # Source: hello-world/templates/service.yaml
   apiVersion: v1
   kind: Service
   metadata:
     name: release-name-hello-world
     labels:
       app.kubernetes.io/name: hello-world
       helm.sh/chart: hello-world-0.1.0
   spec:
     type: NodePort
     ports:
       - port: 80
         targetPort: http
         nodePort: 30000
         protocol: TCP
         name: http
     selector:
       app.kubernetes.io/name: hello-world
   ---
   # Source: hello-world/templates/deployment.yaml
   apiVersion: apps/v1
   kind: Deployment
   metadata:
     name: release-name-hello-world
     labels:
       app.kubernetes.io/name: hello-world
       helm.sh/chart: hello-world-0.1.0
   spec:
     replicas: 1
     selector:
       matchLabels:
         app.kubernetes.io/name: hello-world
     template:
       metadata:
         labels:
           app.kubernetes.io/name: hello-world
       spec:
         containers:
           - name: hello-world
             image: "nginxdemos/hello:latest"
             imagePullPolicy: IfNotPresent
             ports:
               - name: http
                 containerPort: 80
                 protocol: TCP
   ```

   <div style="page-break-after: always"></div>

9. No mesmo diretório, execute o seguinte comando:

   ```bash
   $ helm install hello-world .
   NAME: hello-world
   LAST DEPLOYED: Sun Mar  9 11:47:06 2025
   NAMESPACE: default
   STATUS: deployed
   REVISION: 1
   TEST SUITE: None
   ```

10. Execute o seguinte comando e acesse a url http://192.168.49.2:30000 (através de um navegador)

    ```bash
    $ minikube service hello-world
    ```

11. Atualize o arquivo values.yaml com o seguinte conteúdo:

    ```yaml
    replicaCount: 1
    image:
      repository: "nginxdemos/hello"
      tag: "plain-text"
      pullPolicy: IfNotPresent
    service:
      type: NodePort
      port: 80
    ```

12. Execute o seguinte comando e acesse novamente a url http://192.168.49.2:30000

    ```bash
    $ helm upgrade hello-world .
    Release "hello-world" has been upgraded. Happy Helming!
    NAME: hello-world
    LAST DEPLOYED: Sun Mar  9 12:03:20 2025
    NAMESPACE: default
    STATUS: deployed
    REVISION: 2
    TEST SUITE: None
    ```

13. Execute o seguinte comando e acesse novamente a url http://192.168.49.2:30000

    ```bash
    $ helm rollback hello-world 1
    Rollback was a success! Happy Helming!
    ```

14. Por fim, no mesmo diretório, execute o seguinte comando:

    ```bash
    $ helm uninstall hello-world
    release "hello-world" uninstalled
    ```

15. Fim