package pojo;

import java.util.ArrayList;
import java.util.Iterator;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;

import dao.Fantacalcio;
import dao.Giocatore;
import dao.Squadra;
import views.BotManager;

public class Squadre extends BotManager {

	/**
	 * Carico dal workbook tutte le informazioni delle squadre presenti.
	 * <p>
	 * Gli sheet presi in considerazione saranno quelli che iniziano con la parola
	 * FORMAZIONE...
	 *
	 * @return ArrayList - array delle squadre
	 *
	 * @throws Exception
	 */
	public static ArrayList<Squadra> creaSquadre(ArrayList<Squadra> squadre, Fantacalcio fantacalcio) throws Exception {
		System.out.println("Caricamento squadre");

		for (Sheet sheet : fantacalcio.getWorkbook()) {
			Squadra squadra = new Squadra();

			// nome squadra
			squadra.setNome(sheet.getSheetName());
			squadra.setAnno(fantacalcio.getAnno());
			Iterator<Row> iterator = sheet.iterator();
			while (iterator.hasNext()) {
				Row nextRow = iterator.next();
				Iterator<Cell> cellIterator = nextRow.cellIterator();
				int indexCol = 0;
				Giocatore g = new Giocatore();
				while (cellIterator.hasNext()) {
					Cell cell = cellIterator.next();
					indexCol++;

					// nome giocatore
					if (indexCol == 2) {
						g.setNome(cell.getStringCellValue().toUpperCase().replaceAll("\\*", "").trim());
					}

					// ruoli
					if (indexCol == 1) {

						if (!cell.getStringCellValue().toUpperCase().trim().contains(";"))
							g.getRuoli().add(cell.getStringCellValue().toUpperCase().trim()); // singolo ruolo
						else
							for (String r : cell.getStringCellValue().toUpperCase().trim().split(";"))
								g.getRuoli().add(r.trim()); // multi ruolo
					}

					// nome squadra fantacalcio
					if (indexCol == 3) {
						g.setSquadraFantacalcio(cell.getStringCellValue().toUpperCase().trim());
					}
				}

				// aggiungo il giocatore se presente
				if (g.getNome() != null && !g.getNome().isEmpty()) {
					squadra.getRosa().add(g);
				}
			}
			squadre.add(squadra);
		}
		fantacalcio.getWorkbook().close();

		System.out.println("Squadre caricate");
		return squadre;
	}
}