package Dao;

import java.util.ArrayList;

public class Squadra {

	private String nome;
	private String anno;
	private int giornateCampionato;
	private ArrayList<Giocatore> rosa = new ArrayList<Giocatore>();

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public ArrayList<Giocatore> getRosa() {
		return rosa;
	}

	public String getAnno() {
		return anno;
	}

	public void setAnno(String anno) {
		this.anno = anno;
	}

	public void setRosa(ArrayList<Giocatore> rosa) {
		this.rosa = rosa;
	}

	public int getGiornateCampionato() {
		return giornateCampionato;
	}

	public void setGiornateCampionato(int giornateCampionato) {
		this.giornateCampionato = giornateCampionato;
	}
}
