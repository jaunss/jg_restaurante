package br.com.joaogcm.jg.restaurante.caseiro.service;

import java.util.Set;

import br.com.joaogcm.jg.restaurante.caseiro.dao.PedidoLancheDAO;
import br.com.joaogcm.jg.restaurante.caseiro.model.Lanche;
import br.com.joaogcm.jg.restaurante.caseiro.model.Pedido;

public class PedidoLancheService {

	public void adicionarPedidoLanche(Pedido pedido, Lanche lanche) {
		new PedidoLancheDAO().adicionarPedidoLanche(pedido, lanche);
	}

	public void removerPedidoLanchePorCodigoDoPedido(Pedido pedido) {
		new PedidoLancheDAO().removerPedidoLanchePorCodigoDoPedido(pedido);
	}

	public Set<Lanche> buscarTodosOsLanchesDeUmDeterminadoPedido(Pedido pedido) {
		return new PedidoLancheDAO().buscarTodosOsLanchesDeUmDeterminadoPedido(pedido);
	}
}