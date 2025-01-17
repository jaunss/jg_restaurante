package br.com.joaogcm.jg.restaurante.caseiro.service;

import java.util.Set;

import br.com.joaogcm.jg.restaurante.caseiro.dao.PedidoDAO;
import br.com.joaogcm.jg.restaurante.caseiro.model.Pedido;

public class PedidoService {

	public void adicionarPedido(Pedido pedido) {
		new PedidoDAO().adicionarPedido(pedido);
	}

	public void atualizarPedidoPorCodigo(Pedido pedido) {
		new PedidoDAO().atualizarPedidoPorCodigo(pedido);
	}

	public void removerPedidoPorCodigo(Pedido pedido) {
		new PedidoDAO().removerPedidoPorCodigo(pedido);
	}

	public Set<Pedido> buscarTodosPedidos() {
		return new PedidoDAO().buscarTodosPedidos();
	}

	public Pedido buscarPedidoPorCodigo(Pedido pedido) {
		return new PedidoDAO().buscarPedidoPorCodigo(pedido);
	}
}