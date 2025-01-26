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

import br.com.joaogcm.jg.restaurante.caseiro.argon.ArgonUtil;
import br.com.joaogcm.jg.restaurante.caseiro.model.Cliente;
import br.com.joaogcm.jg.restaurante.caseiro.model.Menu;
import br.com.joaogcm.jg.restaurante.caseiro.service.AutenticacaoService;
import br.com.joaogcm.jg.restaurante.caseiro.service.MenuService;

@WebServlet(name = "Autenticacao", urlPatterns = { "/Autenticacao" })
public class AutenticacaoServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	private static final Logger logger = Logger.getLogger(AutenticacaoServlet.class.getName());

	private Cliente cliente = null;

	private AutenticacaoService autenticacaoService = null;
	private MenuService menuService = null;

	public AutenticacaoServlet() {
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String acao = request.getParameter("acao");

		/* Usado para deixar o item do submenu selecionado quando clicado */
		request.setAttribute("acao", acao);

		try {
			cliente = new Cliente();

			autenticacaoService = new AutenticacaoService();
			menuService = new MenuService();

			HttpSession sessaoCliente = request.getSession(false);
			Cliente cliente = (Cliente) sessaoCliente.getAttribute("clienteComCadastro");

			Set<Menu> menus = menuService.listarTodasUrlsSubMenu();
			request.setAttribute("menus", menus);

			if (acao.equalsIgnoreCase("autenticarCliente")) {
				redirecionarParaPagina(request, response, "/paginas/autenticacao/autenticar-login.jsp",
						"Insira os dados para autenticar!", "perigo");
			} else if (acao.equalsIgnoreCase("deslogarCliente")) {
				if (sessaoCliente != null && cliente != null) {
					sessaoCliente.invalidate();

					redirecionarParaPagina(request, response, "/paginas/autenticacao/autenticar-login.jsp",
							"Já vai? :( Um até logo e retorne novamente :)", "perigo");
				} else {
					redirecionarParaPagina(request, response, "/error.jsp",
							"Erro ao processar a solicitação de encerramento da sessão do cliente!", "erro");
				}
			}
		} catch (Exception e) {
			logger.severe("Erro ao processar a solicitação de autenticação: " + e.getMessage());

			redirecionarParaPagina(request, response, "/error.jsp", "Erro ao processar a solicitação de autenticação!",
					"erro");
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		try {
			cliente = new Cliente();

			autenticacaoService = new AutenticacaoService();

			Set<Menu> menus = new MenuService().listarTodasUrlsSubMenu();
			request.setAttribute("menus", menus);

			String email = request.getParameter("email");
			String senha = request.getParameter("senha");

			cliente.setEmail(email != null && !email.isEmpty() ? email : null);
			cliente.setSenha(senha != null && !senha.isEmpty() ? senha.trim() : null);

			Cliente clienteComCadastro = autenticacaoService.autenticarClientePorEmailESenha(cliente.getEmail());
			if (clienteComCadastro != null) {
				if (ArgonUtil.verificarSenhaHash(cliente.getSenha(), clienteComCadastro.getSenha())) {
					HttpSession sessaoUsuario = request.getSession(true);

					sessaoUsuario.setAttribute("clienteComCadastro", clienteComCadastro);
					sessaoUsuario.setMaxInactiveInterval(1800);

					redirecionarParaPagina(request, response, "/index.jsp",
							"Olá " + clienteComCadastro.getNome() + ", seja bem-vindo ao JG Restaurante :)", "sucesso");
				} else {
					redirecionarParaPagina(request, response, "/paginas/autenticacao/autenticar-login.jsp",
							"Email e/ou Senha incorretos!", "perigo");
				}
			} else {
				redirecionarParaPagina(request, response, "/paginas/cliente/cadastrar-cliente.jsp",
						"Você ainda não tem um cadastro, faça um!", "perigo");
			}
		} catch (Exception e) {
			logger.severe("Erro ao processar a solicitação de autenticação: " + e.getMessage());

			redirecionarParaPagina(request, response, "/error.jsp", "Erro ao processar a solicitação de autenticação!",
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