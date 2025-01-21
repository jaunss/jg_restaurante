package br.com.joaogcm.jg.restaurante.caseiro.servlet;

import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import br.com.joaogcm.jg.restaurante.caseiro.model.Lanche;
import br.com.joaogcm.jg.restaurante.caseiro.model.Pedido;
import br.com.joaogcm.jg.restaurante.caseiro.service.LancheService;
import br.com.joaogcm.jg.restaurante.caseiro.service.PedidoLancheService;
import br.com.joaogcm.jg.restaurante.caseiro.service.PedidoService;

@WebServlet(name = "Pedido", urlPatterns = { "/Pedido" })
public class PedidoServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	private Pedido pedido = null;
	private Lanche lanche = null;

	private PedidoService pedidoService = null;
	private LancheService lancheService = null;
	private PedidoLancheService pedidoLancheService = null;

	public PedidoServlet() {
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String acao = request.getParameter("acao");

		/* Usado para deixar o item do submenu selecionado quando clicado */
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
			Set<Lanche> lanches = new HashSet<Lanche>();

			pedido = new Pedido();
			lanche = new Lanche();

			pedidoService = new PedidoService();
			pedidoLancheService = new PedidoLancheService();

			String codigo = request.getParameter("codigo");
			String dataPedido = request.getParameter("dataPedido");
			String subTotal = request.getParameter("subTotal");
			String total = request.getParameter("total");

			/* Obtém os códigos dos lanches selecionados */
			String[] codigoLanches = request.getParameterValues("lanches");

			pedido.setCodigo(codigo != null && !codigo.isEmpty() ? Integer.parseInt(codigo) : null);
			pedido.setDataPedido(dataPedido != null && !dataPedido.isEmpty() ? LocalDateTime.parse(dataPedido) : null);

			Pedido newPedido = pedidoService.buscarPedidoPorCodigo(pedido);

			if (newPedido != null) {
				pedido.setCliente(newPedido.getCliente() != null ? newPedido.getCliente() : null);
			}

			pedido.setSubTotal(subTotal != null && !subTotal.isEmpty() ? new BigDecimal(subTotal) : null);
			pedido.setTotal(total != null && !total.isEmpty() ? new BigDecimal(total) : null);

			if (codigoLanches != null) {
				for (String codigoLanche : codigoLanches) {
					if (codigoLanche != null && !codigoLanche.isEmpty()) {
						try {
							lanche.setCodigo(Integer.parseInt(codigoLanche));
							lanche = lancheService.buscarLanchePorCodigo(lanche);

							if (lanche != null) {
								lanches.add(lanche);
							}
						} catch (NumberFormatException e) {
							redirecionarParaPagina(request, response, "/error.jsp",
									"Erro ao processar a solicitação do pedido pois o código do lanche é inválido!");
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
				}

				pedido.setLanches(lanches);
			}

			if (pedido.getCodigo() != null) {
				pedidoService.atualizarPedidoPorCodigo(pedido);

				/*
				 * Remove os relacionamentos atuais entre Pedido e Lanche para adicionar os
				 * novos
				 */
				pedidoLancheService.removerPedidoLanchePorCodigoDoPedido(pedido);

				/* Adiciona novos relacionamentos entre Pedido e Lanche */
				for (Lanche l : pedido.getLanches()) {
					pedidoLancheService.adicionarPedidoLanche(pedido, l);
				}

				request.setAttribute("pedidos", pedidoService.buscarTodosPedidos());
				redirecionarParaPagina(request, response, "/paginas/pedido/listar-pedido.jsp",
						"Pedido atualizado com sucesso!");
			} else {
				/* Adiciona novos relacionamentos entre Pedido e Lanche */
				for (Lanche l : pedido.getLanches()) {
					pedidoLancheService.adicionarPedidoLanche(pedido, l);
				}

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