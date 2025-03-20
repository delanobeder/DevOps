resource "kubernetes_deployment" "db-deployment" {
  metadata {
    name = "${var.release_name}-db"
    labels = {
      app = "${var.release_name}-db"
    }
  }

  spec {
    replicas = 1

    selector {
      match_labels = {
        app = "${var.release_name}-db"
      }
    }

    strategy {
      type = "Recreate"
    }

    template {
      metadata {
        labels = {
          app = "${var.release_name}-db"
        }
      }

      spec {
        container {
          name  = "${var.release_name}-db"
          image = "mongo:4.2"
          port {
            container_port = "27017"
          }

          env {
            name = "MONGO_INITDB_ROOT_USERNAME"
            value_from {
              secret_key_ref {
                key = "username"
                name  = "${var.release_name}-db-secret"
              }
            }
          }

          env {
            name = "MONGO_INITDB_ROOT_PASSWORD"
            value_from {
              secret_key_ref {
                key = "password"
                name  = "${var.release_name}-db-secret"
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

          volume_mount {
            name  = "${var.release_name}-persistent-storage"
            mount_path = "/data/db"
          }
        }
        volume {
          name  = "${var.release_name}-persistent-storage"
          persistent_volume_claim {
            claim_name = "${var.release_name}-pv-claim"
          }
        }
      }
    }
  }
}
