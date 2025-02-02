package br.com.joaogcm.jg.restaurante.caseiro.servlet;

import java.io.IOException;
import java.util.Set;
import java.util.logging.Logger;

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
import br.com.joaogcm.jg.restaurante.caseiro.util.ValidacaoUtil;

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

			Set<Menu> menus = new MenuService().listarTodasUrlsSubMenu();
			request.setAttribute("menus", menus);

		} catch (Exception e) {
			logger.severe("Erro ao processar a solicitação do pedido e/ou lanche: " + e.getMessage());

			new ValidacaoUtil().redirecionarParaAPagina(request, response, ValidacaoUtil.getPaginaError(),
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

			Set<Menu> menus = new MenuService().listarTodasUrlsSubMenu();
			request.setAttribute("menus", menus);

		} catch (Exception e) {
			logger.severe("Erro ao processar a solicitação do pedido e/ou lanche: " + e.getMessage());

			new ValidacaoUtil().redirecionarParaAPagina(request, response, ValidacaoUtil.getPaginaError(),
					"Erro ao processar a solicitação do pedido e/ou lanche!", "erro");
		}
	}
}