package br.com.joaogcm.jg.restaurante.caseiro.servlet;

import java.io.IOException;
import java.util.Set;
import java.util.logging.Logger;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import br.com.joaogcm.jg.restaurante.caseiro.model.Cidade;
import br.com.joaogcm.jg.restaurante.caseiro.model.Cliente;
import br.com.joaogcm.jg.restaurante.caseiro.model.Estado;
import br.com.joaogcm.jg.restaurante.caseiro.model.Menu;
import br.com.joaogcm.jg.restaurante.caseiro.model.Perfil;
import br.com.joaogcm.jg.restaurante.caseiro.service.ClienteService;
import br.com.joaogcm.jg.restaurante.caseiro.service.EstadoService;
import br.com.joaogcm.jg.restaurante.caseiro.service.MenuService;
import br.com.joaogcm.jg.restaurante.caseiro.service.PerfilService;
import br.com.joaogcm.jg.restaurante.caseiro.util.ValidacaoUtil;
import de.mkammerer.argon2.Argon2;
import de.mkammerer.argon2.Argon2Factory;

@WebServlet(name = "Cliente", urlPatterns = { "/Cliente" })
public class ClienteServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	private static final Logger logger = Logger.getLogger(AutenticacaoServlet.class.getName());

	private Cliente cliente = null;
	private Perfil perfil = null;
	private Cidade cidade = null;

	private ClienteService clienteService = null;
	private PerfilService perfilService = null;
	private MenuService menuService = null;
	private EstadoService estadoService = null;

	public ClienteServlet() {
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String acao = new ValidacaoUtil().getParametroString(request, ValidacaoUtil.getAcao());

		/* Usado para deixar o item do submenu selecionado quando clicado */
		request.setAttribute("acao", acao);

		try {
			cliente = new Cliente();

			clienteService = new ClienteService();
			perfilService = new PerfilService();
			menuService = new MenuService();
			estadoService = new EstadoService();

			HttpSession sessaoCliente = request.getSession(false);
			Cliente cliente = (Cliente) sessaoCliente.getAttribute("clienteComCadastro");

			Set<Menu> menus = menuService.listarTodasUrlsSubMenu();
			request.setAttribute("menus", menus);

			if (acao.equalsIgnoreCase(ValidacaoUtil.getAcaoListarCliente())) {
				if (sessaoCliente != null && cliente != null && cliente.getPerfil() != null
						&& cliente.getPerfil().getCodigo() == 1) {
					Cliente newCliente = clienteService.buscarClientePorCodigo(cliente);

					request.setAttribute("newCliente", newCliente);
					new ValidacaoUtil().redirecionarParaAPagina(request, response,
							ValidacaoUtil.getPaginaListarCliente(), null, null);
				} else if (sessaoCliente != null && cliente != null && cliente.getPerfil() != null
						&& cliente.getPerfil().getCodigo() == 2) {
					request.setAttribute("clientes", clienteService.buscarTodosClientes());
					new ValidacaoUtil().redirecionarParaAPagina(request, response,
							ValidacaoUtil.getPaginaListarCliente(), null, null);
				} else {
					new ValidacaoUtil().redirecionarParaAPagina(request, response,
							ValidacaoUtil.getPaginaListarCliente(), "Você não está logado, faça o login!", "perigo");
				}
			} else if (acao.equalsIgnoreCase(ValidacaoUtil.getAcaoCadastrarCliente())) {
				Set<Estado> estados = estadoService.buscarTodosEstados();
				request.setAttribute("estados", estados);

				if (sessaoCliente != null && cliente != null && cliente.getPerfil() != null
						&& cliente.getPerfil().getCodigo() == 1) {
					new ValidacaoUtil().redirecionarParaAPagina(request, response,
							ValidacaoUtil.getPaginaCadastrarCliente(), null, null);
				} else if (sessaoCliente != null && cliente != null && cliente.getPerfil() != null
						&& cliente.getPerfil().getCodigo() == 2) {
					/* Lista os perfis do cliente se o perfil do cliente for administrador */
					request.setAttribute("perfis", perfilService.buscarPerfilPorCodigo(cliente.getPerfil()));
					new ValidacaoUtil().redirecionarParaAPagina(request, response,
							ValidacaoUtil.getPaginaCadastrarCliente(), null, null);
				} else {
					new ValidacaoUtil().redirecionarParaAPagina(request, response,
							ValidacaoUtil.getPaginaCadastrarCliente(), null, null);
				}
			} else if (acao.equalsIgnoreCase(ValidacaoUtil.getAcaoEditarCliente())) {
				if (sessaoCliente != null && cliente != null) {
					Integer codigoC = new ValidacaoUtil().getParametroInteger(request, "codigo");
					cliente.setCodigo(codigoC);

					cliente = clienteService.buscarClientePorCodigo(cliente);

					if (cliente.getCodigo() != null) {
						request.setAttribute("cliente", cliente);
						new ValidacaoUtil().redirecionarParaAPagina(request, response,
								ValidacaoUtil.getPaginaCadastrarCliente(), "Edite o cliente!", "sucesso");
					} else {
						request.setAttribute("clientes", clienteService.buscarTodosClientes());
						new ValidacaoUtil().redirecionarParaAPagina(request, response,
								ValidacaoUtil.getPaginaListarCliente(), "Cliente não encontrado!", "perigo");
					}
				} else {
					new ValidacaoUtil().redirecionarParaAPagina(request, response,
							ValidacaoUtil.getPaginaAutenticarCliente(), "Você não está logado, faça o login!",
							"perigo");
				}
			} else if (acao.equalsIgnoreCase("removerCliente")) {
				if (sessaoCliente != null && cliente != null) {
					Integer codigoC = new ValidacaoUtil().getParametroInteger(request, "codigo");
					cliente.setCodigo(codigoC);

					cliente = clienteService.buscarClientePorCodigo(cliente);

					if (cliente.getCodigo() != null) {
						clienteService.removerClientePorCodigo(cliente);

						request.setAttribute("clientes", clienteService.buscarTodosClientes());

						new ValidacaoUtil().redirecionarParaAPagina(request, response,
								ValidacaoUtil.getPaginaListarCliente(), "Cliente removido com sucesso!", "sucesso");
					} else {
						request.setAttribute("clientes", clienteService.buscarTodosClientes());
						new ValidacaoUtil().redirecionarParaAPagina(request, response,
								ValidacaoUtil.getPaginaListarCliente(), "Não foi possível remover o cliente!",
								"perigo");
					}
				} else {
					new ValidacaoUtil().redirecionarParaAPagina(request, response,
							ValidacaoUtil.getPaginaAutenticarCliente(), "Você não está logado, faça o login!",
							"perigo");
				}
			}
		} catch (Exception e) {
			logger.severe("Erro ao processar a solicitação do cliente: " + e.getMessage());

			new ValidacaoUtil().redirecionarParaAPagina(request, response, ValidacaoUtil.getPaginaError(),
					"Erro ao processar a solicitação do cliente!", "erro");
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		try {
			clienteService = new ClienteService();
			perfilService = new PerfilService();
			estadoService = new EstadoService();

			Set<Menu> menus = new MenuService().listarTodasUrlsSubMenu();
			request.setAttribute("menus", menus);

			Integer codigo = new ValidacaoUtil().getParametroInteger(request, "codigo");
			String nome = new ValidacaoUtil().getParametroString(request, "nome");
			String email = new ValidacaoUtil().getParametroString(request, "email");
			String telefone = new ValidacaoUtil().getParametroString(request, "telefone");
			String cpf = new ValidacaoUtil().getParametroString(request, "cpf");
			String senha = new ValidacaoUtil().getParametroString(request, "senha");
			String perfil_id = new ValidacaoUtil().getParametroString(request, "perfil.codigo");

			cliente = new Cliente();
			cliente.setCodigo(codigo);
			cliente.setNome(nome);
			cliente.setEmail(email);
			cliente.setTelefone(telefone);
			cliente.setCpf(cpf);

			String senhaHashGerada = gerarSenhaHash(senha.trim());
			cliente.setSenha(senhaHashGerada != null && !senhaHashGerada.isEmpty() ? senhaHashGerada : null);

			perfil = new Perfil();
			if (perfil_id != null && !perfil_id.isEmpty()) {
				perfil.setCodigo(Integer.parseInt(perfil_id));
			} else {
				perfil.setCodigo(1);
			}

			cliente.setPerfil(perfil);

			if (cliente.getCodigo() != null) {
				clienteService.atualizarClientePorCodigo(cliente);

				request.setAttribute("clientes", clienteService.buscarTodosClientes());
				new ValidacaoUtil().redirecionarParaAPagina(request, response, ValidacaoUtil.getPaginaListarCliente(),
						"Cliente atualizado com sucesso!", "sucesso");
			} else {
				clienteService.adicionarCliente(cliente);

				new ValidacaoUtil().redirecionarParaAPagina(request, response,
						ValidacaoUtil.getPaginaAutenticarCliente(), "Cliente cadastrado com sucesso!", "sucesso");
			}
		} catch (Exception e) {
			logger.severe("Erro ao processar a solicitação do cliente: " + e.getMessage());

			new ValidacaoUtil().redirecionarParaAPagina(request, response, ValidacaoUtil.getPaginaError(),
					"Erro ao processar a solicitação do cliente!", "erro");
		}
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