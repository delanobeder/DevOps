package br.ufscar.dc.dsw;

import java.math.BigDecimal;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import com.github.f4b6a3.uuid.UuidCreator;

import br.ufscar.dc.dsw.dao.ILivroDAO;
import br.ufscar.dc.dsw.domain.Livro;

@SpringBootApplication
public class LivrariaMvcApplication {

	public static void main(String[] args) {
		SpringApplication.run(LivrariaMvcApplication.class, args);
	}

	@Bean
	public CommandLineRunner demo(ILivroDAO livroDAO) {
		return (args) -> {
						
			String e1 = "Companhia das Letras";	
			String e2 = "Record";
			String e3 = "Objetiva";
			
			Livro l1 = new Livro();
			l1.setId(UuidCreator.getTimeOrdered());
			l1.setTitulo("Ensaio sobre a Cegueira");
			l1.setAutor("José Saramago");
			l1.setAno(1995);
			l1.setPreco(BigDecimal.valueOf(54.9));
			l1.setEditora(e1);
			livroDAO.save(l1);
			
			Livro l2 = new Livro();
			l2.setId(UuidCreator.getTimeOrdered());
			l2.setTitulo("Cem anos de Solidão");
			l2.setAutor("Gabriel Garcia Márquez");
			l2.setAno(1977);
			l2.setPreco(BigDecimal.valueOf(59.9));
			l2.setEditora(e2);
			livroDAO.save(l2);
			
			Livro l3 = new Livro();
			l3.setId(UuidCreator.getTimeOrdered());
			l3.setTitulo("Diálogos Impossíveis");
			l3.setAutor("Luis Fernando Verissimo");
			l3.setAno(2012);
			l3.setPreco(BigDecimal.valueOf(22.9));
			l3.setEditora(e3);
			livroDAO.save(l3);
		};
	}
}
