package br.ufscar.dc.dsw.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.ufscar.dc.dsw.dao.IContatoDAO;
import br.ufscar.dc.dsw.domain.Contato;
import br.ufscar.dc.dsw.service.spec.IContatoService;

@Service
@Transactional(readOnly = false)
public class ContatoService implements IContatoService {

	@Autowired
	IContatoDAO dao;

	@Override
	@Transactional(readOnly = true)
	public Contato findById(long id) {
		return dao.findById(id);
	}

	@Override
	@Transactional(readOnly = true)
	public List<Contato> findAll() {
		return dao.findAll();
	}

	@Override
	public void save(Contato contato) {
		dao.save(contato);
	}
	
	@Override
	public void delete(Long id) {
		dao.deleteById(id);
	}
}
