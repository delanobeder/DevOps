module "db" {
  source = "./db"
}

module "app" {
  source = "./app"
}

resource "kubernetes_ingress_v1" "gateway-ingress" {
  metadata {
    name = "gateway-ingress"
  }
  spec {
    rule {
      host = "k8s.local"
      http {
        path {
          backend {
            service {
                name = var.release_name
                port {
                    number = 80
                }
            }
          }
          path = "/"
        }
      }
    }
  }
}