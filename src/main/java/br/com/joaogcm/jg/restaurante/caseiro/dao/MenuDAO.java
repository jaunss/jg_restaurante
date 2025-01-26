package br.com.joaogcm.jg.restaurante.caseiro.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedHashSet;
import java.util.Set;

import br.com.joaogcm.jg.restaurante.caseiro.configuration.connection.ConfiguraConexaoBancoDeDados;
import br.com.joaogcm.jg.restaurante.caseiro.model.Menu;

public class MenuDAO {

	private StringBuilder sb = null;
	private PreparedStatement ps = null;
	private ResultSet rs = null;
	private Connection conn = null;

	public MenuDAO() {

	}

	public Set<Menu> listarTodasUrlsSubMenu() {
		Set<Menu> menus = new LinkedHashSet<Menu>();

		try {
			sb = new StringBuilder();
			sb.append("SELECT * FROM menu WHERE codigo IN (1, 4, 8, 12) ORDER BY codigo ASC");

			conn = new ConfiguraConexaoBancoDeDados().getConexao();

			ps = conn.prepareStatement(sb.toString());

			rs = ps.executeQuery();

			while (rs.next()) {
				Menu menu = new Menu();
				menu.setCodigo(rs.getInt("CODIGO"));
				menu.setUrl(rs.getString("URL"));
				menu.setAcao(rs.getString("ACAO"));
				menu.setNome(rs.getString("NOME"));
				menu.setIcone(rs.getString("ICONE"));

				menus.add(menu);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			ConfiguraConexaoBancoDeDados.fecharConn(conn);
			ConfiguraConexaoBancoDeDados.fecharPS(ps);
			ConfiguraConexaoBancoDeDados.fecharRS(rs);
		}

		return menus;
	}
}