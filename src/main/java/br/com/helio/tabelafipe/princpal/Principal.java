package br.com.helio.tabelafipe.princpal;

import java.util.Comparator;
import java.util.Scanner;

import br.com.helio.tabelafipe.model.Dados;
import br.com.helio.tabelafipe.service.ConsumoApi;
import br.com.helio.tabelafipe.service.ConverteDados;

public class Principal {
	
	private Scanner leitura = new Scanner(System.in);	
	private ConsumoApi consumoApi = new ConsumoApi(); // Cria uma instância da classe ConsumoApi
	private ConverteDados conversor = new ConverteDados();
	
	
	final String ENDERECO = "https://parallelum.com.br/fipe/api/v1/";

	
	public void exibeMenu() {
		
		System.out.println("*************Opções*************");
		System.out.println("Carro");
		System.out.println("Moto");
		System.out.println("Caminhão\n");
		System.out.println("Digite uma das opções para consultar valores: ");
		var busca = leitura.nextLine();
		
		String variacaoEndereco;
		
		if(busca.toLowerCase().contains("carr")){			
			variacaoEndereco = ENDERECO + "carros/marcas";				
		} else if (busca.toLowerCase().contains("mot")) {
			variacaoEndereco = ENDERECO + "motos/marcas";	
		} else {
			variacaoEndereco = ENDERECO + "caminhoes/marcas";
		}
		
		var json = consumoApi.obterDados(variacaoEndereco);		
		// Imprime o JSON obtido no console
		System.out.println(json);
		
		var marcas = conversor.obterDadosLista(json, Dados.class);	
		marcas.stream()
				.sorted(Comparator.comparing(Dados::codigo))
				.forEach(System.out::println);
		

		
	}

}
