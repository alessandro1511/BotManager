package views;

import java.util.ArrayList;

import commons.Grafica;
import commons.Utils;
import dao.Fantacalcio;
import dao.Squadra;
import pojo.Squadre;
import pojo.Statistiche;

public abstract class Master {

	public static void main(String[] args) throws Exception {
		try {
			caricaInfo();
		} catch (Throwable e) {
			System.out.println("Errore programma: " + e);
		}
	}

	public static void caricaInfo() throws Exception {
		System.out.println("Caricamento programma in corso... (sistema: " + System.getProperty("os.name") + ")");

		ArrayList<Squadra> squadreGlobali = new ArrayList<>();

		ArrayList<Fantacalcio> listaFileFantacalcio = Utils.caricaListaFileFantacalcio();

		for (Fantacalcio fantacalcio : listaFileFantacalcio) {

			ArrayList<Squadra> squadre = new ArrayList<>();

			squadre = Squadre.creaSquadre(squadre, fantacalcio);

			squadre = Statistiche.calcolaQuotazioni(squadre, fantacalcio);

			squadre = Statistiche.calcolaStatistiche(squadre, fantacalcio);

			squadre = Statistiche.calcolaCalendario(squadre, fantacalcio);

			squadre = Statistiche.calcolaVotiGiornate(squadre, fantacalcio);

			squadre = Statistiche.calcolaProbabiliFormazioni(squadre, fantacalcio);

			for (Squadra s : squadre) {
				squadreGlobali.add(s);
			}
		}
		Grafica.creaFile(squadreGlobali);

	}
}