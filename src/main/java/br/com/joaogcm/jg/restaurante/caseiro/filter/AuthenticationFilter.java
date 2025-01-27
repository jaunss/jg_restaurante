package br.com.joaogcm.jg.restaurante.caseiro.filter;

import java.io.IOException;
import java.util.Set;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import br.com.joaogcm.jg.restaurante.caseiro.model.Cliente;
import br.com.joaogcm.jg.restaurante.caseiro.model.Menu;
import br.com.joaogcm.jg.restaurante.caseiro.service.MenuService;

public class AuthenticationFilter extends HttpFilter implements Filter {

	private static final long serialVersionUID = 1L;

	public AuthenticationFilter() {
		super();
	}

	public void destroy() {

	}

	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		HttpServletRequest req = (HttpServletRequest) request;
		HttpServletResponse res = (HttpServletResponse) response;

		String contextPath = req.getContextPath();

		String[] publicUrls = { 
				contextPath + "/",
				contextPath + "/Home",
				contextPath + "/Pedido",
				contextPath + "/Lanche",
				contextPath + "/Cliente",
				contextPath + "/Endereco",
				contextPath + "/Autenticacao"
				};

		String requestURI = req.getRequestURI();
		String queryString = req.getQueryString();
		
		HttpSession sessaoCliente = req.getSession();
		Cliente cliente = (Cliente) sessaoCliente.getAttribute("clienteComCadastro");

		Set<Menu> menus = new MenuService().listarTodasUrlsSubMenu();
		req.setAttribute("menus", menus);

		if (requestURI.equals(contextPath + "/")) {
			if (sessaoCliente != null && cliente != null) {
				redirecionarParaPagina(req, res, "/index.jsp",
						"Olá " + cliente.getNome() + ", seja bem-vindo ao JG Restaurante :)", "sucesso");
				return;
			} else {
				redirecionarParaPagina(req, res, "/index.jsp", "Seja bem-vindo ao JG Restaurante :)", "sucesso");
				return;
			}
		} else {
			boolean isExisteQueryString = false;
			if (queryString != null) {
				isExisteQueryString = true;
			}

			if (requestURI.equals(contextPath + "/Autenticacao")
					|| (isExisteQueryString && queryString.equals("acao=autenticarCliente"))) {
				chain.doFilter(request, response);
				return;
			}

			if (sessaoCliente != null && cliente != null) {
				chain.doFilter(request, response);
				return;
			} else {
				boolean liberarAcesso = false;

				for (String url : publicUrls) {
					if (requestURI.endsWith(url.substring(contextPath.length()))) {
						liberarAcesso = true;
						break;
					}
				}

				// Adiciona verificação para arquivos estáticos, como CSS, JS, imagens, etc.
				if (requestURI.endsWith(".css") || requestURI.endsWith(".js") || requestURI.endsWith(".png")
						|| requestURI.endsWith(".jpg") || requestURI.endsWith(".jpeg") || requestURI.endsWith(".gif")
						|| requestURI.endsWith(".woff") || requestURI.endsWith(".woff2")
						|| requestURI.endsWith(".ttf")) {
					liberarAcesso = true;
				}

				if (liberarAcesso) {
					chain.doFilter(request, response);
					return;
				} else {
					redirecionarParaPagina(req, res, "/paginas/autenticacao/autenticar-login.jsp",
							"Você não está logado, faça o login!", "perigo");
					return;
				}
			}
		}
	}

	public void init(FilterConfig fConfig) throws ServletException {

	}

	private void redirecionarParaPagina(HttpServletRequest request, HttpServletResponse response, String pagina,
			String mensagem, String tipoMensagem) throws IOException, ServletException {
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