package br.com.joaogcm.jg.restaurante.caseiro.util;

import java.io.IOException;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ValidacaoUtil {

	// Ação
	private static final String ACAO = "acao";

	// Home
	private static final String ACAO_HOME = "home";

	// Páginas de Home
	private static final String PAGINA_HOME = "/index.jsp";

	// Páginas de Error
	private static final String PAGINA_ERROR = "/error.jsp";

	// Cliente
	private static final String ACAO_LISTAR_CLIENTE = "listarCliente";
	private static final String ACAO_CADASTRAR_CLIENTE = "cadastrarCliente";
	private static final String ACAO_EDITAR_CLIENTE = "editarCliente";
	private static final String ACAO_REMOVER_CLIENTE = "removerCliente";

	// Páginas de Cliente
	private static final String PAGINA_LISTAR_CLIENTE = "/paginas/cliente/listar-cliente.jsp";
	private static final String PAGINA_CADASTRAR_CLIENTE = "/paginas/cliente/cadastrar-cliente.jsp";

	// Lanche
	private static final String ACAO_LISTAR_LANCHE = "listarCliente";
	private static final String ACAO_CADASTRAR_LANCHE = "cadastrarCliente";
	private static final String ACAO_EDITAR_LANCHE = "editarCliente";
	private static final String ACAO_REMOVER_LANCHE = "removerCliente";

	// Páginas de Lanche
	private static final String PAGINA_LISTAR_LANCHE = "/paginas/lanche/listar-lanche.jsp";
	private static final String PAGINA_CADASTRAR_LANCHE = "/paginas/lanche/cadastrar-lanche.jsp";

	// Pedido
	private static final String ACAO_LISTAR_PEDIDO = "listarPedido";
	private static final String ACAO_CADASTRAR_PEDIDO = "cadastrarPedido";
	private static final String ACAO_EDITAR_PEDIDO = "editarPedido";
	private static final String ACAO_REMOVER_PEDIDO = "removerPedido";

	// Páginas de Pedido
	private static final String PAGINA_LISTAR_PEDIDO = "/paginas/pedido/listar-pedido.jsp";
	private static final String PAGINA_CADASTRAR_PEDIDO = "/paginas/pedido/cadastrar-pedido.jsp";

	// Endereço
	private static final String ACAO_LISTAR_ENDERECO = "listarEndereco";
	private static final String ACAO_CADASTRAR_ENDERECO = "cadastrarEndereco";
	private static final String ACAO_EDITAR_ENDERECO = "editarEndereco";
	private static final String ACAO_REMOVER_ENDERECO = "removerEndereco";

	// Páginas de Endereço
	private static final String PAGINA_LISTAR_ENDERECO = "/paginas/endereco/listar-endereco.jsp";
	private static final String PAGINA_CADASTRAR_ENDERECO = "/paginas/endereco/cadastrar-endereco.jsp";

	// Autenticação
	private static final String ACAO_AUTENTICAR_CLIENTE = "autenticarCliente";
	private static final String ACAO_DESLOGAR_CLIENTE = "deslogarCliente";

	// Paáginas de Autenticação
	private static final String PAGINA_AUTENTICAR_CLIENTE = "/paginas/autenticacao/autenticar-login.jsp";

	/**
	 * Fecha a conexão do Connection.
	 * 
	 * @param conn
	 */
	public void fecharConn(Connection conn) {
		try {
			if (conn != null) {
				conn.close();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Fecha a conexão do PreparedStatement.
	 * 
	 * @param ps
	 */
	public void fecharPS(PreparedStatement ps) {
		try {
			if (ps != null) {
				ps.close();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Fecha a conexão do ResultSet.
	 * 
	 * @param rs
	 */
	public void fecharRS(ResultSet rs) {
		try {
			if (rs != null) {
				rs.close();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Valida o parâmetro do tipo Integer.
	 * 
	 * @param request
	 * @param parametro
	 * @return
	 */
	public Integer getParametroInteger(HttpServletRequest request, String parametro) {
		String valor = request.getParameter(parametro);
		return valor != null && !valor.isEmpty() ? Integer.parseInt(valor) : null;
	}

	/**
	 * Valida o parâmetro do tipo String.
	 * 
	 * @param request
	 * @param parametro
	 * @return
	 */
	public String getParametroString(HttpServletRequest request, String parametro) {
		String valor = request.getParameter(parametro);
		return valor != null && !valor.isEmpty() ? valor : null;
	}

	/**
	 * Valida o parâmetro do tipo BigDecimal.
	 * 
	 * @param request
	 * @param parametro
	 * @return
	 */
	public BigDecimal getParametroBigDecimal(HttpServletRequest request, String parametro) {
		String valor = request.getParameter(parametro);
		return valor != null && !valor.isEmpty() ? new BigDecimal(valor) : null;
	}

	/**
	 * Valida o parâmetro do tipo LocalDateTime.
	 * 
	 * @param request
	 * @param parametro
	 * @return
	 */
	public LocalDateTime getParametroLocalDateTime(HttpServletRequest request, String parametro) {
		String valor = request.getParameter(parametro);
		return valor != null && !valor.isEmpty() ? LocalDateTime.parse(valor) : null;
	}

	public String[] getParametroArrayString(HttpServletRequest request, String parametro) {
		String[] valor = request.getParameterValues(parametro);
		return valor != null ? valor : null;
	}

	/**
	 * Redireciona para determinadas páginas incluindo mensagem e o tipo da
	 * mensagem.
	 * 
	 * @param request
	 * @param response
	 * @param pagina
	 * @param mensagem
	 * @param tipoMensagem
	 * @throws ServletException
	 * @throws IOException
	 */
	public void redirecionarParaAPagina(HttpServletRequest request, HttpServletResponse response, String pagina,
			String mensagem, String tipoMensagem) throws ServletException, IOException {
		if (mensagem != null) {
			request.setAttribute("mensagem", mensagem);
		}

		if (tipoMensagem != null) {
			request.setAttribute("tipoMensagem", tipoMensagem);
		}

		RequestDispatcher rd = request.getRequestDispatcher(pagina);
		rd.forward(request, response);
	}

	public static String getAcao() {
		return ACAO;
	}

	public static String getAcaoHome() {
		return ACAO_HOME;
	}

	public static String getPaginaHome() {
		return PAGINA_HOME;
	}

	public static String getPaginaError() {
		return PAGINA_ERROR;
	}

	public static String getAcaoListarCliente() {
		return ACAO_LISTAR_CLIENTE;
	}

	public static String getAcaoCadastrarCliente() {
		return ACAO_CADASTRAR_CLIENTE;
	}

	public static String getAcaoEditarCliente() {
		return ACAO_EDITAR_CLIENTE;
	}

	public static String getAcaoRemoverCliente() {
		return ACAO_REMOVER_CLIENTE;
	}

	public static String getPaginaListarCliente() {
		return PAGINA_LISTAR_CLIENTE;
	}

	public static String getPaginaCadastrarCliente() {
		return PAGINA_CADASTRAR_CLIENTE;
	}

	public static String getAcaoListarLanche() {
		return ACAO_LISTAR_LANCHE;
	}

	public static String getAcaoCadastrarLanche() {
		return ACAO_CADASTRAR_LANCHE;
	}

	public static String getAcaoEditarLanche() {
		return ACAO_EDITAR_LANCHE;
	}

	public static String getAcaoRemoverLanche() {
		return ACAO_REMOVER_LANCHE;
	}

	public static String getPaginaListarLanche() {
		return PAGINA_LISTAR_LANCHE;
	}

	public static String getPaginaCadastrarLanche() {
		return PAGINA_CADASTRAR_LANCHE;
	}

	public static String getAcaoListarPedido() {
		return ACAO_LISTAR_PEDIDO;
	}

	public static String getAcaoCadastrarPedido() {
		return ACAO_CADASTRAR_PEDIDO;
	}

	public static String getAcaoEditarPedido() {
		return ACAO_EDITAR_PEDIDO;
	}

	public static String getAcaoRemoverPedido() {
		return ACAO_REMOVER_PEDIDO;
	}

	public static String getPaginaListarPedido() {
		return PAGINA_LISTAR_PEDIDO;
	}

	public static String getPaginaCadastrarPedido() {
		return PAGINA_CADASTRAR_PEDIDO;
	}

	public static String getAcaoListarEndereco() {
		return ACAO_LISTAR_ENDERECO;
	}

	public static String getAcaoCadastrarEndereco() {
		return ACAO_CADASTRAR_ENDERECO;
	}

	public static String getAcaoEditarEndereco() {
		return ACAO_EDITAR_ENDERECO;
	}

	public static String getAcaoRemoverEndereco() {
		return ACAO_REMOVER_ENDERECO;
	}

	public static String getPaginaListarEndereco() {
		return PAGINA_LISTAR_ENDERECO;
	}

	public static String getPaginaCadastrarEndereco() {
		return PAGINA_CADASTRAR_ENDERECO;
	}

	public static String getAcaoAutenticarCliente() {
		return ACAO_AUTENTICAR_CLIENTE;
	}

	public static String getAcaoDeslogarCliente() {
		return ACAO_DESLOGAR_CLIENTE;
	}

	public static String getPaginaAutenticarCliente() {
		return PAGINA_AUTENTICAR_CLIENTE;
	}
}