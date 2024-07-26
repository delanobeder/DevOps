#!/bin/bash

################################################
# Gerando a imagem docker do LivrariaMVC
################################################

cd LivrariaMVC

mvn package

cd ..

################################################
# Gerando a imagem docker do TransacaoAPI
################################################

cd TransacaoAPI

mvn package

cd ..

################################################
# Executando o docker compose
################################################

docker compose up
