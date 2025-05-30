resource "kubernetes_deployment" "app-deployment" {
  metadata {
    name = var.release_name
    labels = {
      app = var.release_name
    }
  }

  spec {
    replicas = 1

    selector {
      match_labels = {
        app = var.release_name
      }
    }

    strategy {
      type = "Recreate"
    }

    template {
      metadata {
        labels = {
          app = var.release_name
        }
      }

      spec {
        container {
          name  = var.release_name
          image = "devopsufscar/todolist-app:latest"
          image_pull_policy = "IfNotPresent"
          port {
            container_port = "80"
          }

          env {
            name = "PORT"
            value = "80"
          }

          env {
            name = "MONGO_INITDB_HOST"
            value = "${var.release_name}-db"
          }

          env {
            name = "MONGO_INITDB_ROOT_USERNAME"
            value_from {
              secret_key_ref {
                key = "username"
                name = "${var.release_name}-db-secret"
              }
            }
          }

          env {
            name = "MONGO_INITDB_ROOT_PASSWORD"
            value_from {
              secret_key_ref {
                key = "password"
                name = "${var.release_name}-db-secret"
              }
            }
          }

          resources {
            limits = {
              cpu    = "2"
              memory = "2Gi"
            }
            requests = {
              cpu    = "1"
              memory = "1Gi"
            }
          }
        }
      }
    }
  }
}
