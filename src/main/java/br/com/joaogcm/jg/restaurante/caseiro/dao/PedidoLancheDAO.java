package br.com.joaogcm.jg.restaurante.caseiro.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedHashSet;
import java.util.Set;

import br.com.joaogcm.jg.restaurante.caseiro.configuration.connection.ConfiguraConexaoBancoDeDados;
import br.com.joaogcm.jg.restaurante.caseiro.model.Lanche;
import br.com.joaogcm.jg.restaurante.caseiro.model.Pedido;

public class PedidoLancheDAO {

	private StringBuilder sb = null;
	private PreparedStatement ps = null;
	private ResultSet rs = null;
	private Connection conn = null;

	public PedidoLancheDAO() {

	}

	public void adicionarPedidoLanche(Pedido pedido, Lanche lanche) {
		try {
			sb = new StringBuilder();
			sb.append("INSERT INTO pedido_lanche (codigo_pedido, codigo_lanche) VALUES (?, ?)");

			conn = new ConfiguraConexaoBancoDeDados().getConexao();

			ps = conn.prepareStatement(sb.toString());
			ps.setInt(1, pedido.getCodigo());
			ps.setInt(2, lanche.getCodigo());

			ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			ConfiguraConexaoBancoDeDados.fecharConn(conn);
			ConfiguraConexaoBancoDeDados.fecharPS(ps);
		}
	}

	public void removerPedidoLanchePorCodigoDoPedido(Pedido pedido) {
		try {
			sb = new StringBuilder();
			sb.append("DELETE FROM pedido_lanche WHERE codigo_pedido = ?");

			conn = new ConfiguraConexaoBancoDeDados().getConexao();

			ps = conn.prepareStatement(sb.toString());
			ps.setInt(1, pedido.getCodigo());

			ps.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			ConfiguraConexaoBancoDeDados.fecharConn(conn);
			ConfiguraConexaoBancoDeDados.fecharPS(ps);
		}
	}

	public Set<Lanche> buscarTodosOsLanchesDeUmDeterminadoPedido(Pedido pedido) {
		Set<Lanche> lanches = new LinkedHashSet<Lanche>();

		try {
			sb = new StringBuilder();
			sb.append("SELECT l.* FROM lanche l, pedido p, pedido_lanche pl, ");
			sb.append("AND pl.codigo_lanche = l.codigo ");
			sb.append("WHERE pl.codigo_pedido = p.codigo ");
			sb.append("AND p.codigo = ? ");
			sb.append("ORDER BY UPPER(l.nome) ASC");

			conn = new ConfiguraConexaoBancoDeDados().getConexao();

			ps = conn.prepareStatement(sb.toString());
			ps.setInt(1, pedido.getCodigo());

			rs = ps.executeQuery();

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
}