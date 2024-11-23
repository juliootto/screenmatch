package br.com.otto.screenmatch.principal;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

import br.com.otto.screenmatch.model.DadosEpisodio;
import br.com.otto.screenmatch.model.DadosSerie;
import br.com.otto.screenmatch.model.DadosTemporada;
import br.com.otto.screenmatch.model.Episodio;
import br.com.otto.screenmatch.service.ConsumoAPI;
import br.com.otto.screenmatch.service.ConverteDados;

public class Principal {
	  private Scanner leitura = new Scanner(System.in); 
	  private ConsumoAPI consumo = new ConsumoAPI();
	  
	  private final String ENDERECO = "https://www.omdbapi.com/?t=";
	  private final String API_KEY = "&apikey=6585022c";

	    public void exibMenu() {
	    	var nomeSerie ="";
	    	do {
	    		System.out.println("Digite o nome da série para a busca");
	    		
	    		nomeSerie = leitura.nextLine().toUpperCase();
	    		
	    		System.out.println("A partir de que ano gostaria de ver os episódios?");
	    		var ano = leitura.nextInt();
	    		leitura.nextLine();
	    		
	    		LocalDate data = LocalDate.of(ano, 1, 1);
	    		
	    		var enderecoSerie = ENDERECO + nomeSerie.replace(" ", "+") + API_KEY;
	            var json = consumo.obterDados(enderecoSerie);
	            
	            ConverteDados conversor = new ConverteDados();
	            
	            DadosSerie dados = conversor.obterDados(json, DadosSerie.class);

	            List<DadosTemporada> temporadas = new ArrayList<DadosTemporada>();
	            List<DadosEpisodio> dadosEpisodios = new ArrayList<DadosEpisodio>();
	    		
	    		for (int i = 1; i <= dados.totalTemporadas(); i++) {
	    			json = consumo.obterDados(enderecoSerie + "&season=" + i );
	    			DadosTemporada dadosTemporada = conversor.obterDados(json, DadosTemporada.class);
	    			temporadas.add(dadosTemporada);

	    		}
	    		temporadas.forEach(t-> dadosEpisodios.addAll(t.episodios()));
	    		
	    		 List<Episodio> episodios = temporadas.stream()
	    	                .flatMap(t -> t.episodios().stream()
	    	                        .map(d -> new Episodio(t.numero(), d)))
	    	                .collect(Collectors.toList());
	    		
	    		episodios.stream()
	    		         .filter(e-> e.getDataLancamento()!=null && e.getDataLancamento().isAfter(data))
	    		         .forEach(System.out::println);
	    		
	    		//episodios.forEach(e->System.out.println(e.toString()));
	            System.out.println("");
	            System.out.println("");
			} while (!nomeSerie.equals("SAIR"));				
			
	            
	        
	    }

}
