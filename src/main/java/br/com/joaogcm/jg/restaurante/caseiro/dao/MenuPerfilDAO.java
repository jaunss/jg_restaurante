package br.com.joaogcm.jg.restaurante.caseiro.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.Set;

import br.com.joaogcm.jg.restaurante.caseiro.configuration.connection.ConfiguraConexaoBancoDeDados;
import br.com.joaogcm.jg.restaurante.caseiro.model.Menu;
import br.com.joaogcm.jg.restaurante.caseiro.model.Perfil;

public class MenuPerfilDAO {

	private StringBuilder sb = null;
	private PreparedStatement ps = null;
	private ResultSet rs = null;
	private Connection conn = null;

	public MenuPerfilDAO() {

	}

	public Set<Menu> buscarTodosMenusPorCodigoDoPerfil(Perfil perfil) {
		Set<Menu> menus = new HashSet<Menu>();

		try {
			sb = new StringBuilder();
			sb.append("SELECT m.* FROM menu m, perfil p, menu_perfil mp ");
			sb.append("WHERE mp.codigo_menu = m.codigo ");
			sb.append("AND mp.codigo_perfil = p.codigo ");
			sb.append("AND p.codigo = ?");

			conn = new ConfiguraConexaoBancoDeDados().getConexao();

			ps = conn.prepareStatement(sb.toString());
			ps.setInt(1, perfil.getCodigo());

			rs = ps.executeQuery();

			while (rs.next()) {
				Menu menu = new Menu();
				menu.setCodigo(rs.getInt("CODIGO"));
				menu.setUrl(rs.getString("URL"));
				menu.setAcao(rs.getString("ACAO"));
				menu.setNome(rs.getString("NOME"));
				menu.setExibir(rs.getInt("EXIBIR"));

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