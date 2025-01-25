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

import br.com.joaogcm.jg.restaurante.caseiro.model.Lanche;
import br.com.joaogcm.jg.restaurante.caseiro.model.Menu;
import br.com.joaogcm.jg.restaurante.caseiro.model.Pedido;
import br.com.joaogcm.jg.restaurante.caseiro.service.LancheService;
import br.com.joaogcm.jg.restaurante.caseiro.service.MenuService;
import br.com.joaogcm.jg.restaurante.caseiro.service.PedidoService;

@WebServlet(name = "PedidoLanche", urlPatterns = { "/PedidoLanche" })
public class PedidoLancheServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	private static final Logger logger = Logger.getLogger(AutenticacaoServlet.class.getName());

	private Pedido pedido = null;
	private Lanche lanche = null;

	private PedidoService pedidoService = null;
	private LancheService lancheService = null;

	public PedidoLancheServlet() {
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String acao = request.getParameter("acao");

		/* Usado para deixar o item do submenu selecionado quando clicado */
		request.setAttribute("acao", acao);

		try {
			pedido = new Pedido();
			lanche = new Lanche();

			pedidoService = new PedidoService();
			lancheService = new LancheService();

			List<Menu> menus = new MenuService().listarTodasUrlsSubMenu();
			request.setAttribute("menus", menus);

		} catch (Exception e) {
			logger.severe("Erro ao processar a solicitação do pedido e/ou lanche: " + e.getMessage());

			redirecionarParaPagina(request, response, "/error.jsp",
					"Erro ao processar a solicitação do pedido e/ou lanche!", "erro");
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		try {
			pedido = new Pedido();
			lanche = new Lanche();

			pedidoService = new PedidoService();
			lancheService = new LancheService();

			List<Menu> menus = new MenuService().listarTodasUrlsSubMenu();
			request.setAttribute("menus", menus);

		} catch (Exception e) {
			logger.severe("Erro ao processar a solicitação do pedido e/ou lanche: " + e.getMessage());

			redirecionarParaPagina(request, response, "/error.jsp",
					"Erro ao processar a solicitação do pedido e/ou lanche!", "erro");
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