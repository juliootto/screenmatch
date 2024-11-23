package br.com.otto.screenmatch.model;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;

import lombok.AllArgsConstructor;
import lombok.Getter;

import lombok.Setter;
import lombok.ToString;

@Getter
@AllArgsConstructor
@Setter
@ToString
public class Episodio {
	public Episodio(Integer temporada, DadosEpisodio dadosEpisodio) {
		this.temporada = temporada;
		this.titulo = dadosEpisodio.titulo();
		this.numeroEpisodio = dadosEpisodio.numero();
		
		try {
            this.avaliacao = Double.valueOf(dadosEpisodio.avaliacao());
        } catch (NumberFormatException ex) {
            this.avaliacao = 0.0;
        }

		try {
	        this.dataLancamento = LocalDate.parse(dadosEpisodio.dataLancamento());
	    } catch (DateTimeParseException ex) {
	        this.dataLancamento = null;
	    }
	}

	private Integer temporada;
	private String titulo;
	private Integer numeroEpisodio;
	private Double avaliacao;
	private LocalDate dataLancamento;


}
