package br.com.joaogcm.jg.restaurante.caseiro.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedHashSet;
import java.util.Set;

import br.com.joaogcm.jg.restaurante.caseiro.configuration.connection.ConfiguraConexaoBancoDeDados;
import br.com.joaogcm.jg.restaurante.caseiro.model.Endereco;

public class EnderecoDAO {

	private StringBuilder sb = null;
	private PreparedStatement ps = null;
	private ResultSet rs = null;
	private Connection conn = null;

	public EnderecoDAO() {

	}

	public void adicionarEndereco(Endereco endereco) {
		try {
			sb = new StringBuilder();
			sb.append(
					"INSERT INTO endereco (cep, logradouro, complemento, localidade, uf, bairro) VALUES (?, ?, ?, ?, ?, ?)");

			conn = new ConfiguraConexaoBancoDeDados().getConexao();

			ps = conn.prepareStatement(sb.toString());
			ps.setString(1, endereco.getCep());
			ps.setString(2, endereco.getLogradouro());
			ps.setString(3, endereco.getComplemento());
			ps.setString(4, endereco.getLocalidade());
			ps.setString(5, endereco.getUf());
			ps.setString(6, endereco.getBairro());

			ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			ConfiguraConexaoBancoDeDados.fecharConn(conn);
			ConfiguraConexaoBancoDeDados.fecharPS(ps);
		}
	}

	public void atualizarEnderecoPorCodigo(Endereco endereco) {
		try {
			sb = new StringBuilder();
			sb.append(
					"UPDATE endereco SET cep = ?, logradouro = ?, complemento = ?, localidade = ?, uf = ?, bairro = ? WHERE codigo = ?");

			conn = new ConfiguraConexaoBancoDeDados().getConexao();

			ps = conn.prepareStatement(sb.toString());
			ps.setString(1, endereco.getCep());
			ps.setString(2, endereco.getLogradouro());
			ps.setString(3, endereco.getComplemento());
			ps.setString(4, endereco.getLocalidade());
			ps.setString(5, endereco.getUf());
			ps.setString(6, endereco.getBairro());
			ps.setInt(7, endereco.getCodigo());

			ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			ConfiguraConexaoBancoDeDados.fecharConn(conn);
			ConfiguraConexaoBancoDeDados.fecharPS(ps);
		}
	}

	public void removerEnderecoPorCodigo(Endereco endereco) {
		try {
			sb = new StringBuilder();
			sb.append("DELETE FROM endereco WHERE codigo = ?");

			conn = new ConfiguraConexaoBancoDeDados().getConexao();

			ps = conn.prepareStatement(sb.toString());
			ps.setInt(1, endereco.getCodigo());

			ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			ConfiguraConexaoBancoDeDados.fecharConn(conn);
			ConfiguraConexaoBancoDeDados.fecharPS(ps);
		}
	}

	public Set<Endereco> buscarTodosEnderecos() {
		Set<Endereco> enderecos = new LinkedHashSet<Endereco>();

		try {
			sb = new StringBuilder();
			sb.append("SELECT * FROM endereco ORDER BY cep ASC");

			conn = new ConfiguraConexaoBancoDeDados().getConexao();

			ps = conn.prepareStatement(sb.toString());

			rs = ps.executeQuery();

			while (rs.next()) {
				Endereco endereco = new Endereco();
				endereco.setCodigo(rs.getInt("CODIGO"));
				endereco.setCep(rs.getString("CEP"));
				endereco.setLogradouro(rs.getString("LOGRADOURO"));
				endereco.setComplemento(rs.getString("COMPLEMENTO"));
				endereco.setLocalidade(rs.getString("LOCALIDADE"));
				endereco.setUf(rs.getString("UF"));
				endereco.setBairro(rs.getString("BAIRRO"));

				enderecos.add(endereco);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			ConfiguraConexaoBancoDeDados.fecharConn(conn);
			ConfiguraConexaoBancoDeDados.fecharPS(ps);
			ConfiguraConexaoBancoDeDados.fecharRS(rs);
		}

		return enderecos;
	}

	public Endereco buscarEnderecoPorCodigo(Endereco endereco) {
		try {
			sb = new StringBuilder();
			sb.append("SELECT * FROM endereco WHERE codigo = ?");

			conn = new ConfiguraConexaoBancoDeDados().getConexao();

			ps = conn.prepareStatement(sb.toString());
			ps.setInt(1, endereco.getCodigo());

			rs = ps.executeQuery();

			while (rs.next()) {
				endereco = new Endereco();
				endereco.setCodigo(rs.getInt("CODIGO"));
				endereco.setCep(rs.getString("CEP"));
				endereco.setLogradouro(rs.getString("LOGRADOURO"));
				endereco.setComplemento(rs.getString("COMPLEMENTO"));
				endereco.setLocalidade(rs.getString("LOCALIDADE"));
				endereco.setUf(rs.getString("UF"));
				endereco.setBairro(rs.getString("BAIRRO"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			ConfiguraConexaoBancoDeDados.fecharConn(conn);
			ConfiguraConexaoBancoDeDados.fecharPS(ps);
			ConfiguraConexaoBancoDeDados.fecharRS(rs);
		}

		return endereco;
	}

	public boolean isVerificarSeClienteJaPossuiOEndereco(Endereco endereco) {
		boolean isClientePossuiOEndereco = false;

		try {
			sb = new StringBuilder();
			sb.append("SELECT c.* FROM cliente c, endereco e ");
			sb.append("WHERE c.endereco_id = e.codigo ");
			sb.append("AND e.codigo = ?");

			conn = new ConfiguraConexaoBancoDeDados().getConexao();

			ps = conn.prepareStatement(sb.toString());
			ps.setInt(1, endereco.getCodigo());

			rs = ps.executeQuery();

			while (rs.next()) {
				isClientePossuiOEndereco = true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			ConfiguraConexaoBancoDeDados.fecharConn(conn);
			ConfiguraConexaoBancoDeDados.fecharPS(ps);
			ConfiguraConexaoBancoDeDados.fecharRS(rs);
		}

		return isClientePossuiOEndereco;
	}
}