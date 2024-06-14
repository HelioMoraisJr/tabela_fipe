package br.com.helio.tabelafipe.service;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class ConsumoApi {
	
	// Método que obtém dados a partir de uma URL fornecida
	public String obterDados(String tipo) {
        // Cria uma instância de HttpClient que será usada para enviar a requisição HTTP		
		HttpClient client = HttpClient.newHttpClient();

		// Cria uma requisição HTTP GET para a URI fornecida no parâmetro 'tipo'
		HttpRequest request = HttpRequest.newBuilder()
				.uri(URI.create(tipo)) // Define a URI para a requisição
				.GET() // Define que o método da requisição será GET
				.build(); // Constrói a requisição
		HttpResponse<String> response = null;

		try {
			// Envia a requisição e espera pela resposta, usando um handler que converte o corpo da resposta em uma String
			response = client.send(request, HttpResponse.BodyHandlers.ofString());
		} catch (IOException | InterruptedException e) {
			// Em caso de erro de entrada/saída ou interrupção, lança uma exceção em runtime
			throw new RuntimeException(e);
		}
		// Obtém o corpo da resposta como uma String
		String json = response.body();
		return json; // Retorna o corpo da resposta como JSON

	}

}
