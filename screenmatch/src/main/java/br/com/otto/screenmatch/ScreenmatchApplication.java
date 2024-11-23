package br.com.otto.screenmatch;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import br.com.otto.screenmatch.model.DadosSerie;
import br.com.otto.screenmatch.service.ConsumoAPI;
import br.com.otto.screenmatch.service.ConverteDados;

@SpringBootApplication
public class ScreenmatchApplication implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(ScreenmatchApplication.class, args);
	}
	@Override
	public void run(String...args ) throws Exception {
		System.out.println("entrou");
		var consumoAPI = new ConsumoAPI();
		var json = consumoAPI.obterDados("https://www.omdbapi.com/?t=gilmore+girls&apikey=6585022c");
		System.out.println(json);
		
		ConverteDados conversor = new ConverteDados();
	    DadosSerie dados = conversor.obterDados(json, DadosSerie.class);
	    System.out.println(dados);
	}

}