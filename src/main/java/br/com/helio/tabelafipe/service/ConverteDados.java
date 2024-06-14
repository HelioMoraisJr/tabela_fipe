package br.com.helio.tabelafipe.service;

import java.util.List;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.CollectionType;

public class ConverteDados implements  IConverteDados {

    private final ObjectMapper mapper = new ObjectMapper();

    @Override
    public <T> T obterDados(String json, Class<T> classe){
        try {
            return mapper.readValue(json, classe);
        } catch (JsonProcessingException e) {
            //throw new RuntimeException(e);
            System.err.println("Erro ao processar JSON: " + e.getMessage());
            throw new RuntimeException("Erro ao processar JSON", e);
        }
        
    }

	@Override
	public <T> List<T> obterDadosLista(String json, Class<T> classe) {
		CollectionType lista = mapper.getTypeFactory()
				.constructCollectionType(List.class, classe);
		try {
			return mapper.readValue(json, lista);

		} catch (JsonProcessingException e) {
			
			throw new RuntimeException(e);
		}
	}

}
