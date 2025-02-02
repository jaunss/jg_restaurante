package br.com.joaogcm.jg.restaurante.caseiro.servlet;

import java.io.IOException;
import java.math.BigDecimal;
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
import br.com.joaogcm.jg.restaurante.caseiro.service.LancheService;
import br.com.joaogcm.jg.restaurante.caseiro.service.MenuService;
import br.com.joaogcm.jg.restaurante.caseiro.util.ValidacaoUtil;

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
		String acao = new ValidacaoUtil().getParametroString(request, "acao");

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

			if (acao.equalsIgnoreCase(ValidacaoUtil.getAcaoListarLanche())) {
				request.setAttribute("lanches", lancheService.buscarTodosLanches());
				new ValidacaoUtil().redirecionarParaAPagina(request, response, ValidacaoUtil.getPaginaListarLanche(),
						null, null);
			} else if (acao.equalsIgnoreCase(ValidacaoUtil.getAcaoCadastrarLanche())) {
				if (sessaoCliente != null && cliente != null && cliente.getPerfil() != null
						&& cliente.getPerfil().getCodigo() == 2) {
					new ValidacaoUtil().redirecionarParaAPagina(request, response,
							ValidacaoUtil.getPaginaCadastrarLanche(), null, null);
				} else {
					new ValidacaoUtil().redirecionarParaAPagina(request, response,
							ValidacaoUtil.getPaginaAutenticarCliente(),
							"Você não está logado e/ou não tem o perfil de administrador!", "perigo");
				}
			} else if (acao.equalsIgnoreCase(ValidacaoUtil.getAcaoEditarLanche())) {
				if (sessaoCliente != null && cliente != null && cliente.getPerfil() != null
						&& cliente.getPerfil().getCodigo() == 2) {
					Integer codigoL = new ValidacaoUtil().getParametroInteger(request, "codigo");
					lanche.setCodigo(codigoL);

					lanche = lancheService.buscarLanchePorCodigo(lanche);

					if (lanche.getCodigo() != null) {
						request.setAttribute("lanche", lanche);
						new ValidacaoUtil().redirecionarParaAPagina(request, response,
								ValidacaoUtil.getPaginaCadastrarLanche(), "Edite o lanche!", "sucesso");
					} else {
						request.setAttribute("lanches", lancheService.buscarTodosLanches());
						new ValidacaoUtil().redirecionarParaAPagina(request, response,
								ValidacaoUtil.getPaginaListarLanche(), "Lanche não encontrado!", "perigo");
					}
				} else {
					new ValidacaoUtil().redirecionarParaAPagina(request, response,
							ValidacaoUtil.getPaginaAutenticarCliente(),
							"Você não está logado e/ou não tem o perfil de administrador!", "perigo");
				}
			} else if (acao.equalsIgnoreCase(ValidacaoUtil.getAcaoRemoverLanche())) {
				if (sessaoCliente != null && cliente != null && cliente.getPerfil() != null
						&& cliente.getPerfil().getCodigo() == 2) {
					Integer codigoL = new ValidacaoUtil().getParametroInteger(request, "codigo");
					lanche.setCodigo(codigoL);

					lanche = lancheService.buscarLanchePorCodigo(lanche);

					if (lanche.getCodigo() != null) {
						lancheService.removerLanchePorCodigo(lanche);

						request.setAttribute("lanches", lancheService.buscarTodosLanches());
						new ValidacaoUtil().redirecionarParaAPagina(request, response,
								ValidacaoUtil.getPaginaListarLanche(), "Lanche removido com sucesso!", "sucesso");
					} else {
						request.setAttribute("lanches", lancheService.buscarTodosLanches());
						new ValidacaoUtil().redirecionarParaAPagina(request, response,
								ValidacaoUtil.getPaginaListarLanche(), "Não foi possível remover o lanche!", "perigo");
					}
				} else {
					new ValidacaoUtil().redirecionarParaAPagina(request, response,
							ValidacaoUtil.getPaginaAutenticarCliente(),
							"Você não está logado e/ou não tem o perfil de administrador!", "perigo");
				}
			}
		} catch (Exception e) {
			logger.severe("Erro ao processar a solicitação do lanche: " + e.getMessage());

			new ValidacaoUtil().redirecionarParaAPagina(request, response, ValidacaoUtil.getPaginaError(),
					"Erro ao processar a solicitação do lanche!", "erro");
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		try {
			lanche = new Lanche();

			lancheService = new LancheService();

			Set<Menu> menus = new MenuService().listarTodasUrlsSubMenu();
			request.setAttribute("menus", menus);

			Integer codigo = new ValidacaoUtil().getParametroInteger(request, "codigo");
			String nome = new ValidacaoUtil().getParametroString(request, "nome");
			String descricao_conteudo = new ValidacaoUtil().getParametroString(request, "descricao_conteudo");
			BigDecimal preco = new ValidacaoUtil().getParametroBigDecimal(request, "preco");

			lanche.setCodigo(codigo);
			lanche.setNome(nome);
			lanche.setDescricao_conteudo(descricao_conteudo);
			lanche.setPreco(preco);

			if (lanche.getCodigo() != null) {
				lancheService.atualizarLanchePorCodigo(lanche);

				request.setAttribute("lanches", lancheService.buscarTodosLanches());
				new ValidacaoUtil().redirecionarParaAPagina(request, response, ValidacaoUtil.getPaginaListarLanche(),
						"Lanche atualizado com sucesso!", "sucesso");
			} else {
				lancheService.adicionarLanche(lanche);

				new ValidacaoUtil().redirecionarParaAPagina(request, response, ValidacaoUtil.getPaginaCadastrarLanche(),
						"Lanche adicionado com sucesso!", "sucesso");
			}
		} catch (Exception e) {
			logger.severe("Erro ao processar a solicitação do lanche: " + e.getMessage());

			new ValidacaoUtil().redirecionarParaAPagina(request, response, ValidacaoUtil.getPaginaError(),
					"Erro ao processar a solicitação do lanche!", "erro");
		}
	}
}