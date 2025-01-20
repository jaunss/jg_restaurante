package br.com.joaogcm.jg.restaurante.caseiro.servlet;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import br.com.joaogcm.jg.restaurante.caseiro.model.Lanche;
import br.com.joaogcm.jg.restaurante.caseiro.model.Pedido;
import br.com.joaogcm.jg.restaurante.caseiro.service.LancheService;
import br.com.joaogcm.jg.restaurante.caseiro.service.PedidoService;

@WebServlet(name = "PedidoLanche", urlPatterns = { "/PedidoLanche" })
public class PedidoLancheServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	private Pedido pedido = null;
	private PedidoService pedidoService = null;

	private Lanche lanche = null;
	private LancheService lancheService = null;

	public PedidoLancheServlet() {
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String acao = request.getParameter("acao");

		// Usado para deixar o item do submenu selecionado quando clicado
		request.setAttribute("acao", acao);

		try {
			pedido = new Pedido();
			pedidoService = new PedidoService();

			lanche = new Lanche();
			lancheService = new LancheService();

		} catch (Exception e) {
			e.printStackTrace();

			redirecionarParaPagina(request, response, "/error.jsp",
					"Erro ao processar a solicitação do pedido e/ou lanche!");
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		try {
			pedido = new Pedido();
			pedidoService = new PedidoService();

			lanche = new Lanche();
			lancheService = new LancheService();

		} catch (Exception e) {
			e.printStackTrace();

			redirecionarParaPagina(request, response, "/error.jsp",
					"Erro ao processar a solicitação do pedido e/ou lanche!");
		}
	}

	private void redirecionarParaPagina(HttpServletRequest request, HttpServletResponse response, String pagina,
			String mensagem) throws ServletException, IOException {
		if (mensagem != null) {
			request.setAttribute("mensagem", mensagem);
		}

		RequestDispatcher requestDispatcher = request.getRequestDispatcher(pagina);
		requestDispatcher.forward(request, response);
	}
}