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
import br.com.joaogcm.jg.restaurante.caseiro.service.ClienteService;

@WebServlet(name = "Cliente", urlPatterns = { "/Cliente" })
public class ClienteServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	private Cliente cliente = null;
	private ClienteService clienteService = null;

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

			if (acao.equalsIgnoreCase("listarCliente")) {
				request.setAttribute("clientes", clienteService.buscarTodosClientes());
				redirecionarParaPagina(request, response, "/paginas/cliente/listar-cliente.jsp", null);
			} else if (acao.equalsIgnoreCase("cadastrarCliente")) {
				redirecionarParaPagina(request, response, "/paginas/cliente/cadastrar-cliente.jsp", null);
			} else if (acao.equalsIgnoreCase("editarCliente")) {
				String codigo = request.getParameter("codigo");
				Integer codigoC = codigo != null && !codigo.isEmpty() ? Integer.parseInt(codigo) : null;

				cliente.setCodigo(codigoC);

				cliente = clienteService.buscarClientePorCodigo(cliente);

				if (cliente.getCodigo() != null) {
					request.setAttribute("cliente", cliente);
					redirecionarParaPagina(request, response, "/paginas/cliente/cadastrar-cliente.jsp", null);
				} else {
					request.setAttribute("clientes", clienteService.buscarTodosClientes());
					redirecionarParaPagina(request, response, "/paginas/cliente/listar-cliente.jsp",
							"Cliente não encontrado!");
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
							"Cliente removido com sucesso!");
				} else {
					request.setAttribute("clientes", clienteService.buscarTodosClientes());
					redirecionarParaPagina(request, response, "/paginas/cliente/listar-cliente.jsp",
							"Não foi possível remover o cliente!");
				}
			}
		} catch (Exception e) {
			e.printStackTrace();

			redirecionarParaPagina(request, response, "/error.jsp", "Erro ao processar a solicitação do cliente!");
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		try {
			cliente = new Cliente();
			clienteService = new ClienteService();

			String codigo = request.getParameter("codigo");
			String nome = request.getParameter("nome");
			String email = request.getParameter("email");
			String telefone = request.getParameter("telefone");
			String cpf = request.getParameter("cpf");
			String senha = request.getParameter("senha");

			cliente.setCodigo(codigo != null && !codigo.isEmpty() ? Integer.parseInt(codigo) : null);
			cliente.setNome(nome != null && !nome.isEmpty() ? nome : null);
			cliente.setEmail(email != null && !email.isEmpty() ? email : null);
			cliente.setTelefone(telefone != null && !telefone.isEmpty() ? telefone : null);
			cliente.setCpf(cpf != null && !cpf.isEmpty() ? cpf : null);
			cliente.setSenha(senha != null && !senha.isEmpty() ? senha : null);

			gerarSenhaHash(cliente.getSenha());

			if (cliente.getCodigo() != null) {
				clienteService.atualizarClientePorCodigo(cliente);

				request.setAttribute("clientes", clienteService.buscarTodosClientes());
				redirecionarParaPagina(request, response, "/paginas/cliente/listar-cliente.jsp",
						"Cliente atualizado com sucesso!");
			} else {
				clienteService.adicionarCliente(cliente);

				redirecionarParaPagina(request, response, "/paginas/cliente/cadastrar-cliente.jsp",
						"Cliente cadastrado com sucesso!");
			}
		} catch (Exception e) {
			e.printStackTrace();

			redirecionarParaPagina(request, response, "/error.jsp", "Erro ao processar a solicitação do cliente!");
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

	public String gerarSenhaHash(String senha) {
		return BCrypt.hashpw(senha, BCrypt.gensalt());
	}
}