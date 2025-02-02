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

import br.com.joaogcm.jg.restaurante.caseiro.model.Cliente;
import br.com.joaogcm.jg.restaurante.caseiro.model.Menu;
import br.com.joaogcm.jg.restaurante.caseiro.service.MenuService;
import br.com.joaogcm.jg.restaurante.caseiro.util.ValidacaoUtil;

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
		String acao = new ValidacaoUtil().getParametroString(request, "acao");

		/* Usado para deixar o item do submenu selecionado quando clicado */
		request.setAttribute("acao", acao);

		try {
			menuService = new MenuService();

			Set<Menu> menus = menuService.listarTodasUrlsSubMenu();
			request.setAttribute("menus", menus);

			if (acao.equalsIgnoreCase(ValidacaoUtil.getAcaoHome())) {
				HttpSession sessaoCliente = request.getSession(false);
				Cliente cliente = (Cliente) sessaoCliente.getAttribute("clienteComCadastro");

				if (sessaoCliente != null && cliente != null) {
					new ValidacaoUtil().redirecionarParaAPagina(request, response, ValidacaoUtil.getPaginaHome(),
							"Olá " + cliente.getNome() + ", seja bem-vindo ao JG Restaurante :)", "sucesso");
				} else {
					new ValidacaoUtil().redirecionarParaAPagina(request, response, ValidacaoUtil.getPaginaHome(),
							"Seja bem-vindo ao JG Restaurante :)", "sucesso");
				}
			}
		} catch (Exception e) {
			logger.severe("Erro ao processar a solicitação da home: " + e.getMessage());

			new ValidacaoUtil().redirecionarParaAPagina(request, response, ValidacaoUtil.getPaginaError(),
					"Erro ao processar a solicitação da home!", "erro");
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		try {
			Set<Menu> menus = new MenuService().listarTodasUrlsSubMenu();
			request.setAttribute("menus", menus);
		} catch (Exception e) {
			e.printStackTrace();

			new ValidacaoUtil().redirecionarParaAPagina(request, response, ValidacaoUtil.getPaginaError(),
					"Erro ao processar a solicitação da home!", "erro");
		}
	}
}