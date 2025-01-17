package br.com.joaogcm.jg.restaurante.caseiro.service;

import java.util.Set;

import br.com.joaogcm.jg.restaurante.caseiro.dao.PedidoLancheDAO;
import br.com.joaogcm.jg.restaurante.caseiro.model.Lanche;
import br.com.joaogcm.jg.restaurante.caseiro.model.Pedido;

public class PedidoLancheService {

	public Set<Lanche> buscarTodosOsLanchesDeUmDeterminadoPedido(Pedido pedido) {
		return new PedidoLancheDAO().buscarTodosOsLanchesDeUmDeterminadoPedido(pedido);
	}
}