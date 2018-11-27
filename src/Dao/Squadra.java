package Dao;

import java.util.ArrayList;

public class Squadra {

	private String nome; //nome squadra
	private String anno; //anno della squadra
	private int giornateCampionato; //giornate totali di campionato
	private int prossimaGiornataCampionato; //prossima giornata di campionato
	private ArrayList<Giocatore> rosa = new ArrayList<>(); //rosa della squadra nel relativo anno

	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public String getAnno() {
		return anno;
	}
	public void setAnno(String anno) {
		this.anno = anno;
	}
	public int getGiornateCampionato() {
		return giornateCampionato;
	}
	public void setGiornateCampionato(int giornateCampionato) {
		this.giornateCampionato = giornateCampionato;
	}
	public int getProssimaGiornataCampionato() {
		return prossimaGiornataCampionato;
	}
	public void setProssimaGiornataCampionato(int prossimaGiornataCampionato) {
		this.prossimaGiornataCampionato = prossimaGiornataCampionato;
	}
	public ArrayList<Giocatore> getRosa() {
		return rosa;
	}
	public void setRosa(ArrayList<Giocatore> rosa) {
		this.rosa = rosa;
	}

}
