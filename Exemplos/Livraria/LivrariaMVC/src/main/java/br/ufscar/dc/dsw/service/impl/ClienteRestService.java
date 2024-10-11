package br.ufscar.dc.dsw.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;
import br.ufscar.dc.dsw.domain.Cartao;
import br.ufscar.dc.dsw.domain.Transacao;
import br.ufscar.dc.dsw.service.spec.IClienteRestService;

@Service
public class ClienteRestService implements IClienteRestService {

	@Value("${TRANSACAO_API_URL:http://localhost:8081}")
	private String URL_BASE;

	RestClient restClient = RestClient.create();

	@Override
	public Cartao buscaCartao(Long id) {
		Cartao cartao = restClient.get()
				.uri(URL_BASE + "/cartoes/" + id)
				.accept(MediaType.APPLICATION_JSON)
				.retrieve()
				.body(Cartao.class);
		return cartao;
	}

	public List<Cartao> buscaCartoes(String cpf) {
		List<Cartao> list = restClient.get()
                .uri(URL_BASE + "/cartoes/cpf/" + cpf)
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .body(new ParameterizedTypeReference<List<Cartao>>() {
                });
        return list;
	}

	public Transacao buscaTransacao(Long id) {
		Transacao transacao = restClient.get()
				.uri(URL_BASE + "/transacoes/" + id)
				.accept(MediaType.APPLICATION_JSON)
				.retrieve()
				.body(Transacao.class);
		return transacao;
	}

	@Override
	public Long salva(Transacao transacao) {
		ResponseEntity<Transacao> res = restClient.post()
    		    .uri(URL_BASE + "/transacoes")
    		    .contentType(MediaType.APPLICATION_JSON)
    		    .body(transacao)
    		    .retrieve()
    		    .toEntity(Transacao.class);

		Transacao t = res.getBody();

		return t.getId();
	}

	@Override
	public boolean remove(Long id) {
		ResponseEntity<Void> res = restClient.delete()
    			  .uri(URL_BASE + "/transacoes/" + id)
    			  .retrieve()
    			  .toBodilessEntity();
    	return res.getStatusCode() == HttpStatus.NO_CONTENT;
	}
}
