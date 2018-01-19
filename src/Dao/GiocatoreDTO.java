package Dao;

import java.util.ArrayList;

public class GiocatoreDTO {

	private String nome;
	private ArrayList<String> ruoli = new ArrayList<String>();
	private String squadra;
	private Integer quotazioneIniziale;
	private Integer quotazioneAttuale;
	private Integer partiteGiocate;
	private Double mediaVoto;
	private Double mediaFantacalcio;
	private Integer golFatti;
	private Integer golSubiti;
	private Integer assist;
	private Integer ammunizioni;
	private Integer espulsioni;
	private Double valutazione;

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
	public Integer getQuotazioneIniziale() {
		return quotazioneIniziale;
	}
	public void setQuotazioneIniziale(Integer quotazioneIniziale) {
		this.quotazioneIniziale = quotazioneIniziale;
	}
	public Integer getQuotazioneAttuale() {
		return quotazioneAttuale;
	}
	public void setQuotazioneAttuale(Integer quotazioneAttuale) {
		this.quotazioneAttuale = quotazioneAttuale;
	}
	public Integer getPartiteGiocate() {
		return partiteGiocate;
	}
	public void setPartiteGiocate(Integer partiteGiocate) {
		this.partiteGiocate = partiteGiocate;
	}
	public Double getMediaVoto() {
		return mediaVoto;
	}
	public void setMediaVoto(Double mediaVoto) {
		this.mediaVoto = mediaVoto;
	}
	public Double getMediaFantacalcio() {
		return mediaFantacalcio;
	}
	public void setMediaFantacalcio(Double mediaFantacalcio) {
		this.mediaFantacalcio = mediaFantacalcio;
	}
	public Integer getGolFatti() {
		return golFatti;
	}
	public void setGolFatti(Integer golFatti) {
		this.golFatti = golFatti;
	}
	public Integer getGolSubiti() {
		return golSubiti;
	}
	public void setGolSubiti(Integer golSubiti) {
		this.golSubiti = golSubiti;
	}
	public Integer getAssist() {
		return assist;
	}
	public void setAssist(Integer assist) {
		this.assist = assist;
	}
	public Integer getAmmunizioni() {
		return ammunizioni;
	}
	public void setAmmunizioni(Integer ammunizioni) {
		this.ammunizioni = ammunizioni;
	}
	public Integer getEspulsioni() {
		return espulsioni;
	}
	public void setEspulsioni(Integer espulsioni) {
		this.espulsioni = espulsioni;
	}
	public Double getValutazione() {
		return valutazione;
	}
	public void setValutazione(Double valutazione) {
		this.valutazione = valutazione;
	}
}
