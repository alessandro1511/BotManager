package Commons;

import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.usermodel.charts.AxisCrosses;
import org.apache.poi.ss.usermodel.charts.AxisPosition;
import org.apache.poi.ss.usermodel.charts.ChartAxis;
import org.apache.poi.ss.usermodel.charts.ChartDataSource;
import org.apache.poi.ss.usermodel.charts.ChartLegend;
import org.apache.poi.ss.usermodel.charts.DataSources;
import org.apache.poi.ss.usermodel.charts.LegendPosition;
import org.apache.poi.ss.usermodel.charts.LineChartData;
import org.apache.poi.ss.usermodel.charts.LineChartSeries;
import org.apache.poi.ss.usermodel.charts.ValueAxis;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFChart;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openxmlformats.schemas.drawingml.x2006.chart.CTBoolean;
import org.openxmlformats.schemas.drawingml.x2006.chart.CTLineSer;
import org.openxmlformats.schemas.drawingml.x2006.chart.CTPlotArea;
import org.apache.poi.ss.usermodel.Comment;

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
			// Create a Sheet Grafici
			//Sheet sheetGrafici = workbook.createSheet("Grafica");

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
				sheet.createFreezePane(7, 0);

				// Create a Font for styling header cells
				Font headerFont = workbook.createFont();
				headerFont.setBold(true);
				headerFont.setFontHeightInPoints((short) 11);

				// Create a CellStyle with the font
				CellStyle headerCellStyle = workbook.createCellStyle();
				headerCellStyle.setFont(headerFont);

				// Create a Header Row and Cells
				Row headerRow = sheet.createRow(0);
				Cell cell0 = headerRow.createCell(0);
				cell0.setCellValue("G.");
				Utils.addComment(workbook, sheet, headerRow.getRowNum(), 0, "", "Giocatore");
				cell0.setCellStyle(headerCellStyle);

				Cell cell1 = headerRow.createCell(1);
				cell1.setCellValue("R.");
				Utils.addComment(workbook, sheet, headerRow.getRowNum(), 1, "", "Ruolo");
				cell1.setCellStyle(headerCellStyle);

				Cell cell2 = headerRow.createCell(2);
				cell2.setCellValue("S.");
				Utils.addComment(workbook, sheet, headerRow.getRowNum(), 2, "", "Squadra");
				cell2.setCellStyle(headerCellStyle);

				Cell cell3 = headerRow.createCell(3);
				cell3.setCellValue("Q.");
				Utils.addComment(workbook, sheet, headerRow.getRowNum(), 3, "", "Quotazione");
				cell3.setCellStyle(headerCellStyle);

				Cell cell4 = headerRow.createCell(4);
				cell4.setCellValue("A.");
				Utils.addComment(workbook, sheet, headerRow.getRowNum(), 4, "", "Squadra Avversaria");
				cell4.setCellStyle(headerCellStyle);

				Cell cell5 = headerRow.createCell(5);
				cell5.setCellValue("CoT");
				Utils.addComment(workbook, sheet, headerRow.getRowNum(), 5, "", "Casa o Trasferta");
				cell5.setCellStyle(headerCellStyle);

				Cell cell6 = headerRow.createCell(6);
				cell6.setCellValue("P.");
				Utils.addComment(workbook, sheet, headerRow.getRowNum(), 6, "", "Probabilità di giocare");
				cell6.setCellStyle(headerCellStyle);

				for (int i = 0; i < squadra.getGiornateCampionato(); i++) {
					Cell celli = headerRow.createCell(i + 7);
					if (i == squadra.getProssimaGiornataCampionato() - 1) {
						celli.setCellValue((i + 1) + "" + (char) 170 + " C");
						Utils.addComment(workbook, sheet, headerRow.getRowNum(), (i + 7), "", (i + 1) + " Giornata Corrente");
					} else {
						celli.setCellValue((i + 1) + "" + (char) 170);
						Utils.addComment(workbook, sheet, headerRow.getRowNum(), (i + 7), "", (i + 1) + " Giornata");
					}
					celli.setCellStyle(headerCellStyle);
				}

				// Create Cell Style for formatting Date
				int rowNum = 1;
				for (Giocatore giocatore : squadra.getRosa()) {
					Row row = sheet.createRow(rowNum);

					row.createCell(0).setCellValue(giocatore.getNome());

					String ruoli = "";
					for (String r : giocatore.getRuoli()) {
						ruoli = ruoli + r + " ";
					}
					row.createCell(1).setCellValue(ruoli);

					row.createCell(2).setCellValue(giocatore.getSquadra());

					if (giocatore.getQuotazioneAttuale() != null && giocatore.getQuotazioneAttuale().compareTo(Integer.valueOf(0)) > 0) {
						row.createCell(3).setCellType(CellType.NUMERIC);
						row.getCell(3).setCellValue(giocatore.getQuotazioneAttuale().intValue());
					} else {
						row.createCell(3).setCellType(CellType.STRING);
						row.getCell(3).setCellValue("");
					}

					row.createCell(4).setCellType(CellType.STRING);
					row.createCell(5).setCellType(CellType.STRING);
					if ((squadra.getProssimaGiornataCampionato()) <= giocatore.getCalendarioAvversarie().size()) {
						giocatore.setProssimaSquadraAvversaria(
								giocatore.getCalendarioAvversarie().get(squadra.getProssimaGiornataCampionato() - 1));
						row.getCell(4).setCellValue(giocatore.getProssimaSquadraAvversaria());
						row.getCell(5).setCellValue(giocatore.getCasaTrasferta().get(squadra.getProssimaGiornataCampionato() - 1));
					}

					row.createCell(6).setCellValue(giocatore.getProbabilitaProssimoIncontro());

					for (int i = 0; i < squadra.getGiornateCampionato(); i++) {
						String giornata = (i + 1) + "" + (char) 170 + " Giornata\n";
						if (i < giocatore.getCalendarioAvversarie().size()
								&& !giocatore.getCalendarioAvversarie().get(i).isEmpty()) {
							giornata = giornata + giocatore.getCalendarioAvversarie().get(i) + "\n";
						}
						if (i < giocatore.getCasaTrasferta().size() && !giocatore.getCasaTrasferta().get(i).isEmpty()) {
							giornata = giornata + (giocatore.getCasaTrasferta().get(i).equalsIgnoreCase("C") ? "Casa"
									: "Trasferta");
						}

						double voto = (i < giocatore.getVoti().size()
								&& giocatore.getVoti().get(i).getValutazione() != null)
										? giocatore.getVoti().get(i).getValutazione().doubleValue()
										: 0.0;
						row.createCell(7 + i).setCellType(CellType.NUMERIC);
						row.getCell(7 + i).setCellValue(voto);
						Utils.addComment(workbook, sheet, rowNum, 7 + i, "", giornata.trim());
					}

					rowNum = rowNum + 1;

				}

				// Resize all columns to fit the content size
				for (int i = 0; i < headerRow.getLastCellNum(); i++) {
					sheet.autoSizeColumn(i);
				}
			}

			// Creazione Grafici
//			Drawing drawing = sheetGrafici.createDrawingPatriarch();
//			ClientAnchor anchor = drawing.createAnchor(0, 0, 0, 0, 1, 1, 17, 22);
//
//			Chart chart = drawing.createChart(anchor);
//			ChartLegend legend = chart.getOrCreateLegend();
//			legend.setPosition(LegendPosition.TOP);
//
//			LineChartData data = chart.getChartDataFactory().createLineChartData();
//
//			ChartAxis bottomAxis = chart.getChartAxisFactory().createCategoryAxis(AxisPosition.BOTTOM);
//			ValueAxis leftAxis = chart.getChartAxisFactory().createValueAxis(AxisPosition.LEFT);
//			leftAxis.setCrosses(AxisCrosses.AUTO_ZERO);
//
//			for (Squadra squadra : squadre) {
//				for (Giocatore giocatore : squadra.getRosa()) {
//					ChartDataSource<Number> asseX = DataSources.fromNumericCellRange(workbook.getSheetAt(1), new CellRangeAddress(0, 0, 7, 43));
//					for (int i = 1; i < workbook.getNumberOfSheets(); i++) {
//
//						ChartDataSource<Number> asseY = DataSources.fromNumericCellRange(workbook.getSheetAt(i),
//								new CellRangeAddress(Utils.findRow(workbook.getSheetAt(i), giocatore.getNome()),
//										Utils.findRow(workbook.getSheetAt(i), giocatore.getNome()), 7, 43));
//
//						LineChartSeries series1 = data.addSeries(asseX, asseY);
//						series1.setTitle(giocatore.getNome());
//
//						chart.plot(data, bottomAxis, leftAxis);
//					}
//				}
//			}
		} catch (

		Exception e) {
			System.out.println("Errore carimecamento file " + Costanti.FILE_BOT_MANAGER);
		}

		// Write the output to a file
		FileOutputStream fileOut = new FileOutputStream(Utils.jarPath() + Costanti.FILE_BOT_MANAGER);
		workbook.write(fileOut);
		fileOut.close();

		// Closing the workbook
		workbook.close();

		System.out.println("File " + Costanti.FILE_BOT_MANAGER + " caricato");
	}
}
