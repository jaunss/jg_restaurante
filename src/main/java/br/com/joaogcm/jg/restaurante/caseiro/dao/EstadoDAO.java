package br.com.joaogcm.jg.restaurante.caseiro.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedHashSet;
import java.util.Set;

import br.com.joaogcm.jg.restaurante.caseiro.configuration.connection.ConfiguraConexaoBancoDeDados;
import br.com.joaogcm.jg.restaurante.caseiro.model.Estado;

public class EstadoDAO {

	private StringBuilder sb = null;
	private PreparedStatement ps = null;
	private ResultSet rs = null;
	private Connection conn = null;

	public EstadoDAO() {

	}

	public Set<Estado> buscarTodosEstados() {
		Set<Estado> estados = new LinkedHashSet<Estado>();

		try {
			sb = new StringBuilder();
			sb.append("SELECT * FROM estado ORDER BY nome ASC");

			conn = new ConfiguraConexaoBancoDeDados().getConexao();

			ps = conn.prepareStatement(sb.toString());

			rs = ps.executeQuery();

			while (rs.next()) {
				Estado estado = new Estado();
				estado.setCodigo(rs.getInt("CODIGO"));
				estado.setNome(rs.getString("NOME"));
				estado.setSigla(rs.getString("SIGLA"));

				estados.add(estado);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			ConfiguraConexaoBancoDeDados.fecharConn(conn);
			ConfiguraConexaoBancoDeDados.fecharPS(ps);
			ConfiguraConexaoBancoDeDados.fecharRS(rs);
		}

		return estados;
	}

	public Estado buscarEstadoPorCodigo(Estado estado) {
		try {
			sb = new StringBuilder();
			sb.append("SELECT * FROM estado WHERE codigo = ?");

			conn = new ConfiguraConexaoBancoDeDados().getConexao();

			ps = conn.prepareStatement(sb.toString());
			ps.setInt(1, estado.getCodigo());

			rs = ps.executeQuery();

			while (rs.next()) {
				estado = new Estado();
				estado.setCodigo(rs.getInt("CODIGO"));
				estado.setNome(rs.getString("NOME"));
				estado.setSigla(rs.getString("SIGLA"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			ConfiguraConexaoBancoDeDados.fecharConn(conn);
			ConfiguraConexaoBancoDeDados.fecharPS(ps);
			ConfiguraConexaoBancoDeDados.fecharRS(rs);
		}

		return estado;
	}
}