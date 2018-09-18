package Commons;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;
import java.net.URI;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import org.apache.commons.lang3.StringUtils;

import Dao.Squadra;
import Views.Master;

public class Grafica {

	/**
	 * Visualizzazione grafica di ogni squadra
	 *
	 * @param squadre
	 */
	public static void inferfaccia(ArrayList<Squadra> squadre) {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");

		while (true) {
			try {
				Utils.CLS();

				int index = 0;
				for (Squadra squadra : squadre) {
					System.out.println(index + " - " + squadra.getNome());
					index++;
				}
				System.out.println("S - Scarica file Statistiche");
				System.out.println("V - Scarica file Voti");
				System.out.println("Q - Scarica file Quotazioni");
				System.out.println("P - Scarica file Probabili Formazioni");
				System.out.println("R - Ricarica tutto");
				System.out.println("E - Exit");

				System.out.print("Inserire un valore per proseguire: ");
				String input = br.readLine();

				Utils.CLS();

				if (StringUtils.isNumeric(input)) {
					System.out.println(Utils.printRosa(squadre.get(Integer.parseInt(input))));
					System.out.println("Premere invio per continuare");
					br.readLine();
				} else if (input.toUpperCase().equals("S")) {
					java.awt.Desktop.getDesktop().browse(new URI(Costanti.URL_STATISTICHE));
				} else if (input.toUpperCase().equals("V")) {
					java.awt.Desktop.getDesktop().browse(new URI(Costanti.URL_VOTI));
				} else if (input.toUpperCase().equals("Q")) {
					java.awt.Desktop.getDesktop().browse(new URI(Costanti.URL_QUOTAZIONI));
				} else if (input.toUpperCase().equals("P")) {
					java.awt.Desktop.getDesktop().browse(new URI(Costanti.URL_PROBABILI_FORMAZIONI));
				} else if (input.toUpperCase().equals("R")) {
					Master.caricaInfo();
				} else if (input.toUpperCase().equals("E")) {
					System.exit(0);
				}
			} catch (Exception e) {
				System.out.println("Valore non ammesso.");
			}
		}
	}
}
