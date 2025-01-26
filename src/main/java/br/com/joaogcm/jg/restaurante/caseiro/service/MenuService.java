package br.com.joaogcm.jg.restaurante.caseiro.service;

import java.util.Set;

import br.com.joaogcm.jg.restaurante.caseiro.dao.MenuDAO;
import br.com.joaogcm.jg.restaurante.caseiro.model.Menu;

public class MenuService {

	public Set<Menu> listarTodasUrlsSubMenu() {
		return new MenuDAO().listarTodasUrlsSubMenu();
	}
}