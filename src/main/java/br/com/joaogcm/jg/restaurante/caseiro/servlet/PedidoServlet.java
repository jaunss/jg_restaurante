package br.com.joaogcm.jg.restaurante.caseiro.servlet;

import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;
import java.util.logging.Logger;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import br.com.joaogcm.jg.restaurante.caseiro.model.Cliente;
import br.com.joaogcm.jg.restaurante.caseiro.model.Lanche;
import br.com.joaogcm.jg.restaurante.caseiro.model.Menu;
import br.com.joaogcm.jg.restaurante.caseiro.model.Pedido;
import br.com.joaogcm.jg.restaurante.caseiro.service.LancheService;
import br.com.joaogcm.jg.restaurante.caseiro.service.MenuService;
import br.com.joaogcm.jg.restaurante.caseiro.service.PedidoLancheService;
import br.com.joaogcm.jg.restaurante.caseiro.service.PedidoService;
import br.com.joaogcm.jg.restaurante.caseiro.util.ValidacaoUtil;

@WebServlet(name = "Pedido", urlPatterns = { "/Pedido" })
public class PedidoServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	private static final Logger logger = Logger.getLogger(AutenticacaoServlet.class.getName());

	private Pedido pedido = null;
	private Lanche lanche = null;

	private PedidoService pedidoService = null;
	private LancheService lancheService = null;
	private PedidoLancheService pedidoLancheService = null;
	private MenuService menuService = null;

	public PedidoServlet() {
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String acao = new ValidacaoUtil().getParametroString(request, "acao");

		/* Usado para deixar o item do submenu selecionado quando clicado */
		request.setAttribute("acao", acao);

		try {
			pedido = new Pedido();

			pedidoService = new PedidoService();
			lancheService = new LancheService();
			menuService = new MenuService();

			HttpSession sessaoCliente = request.getSession(false);
			Cliente cliente = (Cliente) sessaoCliente.getAttribute("clienteComCadastro");

			Set<Menu> menus = menuService.listarTodasUrlsSubMenu();
			request.setAttribute("menus", menus);

			if (acao.equalsIgnoreCase(ValidacaoUtil.getAcaoListarPedido())) {
				if (sessaoCliente != null && cliente != null && cliente.getPerfil() != null
						&& cliente.getPerfil().getCodigo() == 1) {
					Set<Pedido> pedidos = pedidoService.buscarPedidoPorCliente(cliente);

					request.setAttribute("pedidos", pedidos);
					new ValidacaoUtil().redirecionarParaAPagina(request, response,
							ValidacaoUtil.getPaginaListarPedido(), null, null);
				} else if (sessaoCliente != null && cliente != null && cliente.getPerfil() != null
						&& cliente.getPerfil().getCodigo() == 2) {
					Set<Pedido> pedidos = pedidoService.buscarTodosPedidos();

					request.setAttribute("pedidos", pedidos);
					new ValidacaoUtil().redirecionarParaAPagina(request, response,
							ValidacaoUtil.getPaginaListarPedido(), null, null);
				} else {
					new ValidacaoUtil().redirecionarParaAPagina(request, response,
							ValidacaoUtil.getPaginaAutenticarCliente(), "Você não está logado, faça o login!",
							"perigo");
				}
			} else if (acao.equalsIgnoreCase(ValidacaoUtil.getAcaoCadastrarPedido())) {
				if (sessaoCliente != null && cliente != null) {
					request.setAttribute("cliente", cliente);

					request.setAttribute("lanches", lancheService.buscarTodosLanches());
					new ValidacaoUtil().redirecionarParaAPagina(request, response,
							ValidacaoUtil.getPaginaCadastrarPedido(), null, null);
				} else {
					new ValidacaoUtil().redirecionarParaAPagina(request, response,
							ValidacaoUtil.getPaginaAutenticarCliente(), "Você não está logado, faça o login!",
							"perigo");
				}
			} else if (acao.equalsIgnoreCase(ValidacaoUtil.getAcaoEditarPedido())) {
				if (sessaoCliente != null && cliente != null) {
					Integer codigoP = new ValidacaoUtil().getParametroInteger(request, "codigo");
					pedido.setCodigo(codigoP);

					pedido = pedidoService.buscarPedidoPorCodigo(pedido);

					if (pedido.getCodigo() != null) {
						request.setAttribute("pedido", pedido);
						new ValidacaoUtil().redirecionarParaAPagina(request, response,
								ValidacaoUtil.getPaginaCadastrarPedido(), "Edite o pedido!", "sucesso");
					} else {
						request.setAttribute("pedidos", pedidoService.buscarTodosPedidos());
						new ValidacaoUtil().redirecionarParaAPagina(request, response,
								ValidacaoUtil.getPaginaListarPedido(), "Pedido não encontrado!", "perigo");
					}
				} else {
					new ValidacaoUtil().redirecionarParaAPagina(request, response,
							ValidacaoUtil.getPaginaAutenticarCliente(), "Você não está logado, faça o login!",
							"perigo");
				}
			} else if (acao.equalsIgnoreCase(ValidacaoUtil.getAcaoRemoverPedido())) {
				if (sessaoCliente != null && cliente != null) {
					Integer codigoP = new ValidacaoUtil().getParametroInteger(request, "codigo");
					pedido.setCodigo(codigoP);

					pedido = pedidoService.buscarPedidoPorCodigo(pedido);

					if (pedido.getCodigo() != null) {
						pedidoService.removerPedidoPorCodigo(pedido);

						request.setAttribute("pedidos", pedidoService.buscarTodosPedidos());
						new ValidacaoUtil().redirecionarParaAPagina(request, response,
								ValidacaoUtil.getPaginaListarPedido(), "Pedido removido com sucesso!", "sucesso");
					} else {
						request.setAttribute("pedidos", pedidoService.buscarTodosPedidos());
						new ValidacaoUtil().redirecionarParaAPagina(request, response,
								ValidacaoUtil.getPaginaListarPedido(), "Não foi possível remover o pedido!", "perigo");
					}
				} else {
					new ValidacaoUtil().redirecionarParaAPagina(request, response,
							ValidacaoUtil.getPaginaAutenticarCliente(), "Você não está logado, faça o login!",
							"perigo");
				}
			}
		} catch (Exception e) {
			logger.severe("Erro ao processar a solicitação do pedido: " + e.getMessage());

			new ValidacaoUtil().redirecionarParaAPagina(request, response, ValidacaoUtil.getPaginaError(),
					"Erro ao processar a solicitação do pedido!", "erro");
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

			Set<Menu> menus = new MenuService().listarTodasUrlsSubMenu();
			request.setAttribute("menus", menus);

			Integer codigo = new ValidacaoUtil().getParametroInteger(request, "codigo");
			LocalDateTime dataPedido = new ValidacaoUtil().getParametroLocalDateTime(request, "dataPedido");
			BigDecimal total = new ValidacaoUtil().getParametroBigDecimal(request, "total");

			/* Obtém os códigos dos lanches selecionados */
			String[] codigoLanches = new ValidacaoUtil().getParametroArrayString(request, "lanches");

			pedido.setCodigo(codigo);
			pedido.setDataPedido(dataPedido);
			pedido.setTotal(total);

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
							new ValidacaoUtil().redirecionarParaAPagina(request, response,
									ValidacaoUtil.getPaginaError(),
									"Erro ao processar a solicitação do pedido pois o código do lanche é inválido!",
									"erro");
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
				}

				pedido.setLanches(lanches);
			}

			if (pedido.getCodigo() != null) {
				Pedido newPedido = pedidoService.buscarPedidoPorCodigo(pedido);

				if (newPedido != null) {
					pedido.setCliente(newPedido.getCliente() != null ? newPedido.getCliente() : null);
				}

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
				new ValidacaoUtil().redirecionarParaAPagina(request, response, ValidacaoUtil.getPaginaListarPedido(),
						"Pedido atualizado com sucesso!", "sucesso");
			} else {
				/* Adiciona novos relacionamentos entre Pedido e Lanche */
				for (Lanche l : pedido.getLanches()) {
					pedidoLancheService.adicionarPedidoLanche(pedido, l);
				}

				pedidoService.adicionarPedido(pedido);

				new ValidacaoUtil().redirecionarParaAPagina(request, response, ValidacaoUtil.getPaginaCadastrarPedido(),
						"Pedido adicionado com sucesso!", "sucesso");
			}
		} catch (Exception e) {
			logger.severe("Erro ao processar a solicitação do pedido: " + e.getMessage());

			new ValidacaoUtil().redirecionarParaAPagina(request, response, ValidacaoUtil.getPaginaError(),
					"Erro ao processar a solicitação do pedido!", "erro");
		}
	}
}