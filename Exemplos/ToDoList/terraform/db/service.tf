resource "kubernetes_service" "db-service" {
  metadata {
    name = "${var.release_name}-db"
    labels = {
        run = "${var.release_name}-db"
    }
  }
  spec {
    selector = {
      app = "${var.release_name}-db"
    }
    port {
      port        = 27017
      target_port = 27017
      protocol = "TCP"
    }
  }
}