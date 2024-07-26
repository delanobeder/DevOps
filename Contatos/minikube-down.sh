#!/bin/bash

################################################
# Removendo a configuração do Ingress
################################################

cd K8s

kubectl delete -f ingress.yaml

cd ..

################################################
# Removendo a configuração do frontend
################################################

cd K8s/frontend

kubectl delete -f deployment.yaml; kubectl delete -f service.yaml;  

cd ../..

################################################
# Removendo a configuração do backend
################################################

cd K8s/backend

kubectl delete -f deployment.yaml; kubectl delete -f service.yaml; 

cd ../..

################################################
# Removendo a configuração do K8s (DB)
################################################

cd K8s/db

kubectl delete -f deployment.yaml; kubectl delete -f service.yaml; kubectl delete -f configmap.yaml; kubectl delete -f secret.yaml;  

kubectl delete -f pvc.yaml; kubectl delete pv contatos-pv-volume

cd ../..