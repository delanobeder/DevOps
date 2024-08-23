package br.ufscar.dc.dsw;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import br.ufscar.dc.dsw.dao.IContatoDAO;
import br.ufscar.dc.dsw.domain.Contato;

@SpringBootApplication
public class ContatosRSApplication {
	
	public static void main(String[] args) {
		SpringApplication.run(ContatosRSApplication.class, args);
	}

	@Bean
	public CommandLineRunner run(IContatoDAO contatoDAO) throws Exception {
		return args -> {
			if (contatoDAO.findAll().isEmpty()) {
				Contato contato = new Contato();
				contato.setNome("Fulano de Tal");
				contato.setTelefone("(16) 98123-4567");
				contato.setEmail("fulano@gmail.com");
				contato.setDataNasc("01/01/1990");
				contatoDAO.save(contato);
			}
		};
	}
}