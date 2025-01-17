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
		request.setAttribute("acao", acao);

		try {
			lanche = new Lanche();
			lancheService = new LancheService();

			if (acao.equalsIgnoreCase("cadastrarLanche")) {
				redirecionarParaPagina(request, response, "/paginas/lanche/cadastrar-lanche.jsp", null);
			} else if (acao.equalsIgnoreCase("listarLanche")) {
				request.setAttribute("lanches", lancheService.buscarTodosLanches());
				redirecionarParaPagina(request, response, "/paginas/lanche/listar-lanche.jsp", null);
			} else if (acao.equalsIgnoreCase("editarLanche")) {
				Integer codigo = request.getParameter("codigo") != null && !request.getParameter("codigo").isEmpty()
						? Integer.parseInt(request.getParameter("codigo"))
						: null;

				lanche = lancheService.buscarLanchePorCodigo(codigo);

				if (lanche != null && lanche.getCodigo() != null) {
					request.setAttribute("lanche", lanche);
					redirecionarParaPagina(request, response, "/paginas/lanche/cadastrar-lanche.jsp", null);
				} else {
					request.setAttribute("lanches", lancheService.buscarTodosLanches());
					redirecionarParaPagina(request, response, "/paginas/lanche/listar-lanche.jsp",
							"Lanche não encontrado!");
				}

			} else if (acao.equalsIgnoreCase("removerLanche")) {
				Integer codigo = request.getParameter("codigo") != null && !request.getParameter("codigo").isEmpty()
						? Integer.parseInt(request.getParameter("codigo"))
						: null;

				lanche = lancheService.buscarLanchePorCodigo(codigo);

				if (lanche != null && lanche.getCodigo() != null) {
					lancheService.removerLanchePorCodigo(lanche);

					request.setAttribute("lanches", lancheService.buscarTodosLanches());
					redirecionarParaPagina(request, response, "/paginas/lanche/listar-lanche.jsp",
							"Lanche removido com sucesso!");
				} else {
					request.setAttribute("lanches", lancheService.buscarTodosLanches());
					redirecionarParaPagina(request, response, "/paginas/lanche/listar-lanche.jsp",
							"Não foi possível remover o lanche!");
				}
			}
		} catch (Exception e) {
			Integer codigoStatus = (Integer) request.getAttribute("javax.servlet.error.status_code");
			String mensagem = (String) request.getAttribute("javax.servlet.error.message");
			Throwable exception = (Throwable) request.getAttribute("javax.servlet.error.exception");

			request.setAttribute("codigoStatus", codigoStatus);
			request.setAttribute("mensagem", mensagem);
			request.setAttribute("exception", exception);

			RequestDispatcher requestDispatcher = request.getRequestDispatcher("/error.jsp");
			requestDispatcher.forward(request, response);
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

			if (lanche != null && lanche.getCodigo() != null) {
				lancheService.atualizarLanchePorCodigo(lanche);

				request.setAttribute("lanches", lancheService.buscarTodosLanches());
				redirecionarParaPagina(request, response, "/paginas/lanche/listar-lanche.jsp",
						"Lanche atualizado com sucesso!");
			} else {
				lancheService.adicionarLanche(lanche);

				request.setAttribute("lanches", lancheService.buscarTodosLanches());
				redirecionarParaPagina(request, response, "/paginas/lanche/cadastrar-lanche.jsp",
						"Lanche adicionado com sucesso!");
			}

		} catch (Exception e) {
			Integer codigoStatus = (Integer) request.getAttribute("javax.servlet.error.status_code");
			String mensagem = (String) request.getAttribute("javax.servlet.error.message");
			Throwable exception = (Throwable) request.getAttribute("javax.servlet.error.exception");

			request.setAttribute("codigoStatus", codigoStatus);
			request.setAttribute("mensagem", mensagem);
			request.setAttribute("exception", exception);

			RequestDispatcher requestDispatcher = request.getRequestDispatcher("/error.jsp");
			requestDispatcher.forward(request, response);
			e.printStackTrace();
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