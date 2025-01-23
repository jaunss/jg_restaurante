package br.com.joaogcm.jg.restaurante.caseiro.filter;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

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

import com.nimbusds.jose.JWSAlgorithm;
import com.nimbusds.jose.JWSHeader;
import com.nimbusds.jose.crypto.MACVerifier;
import com.nimbusds.jwt.SignedJWT;

import br.com.joaogcm.jg.restaurante.caseiro.jwt.JWTUtil;

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

		String[] publicUrls = { contextPath + "/", contextPath + "/index.jsp", contextPath + "/error.jsp",
				contextPath + "/Home", contextPath + "/Autenticacao", contextPath + "/Cliente",
				contextPath + "Lanche" };

		String requestURI = req.getRequestURI();
		String queryString = req.getQueryString();

		for (String url : publicUrls) {
			if (requestURI.equals(url)) {
				chain.doFilter(request, response);
				return;
			}

			if (requestURI.startsWith(url)) {
				if (liberarAcessoParaDeterminadasPaginas(contextPath, requestURI, queryString)) {
					chain.doFilter(request, response);
					return;
				} else {
					redirecionarParaPagina(req, res, "/paginas/autenticacao/autenticar-login.jsp",
							"Acesso negado! Faça o login ou cadastre-se!", "perigo");
					return;
				}
			}
		}

		String authHeader = req.getHeader("Authorization");

		if (authHeader == null || !authHeader.startsWith("Bearer ")) {
			redirecionarParaPagina(req, res, "/paginas/autenticacao/autenticar-login.jsp",
					"Token inválido, realize o login!", "perigo");
			return;
		}

		String token = authHeader.substring(7);

		try {
			SignedJWT signedJwt = SignedJWT.parse(token);
			JWSHeader jwsHeader = signedJwt.getHeader();

			// Verifica o algoritmo do token
			JWSAlgorithm jwsAlgorithm = jwsHeader.getAlgorithm();
			if (!jwsAlgorithm.equals(JWSAlgorithm.HS512)) {
				redirecionarParaPagina(req, res, "/paginas/autenticacao/autenticar-login.jsp",
						"Algoritmo inválido, faça o login novamente!", "perigo");
				return;
			}

			// Verifica a assinatura do token
			MACVerifier macVerifier = new MACVerifier(JWTUtil.obterChaveSecretaToken().getBytes());
			if (!signedJwt.verify(macVerifier)) {
				redirecionarParaPagina(req, res, "/paginas/autenticacao/autenticar-login.jsp",
						"Assinatura do token inválida, faça o login novamente!", "perigo");
				return;
			}

		} catch (Exception e) {
			redirecionarParaPagina(req, res, "/paginas/autenticacao/autenticar-login.jsp", "Token inválido!", "erro");
			return;
		}

		// Se o token for válido, segue o fluxo normalmente.
		chain.doFilter(request, response);
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

	private boolean liberarAcessoParaDeterminadasPaginas(String contextPath, String requestURI, String queryString) {
		Map<String, String> params = getQueryParams(queryString);

		if (queryString == null) {
			return false;
		}

		if (requestURI.equals(contextPath + "/Home") && "home".equals(params.get("acao"))) {
			return true;
		} else if (requestURI.equals(contextPath + "/Cliente") && "cadastrarCliente".equals(params.get("acao"))) {
			return true;
		} else if (requestURI.equals(contextPath + "/Lanche") && "listarLanche".equals(params.get("acao"))) {
			return true;
		} else if (requestURI.equals(contextPath + "/Autenticacao") && "autenticarCliente".equals(params.get("acao"))) {
			return true;
		}

		return false;
	}

	private Map<String, String> getQueryParams(String queryString) {
		Map<String, String> params = new HashMap<String, String>();

		if (queryString != null && !queryString.isEmpty()) {
			String[] pairs = queryString.split("&");

			for (String pair : pairs) {
				String[] keyValue = pair.split("=");
				if (keyValue.length == 2) {
					params.put(keyValue[0], keyValue[1]);
				}
			}
		}

		return params;
	}
}