package br.com.joaogcm.jg.restaurante.caseiro.model;

import java.util.HashSet;
import java.util.Set;

public class Menu {

	private Integer codigo;
	private String url;
	private String acao;
	private String nome;
	private Integer exibir;
	private Set<Perfil> perfis = new HashSet<Perfil>();

	public Menu() {

	}

	public Integer getCodigo() {
		return codigo;
	}

	public void setCodigo(Integer codigo) {
		this.codigo = codigo;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getAcao() {
		return acao;
	}

	public void setAcao(String acao) {
		this.acao = acao;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public Integer getExibir() {
		return exibir;
	}

	public void setExibir(Integer exibir) {
		this.exibir = exibir;
	}

	public Set<Perfil> getPerfis() {
		return perfis;
	}

	public void setPerfis(Set<Perfil> perfis) {
		this.perfis = perfis;
	}
}