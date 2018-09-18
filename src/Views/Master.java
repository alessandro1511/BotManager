package Views;

import java.util.ArrayList;

import org.apache.poi.ss.usermodel.Workbook;

import Commons.Grafica;
import Commons.Utils;
import Dao.Squadra;
import Pojo.Squadre;
import Pojo.Statistiche;

public abstract class Master {

	public static ArrayList<Squadra> squadre = new ArrayList<>();
	public static Workbook workbook = null;
	public static ArrayList<String> paths = null;

	public static void main(String[] args) throws Exception {
		try {
			caricaInfo();
		} catch (Throwable e) {
			System.out.println("Errore programma: " + e);
		}
	}

	public static void caricaInfo() throws Exception {
		System.out.println("Caricamento programma in corso... (sistema: " + System.getProperty("os.name") + ")");

		paths = Utils.calcolaPaths();

		for (String path : paths) {

			ArrayList<Squadra> teams = new ArrayList<>();

			workbook = Utils.connectionWorkbook(path);

			teams = Squadre.creaSquadre(teams, path);

			teams = Statistiche.calcolaQuotazioni(teams, path);

			teams = Statistiche.calcolaStatistiche(teams, path);

			teams = Statistiche.calcolaCalendario(teams, path);

			teams = Statistiche.calcolaVotiGiornate(teams, path);

			teams = Statistiche.calcolaProbabiliFormazioni(teams, path);

			for(Squadra s: teams) {
				squadre.add(s);
			}
		}

		Grafica.inferfaccia(squadre);
	}
}