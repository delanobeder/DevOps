"""Esse módulo é o controlador da api."""

from config import client
from app import app
from bson.json_util import dumps
from flask import request, jsonify
import json
import ast
from importlib.machinery import SourceFileLoader

helper_module = SourceFileLoader('*', './app/helpers.py').load_module()

db = client.contatos
collection = db.contato

@app.route("/")
def get_initial_response():
    """Welcome message da API."""
    message = {
        'apiVersion': 'v1.0',
        'status': '200',
        'message': 'Welcome to the Flask API'
    }
    resp = jsonify(message)
    return resp

def proximo_id():
    res = db.counter.find_one({'_id': 'contatos'})
    value = res['value'] + 1
    db.counter.update_one({'_id': 'contatos'}, { "$set": {'value': value }})
    return value

@app.route("/contatos", methods=['POST'])
def criar_contato():
    """
       Função para criar contatos.
       """
    try:
        try:
            contato = request.get_json()
            contato['id'] = proximo_id()
            body = ast.literal_eval(json.dumps(contato))
        except:
            return "", 400

        record_created = collection.insert(body)

        if isinstance(record_created, list):
            return jsonify([str(v) for v in record_created]), 201
        else:
            return jsonify(str(record_created)), 201
    except:
        return "", 500

@app.route("/contatos", methods=['GET'])
def recupera_contatos():
    """
       Função to recuperar os contatos.
       """
    try:
        
        query_params = helper_module.parse_query_params(request.query_string)
        
        if query_params:

            query = {k: int(v) if isinstance(v, str) and v.isdigit() else v for k, v in query_params.items()}

            records_fetched = collection.find(query)

            if records_fetched.count() > 0:
                return dumps(records_fetched)
            else:
                return "", 404
        else:
            if collection.find().count() > 0:
                return dumps(collection.find())
            else:
                return jsonify([])
    except:
        return "", 500

@app.route("/contatos/<id>", methods=['GET'])
def recupera_contato(id):
    """
       Função to recuperar 1 contato.
       """
    try:
        records_fetched =  collection.find({"id": int(id)})
        if records_fetched.count() > 0:
            return dumps(records_fetched[0])
        else:
            return jsonify([]) 
    except:
        return "", 500

@app.route("/contatos/<id>", methods=['PUT'])
def atualiza_contato(id):
    """
       Função para atualizar o contato.
       """
    try:
        try:
            body = ast.literal_eval(json.dumps(request.get_json()))
        except:
            return "", 400

        records_updated = collection.update_one({"id": int(id)}, { "$set": body })

        return "", 200
    except:
        return "", 500

@app.route("/contatos/<id>", methods=['DELETE'])
def remove_contato(id):
    """
       Função para remover o contato.
       """
    try:
        delete_user = collection.delete_one({"id": int(id)})

        if delete_user.deleted_count > 0 :
            return "", 204
        else:
            return "", 404
    except:
        return "", 500

@app.errorhandler(404)
def page_not_found(e):
    """Send message to the user with notFound 404 status."""
    # Message to the user
    message = {
        "err":
            {
                "msg": "This route is currently not supported. Please refer API documentation."
            }
    }
    # Making the message looks good
    resp = jsonify(message)
    # Sending OK response
    resp.status_code = 404
    # Returning the object
    return resp
