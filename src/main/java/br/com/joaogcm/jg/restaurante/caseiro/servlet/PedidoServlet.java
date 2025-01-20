package br.com.joaogcm.jg.restaurante.caseiro.servlet;

import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDateTime;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import br.com.joaogcm.jg.restaurante.caseiro.model.Pedido;
import br.com.joaogcm.jg.restaurante.caseiro.service.LancheService;
import br.com.joaogcm.jg.restaurante.caseiro.service.PedidoService;

@WebServlet(name = "Pedido", urlPatterns = { "/Pedido" })
public class PedidoServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	private Pedido pedido = null;
	private PedidoService pedidoService = null;
	private LancheService lancheService = null;

	public PedidoServlet() {
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
			lancheService = new LancheService();

			if (acao.equalsIgnoreCase("listarPedido")) {
				request.setAttribute("pedidos", pedidoService.buscarTodosPedidos());
				redirecionarParaPagina(request, response, "/paginas/pedido/listar-pedido.jsp", null);
			} else if (acao.equalsIgnoreCase("cadastrarPedido")) {
				request.setAttribute("lanches", lancheService.buscarTodosLanches());
				redirecionarParaPagina(request, response, "/paginas/pedido/cadastrar-pedido.jsp", null);
			} else if (acao.equalsIgnoreCase("editarPedido")) {
				String codigo = request.getParameter("codigo");
				Integer codigoP = codigo != null && !codigo.isEmpty() ? Integer.parseInt(codigo) : null;

				pedido.setCodigo(codigoP);

				pedido = pedidoService.buscarPedidoPorCodigo(pedido);

				if (pedido.getCodigo() != null) {
					request.setAttribute("pedido", pedido);
					redirecionarParaPagina(request, response, "/paginas/pedido/cadastrar-pedido.jsp", null);
				} else {
					request.setAttribute("pedidos", pedidoService.buscarTodosPedidos());
					redirecionarParaPagina(request, response, "/paginas/pedido/listar-pedido.jsp",
							"Pedido não encontrado!");
				}
			} else if (acao.equalsIgnoreCase("removerPedido")) {
				String codigo = request.getParameter("codigo");
				Integer codigoP = codigo != null && !codigo.isEmpty() ? Integer.parseInt(codigo) : null;

				pedido.setCodigo(codigoP);

				pedido = pedidoService.buscarPedidoPorCodigo(pedido);

				if (pedido.getCodigo() != null) {
					pedidoService.removerPedidoPorCodigo(pedido);

					request.setAttribute("pedidos", pedidoService.buscarTodosPedidos());
					redirecionarParaPagina(request, response, "/paginas/pedido/listar-pedido.jsp",
							"Pedido removido com sucesso!");
				} else {
					request.setAttribute("pedidos", pedidoService.buscarTodosPedidos());
					redirecionarParaPagina(request, response, "/paginas/pedido/listar-pedido.jsp",
							"Não foi possível remover o pedido!");
				}
			}
		} catch (Exception e) {
			e.printStackTrace();

			redirecionarParaPagina(request, response, "/error.jsp", "Erro ao processar a solicitação do pedido!");
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		try {
			pedido = new Pedido();
			pedidoService = new PedidoService();

			String codigo = request.getParameter("codigo");
			String dataPedido = request.getParameter("dataPedido");
			String subTotal = request.getParameter("subTotal");
			String total = request.getParameter("total");

			pedido.setCodigo(codigo != null && !codigo.isEmpty() ? Integer.parseInt(codigo) : null);
			pedido.setDataPedido(dataPedido != null && !dataPedido.isEmpty() ? LocalDateTime.parse(dataPedido) : null);

			Pedido newPedido = pedidoService.buscarPedidoPorCodigo(pedido);
			pedido.setCliente(newPedido.getCliente() != null ? newPedido.getCliente() : null);

			pedido.setSubTotal(subTotal != null && !subTotal.isEmpty() ? new BigDecimal(subTotal) : null);
			pedido.setTotal(total != null && !total.isEmpty() ? new BigDecimal(total) : null);

			if (pedido.getCodigo() != null) {
				pedidoService.atualizarPedidoPorCodigo(pedido);

				request.setAttribute("pedidos", pedidoService.buscarTodosPedidos());
				redirecionarParaPagina(request, response, "/paginas/pedido/listar-pedido.jsp",
						"Pedido atualizado com sucesso!");
			} else {
				pedidoService.adicionarPedido(pedido);

				redirecionarParaPagina(request, response, "/paginas/pedido/cadastrar-pedido.jsp",
						"Pedido adicionado com sucesso!");
			}
		} catch (Exception e) {
			e.printStackTrace();

			redirecionarParaPagina(request, response, "/error.jsp", "Erro ao processar a solicitação do pedido!");
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