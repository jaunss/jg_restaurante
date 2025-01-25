package br.com.joaogcm.jg.restaurante.caseiro.service;

import java.util.Set;

import br.com.joaogcm.jg.restaurante.caseiro.dao.PerfilDAO;
import br.com.joaogcm.jg.restaurante.caseiro.model.Perfil;

public class PerfilService {

	public Set<Perfil> buscarTodosPerfis() {
		return new PerfilDAO().buscarTodosPerfis();
	}

	public Perfil buscarPerfilPorCodigo(Perfil perfil) {
		return new PerfilDAO().buscarPerfilPorCodigo(perfil);
	}
}