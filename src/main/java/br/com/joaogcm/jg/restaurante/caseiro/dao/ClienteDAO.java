package br.com.joaogcm.jg.restaurante.caseiro.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.Set;

import br.com.joaogcm.jg.restaurante.caseiro.configuration.connection.ConfiguraConexaoBancoDeDados;
import br.com.joaogcm.jg.restaurante.caseiro.model.Cliente;
import br.com.joaogcm.jg.restaurante.caseiro.model.Perfil;

public class ClienteDAO {

	private StringBuilder sb = null;
	private PreparedStatement ps = null;
	private ResultSet rs = null;
	private Connection conn = null;

	public ClienteDAO() {

	}

	public void adicionarCliente(Cliente cliente) {
		try {
			sb = new StringBuilder();
			sb.append("INSERT INTO cliente (nome, email, telefone, cpf, senha, perfil_id) VALUES (?, ?, ?, ?, ?, ?)");

			conn = new ConfiguraConexaoBancoDeDados().getConexao();

			ps = conn.prepareStatement(sb.toString());
			ps.setString(1, cliente.getNome());
			ps.setString(2, cliente.getEmail());
			ps.setString(3, cliente.getTelefone());
			ps.setString(4, cliente.getCpf());
			ps.setString(5, cliente.getSenha());
			ps.setInt(6, cliente.getPerfil().getCodigo());

			ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			ConfiguraConexaoBancoDeDados.fecharConn(conn);
			ConfiguraConexaoBancoDeDados.fecharPS(ps);
		}
	}

	public void atualizarClientePorCodigo(Cliente cliente) {
		try {
			sb = new StringBuilder();
			sb.append(
					"UPDATE cliente SET nome = ?, email = ?, telefone = ?, cpf = ?, senha = ?, perfil_id = ? WHERE codigo = ?");

			conn = new ConfiguraConexaoBancoDeDados().getConexao();

			ps = conn.prepareStatement(sb.toString());
			ps.setString(1, cliente.getNome());
			ps.setString(2, cliente.getEmail());
			ps.setString(3, cliente.getTelefone());
			ps.setString(4, cliente.getCpf());
			ps.setString(5, cliente.getSenha());
			ps.setInt(6, cliente.getPerfil().getCodigo());
			ps.setInt(7, cliente.getCodigo());

			ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			ConfiguraConexaoBancoDeDados.fecharConn(conn);
			ConfiguraConexaoBancoDeDados.fecharPS(ps);
		}
	}

	public void removerClientePorCodigo(Cliente cliente) {
		try {
			sb = new StringBuilder();
			sb.append("DELETE FROM cliente WHERE codigo = ?");

			conn = new ConfiguraConexaoBancoDeDados().getConexao();

			ps = conn.prepareStatement(sb.toString());
			ps.setInt(1, cliente.getCodigo());

			ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			ConfiguraConexaoBancoDeDados.fecharConn(conn);
			ConfiguraConexaoBancoDeDados.fecharPS(ps);
		}
	}

	public Set<Cliente> buscarTodosClientes() {
		Set<Cliente> clientes = new HashSet<Cliente>();

		try {
			sb = new StringBuilder();
			sb.append("SELECT c.* FROM cliente c, perfil p WHERE c.perfil_id = p.codigo");

			conn = new ConfiguraConexaoBancoDeDados().getConexao();

			ps = conn.prepareStatement(sb.toString());

			rs = ps.executeQuery();

			while (rs.next()) {
				Cliente cliente = new Cliente();
				cliente.setCodigo(rs.getInt("CODIGO"));
				cliente.setNome(rs.getString("NOME"));
				cliente.setEmail(rs.getString("EMAIL"));
				cliente.setTelefone(rs.getString("TELEFONE"));

				Perfil perfil = new Perfil();
				cliente.setPerfil(perfil);

				cliente.getPerfil().setCodigo(rs.getInt("PERFIL_ID"));

				clientes.add(cliente);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			ConfiguraConexaoBancoDeDados.fecharConn(conn);
			ConfiguraConexaoBancoDeDados.fecharPS(ps);
			ConfiguraConexaoBancoDeDados.fecharRS(rs);
		}

		return clientes;
	}

	public Cliente buscarClientePorCodigo(Cliente cliente) {
		try {
			sb = new StringBuilder();
			sb.append("SELECT c.* FROM cliente c, perfil p WHERE c.perfil_id = p.codigo AND c.codigo = ?");

			conn = new ConfiguraConexaoBancoDeDados().getConexao();

			ps = conn.prepareStatement(sb.toString());
			ps.setInt(1, cliente.getCodigo());

			rs = ps.executeQuery();

			while (rs.next()) {
				cliente = new Cliente();
				cliente.setCodigo(rs.getInt("CODIGO"));
				cliente.setNome(rs.getString("NOME"));
				cliente.setEmail(rs.getString("EMAIL"));
				cliente.setTelefone(rs.getString("TELEFONE"));
				cliente.setCpf(rs.getString("CPF"));

				Perfil perfil = new Perfil();
				cliente.setPerfil(perfil);

				cliente.getPerfil().setCodigo(rs.getInt("PERFIL_ID"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			ConfiguraConexaoBancoDeDados.fecharConn(conn);
			ConfiguraConexaoBancoDeDados.fecharPS(ps);
			ConfiguraConexaoBancoDeDados.fecharRS(rs);
		}

		return cliente;
	}
}