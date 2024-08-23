package br.ufscar.dc.dsw.dao;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import br.ufscar.dc.dsw.domain.Contato;

@SuppressWarnings("unchecked")
public interface IContatoDAO extends CrudRepository<Contato, Long> {
	Contato findById(long id);
	List<Contato> findAll();
	Contato save(Contato estado);
	void deleteById(Long id);
}