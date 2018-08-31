package Dao;

import java.util.ArrayList;

public class Formazione {

	private String modulo;
	private ArrayList<Giocatore> formazione = new ArrayList<Giocatore>();
	private double valutazione;

	public ArrayList<Giocatore> getFormazione() {
		return formazione;
	}

	public void setFormazione(ArrayList<Giocatore> formazione) {
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