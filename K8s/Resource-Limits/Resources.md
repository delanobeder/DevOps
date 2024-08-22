## DEVOPS

**Prof. Delano M. Beder (UFSCar)**

- - -

#### Recursos (*Requests* & *Limits*)
- - -



Material aqui apresentado é baseado nos seguintes links:

https://kubernetes.io/docs/concepts/configuration/manage-resources-containers/

https://kubernetes.io/docs/tasks/configure-pod-container/assign-cpu-resource/

------



# *Metrics-server*


Alguns dos passos descritos nesse material exigem que você execute o **metrics-server** em seu ***cluster***.   Execute o comando para habilitá-lo:

```bash
$ minikube addons enable metrics-server
```

Para verificar que o **metrics-server** está em execução, execute o comando abaixo:

```bash
$ kubectl get apiservices | grep metrics

v1beta1.metrics.k8s.io                 kube-system/metrics-server   True        4d2h
```

Se o **metrics-server** estiver em execução, a saída incluirá uma referência a **v1beta1.metrics.k8s.io** conforme apresentado acima.



# Criação de um *namespace*



Crie um *namespace* para que os recursos criados neste tutorial fiquem isolados do restante do ***cluster***.

```bash
$ kubectl create namespace mem-example
```


<div style="page-break-after: always"></div>

# Especifique um *request* e *limit* de memória para um pod
------



Para especificar um ***request*** de memória para um Container, inclua o campo **resources:requests** no manifesto de recursos do Container. Para especificar um ***limit*** de memória, inclua **resources:limits** . 

No arquivo (**memory-request.yaml**) apresentado abaixo, é a especificação da criação de um Pod que tem apenas um Container que tem um ***request*** de memória de 100 MiB e  um ***limit*** de memória de 200 MiB. 

```yaml
apiVersion: v1
kind: Pod
metadata:
  name: memory-demo
  namespace: mem-example
spec:
  containers:
  - name: memory-demo-ctr
    image: polinux/stress
    resources:
      requests:
        memory: "100Mi"
      limits:
        memory: "200Mi"
    command: ["stress"]
    args: ["--vm", "1", "--vm-bytes", "150M", "--vm-hang", "1"]
```



O trecho **args** no arquivo de configuração fornece argumentos para o Container quando ele inicia. Os argumentos **"--vm-bytes", "150M"** solicitam ao Container para tentar alocar 150 MiB de memória.

Crie o pod, ao executar o comando abaixo:

```bash
$ kubectl apply -f memory-request.yaml
```

Verifique que o Pod está executando:

```bash
$ kubectl get pod memory-demo --namespace=mem-example
```

Veja as informações detalhadas sobre o Pod:

```bash
$ kubectl get pod memory-demo --output=yaml --namespace=mem-example
```

<div style="page-break-after: always"></div>

A saída mostra que o único Container no Pod tem uma solicitação de memória de 100 MiB e um limite de memória de 200 MiB.

```yaml
...
resources:
      limits:
        memory: 200Mi
      requests:
        memory: 100Mi
...
```

Execute `kubectl top` para obter as métricas do Pod:

```bash
$ kubectl top pod memory-demo --namespace=mem-example
```

A saída nos informa que o Pod está utilizando 150MiB. Isso é maior do que o ***request*** de 100 MiB do Pod mas dentro do ***limit*** de 200 MiB do Pod.

```bash
NAME          CPU(cores)   MEMORY(bytes)   
memory-demo   45m          150Mi
```

Remova/delete o Pod:

```bash
$ kubectl delete pod memory-demo --namespace=mem-example
```









# Exceder o limite de memória de um contêiner

------

Um contêiner pode exceder seu ***request*** de memória se o nó (Node) tiver memória disponível. Mas um contêiner não tem permissão para usar mais do que seu ***limit*** de memória. Se um contêiner alocar mais memória do que seu ***limit***, o contêiner se tornará um candidato para encerramento. Se o contêiner continuar a consumir memória além de seu limite, o contêiner será encerrado. Se um contêiner encerrado puder ser reiniciado, o ***kubelet*** o reiniciará, como em qualquer outro tipo de falha em tempo de execução. Neste exemplo, vamos criar um Pod que tenta alocar mais memória do que seu limite. 

No arquivo (**memory-request-2.yaml**) apresentado abaixo, é a especificação da criação de um Pod que tem apenas um Container que tem um ***request*** de memória de 50 MiB e  um ***limit*** de memória de 100 MiB. 

<div style="page-break-after: always"></div>

```yaml
apiVersion: v1
kind: Pod
metadata:
  name: memory-demo-2
  namespace: mem-example
spec:
  containers:
  - name: memory-demo-2-ctr
    image: polinux/stress
    resources:
      requests:
        memory: "50Mi"
      limits:
        memory: "100Mi"
    command: ["stress"]
    args: ["--vm", "1", "--vm-bytes", "250M", "--vm-hang", "1"]
```



O trecho **args** no arquivo de configuração fornece argumentos para o Container quando ele inicia. Os argumentos **"--vm-bytes", "250M"** solicitam ao Container para tentar alocar 250 MiB de memória, que encontra-se bem acima do limit de 100 MiB.

Crie o pod, ao executar o comando abaixo:

```bash
$ kubectl apply -f memory-request-2.yaml
```

Verifique o *status* do Pod

```bash
$ kubectl get pod memory-demo-2 --namespace=mem-example
```

Neste ponto, o Container pode estar em execução ou morto. Repita o comando anterior até que o Container esteja morto:

```bash
NAME            READY   STATUS      RESTARTS      AGE
memory-demo-2   0/1     OOMKilled   2 (16s ago)   20s
```

Veja as informações detalhadas sobre o Pod:

```bash
$ kubectl describe pod memory-demo-2 --namespace=mem-example
```

<div style="page-break-after: always"></div>

A saída mostra que o Container foi morto porque está sem memória (OOM):

```yaml
...
lastState:
      terminated:
        containerID: docker://387f6d1387dadcf5681e9bbdbfb4a70edf55c49de94d8f3a2c459abf5e24c054
        exitCode: 1
        finishedAt: "2024-08-06T17:34:50Z"
        reason: OOMKilled
        startedAt: "2024-08-06T17:34:50Z"
...
```

Remova/delete o Pod:

```bash
$ kubectl delete pod memory-demo-2 --namespace=mem-example
```







# Especifique um *request* de memória que seja muito grande para seus nós (Nodes)

------



***Requests*** e ***limits*** Solicitações e limites de memória são associados a contêineres, mas é útil pensar em um Pod como tendo ***requests*** e ***limits*** de de memória.  A ***request*** de memória para o Pod é a soma das ***requests*** de memória para todos os contêineres presentes no Pod. Analogamente, o limite de memória para o Pod é a soma dos limites de todos os contêineres no Pod. O escalonamento do Pod é baseado em ***requests***. Um Pod é escalonado para ser executado em um nó (Node) somente se o nó tiver memória disponível suficiente para satisfazer a ***request*** de memória do Pod. 

Neste exemplo, vamos criar um Pod que tem um request de memória tão grande que excede a capacidade de qualquer nó (Node) em seu cluster. No arquivo (**memory-request-3.yaml**) apresentado abaixo, é a especificação da criação de um Pod que tem apenas um Container que tem um ***request*** de memória de 100 GiB de memória, o que provavelmente excede a capacidade de qualquer nó (Node) em seu cluster.

<div style="page-break-after: always"></div>

```yaml
apiVersion: v1
kind: Pod
metadata:
  name: memory-demo-3
  namespace: mem-example
spec:
  containers:
    - name: memory-demo-3-ctr
      image: polinux/stress
      resources:
        requests:
          memory: "1000Gi"
        limits:
          memory: "1000Gi"
      command: ["stress"]
      args: ["--vm", "1", "--vm-bytes", "150M", "--vm-hang", "1"]
```

Crie o pod, ao executar o comando abaixo:

```bash
$ kubectl apply -f memory-request-3.yaml
```

Verifique o *status* do Pod

```bash
$ kubectl get pod memory-demo-3 --namespace=mem-example
```

A saída mostra que o status do Pod é PENDING. Ou seja, o Pod não está programado para ser executado em nenhum Node, e permanecerá no estado PENDING indefinidamente:

```bash
NAME            READY   STATUS    RESTARTS   AGE
memory-demo-3   0/1     Pending   0          13s
```

Veja as informações detalhadas sobre o Pod:

```bash
$ kubectl describe pod memory-demo-3 --namespace=mem-example
```

A saída mostra que o Container não pode ser escalonado devido à memória insuficiente:

```yaml
...
Events:
  Type     Reason            Age    From               Message
  ----     ------            ----   ----               -------
  Warning  FailedScheduling  5s     default-scheduler  0/1 nodes are available: 1 Insufficient memory. preemption: 0/1 nodes are available: 1 No preemption victims found for incoming pod..
...
```

Remova/delete o Pod:

```bash
$ kubectl delete pod memory-demo-3 --namespace=mem-example
```

