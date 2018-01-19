package Pojo;

import java.util.ArrayList;
import java.util.Iterator;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;

import Dao.GiocatoreDTO;
import Dao.SquadraDTO;
import Views.Master;

public class Squadre extends Master{

	/**
	 * Carico dal workbook tutte le informazioni delle squadre presenti.
	 * <p>
	 * Gli sheet presi in considerazione saranno quelli che iniziano con la parola FORMAZIONE...
	 *
	 * @return ArrayList - array delle squadre
	 *
	 * @throws Exception
	 */
	public static ArrayList<SquadraDTO> creaSquadre() throws Exception
	{
		System.out.println("Inizio caricamento squadre");

		ArrayList<SquadraDTO> squadre = new ArrayList<SquadraDTO>();
        for (Sheet sheet : workbook)
        {
        	if (sheet.getSheetName().toUpperCase().startsWith("FORMAZIONE"))
        	{
	        	SquadraDTO squadra = new SquadraDTO();
	        	squadra.setNome(sheet.getSheetName());
	        	Iterator<Row> iterator = sheet.iterator();
		        while (iterator.hasNext())
		        {
		            Row nextRow = iterator.next();
		            Iterator<Cell> cellIterator = nextRow.cellIterator();
		            int indexCol = 0;
		            GiocatoreDTO g = new GiocatoreDTO();
		            while (cellIterator.hasNext())
		            {
		                Cell cell = cellIterator.next();
		                indexCol++;
		                if (indexCol == 2) //nome giocatore
		                {
		                	g.setNome(cell.getStringCellValue().toUpperCase().trim());
		                }
		                if (indexCol == 1) // ruoli
		                {
		                	if (!cell.getStringCellValue().toUpperCase().trim().contains(";"))  g.getRuoli().add(cell.getStringCellValue().toUpperCase().trim());	// ruolo
		    				else for (String r : cell.getStringCellValue().toUpperCase().trim().split(";")) g.getRuoli().add(r.trim()); 							// ruoli
		                }
		            }
		            squadra.getRosa().add(g);
		        }
		        squadre.add(squadra);
        	}
        }
        workbook.close();

        System.out.println("Fine caricamento squadre");
		return squadre;
	}
}