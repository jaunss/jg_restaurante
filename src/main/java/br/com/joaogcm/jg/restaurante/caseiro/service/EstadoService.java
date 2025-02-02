package br.com.joaogcm.jg.restaurante.caseiro.service;

import java.util.Set;

import br.com.joaogcm.jg.restaurante.caseiro.dao.EstadoDAO;
import br.com.joaogcm.jg.restaurante.caseiro.model.Estado;

public class EstadoService {

	public Set<Estado> buscarTodosEstados() {
		return new EstadoDAO().buscarTodosEstados();
	}

	public Estado buscarEstadoPorCodigo(Estado endereco) {
		return new EstadoDAO().buscarEstadoPorCodigo(endereco);
	}
}