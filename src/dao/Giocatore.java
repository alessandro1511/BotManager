package dao;

import java.util.ArrayList;

public class Giocatore {

	private String nome;
	private ArrayList<String> ruoli = new ArrayList<>();
	private String squadra;
	private String squadraFantacalcio;
	private Integer quotazioneAttuale;
	private Integer quotazioneIniziale;
	private Integer quotazioneDiff;
	private Double mediaVoto;
	private Double mediaVotoUltimoMese;
	private String mercatoRiparazione;
	private ArrayList<Voti> voti = new ArrayList<>();

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public ArrayList<String> getRuoli() {
		return ruoli;
	}

	public void setRuoli(ArrayList<String> ruoli) {
		this.ruoli = ruoli;
	}

	public String getSquadra() {
		return squadra;
	}

	public void setSquadra(String squadra) {
		this.squadra = squadra;
	}

	public String getSquadraFantacalcio() {
		return squadraFantacalcio;
	}

	public void setSquadraFantacalcio(String squadraFantacalcio) {
		this.squadraFantacalcio = squadraFantacalcio;
	}

	public Integer getQuotazioneAttuale() {
		return quotazioneAttuale;
	}

	public void setQuotazioneAttuale(Integer quotazioneAttuale) {
		this.quotazioneAttuale = quotazioneAttuale;
	}

	public Integer getQuotazioneIniziale() {
		return quotazioneIniziale;
	}

	public void setQuotazioneIniziale(Integer quotazioneIniziale) {
		this.quotazioneIniziale = quotazioneIniziale;
	}

	public Integer getQuotazioneDiff() {
		return quotazioneDiff;
	}

	public void setQuotazioneDiff(Integer quotazioneDiff) {
		this.quotazioneDiff = quotazioneDiff;
	}

	public Double getMediaVoto() {
		return mediaVoto;
	}

	public void setMediaVoto(Double mediaVoto) {
		this.mediaVoto = mediaVoto;
	}

	public Double getMediaVotoUltimoMese() {
		return mediaVotoUltimoMese;
	}

	public void setMediaVotoUltimoMese(Double mediaVotoUltimoMese) {
		this.mediaVotoUltimoMese = mediaVotoUltimoMese;
	}

	public String getMercatoRiparazione() {
		return mercatoRiparazione;
	}

	public void setMercatoRiparazione(String mercatoRiparazione) {
		this.mercatoRiparazione = mercatoRiparazione;
	}

	public ArrayList<Voti> getVoti() {
		return voti;
	}

	public void setVoti(ArrayList<Voti> voti) {
		this.voti = voti;
	}

	public void addVoti(Voti voti) {
		this.voti.add(voti);
	}

}
