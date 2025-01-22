package br.com.joaogcm.jg.restaurante.caseiro.servlet;

import java.io.IOException;
import java.math.BigDecimal;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import br.com.joaogcm.jg.restaurante.caseiro.model.Lanche;
import br.com.joaogcm.jg.restaurante.caseiro.service.LancheService;

@WebServlet(name = "Lanche", urlPatterns = { "/Lanche" })
public class LancheServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	private Lanche lanche = null;
	private LancheService lancheService = null;

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

			if (acao.equalsIgnoreCase("listarLanche")) {
				request.setAttribute("lanches", lancheService.buscarTodosLanches());
				redirecionarParaPagina(request, response, "/paginas/lanche/listar-lanche.jsp", null, null);
			} else if (acao.equalsIgnoreCase("cadastrarLanche")) {
				redirecionarParaPagina(request, response, "/paginas/lanche/cadastrar-lanche.jsp", null, null);
			} else if (acao.equalsIgnoreCase("editarLanche")) {
				String codigo = request.getParameter("codigo");
				Integer codigoL = codigo != null && !codigo.isEmpty() ? Integer.parseInt(codigo) : null;

				lanche.setCodigo(codigoL);

				lanche = lancheService.buscarLanchePorCodigo(lanche);

				if (lanche.getCodigo() != null) {
					request.setAttribute("lanche", lanche);
					redirecionarParaPagina(request, response, "/paginas/lanche/cadastrar-lanche.jsp", "Edite o lanche!",
							"sucesso");
				} else {
					request.setAttribute("lanches", lancheService.buscarTodosLanches());
					redirecionarParaPagina(request, response, "/paginas/lanche/listar-lanche.jsp",
							"Lanche não encontrado!", "perigo");
				}

			} else if (acao.equalsIgnoreCase("removerLanche")) {
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
			}
		} catch (Exception e) {
			e.printStackTrace();

			redirecionarParaPagina(request, response, "/error.jsp", "Erro ao processar a solicitação do lanche!",
					"erro");
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		try {
			lanche = new Lanche();
			lancheService = new LancheService();

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
			e.printStackTrace();

			redirecionarParaPagina(request, response, "/error.jsp", "Erro ao processar a solicitação do lanche!",
					"erro");
		}

	}

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