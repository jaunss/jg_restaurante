package br.com.joaogcm.jg.restaurante.caseiro.servlet;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(name = "PedidoLanche", urlPatterns = { "/PedidoLanche" })
public class PedidoLancheServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	public PedidoLancheServlet() {
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String acao = request.getParameter("acao");
		request.setAttribute("acao", acao);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

	}
}