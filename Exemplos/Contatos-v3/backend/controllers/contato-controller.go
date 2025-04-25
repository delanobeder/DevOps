
package controllers

import (
    "net/http"
    "fmt"
    "strconv"
    "github.com/gin-gonic/gin"
    "br.ufscar.dc.devops/contatos/models"
)

func init() {
	contato := &models.Contato{Id: 1, Nome: "Fulano de Tal", Telefone: "(16) 98123-4567", Email: "fulano@gmail.com", DataNasc: "01/01/1990"}
	contato.CreateContato()
}

func GetContatos(c *gin.Context) {
    contatos := models.GetAllContatos()
    fmt.Println(contatos) 
    c.IndentedJSON(http.StatusOK, contatos)
}

func GetContatoByID(c *gin.Context) {
    id := c.Param("id")

    ID, err := strconv.ParseInt(id, 0, 0)
	if err != nil {
		fmt.Println("error while parsing")
	} 
    
    contato, _ := models.GetContatoById(ID)
    
    if contato != nil {
        c.IndentedJSON(http.StatusOK, contato)
        return
    }

    c.IndentedJSON(http.StatusNotFound, gin.H{"message": "contato não encontrado"})
}

func PostContatos(c *gin.Context) {
    contato := &models.Contato{}

    if err := c.BindJSON(&contato); err != nil {
        return
    }

    contato.CreateContato() 
    c.IndentedJSON(http.StatusCreated, contato)
}

func UpdateContato(c *gin.Context) {
    contato := &models.Contato{}

    if err := c.BindJSON(&contato); err != nil {
        return
    }

    id := c.Param("id")
    ID, err := strconv.ParseInt(id, 0, 0)
	if err != nil {
		fmt.Println("error while parsing")
	}

    contatoAtual, db := models.GetContatoById(ID)
	
    if (contatoAtual != nil) {
        if contato.Nome != "" {
            contatoAtual.Nome = contato.Nome
        }
        
        if contato.Telefone != "" {
            contatoAtual.Telefone = contato.Telefone
	    }
        
        if contato.Email != "" {
		    contatoAtual.Email = contato.Email
	    }

        if contato.DataNasc != "" {
		    contatoAtual.DataNasc = contato.DataNasc
	    }
        
        db.Save(&contatoAtual)
        c.IndentedJSON(http.StatusOK, contatoAtual)
        return 
    }
    
    c.IndentedJSON(http.StatusNotFound, gin.H{"message": "contato não encontrado"})
}

func DeleteContato(c *gin.Context) {
    id := c.Param("id")
    ID, err := strconv.ParseInt(id, 0, 0)
	if err != nil {
		fmt.Println("error while parsing")
        c.IndentedJSON(http.StatusNotFound, gin.H{"message": "contato não encontrado"})
        return
	}

    contato := models.DeleteContato(ID)
    c.IndentedJSON(http.StatusOK, contato)
    return
}

