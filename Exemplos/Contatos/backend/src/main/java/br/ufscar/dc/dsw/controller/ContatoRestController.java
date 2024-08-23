package br.ufscar.dc.dsw.controller;

import java.io.IOException;
import java.util.List;

import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.CrossOrigin;

import com.fasterxml.jackson.databind.ObjectMapper;

import br.ufscar.dc.dsw.domain.Contato;
import br.ufscar.dc.dsw.service.spec.IContatoService;

@CrossOrigin
@RestController
public class ContatoRestController {

	@Autowired
	private IContatoService service;

	private boolean isJSONValid(String jsonInString) {
		try {
			return new ObjectMapper().readTree(jsonInString) != null;
		} catch (IOException e) {
			return false;
		}
	}

	private void parse(Contato contato, JSONObject json) {
		
		Object id = json.get("id");
		if (id != null) {
			if (id instanceof Integer) {
				contato.setId(((Integer) id).longValue());
			} else {
				contato.setId((Long) id);
			}
		}

		contato.setNome((String) json.get("nome"));
		contato.setTelefone((String) json.get("telefone"));
		contato.setEmail((String) json.get("email"));
		contato.setDataNasc((String) json.get("dataNasc"));
	}

	@GetMapping(path = "/contatos")
	public ResponseEntity<List<Contato>> lista() {
		List<Contato> lista = service.findAll();
		return ResponseEntity.ok(lista);
	}

	@GetMapping(path = "/contatos/{id}")
	public ResponseEntity<Contato> lista(@PathVariable("id") long id) {
		Contato estado = service.findById(id);
		if (estado == null) {
			return ResponseEntity.notFound().build();
		}
		return ResponseEntity.ok(estado);
	}

	@PostMapping(path = "/contatos")
	@ResponseBody
	public ResponseEntity<Contato> cria(@RequestBody JSONObject json) {
		try {
			if (isJSONValid(json.toString())) {
				Contato estado = new Contato();
				parse(estado, json);
				service.save(estado);
				return ResponseEntity.ok(estado);
			} else {
				return ResponseEntity.badRequest().body(null);
			}
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(null);
		}
	}

	@PutMapping(path = "/contatos/{id}")
	public ResponseEntity<Contato> atualiza(@PathVariable("id") long id, @RequestBody JSONObject json) {
		try {
			if (isJSONValid(json.toString())) {
				Contato contato = service.findById(id);
				if (contato == null) {
					return ResponseEntity.notFound().build();
				} else {
					parse(contato, json);
					service.save(contato);
					return ResponseEntity.ok(contato);
				}
			} else {
				return ResponseEntity.badRequest().body(null);
			}
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(null);
		}
	}

	@DeleteMapping(path = "/contatos/{id}")
	public ResponseEntity<Boolean> remove(@PathVariable("id") long id) {

		Contato estado = service.findById(id);
		if (estado == null) {
			return ResponseEntity.notFound().build();
		} else {
			service.delete(id);
			return ResponseEntity.noContent().build();
		}
	}
}
