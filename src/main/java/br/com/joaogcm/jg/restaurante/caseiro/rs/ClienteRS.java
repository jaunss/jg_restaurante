package br.com.joaogcm.jg.restaurante.caseiro.rs;

import br.com.joaogcm.jg.restaurante.caseiro.model.Endereco;
import jakarta.ws.rs.client.Client;
import jakarta.ws.rs.client.ClientBuilder;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

public class ClienteRS {
	public Endereco buscarEnderecoPorCep(String cep) {
		Client client = null;
		Endereco endereco = null;

		try {
			client = ClientBuilder.newClient();

			Response response = client.target("https://viacep.com.br/ws/" + cep + "/json/")
					.request(MediaType.APPLICATION_JSON).get();

			if (response.getStatus() == 200) {
				endereco = response.readEntity(Endereco.class);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (client != null) {
				client.close();
			}
		}

		return endereco;
	}
}