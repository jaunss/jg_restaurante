package br.com.joaogcm.jg.restaurante.caseiro.service;

import br.com.joaogcm.jg.restaurante.caseiro.dao.AutenticacaoDAO;
import br.com.joaogcm.jg.restaurante.caseiro.model.Cliente;

public class AutenticacaoService {
	public Cliente autenticarClientePorEmail(String email) {
		return new AutenticacaoDAO().autenticarClientePorEmail(email);
	}

	public Cliente autenticarClientePorSenhaHash(Cliente cliente) {
		return new AutenticacaoDAO().autenticarClientePorSenhaHash(cliente);
	}
}