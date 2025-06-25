package br.ufscar.dc.dsw.service.spec;

import java.util.List;
import java.util.UUID;

import br.ufscar.dc.dsw.domain.Livro;

public interface ILivroService {

	Livro buscarPorId(UUID id);
	
	List<Livro> buscarTodos();
	
	void salvar(Livro livro);
	
	void excluir(UUID id);
	
}
