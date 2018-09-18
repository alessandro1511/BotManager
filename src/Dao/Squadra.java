package Dao;

import java.util.ArrayList;

public class Squadra {

	private String nome;
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

	public void setRosa(ArrayList<Giocatore> rosa) {
		this.rosa = rosa;
	}
}
