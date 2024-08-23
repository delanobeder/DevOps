"""This module is to configure app to connect with database."""

import os
import json
import ast

from pymongo import MongoClient

HOST = os.environ.get('MONGO_INITDB_HOST','localhost')
USER = os.environ.get('MONGO_INITDB_ROOT_USERNAME','root')
PASS = os.environ.get('MONGO_INITDB_ROOT_PASSWORD','root')
URI = "mongodb://"+ USER + ":" + PASS + "@" + HOST;

DEBUG = True
client = MongoClient(URI)

# Select the database
db = client.contatos
# Select the collection
collection = db.contato

if (collection.count_documents({}) == 0):
    print("Insert Contact")
    contato = {}
    contato['id'] = 1
    contato['nome'] = 'Fulano de Tal'
    contato['telefone'] = '(16) 98123-4567'
    contato['email'] = 'fulano@gmail.com'
    contato['dataNasc'] = '01/01/1990'
    
    body = ast.literal_eval(json.dumps(contato))
    collection.insert(body)

    contador = {}
    contador['_id'] = 'contatos'
    contador['value'] = 1
    db.counter.insert(ast.literal_eval(json.dumps(contador)))