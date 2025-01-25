package br.com.joaogcm.jg.restaurante.caseiro.servlet;

import java.io.IOException;
import java.util.List;
import java.util.logging.Logger;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import br.com.joaogcm.jg.restaurante.caseiro.model.Cliente;
import br.com.joaogcm.jg.restaurante.caseiro.model.Menu;
import br.com.joaogcm.jg.restaurante.caseiro.service.MenuService;

@WebServlet(name = "Home", urlPatterns = { "/Home" })
public class HomeServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	private static final Logger logger = Logger.getLogger(AutenticacaoServlet.class.getName());

	private MenuService menuService = null;

	public HomeServlet() {
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String acao = request.getParameter("acao");

		/* Usado para deixar o item do submenu selecionado quando clicado */
		request.setAttribute("acao", acao);

		try {
			menuService = new MenuService();

			List<Menu> menus = menuService.listarTodasUrlsSubMenu();
			request.setAttribute("menus", menus);

			if (acao.equalsIgnoreCase("home")) {
				HttpSession sessaoCliente = request.getSession(false);
				Cliente cliente = (Cliente) sessaoCliente.getAttribute("clienteComCadastro");

				if (sessaoCliente != null && cliente != null) {
					redirecionarParaPagina(request, response, "/index.jsp",
							"Olá " + cliente.getNome() + ", seja bem-vindo ao JG Restaurante :)", "sucesso");
				} else {
					redirecionarParaPagina(request, response, "/index.jsp", "Seja bem-vindo ao JG Restaurante :)",
							"sucesso");
				}
			}
		} catch (Exception e) {
			logger.severe("Erro ao processar a solicitação da home: " + e.getMessage());

			redirecionarParaPagina(request, response, "/error.jsp", "Erro ao processar a solicitação da home!", "erro");
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		try {

			List<Menu> menus = new MenuService().listarTodasUrlsSubMenu();
			request.setAttribute("menus", menus);
		} catch (Exception e) {
			e.printStackTrace();

			redirecionarParaPagina(request, response, "/error.jsp", "Erro ao processar a solicitação da home!", "erro");
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