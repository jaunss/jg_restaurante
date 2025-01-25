package br.com.joaogcm.jg.restaurante.caseiro.servlet;

import java.io.IOException;
import java.util.List;
import java.util.logging.Logger;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import br.com.joaogcm.jg.restaurante.caseiro.model.Cliente;
import br.com.joaogcm.jg.restaurante.caseiro.model.Menu;
import br.com.joaogcm.jg.restaurante.caseiro.model.Perfil;
import br.com.joaogcm.jg.restaurante.caseiro.service.ClienteService;
import br.com.joaogcm.jg.restaurante.caseiro.service.MenuService;
import br.com.joaogcm.jg.restaurante.caseiro.service.PerfilService;
import de.mkammerer.argon2.Argon2;
import de.mkammerer.argon2.Argon2Factory;

@WebServlet(name = "Cliente", urlPatterns = { "/Cliente" })
public class ClienteServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	private static final Logger logger = Logger.getLogger(AutenticacaoServlet.class.getName());

	private Cliente cliente = null;
	private Perfil perfil = null;

	private ClienteService clienteService = null;
	private PerfilService perfilService = null;
	private MenuService menuService = null;

	public ClienteServlet() {
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String acao = request.getParameter("acao");

		/* Usado para deixar o item do submenu selecionado quando clicado */
		request.setAttribute("acao", acao);

		try {
			cliente = new Cliente();

			clienteService = new ClienteService();
			perfilService = new PerfilService();
			menuService = new MenuService();

			List<Menu> menus = menuService.listarTodasUrlsSubMenu();
			request.setAttribute("menus", menus);

			if (acao.equalsIgnoreCase("listarCliente")) {
				HttpSession sessaoCliente = request.getSession(false);
				Cliente cliente = (Cliente) sessaoCliente.getAttribute("clienteComCadastro");

				if (sessaoCliente != null && cliente != null && cliente.getPerfil() != null
						&& cliente.getPerfil().getCodigo() == 1) {
					Cliente newCliente = clienteService.buscarClientePorCodigo(cliente);

					request.setAttribute("newCliente", newCliente);
					redirecionarParaPagina(request, response, "/paginas/cliente/listar-cliente.jsp", null, null);
				} else if (sessaoCliente != null && cliente != null && cliente.getPerfil() != null
						&& cliente.getPerfil().getCodigo() == 2) {
					request.setAttribute("clientes", clienteService.buscarTodosClientes());
					redirecionarParaPagina(request, response, "/paginas/cliente/listar-cliente.jsp", null, null);
				} else {
					redirecionarParaPagina(request, response, "/paginas/autenticacao/autenticar-login.jsp",
							"Você não está logado, faça o login!", "perigo");
				}
			} else if (acao.equalsIgnoreCase("cadastrarCliente")) {
				HttpSession sessaoCliente = request.getSession(false);
				Cliente cliente = (Cliente) sessaoCliente.getAttribute("clienteComCadastro");

				if (sessaoCliente != null && cliente != null && cliente.getPerfil() != null
						&& cliente.getPerfil().getCodigo() == 1) {
					redirecionarParaPagina(request, response, "/paginas/cliente/cadastrar-cliente.jsp", null, null);
				} else if (sessaoCliente != null && cliente != null && cliente.getPerfil() != null
						&& cliente.getPerfil().getCodigo() == 2) {
					request.setAttribute("perfis", perfilService.buscarPerfilPorCodigo(cliente.getPerfil()));
					redirecionarParaPagina(request, response, "/paginas/cliente/cadastrar-cliente.jsp", null, null);
				} else {
					redirecionarParaPagina(request, response, "/paginas/cliente/cadastrar-cliente.jsp", null, null);
				}
			} else if (acao.equalsIgnoreCase("editarCliente")) {
				String codigo = request.getParameter("codigo");
				Integer codigoC = codigo != null && !codigo.isEmpty() ? Integer.parseInt(codigo) : null;

				cliente.setCodigo(codigoC);

				cliente = clienteService.buscarClientePorCodigo(cliente);

				if (cliente.getCodigo() != null) {
					request.setAttribute("cliente", cliente);
					redirecionarParaPagina(request, response, "/paginas/cliente/cadastrar-cliente.jsp",
							"Edite o cliente!", "sucesso");
				} else {
					request.setAttribute("clientes", clienteService.buscarTodosClientes());
					redirecionarParaPagina(request, response, "/paginas/cliente/listar-cliente.jsp",
							"Cliente não encontrado!", "perigo");
				}
			} else if (acao.equalsIgnoreCase("removerCliente")) {
				String codigo = request.getParameter("codigo");
				Integer codigoC = codigo != null && !codigo.isEmpty() ? Integer.parseInt(codigo) : null;

				cliente.setCodigo(codigoC);

				cliente = clienteService.buscarClientePorCodigo(cliente);

				if (cliente.getCodigo() != null) {
					clienteService.removerClientePorCodigo(cliente);

					request.setAttribute("clientes", clienteService.buscarTodosClientes());
					redirecionarParaPagina(request, response, "/paginas/cliente/listar-cliente.jsp",
							"Cliente removido com sucesso!", "sucesso");
				} else {
					request.setAttribute("clientes", clienteService.buscarTodosClientes());
					redirecionarParaPagina(request, response, "/paginas/cliente/listar-cliente.jsp",
							"Não foi possível remover o cliente!", "perigo");
				}
			}
		} catch (Exception e) {
			logger.severe("Erro ao processar a solicitação do cliente: " + e.getMessage());

			redirecionarParaPagina(request, response, "/error.jsp", "Erro ao processar a solicitação do cliente!",
					"erro");
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		try {
			cliente = new Cliente();
			perfil = new Perfil();

			clienteService = new ClienteService();
			perfilService = new PerfilService();

			List<Menu> menus = new MenuService().listarTodasUrlsSubMenu();
			request.setAttribute("menus", menus);

			String codigo = request.getParameter("codigo");
			String nome = request.getParameter("nome");
			String email = request.getParameter("email");
			String telefone = request.getParameter("telefone");
			String cpf = request.getParameter("cpf");
			String senha = request.getParameter("senha");
			String perfil_id = request.getParameter("perfil.codigo");

			cliente.setCodigo(codigo != null && !codigo.isEmpty() ? Integer.parseInt(codigo) : null);
			cliente.setNome(nome != null && !nome.isEmpty() ? nome : null);
			cliente.setEmail(email != null && !email.isEmpty() ? email : null);
			cliente.setTelefone(telefone != null && !telefone.isEmpty() ? telefone : null);
			cliente.setCpf(cpf != null && !cpf.isEmpty() ? cpf : null);

			String senhaHashGerada = gerarSenhaHash(senha.trim());
			cliente.setSenha(senhaHashGerada != null && !senhaHashGerada.isEmpty() ? senhaHashGerada : null);

			perfil.setCodigo(perfil_id != null && !perfil_id.isEmpty() ? Integer.parseInt(perfil_id) : null);
			cliente.setPerfil(perfil);

			if (cliente.getCodigo() != null) {
				clienteService.atualizarClientePorCodigo(cliente);

				request.setAttribute("clientes", clienteService.buscarTodosClientes());
				redirecionarParaPagina(request, response, "/paginas/cliente/listar-cliente.jsp",
						"Cliente atualizado com sucesso!", "sucesso");
			} else {
				clienteService.adicionarCliente(cliente);

				redirecionarParaPagina(request, response, "/paginas/autenticacao/autenticar-login.jsp",
						"Cliente cadastrado com sucesso!", "sucesso");
			}
		} catch (Exception e) {
			logger.severe("Erro ao processar a solicitação do cliente: " + e.getMessage());

			redirecionarParaPagina(request, response, "/error.jsp", "Erro ao processar a solicitação do cliente!",
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

	/**
	 * Iterações: 6 é o número de iterações que o Argon2 vai fazer (Quanto maior o
	 * número, mais difícil será para alguém atacar e mais lento fica a execução).
	 * 
	 * Memória: 131072 é o tamanho da memória que o Argon2 deve utilizar (Quanto
	 * maior a memória, mais difícil será para alguém atacar utilizando GPU's e mais
	 * lento fica a execução).
	 * 
	 * Threads: 1 é o número de Threads que o Argon2 vai utilizar (1 já é o
	 * suficiente para a maioria dos casos, mas se o servidor utilizar vários
	 * núcleos pode aumentar esse número.
	 * 
	 * @param senhaInserida
	 * @return
	 */
	public String gerarSenhaHash(String senhaInserida) {
		Argon2 gerarSenhaHash = Argon2Factory.create();

		char[] caracteresDaSenha = senhaInserida.toCharArray();

		try {
			return gerarSenhaHash.hash(6, 131072, 1, caracteresDaSenha);
		} finally {
			// Limpeza do array de caracteres da senha
			java.util.Arrays.fill(caracteresDaSenha, ' ');
		}
	}
}