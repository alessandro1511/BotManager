package Pojo;

import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.Iterator;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import Commons.Costanti;
import Commons.Utils;
import Dao.GiocatoreDTO;
import Dao.SquadraDTO;

public class Quotazioni {

	/**
	 * Cerco il file delle Quotazioni e carico tutte le quotazioni per ogni giocatore.
	 *
	 * @param squadre - array delle squadre
	 *
	 * @return ArrayList - array delle squadre aggiornato
	 *
	 * @throws Exception
	 */
	public static ArrayList<SquadraDTO> creaQuotazioni(ArrayList<SquadraDTO> squadre) throws Exception
	{
		System.out.println("Caricamento file quotazioni");
		String pathFile = Utils.connectionFile(Costanti.PATH, Costanti.FILE_QUOTAZIONI);
	    FileInputStream inputStream = new FileInputStream(pathFile);

	    System.out.println("Caricamento delle quotazioni per ogni giocatori");
	    Workbook workbook = new XSSFWorkbook(inputStream);
	    Sheet firstSheet = workbook.getSheetAt(0);

	    for (SquadraDTO squadra : squadre)
	    {
	    	Iterator<Row> iterator = firstSheet.iterator();
	        while (iterator.hasNext()) {
	            Row nextRow = iterator.next();
	            Iterator<Cell> cellIterator = nextRow.cellIterator();

	            int indexCol = 0;
	            int indexGiocatore = -1;
	            boolean findGiocatore = false;
	            while (cellIterator.hasNext()) {
	                Cell cell = cellIterator.next();
	                indexCol++;
	                if (indexCol == 3) //nome giocatore
	                {
	                	for (GiocatoreDTO g : squadra.getRosa())
	                	{
	                		if (g.getNome().equals(cell.getStringCellValue().toUpperCase().trim()))
	                		{
	                			indexGiocatore = squadra.getRosa().indexOf(g);
	                			findGiocatore = true;
	                			break;
	                		}
	                	}
	                }
	                else if (indexCol == 5 && findGiocatore) // quotazione attuale
	                {
	                	squadra.getRosa().get(indexGiocatore).setQuotazioneAttuale(Double.valueOf(cell.getNumericCellValue()).intValue());
	                }
	                else if (indexCol == 6 && findGiocatore) // quotazione iniziale
	                {
	                	squadra.getRosa().get(indexGiocatore).setQuotazioneIniziale(Double.valueOf(cell.getNumericCellValue()).intValue());
	                }
	            }
	        }
	    }
	    workbook.close();
	    inputStream.close();

	    System.out.println("Quotazioni giocatori caricate");
	    return squadre;
	}
}