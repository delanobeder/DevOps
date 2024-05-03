
package br.ufscar.dc.dsw.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@SuppressWarnings("serial")
@Entity
@Table(name = "Contato")
public class Contato extends AbstractEntity<Long> {

	@Column(nullable = false, length = 30)
	private String nome;
	
	@Column(nullable = false, length = 15)
	private String telefone;
	
	@Column(length = 45)
	private String email;
	
	@Column(length = 10)
	private String dataNasc;
	
	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}
	
	public String getTelefone() {
		return telefone;
	}

	public void setTelefone(String telefone) {
		this.telefone = telefone;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	
	public String getDataNasc() {
		return dataNasc;
	}

	public void setDataNasc(String dataNasc) {
		this.dataNasc = dataNasc;
	}

	
	@Override
	public String toString() {
		return nome + " [" +  telefone + "]";
	}
}