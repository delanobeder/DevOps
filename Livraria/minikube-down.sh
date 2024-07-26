#!/bin/bash

################################################
# Removendo a configuração do Ingress
################################################

cd K8s

kubectl delete -f ingress.yaml

cd ..

################################################
# Removendo a configuração do LivrariaMVC
################################################

cd K8s/livraria

kubectl delete -f deployment.yaml; kubectl delete -f service.yaml; kubectl delete -f configmap.yaml  

cd ../..

################################################
# Removendo a configuração do TransacaoAPI
################################################

cd K8s/transacao-api

kubectl delete -f deployment.yaml; kubectl delete -f service.yaml; 

cd ../..

################################################
# Removendo a configuração do K8s (DB)
################################################

cd K8s/db

kubectl delete -f deployment.yaml; kubectl delete -f service.yaml; kubectl delete -f configmap.yaml; kubectl delete -f secret.yaml;  

kubectl delete pvc livraria-pv-claim; kubectl delete pv livraria-pv-volume;

cd ../..