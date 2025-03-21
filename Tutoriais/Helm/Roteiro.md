## DevOps
**Prof. Delano M. Beder (UFSCar)**

- - -

#### Tutorial Helm
- - -

1. Execute o comando abaixo

   ```bash
   $ helm create hello-world
   ```

   

2. Entre no diretório **hello-world** e remova os seguintes arquivos do diretório **templates**:

   - tests (diretório)
   - hpa.yaml
   - NOTES.txt
   - serviceaccount.yaml

   

3. Ou seja, mantenha apenas os seguintes arquivos no diretório **templates**:

   - _helpers.tpl
   - ingress.yaml
   - deployment.yaml
   - service.yaml

   

4. Atualize o arquivo **templates/ingress.yaml** com o seguinte conteúdo:

   ```yaml
   apiVersion: networking.k8s.io/v1
   kind: Ingress
   metadata:
     name: hello-world-ingress
   spec:
     rules:
     - host: k8s.local
       http:
         paths:
         - path: /
           pathType: Prefix
           backend:
             service:
               name: {{ include "hello-world.name" . }}
               port:
                 number: 80
   ```
<div style="page-break-after: always"></div>
5. Atualize o arquivo **templates/deployment.yaml** com o seguinte conteúdo:

   ```yaml
   apiVersion: apps/v1
   kind: Deployment
   metadata:
     name: {{ include "hello-world.name" . }}
     labels:
       app: {{ include "hello-world.name" . }}
   spec:
     replicas: {{ .Values.replicaCount }}
     selector:
       matchLabels:
         app: {{ include "hello-world.name" . }}
     template:
       metadata:
         labels:
           app: {{ include "hello-world.name" . }}
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

   

6. Atualize o arquivo **templates/service.yaml** com o seguinte conteúdo:

   ```yaml
   apiVersion: v1
   kind: Service
   metadata:
     name: {{ include "hello-world.name" . }}
     labels:
       run: {{ include "hello-world.name" . }}
   spec:
     type: {{ .Values.service.type }}
     ports:
       - port: {{ .Values.service.port }}
         targetPort: http
         protocol: TCP
         name: http
     selector:
       app: {{ include "hello-world.name" . }}
   ```

   <div style="page-break-after: always"></div>

7. Por fim, atualize o arquivo **values.yaml** com o seguinte conteúdo:

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

   

8. Entre no diretório **hello-world** e execute o seguinte comando:

   ```bash
   $ cd hello-world
   $ helm lint .
   ==> Linting .
   [INFO] Chart.yaml: icon is recommended
   
   1 chart(s) linted, 0 chart(s) failed
   ```

   

9. No mesmo diretório, execute o seguinte comando:

   ```bash
   $ helm template .
   ---
   # Source: hello-world/templates/service.yaml
   apiVersion: v1
   kind: Service
   metadata:
     name: hello-world
     labels:
       run: hello-world
   spec:
     type: NodePort
     ports:
       - port: 80
         targetPort: http
         protocol: TCP
         name: http
     selector:
       app: hello-world
   ---
   # Source: hello-world/templates/deployment.yaml
   apiVersion: apps/v1
   kind: Deployment
   metadata:
     name: hello-world
     labels:
       app: hello-world
   spec:
     replicas: 1
     selector:
       matchLabels:
         app: hello-world
     template:
       metadata:
         labels:
           app: hello-world
       spec:
         containers:
           - name: hello-world
             image: "nginxdemos/hello:latest"
             imagePullPolicy: IfNotPresent
             ports:
               - name: http
                 containerPort: 80
                 protocol: TCP
   ---
   # Source: hello-world/templates/ingress.yaml
   apiVersion: networking.k8s.io/v1
   kind: Ingress
   metadata:
     name: hello-world-ingress
   spec:
     rules:
     - host: k8s.local
       http:
         paths:
         - path: /
           pathType: Prefix
           backend:
             service:
               name: hello-world
               port:
                 number: 80
   ```

   

10. No mesmo diretório, execute o seguinte comando:

   ```bash
   $ helm install hello-world .
   NAME: hello-world
   LAST DEPLOYED: Sun Mar  9 11:47:06 2025
   NAMESPACE: default
   STATUS: deployed
   REVISION: 1
   TEST SUITE: None
   ```

   

11. Acesse a url http://k8s.local (através de um navegador)

    <div style="page-break-after: always"></div>

12. Atualize o arquivo **values.yaml** com o seguinte conteúdo:

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

    

13. Execute o seguinte comando e acesse novamente a url http://k8s.local

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

    

14. Execute o seguinte comando e acesse novamente a url http://k8s.local

    ```bash
    $ helm rollback hello-world 1
    Rollback was a success! Happy Helming!
    ```

    

15. Por fim, no mesmo diretório, execute o seguinte comando:

    ```bash
    $ helm uninstall hello-world
    release "hello-world" uninstalled
    ```

    

16. Fim