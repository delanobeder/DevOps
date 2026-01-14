package br.ufscar.dc.dsw.dao;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.cassandra.repository.CassandraRepository;
import org.springframework.stereotype.Repository;

import br.ufscar.dc.dsw.domain.Livro;

@SuppressWarnings("unchecked")
@Repository
public interface ILivroDAO extends CassandraRepository<Livro,UUID>{

	Optional<Livro> findById(UUID id);

	List<Livro> findAll();
	
	Livro save(Livro livro);

	void deleteById(UUID id);
}