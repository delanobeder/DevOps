#!/bin/bash

################################################
# Executando o docker compose (DOWN)
# -v (remove volumes)
################################################

docker compose down -v

################################################
# Removendo a imagem docker do backend
################################################

docker rmi contatos/backend

################################################
# Gerando a imagem docker do frontend
################################################

docker rmi contatos/frontend

################################################
# Executando o docker compose (DOWN)
# --rmi all (remove images)
################################################

docker compose down --rmi all