package br.com.joaogcm.jg.restaurante.caseiro.servlet;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.Set;
import java.util.logging.Logger;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import br.com.joaogcm.jg.restaurante.caseiro.model.Cliente;
import br.com.joaogcm.jg.restaurante.caseiro.model.Lanche;
import br.com.joaogcm.jg.restaurante.caseiro.model.Menu;
import br.com.joaogcm.jg.restaurante.caseiro.service.LancheService;
import br.com.joaogcm.jg.restaurante.caseiro.service.MenuService;

@WebServlet(name = "Lanche", urlPatterns = { "/Lanche" })
public class LancheServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	private static final Logger logger = Logger.getLogger(AutenticacaoServlet.class.getName());

	private Lanche lanche = null;

	private LancheService lancheService = null;
	private MenuService menuService = null;

	public LancheServlet() {
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String acao = request.getParameter("acao");

		/* Usado para deixar o item do submenu selecionado quando clicado */
		request.setAttribute("acao", acao);

		try {
			lanche = new Lanche();

			lancheService = new LancheService();
			menuService = new MenuService();

			HttpSession sessaoCliente = request.getSession(false);
			Cliente cliente = (Cliente) sessaoCliente.getAttribute("clienteComCadastro");

			Set<Menu> menus = menuService.listarTodasUrlsSubMenu();
			request.setAttribute("menus", menus);

			if (acao.equalsIgnoreCase("listarLanche")) {
				request.setAttribute("lanches", lancheService.buscarTodosLanches());
				redirecionarParaPagina(request, response, "/paginas/lanche/listar-lanche.jsp", null, null);
			} else if (acao.equalsIgnoreCase("cadastrarLanche")) {
				if (sessaoCliente != null && cliente != null && cliente.getPerfil() != null
						&& cliente.getPerfil().getCodigo() == 2) {
					redirecionarParaPagina(request, response, "/paginas/lanche/cadastrar-lanche.jsp", null, null);
				} else {
					redirecionarParaPagina(request, response, "/paginas/autenticacao/autenticar-login.jsp",
							"Você não está logado e/ou não tem o perfil de administrador!", "perigo");
				}
			} else if (acao.equalsIgnoreCase("editarLanche")) {
				if (sessaoCliente != null && cliente != null && cliente.getPerfil() != null
						&& cliente.getPerfil().getCodigo() == 2) {
					String codigo = request.getParameter("codigo");
					Integer codigoL = codigo != null && !codigo.isEmpty() ? Integer.parseInt(codigo) : null;

					lanche.setCodigo(codigoL);

					lanche = lancheService.buscarLanchePorCodigo(lanche);

					if (lanche.getCodigo() != null) {
						request.setAttribute("lanche", lanche);
						redirecionarParaPagina(request, response, "/paginas/lanche/cadastrar-lanche.jsp",
								"Edite o lanche!", "sucesso");
					} else {
						request.setAttribute("lanches", lancheService.buscarTodosLanches());
						redirecionarParaPagina(request, response, "/paginas/lanche/listar-lanche.jsp",
								"Lanche não encontrado!", "perigo");
					}
				} else {
					redirecionarParaPagina(request, response, "/paginas/autenticacao/autenticar-login.jsp",
							"Você não está logado e/ou não tem o perfil de administrador!", "perigo");
				}
			} else if (acao.equalsIgnoreCase("removerLanche")) {
				if (sessaoCliente != null && cliente != null && cliente.getPerfil() != null
						&& cliente.getPerfil().getCodigo() == 2) {
					String codigo = request.getParameter("codigo");
					Integer codigoL = codigo != null && !codigo.isEmpty() ? Integer.parseInt(codigo) : null;

					lanche.setCodigo(codigoL);

					lanche = lancheService.buscarLanchePorCodigo(lanche);

					if (lanche.getCodigo() != null) {
						lancheService.removerLanchePorCodigo(lanche);

						request.setAttribute("lanches", lancheService.buscarTodosLanches());
						redirecionarParaPagina(request, response, "/paginas/lanche/listar-lanche.jsp",
								"Lanche removido com sucesso!", "sucesso");
					} else {
						request.setAttribute("lanches", lancheService.buscarTodosLanches());
						redirecionarParaPagina(request, response, "/paginas/lanche/listar-lanche.jsp",
								"Não foi possível remover o lanche!", "perigo");
					}
				} else {
					redirecionarParaPagina(request, response, "/paginas/autenticacao/autenticar-login.jsp",
							"Você não está logado e/ou não tem o perfil de administrador!", "perigo");
				}
			}
		} catch (Exception e) {
			logger.severe("Erro ao processar a solicitação do lanche: " + e.getMessage());

			redirecionarParaPagina(request, response, "/error.jsp", "Erro ao processar a solicitação do lanche!",
					"erro");
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		try {
			lanche = new Lanche();

			lancheService = new LancheService();

			Set<Menu> menus = new MenuService().listarTodasUrlsSubMenu();
			request.setAttribute("menus", menus);

			String codigo = request.getParameter("codigo");
			String nome = request.getParameter("nome");
			String descricao_conteudo = request.getParameter("descricao_conteudo");
			String preco = request.getParameter("preco");

			lanche.setCodigo(codigo != null && !codigo.trim().isEmpty() ? Integer.parseInt(codigo) : null);
			lanche.setNome(nome != null && !nome.trim().isEmpty() ? nome : null);
			lanche.setDescricao_conteudo(
					descricao_conteudo != null && !descricao_conteudo.trim().isEmpty() ? descricao_conteudo : null);
			lanche.setPreco(preco != null && !preco.trim().isEmpty() ? new BigDecimal(preco) : null);

			if (lanche.getCodigo() != null) {
				lancheService.atualizarLanchePorCodigo(lanche);

				request.setAttribute("lanches", lancheService.buscarTodosLanches());
				redirecionarParaPagina(request, response, "/paginas/lanche/listar-lanche.jsp",
						"Lanche atualizado com sucesso!", "sucesso");
			} else {
				lancheService.adicionarLanche(lanche);

				redirecionarParaPagina(request, response, "/paginas/lanche/cadastrar-lanche.jsp",
						"Lanche adicionado com sucesso!", "sucesso");
			}

		} catch (Exception e) {
			logger.severe("Erro ao processar a solicitação do lanche: " + e.getMessage());

			redirecionarParaPagina(request, response, "/error.jsp", "Erro ao processar a solicitação do lanche!",
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