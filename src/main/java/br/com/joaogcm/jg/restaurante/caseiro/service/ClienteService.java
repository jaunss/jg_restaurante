package br.com.joaogcm.jg.restaurante.caseiro.service;

import java.util.Set;

import br.com.joaogcm.jg.restaurante.caseiro.dao.ClienteDAO;
import br.com.joaogcm.jg.restaurante.caseiro.model.Cliente;

public class ClienteService {

	public void adicionarCliente(Cliente cliente) {
		new ClienteDAO().adicionarCliente(cliente);
	}

	public void atualizarClientePorCodigo(Cliente cliente) {
		new ClienteDAO().atualizarClientePorCodigo(cliente);
	}

	public void removerClientePorCodigo(Cliente cliente) {
		new ClienteDAO().removerClientePorCodigo(cliente);
	}

	public Set<Cliente> buscarTodosClientes() {
		return new ClienteDAO().buscarTodosClientes();
	}

	public Cliente buscarClientePorCodigo(Cliente cliente) {
		return new ClienteDAO().buscarClientePorCodigo(cliente);
	}
}