package br.com.joaogcm.jg.restaurante.caseiro.dao;

import java.math.RoundingMode;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.Set;

import br.com.joaogcm.jg.restaurante.caseiro.configuration.connection.ConfiguraConexaoBancoDeDados;
import br.com.joaogcm.jg.restaurante.caseiro.model.Lanche;

public class LancheDAO {

	private StringBuilder sb = null;
	private PreparedStatement ps = null;
	private ResultSet rs = null;
	private Connection conn = null;

	public LancheDAO() {

	}

	public void adicionarLanche(Lanche lanche) {
		try {
			sb = new StringBuilder();
			sb.append("INSERT INTO lanche (nome, descricao_conteudo, preco) VALUES (?, ?, ?)");

			conn = new ConfiguraConexaoBancoDeDados().getConexao();

			PreparedStatement ps = conn.prepareStatement(sb.toString());

			ps.setString(1, lanche.getNome());
			ps.setString(2, lanche.getDescricao_conteudo());
			ps.setBigDecimal(3, lanche.getPreco().setScale(2, RoundingMode.HALF_UP));

			ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			ConfiguraConexaoBancoDeDados.fecharConn(conn);
			ConfiguraConexaoBancoDeDados.fecharPS(ps);
		}
	}

	public void atualizarLanchePorCodigo(Lanche lanche) {
		try {
			sb = new StringBuilder();
			sb.append("UPDATE lanche SET nome = ?, descricao_conteudo = ?, preco = ? WHERE codigo = ?");

			conn = new ConfiguraConexaoBancoDeDados().getConexao();

			PreparedStatement ps = conn.prepareStatement(sb.toString());

			ps.setString(1, lanche.getNome());
			ps.setString(2, lanche.getDescricao_conteudo());
			ps.setBigDecimal(3, lanche.getPreco().setScale(2, RoundingMode.HALF_UP));
			ps.setInt(4, lanche.getCodigo());

			ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			ConfiguraConexaoBancoDeDados.fecharConn(conn);
			ConfiguraConexaoBancoDeDados.fecharPS(ps);
		}
	}

	public void removerLanchePorCodigo(Lanche lanche) {
		try {
			sb = new StringBuilder();
			sb.append("DELETE FROM lanche WHERE codigo = ?");

			conn = new ConfiguraConexaoBancoDeDados().getConexao();

			PreparedStatement ps = conn.prepareStatement(sb.toString());
			ps.setInt(1, lanche.getCodigo());

			ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			ConfiguraConexaoBancoDeDados.fecharConn(conn);
			ConfiguraConexaoBancoDeDados.fecharPS(ps);
		}
	}

	public Set<Lanche> buscarTodosLanches() {
		Set<Lanche> lanches = new HashSet<Lanche>();

		try {
			sb = new StringBuilder();
			sb.append("SELECT * FROM lanche");

			conn = new ConfiguraConexaoBancoDeDados().getConexao();

			PreparedStatement ps = conn.prepareStatement(sb.toString());
			ResultSet rs = ps.executeQuery();

			while (rs.next()) {
				Lanche lanche = new Lanche();
				lanche.setCodigo(rs.getInt("CODIGO"));
				lanche.setNome(rs.getString("NOME"));
				lanche.setDescricao_conteudo(rs.getString("DESCRICAO_CONTEUDO"));
				lanche.setPreco(rs.getBigDecimal("PRECO"));

				lanches.add(lanche);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			ConfiguraConexaoBancoDeDados.fecharConn(conn);
			ConfiguraConexaoBancoDeDados.fecharPS(ps);
			ConfiguraConexaoBancoDeDados.fecharRS(rs);
		}

		return lanches;
	}

	public Lanche buscarLanchePorCodigo(Integer codigo) {
		Lanche lanche = null;
		
		try {
			sb = new StringBuilder();
			sb.append("SELECT * FROM lanche WHERE codigo = ?");
			
			conn = new ConfiguraConexaoBancoDeDados().getConexao();

			ps = conn.prepareStatement(sb.toString());
			ps.setInt(1, codigo);

			rs = ps.executeQuery();

			while (rs.next()) {
				lanche = new Lanche();
				lanche.setCodigo(rs.getInt("CODIGO"));
				lanche.setNome(rs.getString("NOME"));
				lanche.setDescricao_conteudo(rs.getString("DESCRICAO_CONTEUDO"));
				lanche.setPreco(rs.getBigDecimal("PRECO"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			ConfiguraConexaoBancoDeDados.fecharConn(conn);
			ConfiguraConexaoBancoDeDados.fecharPS(ps);
			ConfiguraConexaoBancoDeDados.fecharRS(rs);
		}

		return lanche;
	}
}