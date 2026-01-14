package br.ufscar.dc.dsw.domain;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.UUID;

import org.springframework.data.cassandra.core.mapping.CassandraType;
import org.springframework.data.cassandra.core.mapping.Column;
import org.springframework.data.cassandra.core.mapping.PrimaryKey;
import org.springframework.data.cassandra.core.mapping.Table;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

@Table("Livro")
public class Livro {

	@PrimaryKey
	@CassandraType(type = CassandraType.Name.UUID)
    private UUID id;

	@NotBlank(message = "{NotBlank.livro.titulo}")
	@Size(max = 60)
	@Column
	private String titulo;

	@NotBlank(message = "{NotBlank.livro.autor}")
	@Size(max = 60)
	@Column
	private String autor;
    
	@NotNull(message = "{NotNull.livro.ano}")
	@Column
	private Integer ano;
	
	@NotNull(message = "{NotNull.livro.preco}")
	@Column
	private BigDecimal preco;
    
	@NotNull(message = "{NotNull.livro.editora}")
	@Column
	private String editora;
	
	public UUID getId() {
		return id;
	}

	public void setId(UUID id) {
		this.id = id;
	}

	public String getTitulo() {
		return titulo;
	}

	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}

	public String getAutor() {
		return autor;
	}

	public void setAutor(String autor) {
		this.autor = autor;
	}

	public Integer getAno() {
		return ano;
	}

	public void setAno(Integer ano) {
		this.ano = ano;
	}

	public BigDecimal getPreco() {
		return preco == null ? preco : preco.setScale(2, RoundingMode.HALF_UP);
	}

	public void setPreco(BigDecimal preco) {
		this.preco = preco;
	}

	public String getEditora() {
		return editora;
	}

	public void setEditora(String editora) {
		this.editora = editora;
	}
}
