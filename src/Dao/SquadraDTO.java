package Dao;

import java.util.ArrayList;

public class SquadraDTO {

	private String nome;
	private ArrayList<GiocatoreDTO> rosa = new ArrayList<GiocatoreDTO>();
	private ArrayList<FormazioniDTO> formazioni = new ArrayList<FormazioniDTO>();
	private FormazioniDTO formazioneUfficialeCampionato;
	private FormazioniDTO formazioneUfficialeCoppa;
	
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public ArrayList<FormazioniDTO> getFormazioni() {
		return formazioni;
	}
	public void setFormazioni(ArrayList<FormazioniDTO> formazioni) {
		this.formazioni = formazioni;
	}
	public FormazioniDTO getFormazioneUfficialeCampionato() {
		return formazioneUfficialeCampionato;
	}
	public void setFormazioneUfficialeCampionato(FormazioniDTO formazioneUfficialeCampionato) {
		this.formazioneUfficialeCampionato = formazioneUfficialeCampionato;
	}
	public FormazioniDTO getFormazioneUfficialeCoppa() {
		return formazioneUfficialeCoppa;
	}
	public void setFormazioneUfficialeCoppa(FormazioniDTO formazioneUfficialeCoppa) {
		this.formazioneUfficialeCoppa = formazioneUfficialeCoppa;
	}
	public ArrayList<GiocatoreDTO> getRosa() {
		return rosa;
	}
	public void setRosa(ArrayList<GiocatoreDTO> rosa) {
		this.rosa = rosa;
	}
}
