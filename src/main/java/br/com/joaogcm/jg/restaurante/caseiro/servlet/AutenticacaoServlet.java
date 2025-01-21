package br.com.joaogcm.jg.restaurante.caseiro.servlet;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.mindrot.jbcrypt.BCrypt;

import br.com.joaogcm.jg.restaurante.caseiro.model.Cliente;
import br.com.joaogcm.jg.restaurante.caseiro.service.AutenticacaoService;

@WebServlet(name = "Autenticacao", urlPatterns = { "/Autenticacao" })
public class AutenticacaoServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	private Cliente cliente = null;
	private AutenticacaoService autenticacaoService = null;

	public AutenticacaoServlet() {
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String acao = request.getParameter("acao");

		/* Usado para deixar o item do submenu selecionado quando clicado */
		request.setAttribute("acao", acao);

		try {
			cliente = new Cliente();
			autenticacaoService = new AutenticacaoService();

			if (acao.equalsIgnoreCase("autenticarLogin")) {
				redirecionarParaPagina(request, response, "/paginas/autenticacao/autenticar-login.jsp", null);
			}
		} catch (Exception e) {
			e.printStackTrace();

			redirecionarParaPagina(request, response, "/error.jsp", "Erro ao processar a solicitação de autenticação!");
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		try {
			cliente = new Cliente();
			autenticacaoService = new AutenticacaoService();

			String email = request.getParameter("email");
			String senha = request.getParameter("senha");

			cliente.setEmail(email != null && !email.isEmpty() ? email : null);
			cliente.setSenha(senha != null && !senha.isEmpty() ? senha : null);

			Cliente autenticarClientePorEmail = autenticacaoService.autenticarClientePorEmail(cliente.getEmail());

			if (autenticarClientePorEmail != null) {
				Cliente autenticarClientePorSenhaHash = autenticacaoService.autenticarClientePorSenhaHash(cliente);

				if (verificarSenha(cliente.getSenha(), autenticarClientePorSenhaHash.getSenha())) {
					redirecionarParaPagina(request, response, "/index.jsp",
							"Usuário " + cliente.getEmail() + " autenticado com sucesso!");
				} else {
					redirecionarParaPagina(request, response, "/paginas/autenticacao/autenticar-login.jsp",
							"Email e/ou Senha incorretos!");
				}
			} else {
				redirecionarParaPagina(request, response, "/paginas/cliente/cadastrar-cliente.jsp",
						"Você ainda não tem um cadastro, realize um cadastro!");
			}
		} catch (Exception e) {
			e.printStackTrace();

			redirecionarParaPagina(request, response, "/error.jsp", "Erro ao processar a solicitação de autenticação!");
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

	public boolean verificarSenha(String senhaFornecida, String senhaArmazenadaHash) {
		return BCrypt.checkpw(senhaFornecida, senhaArmazenadaHash);
	}
}