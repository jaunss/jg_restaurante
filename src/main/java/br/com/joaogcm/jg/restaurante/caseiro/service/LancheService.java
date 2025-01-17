package br.com.joaogcm.jg.restaurante.caseiro.service;

import java.util.Set;

import br.com.joaogcm.jg.restaurante.caseiro.dao.LancheDAO;
import br.com.joaogcm.jg.restaurante.caseiro.model.Lanche;

public class LancheService {

	public void adicionarLanche(Lanche lanche) {
		new LancheDAO().adicionarLanche(lanche);
	}

	public void atualizarLanchePorCodigo(Lanche lanche) {
		new LancheDAO().atualizarLanchePorCodigo(lanche);
	}

	public void removerLanchePorCodigo(Lanche lanche) {
		new LancheDAO().removerLanchePorCodigo(lanche);
	}

	public Set<Lanche> buscarTodosLanches() {
		return new LancheDAO().buscarTodosLanches();
	}

	public Lanche buscarLanchePorCodigo(Integer codigo) {
		return new LancheDAO().buscarLanchePorCodigo(codigo);
	}
}