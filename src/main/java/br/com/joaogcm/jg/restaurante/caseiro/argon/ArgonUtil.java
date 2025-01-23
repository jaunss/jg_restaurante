package br.com.joaogcm.jg.restaurante.caseiro.argon;

import de.mkammerer.argon2.Argon2;
import de.mkammerer.argon2.Argon2Factory;

public class ArgonUtil {
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