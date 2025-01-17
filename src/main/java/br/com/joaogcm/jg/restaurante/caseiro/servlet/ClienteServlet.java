package br.com.joaogcm.jg.restaurante.caseiro.servlet;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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
		request.setAttribute("acao", acao);

		try {
			cliente = new Cliente();
			clienteService = new ClienteService();

			if (acao.equalsIgnoreCase("inicio")) {
				redirecionarParaPagina(request, response, "/index.jsp", null, null);
			} else if (acao.equalsIgnoreCase("listarCliente")) {
				request.setAttribute("clientes", clienteService.buscarTodosClientes());
				redirecionarParaPagina(request, response, "/paginas/cliente/listar-cliente.jsp", null, null);
			} else if (acao.equalsIgnoreCase("editarCliente")) {
				Integer codigo = request.getParameter("codigo") != null && !request.getParameter("codigo").isEmpty()
						? Integer.parseInt(request.getParameter("codigo"))
						: null;

				cliente = clienteService.buscarClientePorCodigo(codigo);

				if (cliente != null && cliente.getCodigo() != null) {
					request.setAttribute("cliente", cliente);
					redirecionarParaPagina(request, response, "/paginas/cliente/cadastrar-cliente.jsp", null, null);
				} else {
					request.setAttribute("clientes", clienteService.buscarTodosClientes());
					redirecionarParaPagina(request, response, "/paginas/cliente/listar-cliente.jsp",
							"Cliente não encontrado!", null);
				}
			} else if (acao.equalsIgnoreCase("removerCliente")) {
				Integer codigo = request.getParameter("codigo") != null && !request.getParameter("codigo").isEmpty()
						? Integer.parseInt(request.getParameter("codigo"))
						: null;

				cliente = clienteService.buscarClientePorCodigo(codigo);

				if (cliente != null && cliente.getCodigo() != null) {
					clienteService.removerClientePorCodigo(cliente);

					request.setAttribute("clientes", clienteService.buscarTodosClientes());
					redirecionarParaPagina(request, response, "/paginas/cliente/listar-cliente.jsp",
							"Cliente removido com sucesso!", "sucesso");
				} else {
					request.setAttribute("clientes", clienteService.buscarTodosClientes());
					redirecionarParaPagina(request, response, "/paginas/cliente/listar-cliente.jsp",
							"Não foi possível remover o cliente!", "falha");
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
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

			cliente.setCodigo(codigo != null && !codigo.isEmpty() ? Integer.parseInt(codigo) : null);
			cliente.setNome(nome != null && !nome.isEmpty() ? nome : null);
			cliente.setEmail(email != null && !email.isEmpty() ? email : null);
			cliente.setTelefone(telefone != null && !telefone.isEmpty() ? telefone : null);
			cliente.setCpf(cpf != null && !cpf.isEmpty() ? cpf : null);

			if (cliente != null && cliente.getCodigo() != null) {
				clienteService.atualizarClientePorCodigo(cliente);

				request.setAttribute("clientes", clienteService.buscarTodosClientes());
				redirecionarParaPagina(request, response, "/paginas/cliente/listar-cliente.jsp",
						"Cliente atualizado com sucesso!", "sucesso");
			} else {
				clienteService.adicionarCliente(cliente);

				redirecionarParaPagina(request, response, "/paginas/cliente/cadastrar-cliente.jsp",
						"Cliente adicionado com sucesso!", "sucesso");
			}
		} catch (Exception e) {
			Integer codigoStatus = (Integer) request.getAttribute("javax.servlet.error.status_code");
			String mensagem = (String) request.getAttribute("javax.servlet.error.message");
			Throwable exception = (Throwable) request.getAttribute("javax.servlet.error.exception");

			// Adicionar atributos para a página JSP
			request.setAttribute("codigoStatus", codigoStatus);
			request.setAttribute("mensagem", mensagem);
			request.setAttribute("exception", exception);

			// Redirecionar para a página de erro
			RequestDispatcher requestDispatcher = request.getRequestDispatcher("/error.jsp");
			requestDispatcher.forward(request, response);
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