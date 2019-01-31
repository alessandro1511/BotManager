package pojo;

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
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import commons.Costanti;
import commons.Utils;
import dao.Giocatore;
import dao.Squadra;

import java.io.FileOutputStream;
import java.util.ArrayList;

public class Grafica {

	public static void creaFile(ArrayList<Squadra> squadre, boolean grafico, boolean comment) throws Exception {

		System.out.println("Caricamento file " + Costanti.FILE_BOT_MANAGER);

		// Create a Workbook
		Workbook workbook = new XSSFWorkbook(); // new HSSFWorkbook() for generating `.xls` file

		// indice colonne principali
		int indexGiocatore = 0;
		int indexRuolo = 1;
		int indexSquadra = 2;
		int indexQuotazioneAtt = 3;
		int indexQuotazioneDiff = 4;
		int indexMediaVoto = 5;
		int indexSquadraAvv = 6;
		int indexCasaTrasf = 7;
		int indexProb = 8;

		// totale colonne principali
		int totImportantColum = 9;

		try {
			// Create a Sheet Grafici
			Sheet sheetGrafici = null;
			if (grafico) {
				sheetGrafici = workbook.createSheet("Grafica");
			}

			for (Squadra squadra : squadre) {

				// Create a Sheet
				Sheet sheet = workbook.createSheet(squadra.getNome() + " (" + squadra.getAnno() + ")");

				// Freeze le prime 6 colonne (unico caso in cui si parte da 1 e non da 0)
				sheet.createFreezePane(indexGiocatore + 1, 0);
				sheet.createFreezePane(indexRuolo + 1, 0);
				sheet.createFreezePane(indexSquadra + 1, 0);
				sheet.createFreezePane(indexQuotazioneAtt + 1, 0);
				sheet.createFreezePane(indexQuotazioneDiff + 1, 0);
				sheet.createFreezePane(indexMediaVoto + 1, 0);
				sheet.createFreezePane(indexSquadraAvv + 1, 0);
				sheet.createFreezePane(indexCasaTrasf + 1, 0);
				sheet.createFreezePane(indexProb + 1, 0);

				// Create a Font for styling header cells
				Font headerFont = workbook.createFont();
				headerFont.setBold(true);
				headerFont.setFontHeightInPoints((short) 11);

				// Create a CellStyle with the font
				CellStyle headerCellStyle = workbook.createCellStyle();
				headerCellStyle.setFont(headerFont);

				// Creazione colonne principali
				Row headerRow = sheet.createRow(indexGiocatore);
				Cell cellGiocatore = headerRow.createCell(indexGiocatore);
				cellGiocatore.setCellValue("G.");
				Utils.addComment(workbook, sheet, headerRow.getRowNum(), indexGiocatore, "", "Giocatore");
				cellGiocatore.setCellStyle(headerCellStyle);

				Cell cellRuolo = headerRow.createCell(indexRuolo);
				cellRuolo.setCellValue("R.");
				Utils.addComment(workbook, sheet, headerRow.getRowNum(), indexRuolo, "", "Ruolo");
				cellRuolo.setCellStyle(headerCellStyle);

				Cell cellSquadra = headerRow.createCell(indexSquadra);
				cellSquadra.setCellValue("S.");
				Utils.addComment(workbook, sheet, headerRow.getRowNum(), indexSquadra, "", "Squadra");
				cellSquadra.setCellStyle(headerCellStyle);

				Cell cellQuotazioneAtt = headerRow.createCell(indexQuotazioneAtt);
				cellQuotazioneAtt.setCellValue("QA.");
				Utils.addComment(workbook, sheet, headerRow.getRowNum(), indexQuotazioneAtt, "", "Quotazione Attuale");
				cellQuotazioneAtt.setCellStyle(headerCellStyle);

				Cell cellQuotazioneDiff = headerRow.createCell(indexQuotazioneDiff);
				cellQuotazioneDiff.setCellValue("QD.");
				Utils.addComment(workbook, sheet, headerRow.getRowNum(), indexQuotazioneDiff, "", "Quotazione Diff");
				cellQuotazioneDiff.setCellStyle(headerCellStyle);

				Cell cellMediaVoto = headerRow.createCell(indexMediaVoto);
				cellMediaVoto.setCellValue("MV.");
				Utils.addComment(workbook, sheet, headerRow.getRowNum(), indexMediaVoto, "", "Media Voto");
				cellMediaVoto.setCellStyle(headerCellStyle);

				Cell cellSquadraAvv = headerRow.createCell(indexSquadraAvv);
				cellSquadraAvv.setCellValue("A.");
				Utils.addComment(workbook, sheet, headerRow.getRowNum(), indexSquadraAvv, "", "Squadra Avversaria");
				cellSquadraAvv.setCellStyle(headerCellStyle);

				Cell cellCasaTrasf = headerRow.createCell(indexCasaTrasf);
				cellCasaTrasf.setCellValue("CoT");
				Utils.addComment(workbook, sheet, headerRow.getRowNum(), indexCasaTrasf, "", "Casa o Trasferta");
				cellCasaTrasf.setCellStyle(headerCellStyle);

				Cell cellProb = headerRow.createCell(indexProb);
				cellProb.setCellValue("P.");
				Utils.addComment(workbook, sheet, headerRow.getRowNum(), indexProb, "", "Probabilità di giocare");
				cellProb.setCellStyle(headerCellStyle);

				// Creazione colonne delle giornate di campionato
				for (int i = 0; i < squadra.getGiornateCampionato(); i++) {
					Cell celli = headerRow.createCell(i + totImportantColum);
					if (i == squadra.getProssimaGiornataCampionato() - 1) {
						celli.setCellValue((i + 1) + "" + (char) 170 + " C");
						Utils.addComment(workbook, sheet, headerRow.getRowNum(), (i + totImportantColum), "",
								(i + 1) + " Giornata Corrente");
					} else {
						celli.setCellValue((i + 1) + "" + (char) 170);
						Utils.addComment(workbook, sheet, headerRow.getRowNum(), (i + totImportantColum), "",
								(i + 1) + " Giornata");
					}
					celli.setCellStyle(headerCellStyle);
				}

				// Inserimento valori per le colonne
				int rowNum = 1;
				for (Giocatore giocatore : squadra.getRosa()) {
					Row row = sheet.createRow(rowNum);

					// valore del giocatore
					row.createCell(indexGiocatore).setCellType(CellType.STRING);
					row.createCell(indexGiocatore).setCellValue(giocatore.getNome());

					// valore del ruolo
					String ruoli = "";
					for (String r : giocatore.getRuoli()) {
						ruoli = ruoli + r + " ";
					}
					row.createCell(indexRuolo).setCellType(CellType.STRING);
					row.createCell(indexRuolo).setCellValue(ruoli);

					// valore della squadra
					row.createCell(indexSquadra).setCellType(CellType.STRING);
					if (giocatore.getSquadra() != null && !giocatore.getSquadra().isEmpty()) {
						row.createCell(indexSquadra).setCellValue(giocatore.getSquadra().substring(0, 3));
					}

					// valore della quotazione attuale
					if (giocatore.getQuotazioneAttuale() != null
							&& giocatore.getQuotazioneAttuale().compareTo(Integer.valueOf(0)) > 0) {
						row.createCell(indexQuotazioneAtt).setCellType(CellType.NUMERIC);
						row.getCell(indexQuotazioneAtt).setCellValue(giocatore.getQuotazioneAttuale().intValue());
					} else {
						row.createCell(indexQuotazioneAtt).setCellType(CellType.STRING);
						row.getCell(indexQuotazioneAtt).setCellValue("");
					}

					// valore della quotazione diff
					if (giocatore.getQuotazioneDiff() != null) {
						row.createCell(indexQuotazioneDiff).setCellType(CellType.NUMERIC);
						row.getCell(indexQuotazioneDiff).setCellValue(giocatore.getQuotazioneDiff().intValue());
					} else {
						row.createCell(indexQuotazioneDiff).setCellType(CellType.STRING);
						row.getCell(indexQuotazioneDiff).setCellValue("");
					}

					// valore della media voto
					CellStyle cellStyle = workbook.createCellStyle();
					cellStyle.setDataFormat(workbook.createDataFormat().getFormat("0.00"));
					row.createCell(indexMediaVoto).setCellType(CellType.NUMERIC);
					row.createCell(indexMediaVoto).setCellStyle(cellStyle);
					if (giocatore.getMediaVoto() != null) {
						row.getCell(indexMediaVoto).setCellValue(giocatore.getMediaVoto());
					} else {
						row.getCell(indexMediaVoto).setCellValue(0);
					}

					// valore prossima squadra avversara e se in casa o transferta
					if ((squadra.getProssimaGiornataCampionato()) <= giocatore.getCalendarioAvversarie().size()) {
						giocatore.setProssimaSquadraAvversaria(
								giocatore.getCalendarioAvversarie().get(squadra.getProssimaGiornataCampionato() - 1));
						row.createCell(indexSquadraAvv).setCellType(CellType.STRING);
						row.getCell(indexSquadraAvv)
								.setCellValue(giocatore.getProssimaSquadraAvversaria().substring(0, 3));
						row.createCell(indexCasaTrasf).setCellType(CellType.STRING);
						row.getCell(indexCasaTrasf).setCellValue(
								giocatore.getCasaTrasferta().get(squadra.getProssimaGiornataCampionato() - 1));
					}

					// valore probabilità di giocare del giocatore
					row.createCell(indexProb).setCellType(CellType.STRING);
					if (giocatore.getProbabilitaDiGiocare().size() >= squadra.getProssimaGiornataCampionato()) {
						row.createCell(indexProb).setCellValue(
								giocatore.getProbabilitaDiGiocare().get(squadra.getProssimaGiornataCampionato() - 1));
					} else {
						row.createCell(indexProb).setCellValue("");
					}

					for (int i = 0; i < squadra.getGiornateCampionato(); i++) {
						String giornata = (i + 1) + "" + (char) 170 + " Giornata\n";
						if (i < giocatore.getCalendarioAvversarie().size()
								&& !giocatore.getCalendarioAvversarie().get(i).isEmpty()) {
							giornata = giornata + giocatore.getCalendarioAvversarie().get(i) + "\n";
						}
						if (i < giocatore.getCasaTrasferta().size() && !giocatore.getCasaTrasferta().get(i).isEmpty()) {
							giornata = giornata + (giocatore.getCasaTrasferta().get(i).equalsIgnoreCase("Casa") ? "Casa"
									: "Trasferta");
						}

						row.createCell(totImportantColum + i).setCellType(CellType.NUMERIC);
						if (i < giocatore.getVoti().size() && giocatore.getVoti().get(i).getValutazione() != null) {

							// valore voti singola giornata
							CellStyle cellStyleVoti = workbook.createCellStyle();
//							if (giocatore.getSostituzioni().get(i).equalsIgnoreCase("OUT")) {
//								cellStyleVoti.setBorderRight(BorderStyle.THICK);
//								cellStyleVoti.setRightBorderColor(IndexedColors.RED.getIndex());
//							}
							if (giocatore.getSostituzioni().get(i).equalsIgnoreCase("IN")) {
								cellStyleVoti.setBorderRight(BorderStyle.THICK);
								cellStyleVoti.setRightBorderColor(IndexedColors.GREEN.getIndex());
							}
							if (giocatore.getVoti().get(i).getAmmunizioni().intValue() > 0) {
								cellStyleVoti.setFillForegroundColor(IndexedColors.LIGHT_YELLOW.getIndex());
								cellStyleVoti.setFillPattern(FillPatternType.SOLID_FOREGROUND);
							}
							if (giocatore.getVoti().get(i).getEspulsioni().intValue() > 0) {
								cellStyleVoti.setFillForegroundColor(IndexedColors.CORAL.getIndex());
								cellStyleVoti.setFillPattern(FillPatternType.SOLID_FOREGROUND);
							}
							if (giocatore.getVoti().get(i).getGolFatti().intValue() > 0) {
								cellStyleVoti.setFillForegroundColor(IndexedColors.SKY_BLUE.getIndex());
								cellStyleVoti.setFillPattern(FillPatternType.SOLID_FOREGROUND);
							}
							row.getCell(totImportantColum + i)
									.setCellValue(giocatore.getVoti().get(i).getValutazione().doubleValue());
							row.getCell(totImportantColum + i).setCellStyle(cellStyleVoti);
						} else {
							row.getCell(totImportantColum + i).setCellValue(0.0);
							if (i < giocatore.getProbabilitaDiGiocare().size()
									&& giocatore.getProbabilitaDiGiocare().get(i) != null
									&& giocatore.getProbabilitaDiGiocare().get(i).equalsIgnoreCase("0%")) {
								CellStyle cellStyleVoti = workbook.createCellStyle();
								cellStyleVoti.setFillForegroundColor(IndexedColors.LAVENDER.getIndex());
								cellStyleVoti.setFillPattern(FillPatternType.SOLID_FOREGROUND);
								row.getCell(totImportantColum + i).setCellStyle(cellStyleVoti);
							} else {
								CellStyle cellStyleVoti = workbook.createCellStyle();
								cellStyleVoti.setFillForegroundColor(IndexedColors.GREY_25_PERCENT.getIndex());
								cellStyleVoti.setFillPattern(FillPatternType.SOLID_FOREGROUND);
								row.getCell(totImportantColum + i).setCellStyle(cellStyleVoti);
							}
						}
						if (comment) {
							Utils.addComment(workbook, sheet, rowNum, totImportantColum + i, "", giornata.trim());
						}
					}

					rowNum = rowNum + 1;
				}

				// Resize all columns to fit the content size
				for (int i = 0; i < headerRow.getLastCellNum(); i++) {
					sheet.autoSizeColumn(i);
				}
			}

			// Creazione Grafici solo del primo Sheet
			if (grafico) {
				int coord = 1;
				for (Giocatore giocatore : squadre.get(0).getRosa()) {
					Drawing drawing = sheetGrafici.createDrawingPatriarch();
					ClientAnchor anchor = drawing.createAnchor(0, 0, 0, 0, 1, coord, 18, coord + 12);

					Chart chart = drawing.createChart(anchor);
					ChartLegend legend = chart.getOrCreateLegend();
					legend.setPosition(LegendPosition.RIGHT);

					LineChartData data = chart.getChartDataFactory().createLineChartData();

					ChartAxis bottomAxis = chart.getChartAxisFactory().createCategoryAxis(AxisPosition.BOTTOM);
					ValueAxis leftAxis = chart.getChartAxisFactory().createValueAxis(AxisPosition.LEFT);
					leftAxis.setCrosses(AxisCrosses.AUTO_ZERO);

					ChartDataSource<Number> asseX = DataSources.fromNumericCellRange(workbook.getSheetAt(1),
							new CellRangeAddress(0, 0, totImportantColum, 44));

					ChartDataSource<Number> asseY = DataSources.fromNumericCellRange(workbook.getSheetAt(1),
							new CellRangeAddress(Utils.findRow(workbook.getSheetAt(1), giocatore.getNome()),
									Utils.findRow(workbook.getSheetAt(1), giocatore.getNome()), totImportantColum, 44));

					LineChartSeries series1 = data.addSeries(asseX, asseY);
					series1.setTitle(giocatore.getNome() + " [" + workbook.getSheetAt(1).getSheetName() + "]");

					chart.plot(data, bottomAxis, leftAxis);
					coord = coord + 12;
				}
			}
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
