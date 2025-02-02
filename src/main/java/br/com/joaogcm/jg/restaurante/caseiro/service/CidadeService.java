package br.com.joaogcm.jg.restaurante.caseiro.service;

import java.util.Set;

import br.com.joaogcm.jg.restaurante.caseiro.dao.CidadeDAO;
import br.com.joaogcm.jg.restaurante.caseiro.model.Cidade;
import br.com.joaogcm.jg.restaurante.caseiro.model.Estado;

public class CidadeService {

	public Set<Cidade> buscarTodasCidades() {
		return new CidadeDAO().buscarTodasCidades();
	}

	public Cidade buscarCidadePorCodigo(Cidade cidade) {
		return new CidadeDAO().buscarCidadePorCodigo(cidade);
	}

	public Set<Cidade> buscarCidadesPorEstado(Estado estado) {
		return new CidadeDAO().buscarCidadesPorEstado(estado);
	}
}