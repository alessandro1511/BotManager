package Dao;

import java.util.ArrayList;

public class FormazioniDTO {

	private String modulo;
	private ArrayList<GiocatoreDTO> formazione = new ArrayList<GiocatoreDTO>();
	private double valutazione;
	
	public ArrayList<GiocatoreDTO> getFormazione() {
		return formazione;
	}
	public void setFormazione(ArrayList<GiocatoreDTO> formazione) {
		this.formazione = formazione;
	}
	public String getModulo() {
		return modulo;
	}
	public void setModulo(String modulo) {
		this.modulo = modulo;
	}
	public double getValutazione() {
		return valutazione;
	}
	public void setValutazione(double valutazione) {
		this.valutazione = valutazione;
	}
}