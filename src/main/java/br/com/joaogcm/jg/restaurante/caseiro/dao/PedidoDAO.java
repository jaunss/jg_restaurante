package br.com.joaogcm.jg.restaurante.caseiro.dao;

import java.math.RoundingMode;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.LinkedHashSet;
import java.util.Set;

import br.com.joaogcm.jg.restaurante.caseiro.configuration.connection.ConfiguraConexaoBancoDeDados;
import br.com.joaogcm.jg.restaurante.caseiro.model.Cliente;
import br.com.joaogcm.jg.restaurante.caseiro.model.Pedido;

public class PedidoDAO {

	private StringBuilder sb = null;
	private PreparedStatement ps = null;
	private ResultSet rs = null;
	private Connection conn = null;

	public PedidoDAO() {

	}

	public void adicionarPedido(Pedido pedido) {
		try {
			sb = new StringBuilder();
			sb.append("INSERT INTO pedido (data_pedido, cliente_id, total, observacao) VALUES (?, ?, ?, ?)");

			conn = new ConfiguraConexaoBancoDeDados().getConexao();

			ps = conn.prepareStatement(sb.toString());

			LocalDateTime dataAtual = LocalDateTime.now();
			Timestamp dataPedidoConvertido = Timestamp.valueOf(dataAtual);

			ps.setTimestamp(1, dataPedidoConvertido);
			ps.setInt(2, pedido.getCliente().getCodigo());
			ps.setBigDecimal(3, pedido.getTotal().setScale(2, RoundingMode.HALF_UP));
			ps.setString(4, pedido.getObservacao());

			ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			ConfiguraConexaoBancoDeDados.fecharConn(conn);
			ConfiguraConexaoBancoDeDados.fecharPS(ps);
		}
	}

	public void atualizarPedidoPorCodigo(Pedido pedido) {
		try {
			sb = new StringBuilder();
			sb.append("UPDATE pedido SET data_pedido = ?, cliente_id = ?, total = ?, observacao = ? WHERE codigo = ?");

			conn = new ConfiguraConexaoBancoDeDados().getConexao();

			ps = conn.prepareStatement(sb.toString());

			LocalDateTime dataAtual = LocalDateTime.now();
			Timestamp dataPedidoConvertido = Timestamp.valueOf(dataAtual);

			ps.setTimestamp(1, dataPedidoConvertido);
			ps.setInt(2, pedido.getCliente().getCodigo());
			ps.setBigDecimal(3, pedido.getTotal().setScale(2, RoundingMode.HALF_UP));
			ps.setString(4, pedido.getObservacao());
			ps.setInt(5, pedido.getCodigo());

			ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			ConfiguraConexaoBancoDeDados.fecharConn(conn);
			ConfiguraConexaoBancoDeDados.fecharPS(ps);
		}
	}

	public void removerPedidoPorCodigo(Pedido pedido) {
		try {
			sb = new StringBuilder();
			sb.append("DELETE FROM pedido WHERE codigo = ?");

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

	public Set<Pedido> buscarTodosPedidos() {
		Set<Pedido> pedidos = new LinkedHashSet<Pedido>();

		try {
			sb = new StringBuilder();
			sb.append("SELECT * FROM pedido ORDER BY data_pedido ASC");

			conn = new ConfiguraConexaoBancoDeDados().getConexao();

			ps = conn.prepareStatement(sb.toString());

			rs = ps.executeQuery();

			while (rs.next()) {
				Pedido pedido = new Pedido();
				Cliente cliente = new Cliente();

				pedido.setCodigo(rs.getInt("CODIGO"));
				pedido.setDataPedido(rs.getTimestamp("data_pedido").toLocalDateTime());

				pedido.setCliente(cliente);
				pedido.getCliente().setCodigo(rs.getInt("CLIENTE_ID"));
				pedido.setTotal(rs.getBigDecimal("TOTAL"));
				pedido.setObservacao(rs.getString("OBSERVACAO"));

				pedidos.add(pedido);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			ConfiguraConexaoBancoDeDados.fecharConn(conn);
			ConfiguraConexaoBancoDeDados.fecharPS(ps);
			ConfiguraConexaoBancoDeDados.fecharRS(rs);
		}

		return pedidos;
	}

	public Pedido buscarPedidoPorCodigo(Pedido pedido) {
		try {
			sb = new StringBuilder();
			sb.append("SELECT * FROM pedido WHERE codigo = ?");

			conn = new ConfiguraConexaoBancoDeDados().getConexao();

			ps = conn.prepareStatement(sb.toString());
			ps.setInt(1, pedido.getCodigo());

			rs = ps.executeQuery();

			while (rs.next()) {
				pedido = new Pedido();
				Cliente cliente = new Cliente();

				pedido.setCodigo(rs.getInt("CODIGO"));
				pedido.setDataPedido(rs.getTimestamp("data_pedido").toLocalDateTime());

				pedido.setCliente(cliente);
				pedido.getCliente().setCodigo(rs.getInt("CLIENTE_ID"));
				pedido.setTotal(rs.getBigDecimal("TOTAL"));
				pedido.setObservacao(rs.getString("OBSERVACAO"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			ConfiguraConexaoBancoDeDados.fecharConn(conn);
			ConfiguraConexaoBancoDeDados.fecharPS(ps);
			ConfiguraConexaoBancoDeDados.fecharRS(rs);
		}

		return pedido;
	}

	public Set<Pedido> buscarPedidoPorCliente(Cliente cliente) {
		Set<Pedido> pedidos = new LinkedHashSet<Pedido>();

		try {
			sb = new StringBuilder();
			sb.append("SELECT p.* FROM pedido p, cliente c ");
			sb.append("WHERE p.cliente_id = c.codigo ");
			sb.append("AND c.codigo = ? ");
			sb.append("ORDER BY p.data_pedido ASC");

			conn = new ConfiguraConexaoBancoDeDados().getConexao();

			ps = conn.prepareStatement(sb.toString());
			ps.setInt(1, cliente.getCodigo());

			rs = ps.executeQuery();

			while (rs.next()) {
				Pedido pedido = new Pedido();
				pedido.setCodigo(rs.getInt("CODIGO"));
				pedido.setDataPedido(rs.getTimestamp("DATA_PEDIDO").toLocalDateTime());

				pedido.setCliente(cliente);
				pedido.getCliente().setCodigo(rs.getInt("CLIENTE_ID"));

				pedido.setTotal(rs.getBigDecimal("TOTAL"));
				pedido.setObservacao(rs.getString("OBSERVACAO"));

				pedidos.add(pedido);
			}
		} catch (SQLException e) {

		} finally {
			ConfiguraConexaoBancoDeDados.fecharConn(conn);
			ConfiguraConexaoBancoDeDados.fecharPS(ps);
			ConfiguraConexaoBancoDeDados.fecharRS(rs);
		}

		return pedidos;
	}
}