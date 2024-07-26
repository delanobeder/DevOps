#!/bin/bash

################################################
# Gerando a imagem docker do backend
################################################

cd backend

mvn package

cd ..

################################################
# Gerando a imagem docker do frontend
################################################

cd frontend

docker build . -t contatos/frontend

cd ..

################################################
# Executando o docker compose (UP)
################################################

docker compose up
