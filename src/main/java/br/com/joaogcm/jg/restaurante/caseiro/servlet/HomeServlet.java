package br.com.joaogcm.jg.restaurante.caseiro.servlet;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(name = "Home", urlPatterns = { "/Home" })
public class HomeServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	public HomeServlet() {
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String acao = request.getParameter("acao");

		/* Usado para deixar o item do submenu selecionado quando clicado */
		request.setAttribute("acao", acao);

		try {
			if (acao.equalsIgnoreCase("home")) {
				redirecionarParaPagina(request, response, "/index.jsp", "Erro ao processar a solicitação!");
			}
		} catch (Exception e) {
			e.printStackTrace();

			redirecionarParaPagina(request, response, "/error.jsp", "Erro ao processar a solicitação da home!");
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		try {

		} catch (Exception e) {
			e.printStackTrace();

			redirecionarParaPagina(request, response, "/error.jsp", "Erro ao processar a solicitação da home!");
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