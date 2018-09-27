package Pojo;

import java.util.ArrayList;
import java.util.Iterator;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;

import Dao.Fantacalcio;
import Dao.Giocatore;
import Dao.Squadra;
import Views.Master;

public class Squadre extends Master {

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
			if (sheet.getSheetName().toUpperCase().startsWith("FORMAZIONE")) {
				Squadra squadra = new Squadra();

				//nome squadra
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
						if (indexCol == 2) // nome giocatore
						{
							g.setNome(cell.getStringCellValue().toUpperCase().trim());
						}
						if (indexCol == 1) // ruoli
						{
							if (!cell.getStringCellValue().toUpperCase().trim().contains(";"))
								g.getRuoli().add(cell.getStringCellValue().toUpperCase().trim()); // ruolo
							else
								for (String r : cell.getStringCellValue().toUpperCase().trim().split(";"))
									g.getRuoli().add(r.trim()); // ruoli
						}
					}
					squadra.getRosa().add(g);
				}
				squadre.add(squadra);
			}
		}
		fantacalcio.getWorkbook().close();

		System.out.println("Squadre caricate");
		return squadre;
	}
}