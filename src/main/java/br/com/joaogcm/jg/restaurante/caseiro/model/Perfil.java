package br.com.joaogcm.jg.restaurante.caseiro.model;

import java.util.HashSet;
import java.util.Set;

public class Perfil {

	Integer codigo;
	String nome;
	private Set<Menu> menus = new HashSet<Menu>();

	public Perfil() {

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

	public Set<Menu> getMenus() {
		return menus;
	}

	public void setMenus(Set<Menu> menus) {
		this.menus = menus;
	}
}