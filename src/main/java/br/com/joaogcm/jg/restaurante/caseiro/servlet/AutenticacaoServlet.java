package br.com.joaogcm.jg.restaurante.caseiro.servlet;

import java.io.IOException;
import java.util.Set;
import java.util.logging.Logger;

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
import br.com.joaogcm.jg.restaurante.caseiro.util.ValidacaoUtil;

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
		String acao = new ValidacaoUtil().getParametroString(request, "acao");

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

			if (acao.equalsIgnoreCase(ValidacaoUtil.getAcaoAutenticarCliente())) {
				new ValidacaoUtil().redirecionarParaAPagina(request, response,
						ValidacaoUtil.getPaginaAutenticarCliente(), "Insira os dados para autenticar!", "perigo");
			} else if (acao.equalsIgnoreCase(ValidacaoUtil.getAcaoDeslogarCliente())) {
				if (sessaoCliente != null && cliente != null) {
					sessaoCliente.invalidate();

					new ValidacaoUtil().redirecionarParaAPagina(request, response,
							ValidacaoUtil.getPaginaAutenticarCliente(), "Já vai? :( Um até logo e retorne novamente :)",
							"perigo");
				} else {
					new ValidacaoUtil().redirecionarParaAPagina(request, response, ValidacaoUtil.getPaginaError(),
							"Erro ao processar a solicitação de encerramento da sessão do cliente!", "erro");
				}
			}
		} catch (Exception e) {
			logger.severe("Erro ao processar a solicitação de autenticação: " + e.getMessage());

			new ValidacaoUtil().redirecionarParaAPagina(request, response, ValidacaoUtil.getPaginaError(),
					"Erro ao processar a solicitação de autenticação!", "erro");
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

					new ValidacaoUtil().redirecionarParaAPagina(request, response, ValidacaoUtil.getPaginaHome(),
							"Olá " + clienteComCadastro.getNome() + ", seja bem-vindo ao JG Restaurante :)", "sucesso");
				} else {
					new ValidacaoUtil().redirecionarParaAPagina(request, response,
							ValidacaoUtil.getPaginaAutenticarCliente(), "Email e/ou Senha incorretos!", "perigo");
				}
			} else {
				new ValidacaoUtil().redirecionarParaAPagina(request, response,
						ValidacaoUtil.getPaginaCadastrarCliente(), "Você ainda não tem um cadastro, faça um!",
						"perigo");
			}
		} catch (Exception e) {
			logger.severe("Erro ao processar a solicitação de autenticação: " + e.getMessage());

			new ValidacaoUtil().redirecionarParaAPagina(request, response, ValidacaoUtil.getPaginaError(),
					"Erro ao processar a solicitação de autenticação!", "erro");
		}
	}
}