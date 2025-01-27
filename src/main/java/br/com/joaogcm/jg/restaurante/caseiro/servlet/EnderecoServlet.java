package br.com.joaogcm.jg.restaurante.caseiro.servlet;

import java.io.IOException;
import java.util.Set;
import java.util.logging.Logger;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import br.com.joaogcm.jg.restaurante.caseiro.model.Cliente;
import br.com.joaogcm.jg.restaurante.caseiro.model.Endereco;
import br.com.joaogcm.jg.restaurante.caseiro.model.Menu;
import br.com.joaogcm.jg.restaurante.caseiro.service.EnderecoService;
import br.com.joaogcm.jg.restaurante.caseiro.service.MenuService;

@WebServlet(name = "Endereco", urlPatterns = { "/Endereco" })
public class EnderecoServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	private static final Logger logger = Logger.getLogger(AutenticacaoServlet.class.getName());

	private Endereco endereco = null;

	private EnderecoService enderecoService = null;

	public EnderecoServlet() {
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String acao = request.getParameter("acao");

		/* Usado para deixar o item do submenu selecionado quando clicado */
		request.setAttribute("acao", acao);

		try {
			endereco = new Endereco();

			enderecoService = new EnderecoService();

			HttpSession sessaoCliente = request.getSession(false);
			Cliente cliente = (Cliente) sessaoCliente.getAttribute("clienteComCadastro");

			Set<Menu> menus = new MenuService().listarTodasUrlsSubMenu();
			request.setAttribute("menus", menus);

			if (acao.equalsIgnoreCase("listarEndereco")) {
				if (sessaoCliente != null && cliente != null && cliente.getPerfil() != null
						&& cliente.getPerfil().getCodigo() == 1) {
					endereco = enderecoService.buscarEnderecoPorCodigo(endereco);

					request.setAttribute("endereco", endereco);
					redirecionarParaPagina(request, response, "/paginas/endereco/listar-endereco.jsp", null, null);
				} else if (sessaoCliente != null && cliente != null && cliente.getPerfil() != null
						&& cliente.getPerfil().getCodigo() == 2) {
					request.setAttribute("enderecos", enderecoService.buscarTodosEnderecos());
					redirecionarParaPagina(request, response, "/paginas/endereco/listar-endereco.jsp", null, null);
				} else {
					redirecionarParaPagina(request, response, "/paginas/autenticacao/autenticar-login.jsp",
							"Você não está logado, faça o login!", "perigo");
				}
			} else if (acao.equalsIgnoreCase("cadastrarEndereco")) {
				if (sessaoCliente != null && cliente != null && cliente.getPerfil() != null
						&& cliente.getPerfil().getCodigo() == 2) {
					redirecionarParaPagina(request, response, "/paginas/endereco/cadastrar-endereco.jsp", null, null);
				} else {
					redirecionarParaPagina(request, response, "/paginas/endereco/cadastrar-endereco.jsp", null, null);
				}
			} else if (acao.equalsIgnoreCase("editarEndereco")) {
				if (sessaoCliente != null && cliente != null) {
					String codigo = request.getParameter("codigo");
					Integer codigoE = codigo != null && !codigo.isEmpty() ? Integer.parseInt(codigo) : null;

					endereco.setCodigo(codigoE);

					endereco = enderecoService.buscarEnderecoPorCodigo(endereco);

					if (endereco.getCodigo() != null) {
						request.setAttribute("endereco", endereco);
						redirecionarParaPagina(request, response, "/paginas/endereco/cadastrar-endereco.jsp",
								"Edite o endereço!", "sucesso");
					} else {
						request.setAttribute("enderecos", enderecoService.buscarTodosEnderecos());
						redirecionarParaPagina(request, response, "/paginas/endereco/listar-endereco.jsp",
								"Endereço não encontrado!", "perigo");
					}
				} else {
					redirecionarParaPagina(request, response, "/paginas/autenticacao/autenticar-login.jsp",
							"Você não está logado, faça o login!", "perigo");
				}
			} else if (acao.equalsIgnoreCase("removerEndereco")) {
				if (sessaoCliente != null && cliente != null) {
					String codigo = request.getParameter("codigo");
					Integer codigoC = codigo != null && !codigo.isEmpty() ? Integer.parseInt(codigo) : null;

					endereco.setCodigo(codigoC);

					endereco = enderecoService.buscarEnderecoPorCodigo(endereco);

					if (endereco.getCodigo() != null) {
						enderecoService.removerEnderecoPorCodigo(endereco);

						request.setAttribute("enderecos", enderecoService.buscarTodosEnderecos());
						redirecionarParaPagina(request, response, "/paginas/endereco/listar-endereco.jsp",
								"Endereço removido com sucesso!", "sucesso");
					} else {
						request.setAttribute("enderecos", enderecoService.buscarTodosEnderecos());
						redirecionarParaPagina(request, response, "/paginas/endereco/listar-endereco.jsp",
								"Não foi possível remover o endereço!", "perigo");
					}
				} else {
					redirecionarParaPagina(request, response, "/paginas/autenticacao/autenticar-login.jsp",
							"Você não está logado, faça o login!", "perigo");
				}
			}
		} catch (Exception e) {
			logger.severe("Erro ao processar a solicitação do endereço: " + e.getMessage());

			redirecionarParaPagina(request, response, "/error.jsp", "Erro ao processar a solicitação do endereço!",
					"erro");
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		try {

		} catch (Exception e) {
			logger.severe("Erro ao processar a solicitação do cliente: " + e.getMessage());

			redirecionarParaPagina(request, response, "/error.jsp", "Erro ao processar a solicitação do endereço!",
					"erro");
		}
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
	private void redirecionarParaPagina(HttpServletRequest request, HttpServletResponse response, String pagina,
			String mensagem, String tipoMensagem) throws ServletException, IOException {
		if (mensagem != null) {
			request.setAttribute("mensagem", mensagem);
		}

		if (tipoMensagem != null) {
			request.setAttribute("tipoMensagem", tipoMensagem);
		}

		RequestDispatcher requestDispatcher = request.getRequestDispatcher(pagina);
		requestDispatcher.forward(request, response);
	}
}