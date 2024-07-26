#!/bin/bash

################################################
# Inicializando o Minikube
################################################

minikube start --cpus 4 --memory 4096

################################################
# Habilitando o Ingress
################################################

minikube image load k8s.gcr.io/ingress-nginx/controller:v1.2.1

minikube addons enable ingress

################################################
# Inicializando o DashBoard
################################################

minikube addons enable ingress

minikube dashboard &

################################################
# Configurando o K8s (DB)
################################################

minikube image load mysql:5.7

cd K8s/db

kubectl create -f secret.yaml; kubectl create -f pv.yaml; kubectl create -f pvc.yaml;

kubectl create -f deployment.yaml; kubectl create -f service.yaml; kubectl create -f configmap.yaml; 

cd ../..

################################################
# Gerando a imagem docker do Livraria-MVC
################################################

#eval $(minikube docker-env)

cd LivrariaMVC

mvn package

cd ..

minikube image load livraria/livraria-mvc

################################################
# Configurando o Livraria-MVC
################################################

cd K8s/livraria

kubectl wait --for=jsonpath='{.status.phase}'=Running $(kubectl get pods -o=name)

kubectl create -f deployment.yaml; kubectl create -f service.yaml; kubectl create -f configmap.yaml; 

cd ../..

################################################
# Gerando a imagem docker do Transacao-API
################################################

cd TransacaoAPI

mvn package

cd ..

minikube image load livraria/transacao-api

################################################
# Configurando o TransacaoAPI
################################################

cd K8s/transacao-api

kubectl create -f deployment.yaml; kubectl create -f service.yaml;  

cd ../..

################################################
# Configurando o Ingress
################################################

cd K8s

kubectl create -f ingress.yaml
