package main

import (
    "github.com/gin-gonic/gin"
    "br.ufscar.dc.devops/contatos/controllers"
)

func main() {
    router := gin.Default()
    router.GET("/contatos", controllers.GetContatos)
    router.GET("/contatos/:id", controllers.GetContatoByID)
    router.POST("/contatos", controllers.PostContatos)
    router.PUT("/contatos/:id", controllers.UpdateContato)
    router.DELETE("/contatos/:id", controllers.DeleteContato)
       
    router.Run("0.0.0.0:8081")
}

