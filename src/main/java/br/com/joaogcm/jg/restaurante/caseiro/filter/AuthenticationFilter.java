package br.com.joaogcm.jg.restaurante.caseiro.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
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

		String authHeader = req.getHeader("Authorization");

		if (authHeader == null || !authHeader.startsWith("Bearer ")) {
			res.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Token não encontrado!");
			return;
		}

		String token = authHeader.substring(7);

		try {
			SignedJWT signedJwt = SignedJWT.parse(token);
			JWSHeader jwsHeader = signedJwt.getHeader();

			// Verifica o algoritmo do token
			JWSAlgorithm jwsAlgorithm = jwsHeader.getAlgorithm();
			if (!jwsAlgorithm.equals(JWSAlgorithm.HS512)) {
				res.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Algoritmo inválido!");
				return;
			}

			// Verifica a assinatura do token
			MACVerifier macVerifier = new MACVerifier(JWTUtil.obterChaveSecretaToken().getBytes());
			if (!signedJwt.verify(macVerifier)) {
				res.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Assinatura do token inválida!");
				return;
			}
		} catch (Exception e) {
			res.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Token inválido!");
		}

		// Se o token for válido, segue o fluxo normalmente.
		chain.doFilter(request, response);
	}

	public void init(FilterConfig fConfig) throws ServletException {

	}
}