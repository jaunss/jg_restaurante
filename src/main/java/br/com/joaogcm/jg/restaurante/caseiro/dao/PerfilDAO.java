package br.com.joaogcm.jg.restaurante.caseiro.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.Set;

import br.com.joaogcm.jg.restaurante.caseiro.configuration.connection.ConfiguraConexaoBancoDeDados;
import br.com.joaogcm.jg.restaurante.caseiro.model.Perfil;

public class PerfilDAO {

	private StringBuilder sb = null;
	private PreparedStatement ps = null;
	private ResultSet rs = null;
	private Connection conn = null;

	public PerfilDAO() {

	}

	public Set<Perfil> buscarTodosPerfis() {
		Set<Perfil> perfis = new HashSet<Perfil>();

		try {
			sb = new StringBuilder();
			sb.append("SELECT * FROM perfil");

			conn = new ConfiguraConexaoBancoDeDados().getConexao();

			ps = conn.prepareStatement(sb.toString());

			rs = ps.executeQuery();

			while (rs.next()) {
				Perfil perfil = new Perfil();
				perfil.setCodigo(rs.getInt("CODIGO"));
				perfil.setNome(rs.getString("NOME"));

				perfis.add(perfil);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			ConfiguraConexaoBancoDeDados.fecharConn(null);
			ConfiguraConexaoBancoDeDados.fecharPS(ps);
			ConfiguraConexaoBancoDeDados.fecharRS(rs);
		}

		return perfis;
	}

	public Perfil buscarPerfilPorCodigo(Perfil perfil) {
		try {
			sb = new StringBuilder();
			sb.append("SELECT * FROM perfil WHERE codigo = ?");

			conn = new ConfiguraConexaoBancoDeDados().getConexao();

			ps = conn.prepareStatement(sb.toString());
			ps.setInt(1, perfil.getCodigo());

			rs = ps.executeQuery();

			while (rs.next()) {
				perfil = new Perfil();
				perfil.setCodigo(rs.getInt("CODIGO"));
				perfil.setNome(rs.getString("NOME"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			ConfiguraConexaoBancoDeDados.fecharConn(null);
			ConfiguraConexaoBancoDeDados.fecharPS(ps);
			ConfiguraConexaoBancoDeDados.fecharRS(rs);
		}

		return perfil;
	}
}