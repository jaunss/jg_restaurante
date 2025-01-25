package br.com.joaogcm.jg.restaurante.caseiro.service;

import java.util.List;

import br.com.joaogcm.jg.restaurante.caseiro.dao.MenuDAO;
import br.com.joaogcm.jg.restaurante.caseiro.model.Menu;

public class MenuService {

	public List<Menu> listarTodasUrlsSubMenu() {
		return new MenuDAO().listarTodasUrlsSubMenu();
	}
}