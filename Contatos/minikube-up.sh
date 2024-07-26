#!/bin/bash

################################################
# Inicializando o Minikube
################################################

minikube start --cpus 4 --memory 4096

################################################
# Habilitando o Ingress
################################################

minikube image load k8s.gcr.io/ingress-nginx/controller:v1.9.4

minikube addons enable ingress

################################################
# Inicializando o DashBoard
################################################

minikube addons enable dashboard

minikube addons enable metrics-server

minikube dashboard &

################################################
# Configurando o K8s (DB)
################################################

minikube image load mysql:5.7

cd K8s/db

kubectl apply -f secret.yaml; kubectl create -f pv.yaml; kubectl create -f pvc.yaml;

kubectl apply -f deployment.yaml; kubectl apply -f service.yaml; kubectl apply -f configmap.yaml; 

cd ../..

################################################
# Gerando a imagem docker do backend
################################################

#eval $(minikube docker-env)

cd backend

mvn package

cd ..

# minikube image load apenas funciona no docker 24

minikube image load contatos/backend

################################################
# Configurando o backend
################################################

cd K8s/backend

kubectl wait --for=jsonpath='{.status.phase}'=Running $(kubectl get pods -o=name)

kubectl apply -f deployment.yaml; kubectl apply -f service.yaml; 

cd ../..

################################################
# Gerando a imagem docker do frontend
################################################

cd frontend

docker build . -t contatos/frontend

cd ..

minikube image load contatos/frontend

################################################
# Configurando o frontend
################################################

cd K8s/frontend

kubectl apply -f deployment.yaml; kubectl apply -f service.yaml;  

cd ../..

################################################
# Configurando o Ingress
################################################

cd K8s

kubectl apply -f ingress.yaml
