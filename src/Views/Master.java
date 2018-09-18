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

			workbook = Utils.connectionWorkbook(path);

			squadre = Squadre.creaSquadre(squadre, path);

			squadre = Statistiche.calcolaQuotazioni(squadre, path);

			squadre = Statistiche.calcolaStatistiche(squadre, path);

			squadre = Statistiche.calcolaCalendario(squadre, path);

			squadre = Statistiche.calcolaVotiGiornate(squadre, path);

			squadre = Statistiche.calcolaProbabiliFormazioni(squadre, path);
		}

		Grafica.inferfaccia(squadre);
	}
}