



Material here presented is based on the followings links:

https://kubernetes.io/docs/concepts/workloads/controllers/deployment/

https://www.baeldung.com/ops/deployment-rollout-kubernetes

------

In Kubernetes, the Deploymet resource is a declarative approach for managing the Pod and ReplicaSet resources. Specifically, we define the desired state of the application using DeploymentSpec. Then, the Deployment controller constantly works and monitors to ensure the actual state is as expected. At a minimum, Deployment for an application would define the number of replicas as well as the pod template.

## Creating a Deployment

The following file (nginx-deployment.yaml) is an example of a Deployment. It creates a ReplicaSet to bring up three `nginx` Pods:

```yaml
apiVersion: apps/v1
kind: Deployment
metadata:
  name: nginx-deployment
  labels:
    app: nginx
spec:
  replicas: 3
  selector:
    matchLabels:
      app: nginx
  template:
    metadata:
      labels:
        app: nginx
    spec:
      containers:
      - name: nginx
        image: nginx:1.14.2
        ports:
        - containerPort: 80
```



In this example:

* A Deployment named `nginx-deployment` is created, indicated by the`.metadata.name` field. This name will become the basis for the ReplicaSets and Pods which are created later. 

* The Deployment creates a ReplicaSet that creates three replicated Pods, indicated by the `.spec.replicas` field.

* The `.spec.selector` field defines how the created ReplicaSet finds which Pods to manage. In this case, you select a label that is defined in the Pod template (`app: nginx`). However, more sophisticated selection rules are possible, as long as the Pod template itself satisfies the rule.
  
  
  
* The `template` field contains the following sub-fields:
  * The Pods are labeled `app: nginx` using the `.metadata.labels` field.
  * The Pod template's specification, or `.template.spec` field, indicates that the Pods run one container, `nginx`, which runs the nginx Docker Hub image at version 1.14.2.
  * Create one container and name it `nginx` using the `.spec.template.spec.containers[0].name` field.

Before you begin, make sure your Kubernetes cluster is up and running.

Follow the steps given below to create the above Deployment:

1. Create the Deployment by running the followings commands:

   ```shell
   $ kubectl apply -f nginx-deployment.yaml
   ```

2. Annotate our *Deployment* resource

   ```sh
   $ kubectl annotate deployment/nginx-deployment kubernetes.io/change-cause="Set image to 1.14.2"
   ```

3. Run `kubectl get deployments` to check if the Deployment was created.

   ```sh
   $ kubectl get deployments
   ```

   If the Deployment is still being created, the output is similar to the following:
   ```
   NAME               READY   UP-TO-DATE   AVAILABLE   AGE
   nginx-deployment   0/3     0            0           1s
   ```
   When you inspect the Deployments in your cluster, the following fields are displayed:
   * `NAME` lists the names of the Deployments in the namespace.
   * `READY` displays how many replicas of the application are available to your users. It follows the pattern ready/desired.
   * `UP-TO-DATE` displays the number of replicas that have been updated to achieve the desired state.
   * `AVAILABLE` displays how many replicas of the application are available to your users.
   * `AGE` displays the amount of time that the application has been running.

   Notice how the number of desired replicas is 3 according to `.spec.replicas` field.

4. To see the Deployment rollout status, run `kubectl rollout status deployment/nginx-deployment`.

   ```sh
   $ kubectl rollout status deployment/nginx-deployment
   ```

   The output is similar to:
   ```
   Waiting for rollout to finish: 2 out of 3 new replicas have been updated...
   deployment "nginx-deployment" successfully rolled out
   ```

5. Run the `kubectl get deployments` again a few seconds later.

   ```sh
   $ kubectl get deployments
   ```

   The output is similar to this:

   ```
   NAME               READY   UP-TO-DATE   AVAILABLE   AGE
   nginx-deployment   3/3     3            3           18s
   ```
   Notice that the Deployment has created all three replicas, and all replicas are up-to-date (they contain the latest Pod template) and available.

6. To see the ReplicaSet (`rs`) created by the Deployment, run `kubectl get rs`. 

   ```sh
   $ kubectl get rs
   ```

   The output is similar to this:

   ```
   NAME                          DESIRED   CURRENT   READY   AGE
   nginx-deployment-75675f5897   3         3         3       18s
   ```
   ReplicaSet output shows the following fields:

   * `NAME` lists the names of the ReplicaSets in the namespace.
   * `DESIRED` displays the desired number of _replicas_ of the application, which you define when you create the Deployment. This is the _desired state_.
   * `CURRENT` displays how many replicas are currently running.
   * `READY` displays how many replicas of the application are available to your users.
   * `AGE` displays the amount of time that the application has been running.

   Notice that the name of the ReplicaSet is always formatted as `[DEPLOYMENT-NAME]-[HASH]`. This name will become the basis for the Pods   which are created. The `HASH` string is the same as the `pod-template-hash` label on the ReplicaSet.

7. To see the labels automatically generated for each Pod, run `kubectl get pods --show-labels`.

   ```sh
   $ kubectl get pods --show-labels
   ```

   The output is similar to:

   ```
   NAME                                READY     STATUS    RESTARTS   AGE       LABELS
   nginx-deployment-75675f5897-7ci7o   1/1       Running   0          18s       app=nginx,pod-template-hash=75675f5897
   nginx-deployment-75675f5897-kzszj   1/1       Running   0          18s       app=nginx,pod-template-hash=75675f5897
   nginx-deployment-75675f5897-qqcnn   1/1       Running   0          18s       app=nginx,pod-template-hash=75675f5897
   ```
   The created ReplicaSet ensures that there are three `nginx` Pods.

## Updating a Deployment

A Deployment's rollout is triggered if and only if the Deployment's Pod template (that is, `.spec.template`) is changed, for example if the labels or container images of the template are updated. Other updates, such as scaling the Deployment, do not trigger a rollout.

Follow the steps given below to update your Deployment:

1. Let's update the nginx Pods to use the `nginx:1.16.1` image instead of the `nginx:1.14.2` image.

   ```shell
   $ kubectl set image deployment/nginx-deployment nginx=nginx:1.16.1
   ```

   where `deployment/nginx-deployment` indicates the Deployment, `nginx` indicates the Container the update will take place and `nginx:1.16.1` indicates the new image and its tag.


   The output is similar to:

   ```
   deployment.apps/nginx-deployment image updated
   ```

   Alternatively, you can `edit` the Deployment and change `.spec.template.spec.containers[0].image` from `nginx:1.14.2` to `nginx:1.16.1`:

   ```shell
   $ kubectl edit deployment/nginx-deployment
   ```

   The output is similar to:

   ```
   deployment.apps/nginx-deployment edited
   ```

2. Annotate our *Deployment* resource

   ``` shell
   $ kubectl annotate deployment/nginx-deployment kubernetes.io/change-cause="Update image to 1.16.1"
   ```

2. To see the rollout status, run:

   ```shell
   $ kubectl rollout status deployment/nginx-deployment
   ```

   The output is similar to this:

   ```
   Waiting for rollout to finish: 2 out of 3 new replicas have been updated...
   ```

   or

   ```
   deployment "nginx-deployment" successfully rolled out
   ```

Get more details on your updated Deployment:

* After the rollout succeeds, you can view the Deployment by running `kubectl get deployments`. 
  
  ```sh
  $ kubectl get deployments
  ```
  
  The output is similar to this:
  
  ```ini
  NAME               READY   UP-TO-DATE   AVAILABLE   AGE
  nginx-deployment   3/3     3            3           36s
  ```
  
* Run `kubectl get rs` to see that the Deployment updated the Pods by creating a new ReplicaSet and scaling it
up to 3 replicas, as well as scaling down the old ReplicaSet to 0 replicas.

  ```shell
  $ kubectl get rs
  ```

  The output is similar to this:
  ```
  NAME                          DESIRED   CURRENT   READY   AGE
  nginx-deployment-1564180365   3         3         3       6s
  nginx-deployment-2035384211   0         0         0       36s
  ```

* Running `get pods` should now show only the new Pods:

  ```shell
  $ kubectl get pods
  ```

  The output is similar to this:
  ```
  NAME                                READY     STATUS    RESTARTS   AGE
  nginx-deployment-1564180365-khku8   1/1       Running   0          14s
  nginx-deployment-1564180365-nacti   1/1       Running   0          14s
  nginx-deployment-1564180365-z9gth   1/1       Running   0          14s
  ```

  Next time you want to update these Pods, you only need to update the Deployment's Pod template again.

  Deployment ensures that only a certain number of Pods are down while they are being updated. By default, it ensures that at least 75% of the desired number of Pods are up (25% max unavailable).
  

Deployment also ensures that only a certain number of Pods are created above the desired number of Pods. By default, it ensures that at most 125% of the desired number of Pods are up (25% max surge).

  For example, if you look at the above Deployment closely, you will see that it first creates a new Pod, then deletes an old Pod, and creates another new one. It does not kill old Pods until a sufficient number of new Pods have come up, and does not create new Pods until a sufficient number of old Pods have been killed. It makes sure that at least 3 Pods are available and that at max 4 Pods in total are available. In case of a Deployment with 4 replicas, the number of Pods would be between 3 and 5.

* Get details of your Deployment:
  ```shell
  $ kubectl describe deployments
  ```
  The output is similar to this:
  ```
  Name:                   nginx-deployment
  Namespace:              default
  CreationTimestamp:      Thu, 30 Nov 2017 10:56:25 +0000
  Labels:                 app=nginx
  Annotations:            deployment.kubernetes.io/revision=2
  Selector:               app=nginx
  Replicas:               3 desired | 3 updated | 3 total | 3 available | 0 unavailable
  StrategyType:           RollingUpdate
  MinReadySeconds:        0
  RollingUpdateStrategy:  25% max unavailable, 25% max surge
  Pod Template:
    Labels:  app=nginx
     Containers:
      nginx:
        Image:        nginx:1.16.1
        Port:         80/TCP
        Environment:  <none>
        Mounts:       <none>
      Volumes:        <none>
    Conditions:
      Type           Status  Reason
      ----           ------  ------
      Available      True    MinimumReplicasAvailable
      Progressing    True    NewReplicaSetAvailable
    OldReplicaSets:  <none>
    NewReplicaSet:   nginx-deployment-1564180365 (3/3 replicas created)
    Events:
      Type    Reason             Age   From                   Message
      ----    ------             ----  ----                   -------
      Normal  ScalingReplicaSet  2m    deployment-controller  Scaled up replica set nginx-deployment-2035384211 to 3
      Normal  ScalingReplicaSet  24s   deployment-controller  Scaled up replica set nginx-deployment-1564180365 to 1
      Normal  ScalingReplicaSet  22s   deployment-controller  Scaled down replica set nginx-deployment-2035384211 to 2
      Normal  ScalingReplicaSet  22s   deployment-controller  Scaled up replica set nginx-deployment-1564180365 to 2
      Normal  ScalingReplicaSet  19s   deployment-controller  Scaled down replica set nginx-deployment-2035384211 to 1
      Normal  ScalingReplicaSet  19s   deployment-controller  Scaled up replica set nginx-deployment-1564180365 to 3
      Normal  ScalingReplicaSet  14s   deployment-controller  Scaled down replica set nginx-deployment-2035384211 to 0
  ```
  Here you see that when you first created the Deployment, it created a ReplicaSet (nginx-deployment-2035384211) and scaled it up to 3 replicas directly. When you updated the Deployment, it created a new ReplicaSet (nginx-deployment-1564180365) and scaled it up to 1 and waited for it to come up. Then it scaled down the old ReplicaSet to 2 and scaled up the new ReplicaSet to 2 so that at least 3 Pods were available and at most 4 Pods were created at all times. It then continued scaling up and down the new and the old ReplicaSet, with the same rolling update strategy.
  Finally, you'll have 3 available replicas in the new ReplicaSet, and the old ReplicaSet is scaled down to 0.



# Rollover (aka multiple updates in-flight)

Each time a new Deployment is observed by the Deployment controller, a ReplicaSet is created to bring up the desired Pods. If the Deployment is updated, the existing ReplicaSet that controls Pods whose labels match `.spec.selector` but whose template does not match `.spec.template` are scaled down. Eventually, the new ReplicaSet is scaled to `.spec.replicas` and all old ReplicaSets is scaled to 0.

If you update a Deployment while an existing rollout is in progress, the Deployment creates a new ReplicaSet as per the update and start scaling that up, and rolls over the ReplicaSet that it was scaling up previously -- it will add it to its list of old ReplicaSets and start scaling it down.

For example, suppose you create a Deployment to create 5 replicas of `nginx:1.14.2`, but then update the Deployment to create 5 replicas of `nginx:1.16.1`, when only 3 replicas of `nginx:1.14.2` had been created. In that case, the Deployment immediately starts killing the 3 `nginx:1.14.2` Pods that it had created, and starts creating `nginx:1.16.1` Pods. It does not wait for the 5 replicas of `nginx:1.14.2` to be created before changing course.

## Rolling Back a Deployment

Sometimes, you may want to rollback a Deployment; for example, when the Deployment is not stable, such as crash looping.
By default, all of the Deployment's rollout history is kept in the system so that you can rollback anytime you want
(you can change that by modifying revision history limit).

* Suppose that you made a typo while updating the Deployment, by putting the image name as `nginx:1.16.2` instead of `nginx:1.16.1`:

  ```shell
  $ kubectl set image deployment/nginx-deployment nginx=nginx:1.16.2
  
  $ kubectl annotate deployment/nginx-deployment kubernetes.io/change-cause="Update image to 1.16.2"
  ```

  The output is similar to this:
  ```
  deployment.apps/nginx-deployment image updated
  ```

* The rollout gets stuck. You can verify it by checking the rollout status:

  ```shell
  $ kubectl rollout status deployment/nginx-deployment
  ```

  The output is similar to this:
  ```
  Waiting for rollout to finish: 1 out of 3 new replicas have been updated...
  ```

* Press Ctrl-C to stop the above rollout status watch. 

* You see that the number of old replicas (adding the replica count from`nginx-deployment-1564180365` and `nginx-deployment-2035384211`) is 3, and the number of new replicas (from `nginx-deployment-3066724191`) is 1.
  
  ```shell
  $ kubectl get rs
  ```
  
  The output is similar to this:
  ```
  NAME                          DESIRED   CURRENT   READY   AGE
  nginx-deployment-1564180365   3         3         3       25s
  nginx-deployment-2035384211   0         0         0       36s
  nginx-deployment-3066724191   1         1         0       6s
  ```
  
* Looking at the Pods created, you see that 1 Pod created by new ReplicaSet is stuck in an image pull loop.

  ```shell
  $ kubectl get pods
  ```

  The output is similar to this:
  ```
  NAME                                READY     STATUS             RESTARTS   AGE
  nginx-deployment-1564180365-70iae   1/1       Running            0          25s
  nginx-deployment-1564180365-jbqqo   1/1       Running            0          25s
  nginx-deployment-1564180365-hysrc   1/1       Running            0          25s
  nginx-deployment-3066724191-08mng   0/1       ImagePullBackOff   0          6s
  ```

  
  
* Get the description of the Deployment:
  ```shell
  $ kubectl describe deployment
  ```

  The output is similar to this:
  ```
  Name:           nginx-deployment
  Namespace:      default
  CreationTimestamp:  Tue, 15 Mar 2016 14:48:04 -0700
  Labels:         app=nginx
  Selector:       app=nginx
  Replicas:       3 desired | 1 updated | 4 total | 3 available | 1 unavailable
  StrategyType:       RollingUpdate
  MinReadySeconds:    0
  RollingUpdateStrategy:  25% max unavailable, 25% max surge
  Pod Template:
    Labels:  app=nginx
    Containers:
     nginx:
      Image:        nginx:1.161
      Port:         80/TCP
      Host Port:    0/TCP
      Environment:  <none>
      Mounts:       <none>
    Volumes:        <none>
  Conditions:
    Type           Status  Reason
    ----           ------  ------
    Available      True    MinimumReplicasAvailable
    Progressing    True    ReplicaSetUpdated
  OldReplicaSets:     nginx-deployment-1564180365 (3/3 replicas created)
  NewReplicaSet:      nginx-deployment-3066724191 (1/1 replicas created)
  Events:
    FirstSeen LastSeen    Count   From                    SubObjectPath   Type        Reason              Message
    --------- --------    -----   ----                    -------------   --------    ------              -------
    1m        1m          1       {deployment-controller }                Normal      ScalingReplicaSet   Scaled up replica set nginx-deployment-2035384211 to 3
    22s       22s         1       {deployment-controller }                Normal      ScalingReplicaSet   Scaled up replica set nginx-deployment-1564180365 to 1
    22s       22s         1       {deployment-controller }                Normal      ScalingReplicaSet   Scaled down replica set nginx-deployment-2035384211 to 2
    22s       22s         1       {deployment-controller }                Normal      ScalingReplicaSet   Scaled up replica set nginx-deployment-1564180365 to 2
    21s       21s         1       {deployment-controller }                Normal      ScalingReplicaSet   Scaled down replica set nginx-deployment-2035384211 to 1
    21s       21s         1       {deployment-controller }                Normal      ScalingReplicaSet   Scaled up replica set nginx-deployment-1564180365 to 3
    13s       13s         1       {deployment-controller }                Normal      ScalingReplicaSet   Scaled down replica set nginx-deployment-2035384211 to 0
    13s       13s         1       {deployment-controller }                Normal      ScalingReplicaSet   Scaled up replica set nginx-deployment-3066724191 to 1
  ```

  To fix this, you need to rollback to a previous revision of Deployment that is stable.

### Checking Rollout History of a Deployment

Follow the steps given below to check the rollout history:

1. First, check the revisions of this Deployment:
   ```shell
   $ kubectl rollout history deployment/nginx-deployment
   ```
   The output is similar to this:
   ```
   deployments "nginx-deployment"
   REVISION    CHANGE-CAUSE
   1           Set image to 1.14.2
   2           Update image to 1.16.1
   3           Update image to 1.16.2
   ```

   `CHANGE-CAUSE` is copied from the Deployment annotation `kubernetes.io/change-cause` to its revisions upon creation. You can specify the`CHANGE-CAUSE` message by:

   * Annotating the Deployment with `kubectl annotate deployment/nginx-deployment kubernetes.io/change-cause="<message>"`
   * Manually editing the manifest of the resource.

2. To see the details of each revision, run:
   ```shell
   $ kubectl rollout history deployment/nginx-deployment --revision=2
   ```

   The output is similar to this:
   ```
   deployments "nginx-deployment" revision 2
     Labels:       app=nginx
             pod-template-hash=1159050644
     Annotations:  kubernetes.io/change-cause=kubectl set image deployment/nginx-deployment nginx=nginx:1.16.1
     Containers:
      nginx:
       Image:      nginx:1.16.1
       Port:       80/TCP
        QoS Tier:
           cpu:      BestEffort
           memory:   BestEffort
       Environment Variables:      <none>
     No volumes.
   ```

### Rolling Back to a Previous Revision
Follow the steps given below to rollback the Deployment from the current version to the previous version, which is version 2.

1. Now you've decided to undo the current rollout and rollback to the previous revision:
   ```shell
   $ kubectl rollout undo deployment/nginx-deployment
   ```

   The output is similar to this:
   ```
   deployment.apps/nginx-deployment rolled back
   ```
   Alternatively, you can rollback to a specific revision by specifying it with `--to-revision`:

   ```shell
   $ kubectl rollout undo deployment/nginx-deployment --to-revision=2
   ```

   The output is similar to this:
   ```
   deployment.apps/nginx-deployment rolled back
   ```

   For more details about rollout related commands, read [`kubectl rollout`](/docs/reference/generated/kubectl/kubectl-commands#rollout).

   The Deployment is now rolled back to a previous stable revision. As you can see, a `DeploymentRollback` event for rolling back to revision 2 is generated from Deployment controller.
   
2. Check if the rollback was successful and the Deployment is running as expected, run:
   ```shell
   $ kubectl get deployment nginx-deployment
   ```

   The output is similar to this:
   ```
   NAME               READY   UP-TO-DATE   AVAILABLE   AGE
   nginx-deployment   3/3     3            3           30m
   ```
3. Get the description of the Deployment:
   ```shell
   $ kubectl describe deployment nginx-deployment
   ```
   The output is similar to this:
   ```
   Name:                   nginx-deployment
   Namespace:              default
   CreationTimestamp:      Sun, 02 Sep 2018 18:17:55 -0500
   Labels:                 app=nginx
   Annotations:            deployment.kubernetes.io/revision=4
                           kubernetes.io/change-cause=kubectl set image deployment/nginx-deployment nginx=nginx:1.16.1
   Selector:               app=nginx
   Replicas:               3 desired | 3 updated | 3 total | 3 available | 0 unavailable
   StrategyType:           RollingUpdate
   MinReadySeconds:        0
   RollingUpdateStrategy:  25% max unavailable, 25% max surge
   Pod Template:
     Labels:  app=nginx
     Containers:
      nginx:
       Image:        nginx:1.16.1
       Port:         80/TCP
       Host Port:    0/TCP
       Environment:  <none>
       Mounts:       <none>
     Volumes:        <none>
   Conditions:
     Type           Status  Reason
     ----           ------  ------
     Available      True    MinimumReplicasAvailable
     Progressing    True    NewReplicaSetAvailable
   OldReplicaSets:  <none>
   NewReplicaSet:   nginx-deployment-c4747d96c (3/3 replicas created)
   Events:
     Type    Reason              Age   From                   Message
     ----    ------              ----  ----                   -------
     Normal  ScalingReplicaSet   12m   deployment-controller  Scaled up replica set nginx-deployment-75675f5897 to 3
     Normal  ScalingReplicaSet   11m   deployment-controller  Scaled up replica set nginx-deployment-c4747d96c to 1
     Normal  ScalingReplicaSet   11m   deployment-controller  Scaled down replica set nginx-deployment-75675f5897 to 2
     Normal  ScalingReplicaSet   11m   deployment-controller  Scaled up replica set nginx-deployment-c4747d96c to 2
     Normal  ScalingReplicaSet   11m   deployment-controller  Scaled down replica set nginx-deployment-75675f5897 to 1
     Normal  ScalingReplicaSet   11m   deployment-controller  Scaled up replica set nginx-deployment-c4747d96c to 3
     Normal  ScalingReplicaSet   11m   deployment-controller  Scaled down replica set nginx-deployment-75675f5897 to 0
     Normal  ScalingReplicaSet   11m   deployment-controller  Scaled up replica set nginx-deployment-595696685f to 1
     Normal  DeploymentRollback  15s   deployment-controller  Rolled back deployment "nginx-deployment" to revision 2
     Normal  ScalingReplicaSet   15s   deployment-controller  Scaled down replica set nginx-deployment-595696685f to 0
   ```



### Pausing and Resuming a Deployment Rollout

The *Deployment* controller always monitors and triggers a  rollout whenever it detects any specification drift between the running  pods and the backing definition. In other words, **applying an update to the \*Deployment\* resource causes the controller to instantly perform a rolling update to the pods**.

**To prevent the controller from automatically rolling out the updates, we can issue the \*kubectl rollout pause\* command on our \*Deployment\* resource**. By marking our *Deployment* resource as paused, the controller won’t automatically roll out the changes on the *Deployment* level to the pods.

For example, let’s pause our *nginx-deployment* using *kubectl rollout pause*:

```bash
$ kubectl rollout pause deployment/nginx-deployment
deployment.apps/nginx-deployment paused
```

Then, we can change the image version of the *Deployment* manifest to *1.17.2*, just to observe the effect it has on the paused *Deployment*:

```bash
$ cat nginx-deployment.yaml | sed 's;1.14.2;1.17.2;' | kubectl apply -f -
deployment.apps/nginx-deployment configured

$ kubectl annotate deployment/nginx-deployment kubernetes.io/change-cause="Update image to 1.17.2"
```

Although the command says that the *Deployment* resource has been configured, the update isn’t rolled out to all the underlying pods because *Deployment* has been paused. **To check the status of the rollout, we use the \*kubectl rollout status\* command**, followed by the resource name:

```bash
$ kubectl rollout status deployment/nginx-deployment
Waiting for deployment "nginx-deployment" rollout to finish: 0 out of 3 new replicas have been updated...
```

**To resume the rollout, we can issue the \*kubectl rollout resume\* command** to the resource:

```bash
$ kubectl rollout resume deployment/nginx-deployment
deployment.apps/nginx-deployment resumed
```

Checking on the rollout status of our *myapp-deployment* resource now shows that the rollout is successful:

```bash
$ kubectl rollout status deployment/nginx-deployment
deployment "nginx-deployment" successfully rolled out
```



# Strategy

`.spec.strategy` specifies the strategy used to replace old Pods by new ones.`.spec.strategy.type` can be "Recreate" or "RollingUpdate". "RollingUpdate" is the default value.

#### Recreate Deployment

All existing Pods are killed before new ones are created when `.spec.strategy.type==Recreate`.

This will only guarantee Pod termination previous to creation for upgrades. If you upgrade a Deployment, all Pods of the old revision will be terminated immediately. Successful removal is awaited before any Pod of the new revision is created. If you manually delete a Pod, the lifecycle is controlled by the ReplicaSet and the replacement will be created immediately (even if the old Pod is still in a Terminating state). 

#### Rolling Update Deployment

The Deployment updates Pods in a rolling update fashion when `.spec.strategy.type==RollingUpdate`. You can specify `maxUnavailable` and `maxSurge` to control the rolling update process.

##### Max Unavailable

`.spec.strategy.rollingUpdate.maxUnavailable` is an optional field that specifies the maximum number of Pods that can be unavailable during the update process. The value can be an absolute number (for example, 5) or a percentage of desired Pods (for example, 10%). The absolute number is calculated from percentage by rounding down. The value cannot be 0 if `.spec.strategy.rollingUpdate.maxSurge` is 0. The default value is 25%.

For example, when this value is set to 30%, the old ReplicaSet can be scaled down to 70% of desired Pods immediately when the rolling update starts. Once new Pods are ready, old ReplicaSet can be scaled down further, followed by scaling up the new ReplicaSet, ensuring that the total number of Pods available at all times during the update is at least 70% of the desired Pods.

##### Max Surge

`.spec.strategy.rollingUpdate.maxSurge` is an optional field that specifies the maximum number of Pods that can be created over the desired number of Pods. The value can be an absolute number (for example, 5) or a percentage of desired Pods (for example, 10%). The value cannot be 0 if `MaxUnavailable` is 0. The absolute number is calculated from the percentage by rounding up. The default value is 25%.

For example, when this value is set to 30%, the new ReplicaSet can be scaled up immediately when the rolling update starts, such that the total number of old and new Pods does not exceed 130% of desired Pods. Once old Pods have been killed, the new ReplicaSet can be scaled up further, ensuring that the total number of Pods running at any time during the update is at most 130% of desired Pods.