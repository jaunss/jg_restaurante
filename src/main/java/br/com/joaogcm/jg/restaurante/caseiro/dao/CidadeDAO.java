package br.com.joaogcm.jg.restaurante.caseiro.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedHashSet;
import java.util.Set;

import br.com.joaogcm.jg.restaurante.caseiro.configuration.connection.ConfiguraConexaoBancoDeDados;
import br.com.joaogcm.jg.restaurante.caseiro.model.Cidade;
import br.com.joaogcm.jg.restaurante.caseiro.model.Estado;

public class CidadeDAO {

	private StringBuilder sb = null;
	private PreparedStatement ps = null;
	private ResultSet rs = null;
	private Connection conn = null;

	public CidadeDAO() {

	}

	public Set<Cidade> buscarTodasCidades() {
		Set<Cidade> cidades = new LinkedHashSet<Cidade>();

		try {
			sb = new StringBuilder();
			sb.append("SELECT * FROM cidade ORDER BY nome ASC");

			conn = new ConfiguraConexaoBancoDeDados().getConexao();

			ps = conn.prepareStatement(sb.toString());

			rs = ps.executeQuery();

			while (rs.next()) {
				Cidade cidade = new Cidade();
				cidade.setCodigo(rs.getInt("CODIGO"));
				cidade.setNome(rs.getString("NOME"));

				Estado estado = new Estado();
				estado.setCodigo(rs.getInt("ESTADO_ID"));
				cidade.setEstado(estado);

				cidades.add(cidade);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			ConfiguraConexaoBancoDeDados.fecharConn(conn);
			ConfiguraConexaoBancoDeDados.fecharPS(ps);
			ConfiguraConexaoBancoDeDados.fecharRS(rs);
		}

		return cidades;
	}

	public Cidade buscarCidadePorCodigo(Cidade cidade) {
		try {
			sb = new StringBuilder();
			sb.append("SELECT * FROM cidade WHERE codigo = ?");

			conn = new ConfiguraConexaoBancoDeDados().getConexao();

			ps = conn.prepareStatement(sb.toString());
			ps.setInt(1, cidade.getCodigo());

			rs = ps.executeQuery();

			while (rs.next()) {
				cidade = new Cidade();
				cidade.setCodigo(rs.getInt("CODIGO"));
				cidade.setNome(rs.getString("NOME"));

				Estado estado = new Estado();
				estado.setCodigo(rs.getInt("ESTADO_ID"));
				cidade.setEstado(estado);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			ConfiguraConexaoBancoDeDados.fecharConn(conn);
			ConfiguraConexaoBancoDeDados.fecharPS(ps);
			ConfiguraConexaoBancoDeDados.fecharRS(rs);
		}

		return cidade;
	}

	public Set<Cidade> buscarCidadesPorEstado(Estado estado) {
		Set<Cidade> cidades = new LinkedHashSet<Cidade>();

		try {
			sb = new StringBuilder();
			sb.append("SELECT c.* FROM cidade c, estado e ");
			sb.append("WHERE c.estado_id = e.codigo ");
			sb.append("AND e.codigo = ?");

			conn = new ConfiguraConexaoBancoDeDados().getConexao();

			ps = conn.prepareStatement(sb.toString());
			ps.setInt(1, estado.getCodigo());

			rs = ps.executeQuery();

			while (rs.next()) {
				Cidade cidade = new Cidade();
				cidade.setCodigo(rs.getInt("CODIGO"));
				cidade.setNome(rs.getString("NOME"));

				Estado newEstado = new Estado();
				estado.setCodigo(rs.getInt("ESTADO_ID"));
				cidade.setEstado(newEstado);

				cidades.add(cidade);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			ConfiguraConexaoBancoDeDados.fecharConn(conn);
			ConfiguraConexaoBancoDeDados.fecharPS(ps);
			ConfiguraConexaoBancoDeDados.fecharRS(rs);
		}
		
		return cidades;
	}
}