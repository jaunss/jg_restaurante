package br.com.joaogcm.jg.restaurante.caseiro.configuration.connection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Properties;

public class ConfiguraConexaoBancoDeDados {

	private Connection conn = null;

	public synchronized Connection getConexao() {
		Properties properties = new Properties();

		try {
			if (conn == null) {
				ClassLoader classLoader = ConfiguraConexaoBancoDeDados.class.getClassLoader();

				properties.load(classLoader.getResourceAsStream("database.properties"));
				String url = properties.getProperty("URL");
				String usuario = properties.getProperty("USUARIO");
				String senha = properties.getProperty("SENHA");

				Class.forName("com.mysql.cj.jdbc.Driver");
				conn = DriverManager.getConnection(url, usuario, senha);
			}
		} catch (Exception e) {
			throw new RuntimeException("Não foi possível se conectar ao banco de dados!");
		}

		return conn;
	}

	public static void fecharConn(Connection conn) {
		try {
			if (conn != null) {
				conn.close();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public static void fecharPS(PreparedStatement ps) {
		try {
			if (ps != null) {
				ps.close();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public static void fecharRS(ResultSet rs) {
		try {
			if (rs != null) {
				rs.close();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}