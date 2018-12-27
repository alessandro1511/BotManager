package dao;

public class Voti {

	private Double voto;
	private Integer golFatti;
	private Integer golSubiti;
	private Integer rigoreParato;
	private Integer rigoreSbagalto;
	private Integer rigoreFatto;
	private Integer autogol;
	private Integer ammunizioni;
	private Integer espulsioni;
	private Integer assist;
	private Integer assistDaFermo;
	private Integer golDellaVittoria;
	private Integer golDelPareggio;
	private Double valutazione;

	public Voti() {
		super();
		this.voto = null;
		this.golFatti = null;
		this.golSubiti = null;
		this.rigoreParato = null;
		this.rigoreSbagalto = null;
		this.rigoreFatto = null;
		this.autogol = null;
		this.ammunizioni = null;
		this.espulsioni = null;
		this.assist = null;
		this.assistDaFermo = null;
		this.golDellaVittoria = null;
		this.golDelPareggio = null;
		this.valutazione = null;
	}

	public Double getVoto() {
		return voto;
	}

	public void setVoto(Double voto) {
		this.voto = voto;
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

	public Integer getRigoreParato() {
		return rigoreParato;
	}

	public void setRigoreParato(Integer rigoreParato) {
		this.rigoreParato = rigoreParato;
	}

	public Integer getRigoreSbagalto() {
		return rigoreSbagalto;
	}

	public void setRigoreSbagalto(Integer rigoreSbagalto) {
		this.rigoreSbagalto = rigoreSbagalto;
	}

	public Integer getRigoreFatto() {
		return rigoreFatto;
	}

	public void setRigoreFatto(Integer rigoreFatto) {
		this.rigoreFatto = rigoreFatto;
	}

	public Integer getAutogol() {
		return autogol;
	}

	public void setAutogol(Integer autogol) {
		this.autogol = autogol;
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

	public Integer getAssist() {
		return assist;
	}

	public void setAssist(Integer assist) {
		this.assist = assist;
	}

	public Integer getAssistDaFermo() {
		return assistDaFermo;
	}

	public void setAssistDaFermo(Integer assistDaFermo) {
		this.assistDaFermo = assistDaFermo;
	}

	public Integer getGolDellaVittoria() {
		return golDellaVittoria;
	}

	public void setGolDellaVittoria(Integer golDellaVittoria) {
		this.golDellaVittoria = golDellaVittoria;
	}

	public Integer getGolDelPareggio() {
		return golDelPareggio;
	}

	public void setGolDelPareggio(Integer golDelPareggio) {
		this.golDelPareggio = golDelPareggio;
	}

	public Double getValutazione() {
		return valutazione;
	}

	public void setValutazione(Double valutazione) {
		this.valutazione = valutazione;
	}

}
