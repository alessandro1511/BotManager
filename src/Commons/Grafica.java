package Commons;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import Dao.Giocatore;
import Dao.Squadra;

import java.io.FileOutputStream;
import java.util.ArrayList;


public class Grafica {

	public static void creaFile(ArrayList<Squadra> squadre) throws Exception {

		System.out.println("Caricamento file " + Costanti.FILE_BOT_MANAGER);

		// Create a Workbook
		Workbook workbook = new XSSFWorkbook(); // new HSSFWorkbook() for generating `.xls` file

		/*
		 * CreationHelper helps us create instances of various things like DataFormat,
		 * Hyperlink, RichTextString etc, in a format (HSSF, XSSF) independent way
		 */
		CreationHelper createHelper = workbook.getCreationHelper();

		try {

		for (Squadra squadra : squadre) {

			// Create a Sheet
			Sheet sheet = workbook.createSheet(squadra.getNome() + "(" + squadra.getAnno() + ")");

			// Freeze le prime 6 colonne
			sheet.createFreezePane(1, 0);
			sheet.createFreezePane(2, 0);
			sheet.createFreezePane(3, 0);
			sheet.createFreezePane(4, 0);
			sheet.createFreezePane(5, 0);
			sheet.createFreezePane(6, 0);

			// Create a Font for styling header cells
			Font headerFont = workbook.createFont();
			headerFont.setBold(true);
			headerFont.setFontHeightInPoints((short) 11);

			// Create a CellStyle with the font
			CellStyle headerCellStyle = workbook.createCellStyle();
			headerCellStyle.setFont(headerFont);

			// Create a Header Row
			Row headerRow = sheet.createRow(0);
			ArrayList<String> headerColumns = new ArrayList<>();
			headerColumns.add("Giocatore");
			headerColumns.add("Ruolo");
			headerColumns.add("Squadra");
			headerColumns.add("Presenze");
			headerColumns.add("Quotazione");
			headerColumns.add("Probabilità");
			for (int i = 0; i < squadra.getGiornateCampionato(); i++) {
				headerColumns.add("Giornata " + (i+1));
			}

			// Create cells
			for (int i = 0; i < headerColumns.size(); i++) {
				Cell cell = headerRow.createCell(i);
				cell.setCellValue(headerColumns.get(i));
				cell.setCellStyle(headerCellStyle);
			}

			// Create Cell Style for formatting Date
			//CellStyle cellStyle = workbook.createCellStyle();
			int rowNum = 0;
			for (Giocatore giocatore : squadra.getRosa()) {
				Row row = sheet.createRow(rowNum+1);

				row.createCell(0).setCellValue(giocatore.getNome());

				String ruoli = "";
				for (String r : giocatore.getRuoli()) {
					ruoli = ruoli + r + " ";
				}
				row.createCell(1).setCellValue(ruoli);

				row.createCell(2).setCellValue(giocatore.getSquadra());

//				cellStyle.setDataFormat(workbook.getCreationHelper().createDataFormat().getFormat("#"));
//				row.createCell(3).setCellStyle(cellStyle);
				if (giocatore.getPartiteGiocate() != null) {
					row.createCell(3).setCellValue(String.valueOf(giocatore.getPartiteGiocate()));
				}

//				cellStyle.setDataFormat(workbook.getCreationHelper().createDataFormat().getFormat("#"));
//				row.createCell(3).setCellStyle(cellStyle);
				if (giocatore.getQuotazioneAttuale() != null && giocatore.getQuotazioneAttuale() > 0) {
					row.createCell(4).setCellValue(String.valueOf(giocatore.getQuotazioneAttuale()));
				}

				row.createCell(5).setCellValue(giocatore.getProbabilitaProssimoIncontro());

				for (int i= 0; i < squadra.getGiornateCampionato(); i++) {
					String giornata = "";
					if (i < giocatore.getVoti().size() && giocatore.getVoti().get(i).getValutazione() != null){
						giornata = String.valueOf(giocatore.getVoti().get(i).getValutazione().doubleValue()) + " ";
					}
					if (i < giocatore.getCalendarioAvversarie().size() && !giocatore.getCalendarioAvversarie().get(i).isEmpty()){
						giornata = giornata + giocatore.getCalendarioAvversarie().get(i).substring(0, 3) + " ";
					}
					if (i < giocatore.getCasaTrasferta().size() && !giocatore.getCasaTrasferta().get(i).isEmpty()){
						giornata = giornata + giocatore.getCasaTrasferta().get(i);
					}
					row.createCell(6 + i).setCellValue(giornata.trim());
				}
				rowNum++;
			}

			// Resize all columns to fit the content size
			for (int i = 0; i < headerColumns.size(); i++) {
				sheet.autoSizeColumn(i);
			}
		}
		} catch (Exception e) {
			System.out.println("Errore carimecamento file " + Costanti.FILE_BOT_MANAGER);
		}

		// Write the output to a file
		FileOutputStream fileOut = new FileOutputStream(Utils.jarPath() + Costanti.FILE_BOT_MANAGER);
		workbook.write(fileOut);
		fileOut.close();

		// Closing the workbook
		workbook.close();

		System.out.println("File " + Costanti.FILE_BOT_MANAGER +" caricato");
	}
}
