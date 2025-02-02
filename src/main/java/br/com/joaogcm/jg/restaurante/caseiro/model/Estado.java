package br.com.joaogcm.jg.restaurante.caseiro.model;

import java.util.LinkedHashSet;
import java.util.Set;

public class Estado {

	private Integer codigo;
	private String nome;
	private String sigla;
	private Set<Cidade> cidades = new LinkedHashSet<Cidade>();

	public Estado() {

	}

	public Integer getCodigo() {
		return codigo;
	}

	public void setCodigo(Integer codigo) {
		this.codigo = codigo;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getSigla() {
		return sigla;
	}

	public void setSigla(String sigla) {
		this.sigla = sigla;
	}

	public Set<Cidade> getCidades() {
		return cidades;
	}

	public void setCidades(Set<Cidade> cidades) {
		this.cidades = cidades;
	}
}