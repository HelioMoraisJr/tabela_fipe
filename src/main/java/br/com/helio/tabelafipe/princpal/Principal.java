package br.com.helio.tabelafipe.princpal;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

import br.com.helio.tabelafipe.model.Dados;
import br.com.helio.tabelafipe.model.Modelos;
import br.com.helio.tabelafipe.model.Veiculo;
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
		
		System.out.println("Digite o codigo da marca: ");
		var codMarca = leitura.nextLine();
		
		variacaoEndereco = variacaoEndereco + "/" + codMarca + "/modelos";
		json = consumoApi.obterDados(variacaoEndereco);
		
		var modeloLista = conversor.obterDados(json, Modelos.class);
		System.out.println("\nModelos dessa marca: ");
		modeloLista.modelos().stream()
				.sorted(Comparator.comparing(Dados::codigo))
				.forEach(System.out::println);
		
		System.out.println("\nDigite um trecho do nome do carro: ");
		var trechoVeiculo = leitura.nextLine();
		
		List<Dados> modelosFiltrados = modeloLista.modelos()
				.stream()
				.filter(m -> m.nome().toLowerCase().contains(trechoVeiculo.toLowerCase()))
				.collect(Collectors.toList());
		
		System.out.println("\nModelos buscados ");
		modelosFiltrados.forEach(System.out::println);
		
		System.out.println("Digite por favor o código do modelo");
		
		var codigoModelo = leitura.nextLine();
		
		variacaoEndereco = variacaoEndereco  + "/" + codigoModelo + "/anos";
		
		json = consumoApi.obterDados(variacaoEndereco);
		
		List<Dados> anos = conversor.obterDadosLista(json, Dados.class);
		List<Veiculo> veiculos = new ArrayList<>();
		
		for (int i = 0; i < anos.size(); i++) {
			var enderecoAno = variacaoEndereco + "/" + anos.get(i).codigo();
			json = consumoApi.obterDados(enderecoAno);
			Veiculo veiculo = conversor.obterDados(json, Veiculo.class);
			veiculos.add(veiculo);
					
		}
		
		
		
		System.out.println("\nTodos os veiculos buscados com avaliações por ano: "); 
		veiculos.forEach(System.out::println);			
		
	}

}
