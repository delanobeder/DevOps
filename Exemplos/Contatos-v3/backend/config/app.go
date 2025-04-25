package config

import (
	"github.com/jinzhu/gorm"
	_ "github.com/jinzhu/gorm/dialects/mysql"
	"os"
	"log"
)

var (
	db *gorm.DB
)

func getEnv(key, defaultValue string) string {
    value := os.Getenv(key)
    if len(value) == 0 {
        return defaultValue
    }
    return value
}

func Connect() {
	user := getEnv("MYSQL_USER", "root")
	password :=  getEnv("MYSQL_PASSWORD", "root")
	host := getEnv("MYSQL_HOST", "localhost")
	port := getEnv("MYSQL_PORT", "3306")
	database := getEnv("MYSQL_DATABASE", "Contatos")
	url := user + ":" + password + "@tcp(" + host + ":" + port + ")/" + database
	log.Println("url = " + url)
	d, err := gorm.Open("mysql", url)
	if err != nil {
		panic(err)
	}
	db = d
}

func GetDB() *gorm.DB {
	return db
}

