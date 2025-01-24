package br.com.joaogcm.jg.restaurante.caseiro.jwt;

import java.util.Arrays;
import java.util.Date;
import java.util.Properties;

import com.nimbusds.jose.JOSEException;
import com.nimbusds.jose.JWSAlgorithm;
import com.nimbusds.jose.JWSHeader;
import com.nimbusds.jose.KeyLengthException;
import com.nimbusds.jose.crypto.MACSigner;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;

import br.com.joaogcm.jg.restaurante.caseiro.configuration.connection.ConfiguraConexaoBancoDeDados;

public class JWTUtil {
	/**
	 * Retorna um token válido.
	 * 
	 * @param email
	 * @return
	 * @throws KeyLengthException
	 * @throws JOSEException
	 */
	public static String gerarTokenJwt(String email) throws KeyLengthException, JOSEException {
		JWTClaimsSet claims = new JWTClaimsSet.Builder().subject(email).issuer(email).issueTime(new Date())
				.expirationTime(obterDataExpiracaoToken()).claim("roles", Arrays.asList("ROLE_ADMIN", "ROLE_USER"))
				.build();

		SignedJWT signedJWT = new SignedJWT(new JWSHeader(JWSAlgorithm.HS512), claims);
		signedJWT.sign(new MACSigner(obterChaveSecretaToken().getBytes()));

		return signedJWT.serialize();
	}

	/**
	 * Retorna a data de expiração do token: Válido por 30 minutos.
	 * 
	 * @return
	 */
	public static Date obterDataExpiracaoToken() {
		return new Date(System.currentTimeMillis() + 30 * 60 * 1000);
	}

	/**
	 * Retorna a chave secreta do token.
	 * 
	 * @return
	 */
	public static String obterChaveSecretaToken() {
		Properties properties = new Properties();
		String chaveSecreta = null;

		try {
			ClassLoader classLoader = ConfiguraConexaoBancoDeDados.class.getClassLoader();
			properties.load(classLoader.getResourceAsStream("database.properties"));

			chaveSecreta = properties.getProperty("CHAVE_SECRETA");
		} catch (Exception e) {
			throw new RuntimeException("Não foi possível obter a chave secreta do token: " + e.getMessage());
		}

		return chaveSecreta;
	}
}