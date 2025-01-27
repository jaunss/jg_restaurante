package br.com.joaogcm.jg.restaurante.caseiro.service;

import java.util.Set;

import br.com.joaogcm.jg.restaurante.caseiro.dao.EnderecoDAO;
import br.com.joaogcm.jg.restaurante.caseiro.model.Endereco;

public class EnderecoService {
	
	public void adicionarEndereco(Endereco endereco) {
		new EnderecoDAO().adicionarEndereco(endereco);
	}
	
	public void atualizarEnderecoPorCodigo(Endereco endereco) {
		new EnderecoDAO().atualizarEnderecoPorCodigo(endereco);
	}
	
	public void removerEnderecoPorCodigo(Endereco endereco) {
		new EnderecoDAO().removerEnderecoPorCodigo(endereco);
	}

	public Set<Endereco> buscarTodosEnderecos() {
		return new EnderecoDAO().buscarTodosEnderecos();
	}

	public Endereco buscarEnderecoPorCodigo(Endereco endereco) {
		return new EnderecoDAO().buscarEnderecoPorCodigo(endereco);
	}

	public boolean isVerificarSeClienteJaPossuiOEndereco(Endereco endereco) {
		return new EnderecoDAO().isVerificarSeClienteJaPossuiOEndereco(endereco);
	}
}