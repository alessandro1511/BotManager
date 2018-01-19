package Pojo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;

import Dao.ModuliDTO;
import Views.Master;

public class Moduli extends Master {

	/**
	 * Carico dal workbook lo sheet dei moduli (quello che inizia con MODULI).
	 *
	 * @return Map - <nome_modulo, ruoli>
	 *
	 * @throws Exception
	 */
	public static Map<String, ModuliDTO> createModuli() throws Exception
	{
		System.out.println("Inizio creazione e suddivisione dei moduli");

		Map<String, ModuliDTO> moduli = new HashMap<String, ModuliDTO>();
        for (Sheet sheet : workbook)
        {
        	if (sheet.getSheetName().toUpperCase().startsWith("MODULI"))
        	{
	        	Iterator<Row> iterator = sheet.iterator();
		        while (iterator.hasNext())
		        {
		            Row nextRow = iterator.next();
		            Iterator<Cell> cellIterator = nextRow.cellIterator();
		            int indexCol = 0;
		            String nomeModulo = null;
		            ModuliDTO modulo = new ModuliDTO();
		            while (cellIterator.hasNext())
		            {
		                Cell cell = cellIterator.next();
		                indexCol++;

		                if (indexCol == 1)
		                {
		                	nomeModulo = String.valueOf(Double.valueOf(cell.getNumericCellValue()).intValue());	//nome modulo
		                }
		                else
		                {
		                	ArrayList<String> ruoli = new ArrayList<String>();
		                	if (!cell.getStringCellValue().toUpperCase().trim().contains(","))
		                	{
		                		ruoli.add(cell.getStringCellValue().toUpperCase().trim());	// inserisco un solo ruolo
		                	}
		                	else
		                	{
		                		for (String r : cell.getStringCellValue().toUpperCase().trim().split(","))
		                		ruoli.add(r.toUpperCase().trim());												// inserisco tutti i possibili ruoli
		                	}
		                	modulo.getModulo().add(ruoli);	// aggiungo tutti i ruoli per la posizione
		                }
		            }
					moduli.put(nomeModulo, modulo);	// aggiungo nome e modulo
		        }
			}
		}

        System.out.println("Fine creazione e suddivisione dei moduli");
	    return moduli;
	}
}