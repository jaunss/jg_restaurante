package br.com.joaogcm.jg.restaurante.caseiro.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import br.com.joaogcm.jg.restaurante.caseiro.configuration.connection.ConfiguraConexaoBancoDeDados;
import br.com.joaogcm.jg.restaurante.caseiro.model.Cliente;

public class AutenticacaoDAO {

	private StringBuilder sb = null;
	private PreparedStatement ps = null;
	private ResultSet rs = null;
	private Connection conn = null;

	public AutenticacaoDAO() {

	}

	public Cliente autenticarClientePorEmailESenha(String email) {
		Cliente cliente = null;

		try {
			sb = new StringBuilder();
			sb.append("SELECT email, senha FROM cliente WHERE email = ?");

			conn = new ConfiguraConexaoBancoDeDados().getConexao();

			ps = conn.prepareStatement(sb.toString());
			ps.setString(1, email);

			rs = ps.executeQuery();

			while (rs.next()) {
				cliente = new Cliente();
				cliente.setEmail(rs.getString("EMAIL"));
				cliente.setSenha(rs.getString("SENHA"));
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