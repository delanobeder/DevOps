#!/bin/bash

################################################
# Executando o docker compose (DOWN)
# -v (remove volumes)
################################################

docker compose down -v

################################################
# Removendo a imagem docker do LivrariaMVC
################################################

docker rmi livraria/livraria-mvc

################################################
# Gerando a imagem docker do TransacaoAPI
################################################

docker rmi livraria/transacao-api

################################################
# Executando o docker compose (DOWN)
# --rmi all (remove images)
################################################

docker compose down --rmi all
