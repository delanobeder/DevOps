resource "kubernetes_persistent_volume_claim" "db-pv-claim" {
  metadata {
    name = "${var.release_name}-pv-claim"
  }
  spec {
    access_modes = ["ReadWriteOnce"]
    resources {
      requests = {
        storage = "1Gi"
      }
    }
    storage_class_name = "standard"
    volume_name = "${kubernetes_persistent_volume.db-pv-volume.metadata.0.name}"
  }
}

resource "kubernetes_persistent_volume" "db-pv-volume" {
  metadata {
    name = "${var.release_name}-pv-volume"
  }
  spec {
    capacity = {
      storage = "1Gi"
    }
    access_modes = ["ReadWriteOnce"]
    persistent_volume_reclaim_policy = "Retain"
    storage_class_name = "standard"
    persistent_volume_source {
    host_path {
      path = "/mnt/data/${var.release_name}"
    }
    }
  }
}