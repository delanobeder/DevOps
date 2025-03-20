resource "kubernetes_service" "app-service" {
  metadata {
    name = var.release_name
    labels = {
        run = var.release_name
    }
  }
  spec {
    selector = {
      app = var.release_name
    }
    port {
      port        = 80
      target_port = 80
      protocol = "TCP"
    }
  }
}