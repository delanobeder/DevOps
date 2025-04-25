package models

import (
	"github.com/jinzhu/gorm"
    "br.ufscar.dc.devops/contatos/config"
)

var db *gorm.DB

type Contato struct {
	Id       uint    `json:"id" gorm:"unique;primaryKey;autoIncrement"`
    Nome     string  `json:"nome"`
    Telefone string  `json:"telefone"`
    Email    string  `json:"email"`
    DataNasc string  `json:"dataNasc"`
}


func init() {
	config.Connect()
	db = config.GetDB()
	db.AutoMigrate(&Contato{})
}

func (c *Contato) CreateContato() *Contato {
	db.NewRecord(c)
	db.Create(&c)
	return c
}

func GetAllContatos() []Contato {
	var Contatos []Contato
	db.Find(&Contatos)
	return Contatos
}

func GetContatoById(Id int64) (*Contato, *gorm.DB) {
	var getContato Contato
	db := db.Where("ID=?", Id).Find(&getContato)
	return &getContato, db
}

func DeleteContato(ID int64) Contato {
	var contato Contato
	db.Where("ID=?", ID).Delete(contato)
	return contato
}