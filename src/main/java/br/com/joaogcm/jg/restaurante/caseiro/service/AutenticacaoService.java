package br.com.joaogcm.jg.restaurante.caseiro.service;

import br.com.joaogcm.jg.restaurante.caseiro.dao.AutenticacaoDAO;
import br.com.joaogcm.jg.restaurante.caseiro.model.Cliente;

public class AutenticacaoService {
	public Cliente autenticarClientePorEmailESenha(String email) {
		return new AutenticacaoDAO().autenticarClientePorEmailESenha(email);
	}
}