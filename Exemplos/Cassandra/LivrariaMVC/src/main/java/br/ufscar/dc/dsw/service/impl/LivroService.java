package br.ufscar.dc.dsw.service.impl;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.ufscar.dc.dsw.dao.ILivroDAO;
import br.ufscar.dc.dsw.domain.Livro;
import br.ufscar.dc.dsw.service.spec.ILivroService;

@Service
@Transactional(readOnly = false)
public class LivroService implements ILivroService {

	@Autowired
	ILivroDAO dao;
	
	public void salvar(Livro livro) {
		dao.save(livro);
	}

	public void excluir(UUID id) {
		dao.deleteById(id);
	}

	@Transactional(readOnly = true)
	public Livro buscarPorId(UUID id) {
		return dao.findById(id).orElse(null);
	}

	@Transactional(readOnly = true)
	public List<Livro> buscarTodos() {
		
		List<Livro> lista = dao.findAll();

		Collections.sort(lista, new Comparator<Livro>() {
			@Override
			public int compare(Livro arg0, Livro arg1) {
				return arg0.getTitulo().toUpperCase().compareTo(arg1.getTitulo().toUpperCase());
			}
		});
		return lista;
	}
}
