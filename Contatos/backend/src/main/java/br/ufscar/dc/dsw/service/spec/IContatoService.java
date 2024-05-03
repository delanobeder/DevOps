package br.ufscar.dc.dsw.service.spec;

import java.util.List;

import br.ufscar.dc.dsw.domain.Contato;

public interface IContatoService {
	
	Contato findById(long id);

	List<Contato> findAll();

	void save(Contato contato);

	void delete(Long id);
}