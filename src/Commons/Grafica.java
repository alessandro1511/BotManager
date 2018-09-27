package Commons;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import Dao.Squadra;

import java.io.FileOutputStream;
import java.util.ArrayList;


public class Grafica {

	public static void testFile(ArrayList<Squadra> squadre) throws Exception {

		System.out.println("Caricamento file " + Costanti.FILE_BOT_MANAGER);

		// Create a Workbook
		Workbook workbook = new XSSFWorkbook(); // new HSSFWorkbook() for generating `.xls` file

		/*
		 * CreationHelper helps us create instances of various things like DataFormat,
		 * Hyperlink, RichTextString etc, in a format (HSSF, XSSF) independent way
		 */
		CreationHelper createHelper = workbook.getCreationHelper();

		for (Squadra squadra : squadre) {

			// Create a Sheet
			Sheet sheet = workbook.createSheet(squadra.getNome() + " (" + squadra.getAnno() + ")");

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
			headerColumns.add("Quotazione");
			for (int i = 0; i < squadra.getGiornateCampionato(); i++) {
				headerColumns.add("Giornata " + (i+1));
			}

			// Create cells
			for (int i = 0; i < headerColumns.size(); i++) {
				Cell cell = headerRow.createCell(i);
				cell.setCellValue(headerColumns.get(i));
				cell.setCellStyle(headerCellStyle);
			}

			/*
			// Create Cell Style for formatting Date
			CellStyle dateCellStyle = workbook.createCellStyle();
			dateCellStyle.setDataFormat(createHelper.createDataFormat().getFormat("dd-MM-yyyy"));

			// Create Other rows and cells with employees data
			int rowNum = 1;
			for (Employee employee : employees) {
				Row row = sheet.createRow(rowNum++);

				row.createCell(0).setCellValue(employee.getName());

				row.createCell(1).setCellValue(employee.getEmail());

				Cell dateOfBirthCell = row.createCell(2);
				dateOfBirthCell.setCellValue(employee.getDateOfBirth());
				dateOfBirthCell.setCellStyle(dateCellStyle);

				row.createCell(3).setCellValue(employee.getSalary());
			}
			*/

			// Resize all columns to fit the content size
			for (int i = 0; i < headerColumns.size(); i++) {
				sheet.autoSizeColumn(i);
			}
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
