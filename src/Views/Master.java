package Views;

import java.util.ArrayList;
import java.util.Map;

import org.apache.poi.ss.usermodel.Workbook;

import Commons.Grafica;
import Commons.Utils;
import Dao.Modulo;
import Dao.Squadra;
import Pojo.Moduli;
import Pojo.ProbabiliFormazioni;
import Pojo.Squadre;
import Pojo.Statistiche;

public abstract class Master {

	public static ArrayList<Squadra> squadre = null;
	public static Map<String, Modulo> moduli = null;
	public static Workbook workbook = null;
	public static String path = null;

	public static void main(String[] args) throws Exception {
		try {
			caricaInfo();
		} catch (Throwable e) {
			System.out.println("Errore programma: " + e);
		}
	}

	public static void caricaInfo() throws Exception {
		System.out.println("Caricamento programma in corso... (sistema: " + System.getProperty("os.name") + ")");

		path = Utils.calcolaPath();

		workbook = Utils.connectionWorkbook(path);

		squadre = Squadre.creaSquadre();

		moduli = Moduli.createModuli();

		squadre = Statistiche.calcolaQuotazioni(squadre, path);

		squadre = Statistiche.calcolaStatistiche(squadre, path);

		squadre = Statistiche.calcolaCalendario(squadre, path);

		squadre = Statistiche.calcolaVotiGiornate(squadre, path);

		squadre = ProbabiliFormazioni.calcolaProbabiliFormazioni(squadre, path);

		Grafica.inferfaccia(squadre, path);
	}

	public static String getPath() {
		return path;
	}
}