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

public class Statistiche {

	/**
	 * Cerco il file delle Statistiche e per ogni giocatore verifico i suoi dati.
	 *
	 * @param squadre - array delle squadre
	 * @param infoSerieA
	 *
	 * @return array delle squadre aggiornato
	 *
	 * @throws Exception
	 */
	public static ArrayList<SquadraDTO> creaStatistiche(ArrayList<SquadraDTO> squadre, String path) throws Exception
	{
		System.out.println("Caricamento file delle statistiche");
		String pathFile = Utils.connectionFile(path, Costanti.FILE_STATISTICHE);
        FileInputStream inputStream = new FileInputStream(pathFile);

        System.out.println("Caricamento statistiche per ogni giocatore");
        Workbook workbook = new XSSFWorkbook(inputStream);
        Sheet sheet = workbook.getSheetAt(0);

        try {
        for (SquadraDTO squadra : squadre)
        {
        	Iterator<Row> iterator = sheet.iterator();
	        while (iterator.hasNext()) {
	            Row nextRow = iterator.next();
	            Iterator<Cell> cellIterator = nextRow.cellIterator();

	            int indexCol = 0;
	            int indexGiocatore = -1;
	            boolean findGiocatore = false;
	            while (cellIterator.hasNext())
	            {
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
	                else if (indexCol == 4 && findGiocatore) // squadra provenienza
	                {
	                	squadra.getRosa().get(indexGiocatore).setSquadra(cell.getStringCellValue().toUpperCase().trim());
	                }
	                else if (indexCol == 5 && findGiocatore) // partite giocate
	                {
	                	squadra.getRosa().get(indexGiocatore).setPartiteGiocate(Double.valueOf(cell.getNumericCellValue()).intValue());
	                }
	                else if (indexCol == 6 && findGiocatore) // media voto
	                {
	                	squadra.getRosa().get(indexGiocatore).setMediaVoto(cell.getNumericCellValue());
	                }
	                else if (indexCol == 7 && findGiocatore) // media fantacalcio
	                {
	                	squadra.getRosa().get(indexGiocatore).setMediaFantacalcio(cell.getNumericCellValue());
	                }
	                else if (indexCol == 8 && findGiocatore) // gol fatti
	                {
	                	squadra.getRosa().get(indexGiocatore).setGolFatti(Double.valueOf(cell.getNumericCellValue()).intValue());
	                }
	                else if (indexCol == 9 && findGiocatore) // gol subiti
	                {
	                	squadra.getRosa().get(indexGiocatore).setGolSubiti(Double.valueOf(cell.getNumericCellValue()).intValue());
	                }
	                else if ((indexCol == 14 || indexCol == 15) && findGiocatore) // assist
	                {
	                	if (squadra.getRosa().get(indexGiocatore).getAssist() != null)
	                		squadra.getRosa().get(indexGiocatore).setAssist(squadra.getRosa().get(indexGiocatore).getAssist() + Integer.valueOf(Double.valueOf(cell.getNumericCellValue()).intValue()));
	                	else
	                		squadra.getRosa().get(indexGiocatore).setAssist(Integer.valueOf(Double.valueOf(cell.getNumericCellValue()).intValue()));
	                }
	                else if (indexCol == 16 && findGiocatore) // ammunizioni
	                {
	                	squadra.getRosa().get(indexGiocatore).setAmmunizioni(Double.valueOf(cell.getNumericCellValue()).intValue());
	                }
	                else if (indexCol == 17 && findGiocatore) // espulsioni
	                {
	                	squadra.getRosa().get(indexGiocatore).setEspulsioni(Double.valueOf(cell.getNumericCellValue()).intValue());
	                }
	            }
	            if (indexGiocatore != -1 && squadra.getRosa().get(indexGiocatore).getMediaFantacalcio() != null && squadra.getRosa().get(indexGiocatore).getQuotazioneAttuale() != null)
	            {
	            	squadra.getRosa().get(indexGiocatore).setValutazione(squadra.getRosa().get(indexGiocatore).getMediaFantacalcio() +
	            													 squadra.getRosa().get(indexGiocatore).getQuotazioneAttuale());
	            }
	        }
        }
        }
        catch(Exception e){
        	System.out.println("Errore: " + e);
        }
        workbook.close();
        inputStream.close();

        System.out.println("Statistiche per ogni giocatore caricate");
		return squadre;
	}
}
