resource "kubernetes_secret" "db-secret" {
  metadata {
    name = "${var.release_name}-db-secret"
  }

  data = {
    username = "cm9vdA=="
    password = "cm9vdA=="
  }

  type = "kubernetes.io/basic-auth"
}