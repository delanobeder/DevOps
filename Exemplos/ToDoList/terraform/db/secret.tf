resource "kubernetes_secret" "db-secret" {
  metadata {
    name = "${var.release_name}-db-secret"
  }

  data = {
    username = "root"
    password = "root"
  }

  type = "kubernetes.io/basic-auth"
}