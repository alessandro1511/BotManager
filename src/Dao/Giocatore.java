package Dao;

import java.util.ArrayList;

public class Giocatore {

	private String nome;
	private ArrayList<String> ruoli = new ArrayList<>();
	private String squadra;
	private Integer quotazioneIniziale;
	private Integer quotazioneAttuale;
	private Integer partiteGiocate;
	private ArrayList<Voti> voti = new ArrayList<>();
	private ArrayList<String> calendarioAvversarie = new ArrayList<>();
	private ArrayList<String> casaTrasferta = new ArrayList<>();

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

	public ArrayList<Voti> getVoti() {
		return voti;
	}

	public void setVoti(ArrayList<Voti> voti) {
		this.voti = voti;
	}

	public void addVoti(Voti voti) {
		this.voti.add(voti);
	}

	public ArrayList<String> getCalendarioAvversarie() {
		return calendarioAvversarie;
	}

	public void setCalendarioAvversarie(ArrayList<String> calendarioAvversarie) {
		this.calendarioAvversarie = calendarioAvversarie;
	}

	public void addCalendarioAvversaria(String avversaria) {
		this.calendarioAvversarie.add(avversaria);
	}

	public ArrayList<String> getCasaTrasferta() {
		return casaTrasferta;
	}

	public void setCasaTrasferta(ArrayList<String> casaTrasferta) {
		this.casaTrasferta = casaTrasferta;
	}

	public void addCasaTrasferta(String casaTrasferta) {
		this.casaTrasferta.add(casaTrasferta);
	}

}
