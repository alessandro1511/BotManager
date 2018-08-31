package Dao;

import java.util.ArrayList;

public class Squadra {

	private String nome;
	private ArrayList<Giocatore> rosa = new ArrayList<Giocatore>();
	private ArrayList<Formazione> formazioni = new ArrayList<Formazione>();
	private Formazione formazioneUfficialeCampionato;
	private Formazione formazioneUfficialeCoppa;

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public ArrayList<Formazione> getFormazioni() {
		return formazioni;
	}

	public void setFormazioni(ArrayList<Formazione> formazioni) {
		this.formazioni = formazioni;
	}

	public Formazione getFormazioneUfficialeCampionato() {
		return formazioneUfficialeCampionato;
	}

	public void setFormazioneUfficialeCampionato(Formazione formazioneUfficialeCampionato) {
		this.formazioneUfficialeCampionato = formazioneUfficialeCampionato;
	}

	public Formazione getFormazioneUfficialeCoppa() {
		return formazioneUfficialeCoppa;
	}

	public void setFormazioneUfficialeCoppa(Formazione formazioneUfficialeCoppa) {
		this.formazioneUfficialeCoppa = formazioneUfficialeCoppa;
	}

	public ArrayList<Giocatore> getRosa() {
		return rosa;
	}

	public void setRosa(ArrayList<Giocatore> rosa) {
		this.rosa = rosa;
	}
}
