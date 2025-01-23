package br.com.joaogcm.jg.restaurante.caseiro.argon;

import de.mkammerer.argon2.Argon2;
import de.mkammerer.argon2.Argon2Factory;

public class ArgonUtil {
	/**
	 * Verifica a senha hash armazenada no banco de dados com a senha inserida pelo
	 * usuário na autenticação.
	 * 
	 * @param senhaInserida
	 * @param senhaHashGravada
	 * @return
	 */
	public static boolean verificarSenhaHash(String senhaInserida, String senhaHashGravada) {
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