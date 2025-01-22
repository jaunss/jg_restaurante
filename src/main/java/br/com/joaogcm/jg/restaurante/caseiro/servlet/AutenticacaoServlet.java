package br.com.joaogcm.jg.restaurante.caseiro.servlet;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import br.com.joaogcm.jg.restaurante.caseiro.model.Cliente;
import br.com.joaogcm.jg.restaurante.caseiro.service.AutenticacaoService;
import de.mkammerer.argon2.Argon2;
import de.mkammerer.argon2.Argon2Factory;

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

			if (acao.equalsIgnoreCase("autenticarCliente")) {
				redirecionarParaPagina(request, response, "/paginas/autenticacao/autenticar-login.jsp",
						"Insira os dados para autenticar!", "sucesso");
			} else if (acao.equalsIgnoreCase("deslogarCliente")) {

				HttpSession deslogarClienteDaSessao = request.getSession(false);

				if (deslogarClienteDaSessao != null) {
					deslogarClienteDaSessao.invalidate();

					redirecionarParaPagina(request, response, "/paginas/autenticacao/autenticar-login.jsp",
							"Volte novamente! Um até logo :(", "perigo");
				} else {
					redirecionarParaPagina(request, response, "/error.jsp",
							"Erro ao processar a solicitação de encerramento da sessão do cliente!", "erro");
				}
			}
		} catch (Exception e) {
			e.printStackTrace();

			redirecionarParaPagina(request, response, "/error.jsp", "Erro ao processar a solicitação de autenticação!",
					"erro");
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
			cliente.setSenha(senha != null && !senha.isEmpty() ? senha.trim() : null);

			Cliente clienteComEmailCadastrado = autenticacaoService.autenticarClientePorEmail(cliente.getEmail());

			if (clienteComEmailCadastrado != null) {
				Cliente clienteComSenhaCadastrada = autenticacaoService
						.autenticarClientePorEmailESenha(cliente.getEmail());

				if (clienteComSenhaCadastrada != null
						&& verificarSenhaHash(cliente.getSenha(), clienteComSenhaCadastrada.getSenha())) {
					HttpSession clienteDaSessao = request.getSession();
					clienteDaSessao.setAttribute("clienteDaSessaoAutenticado", clienteComSenhaCadastrada);

					redirecionarParaPagina(request, response, "/index.jsp",
							"Usuário " + cliente.getEmail() + " autenticado com sucesso!", "sucesso");
				} else {
					redirecionarParaPagina(request, response, "/paginas/autenticacao/autenticar-login.jsp",
							"Email e/ou Senha incorretos!", "perigo");
				}
			} else {
				redirecionarParaPagina(request, response, "/paginas/cliente/cadastrar-cliente.jsp",
						"Você ainda não tem um cadastro, realize um cadastro!", "perigo");
			}
		} catch (Exception e) {
			e.printStackTrace();

			redirecionarParaPagina(request, response, "/error.jsp", "Erro ao processar a solicitação de autenticação!",
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

	/**
	 * Verifica a senha hash armazenada no banco de dados com a senha inserida pelo
	 * usuário na autenticação.
	 * 
	 * @param senhaInserida
	 * @param senhaHashGravada
	 * @return
	 */
	public boolean verificarSenhaHash(String senhaInserida, String senhaHashGravada) {
		Argon2 verificarSenhaHash = Argon2Factory.create();

		char[] caracteresDaSenha = senhaInserida.toCharArray();

		try {
			return verificarSenhaHash.verify(senhaHashGravada, caracteresDaSenha);
		} finally {
			// Limpeza do array de caracteres da senha
			java.util.Arrays.fill(caracteresDaSenha, ' ');
		}
	}
}