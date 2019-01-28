package pojo;

import java.io.FileInputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.stream.Stream;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.jsoup.Jsoup;

import commons.Costanti;
import commons.Utils;
import dao.Fantacalcio;
import dao.Giocatore;
import dao.Squadra;
import dao.Voti;

public class Statistiche {

	/**
	 * Cerco il file delle Quotazioni e carico tutte le quotazioni per ogni
	 * giocatore.
	 *
	 * @param squadre - array delle squadre
	 *
	 * @return ArrayList - array delle squadre aggiornato
	 *
	 * @throws Exception
	 */
	public static ArrayList<Squadra> calcolaQuotazioni(ArrayList<Squadra> squadre, Fantacalcio fantacalcio)
			throws Exception {
		System.out.println("Caricamento file quotazioni");
		String pathFile = Utils.connectionFile(fantacalcio.getPath(), Costanti.FILE_QUOTAZIONI);
		FileInputStream inputStream = new FileInputStream(pathFile);

		System.out.println("Caricamento delle quotazioni: " + pathFile);
		Workbook workbook = new XSSFWorkbook(inputStream);
		Sheet firstSheet = workbook.getSheetAt(0);

		for (Squadra squadra : squadre) {
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
					if (indexCol == 3) {
						// nome giocatore
						for (Giocatore g : squadra.getRosa()) {
							if (g.getNome().equals(cell.getStringCellValue().toUpperCase().trim())) {
								indexGiocatore = squadra.getRosa().indexOf(g);
								findGiocatore = true;
								break;
							}
						}
					} else if (indexCol == 4 && findGiocatore) {
						// squadra di provenienza
						squadra.getRosa().get(indexGiocatore)
								.setSquadra(cell.getStringCellValue().toUpperCase().trim());
					} else if (indexCol == 5 && findGiocatore) {
						// quotazione attuale
						squadra.getRosa().get(indexGiocatore).setQuotazioneAttuale(
								Integer.valueOf(Double.valueOf(cell.getNumericCellValue()).intValue()));
					} else if (indexCol == 6 && findGiocatore) {
						// quotazione iniziale
						squadra.getRosa().get(indexGiocatore).setQuotazioneIniziale(
								Integer.valueOf(Double.valueOf(cell.getNumericCellValue()).intValue()));
					} else if (indexCol == 7 && findGiocatore) {
						// quotazione diff
						squadra.getRosa().get(indexGiocatore).setQuotazioneDiff(
								Integer.valueOf(Double.valueOf(cell.getNumericCellValue()).intValue()));
					}
				}
			}
		}
		workbook.close();
		inputStream.close();

		System.out.println("Quotazioni giocatori caricate");
		return squadre;
	}

	/**
	 * Cerco il file del Calendario e per ogni giocatore cerco le giornate
	 *
	 * @param squadre - array delle squadre
	 * @param path    file
	 *
	 * @return array delle squadre aggiornato
	 *
	 * @throws Exception
	 */
	public static ArrayList<Squadra> calcolaCalendario(ArrayList<Squadra> squadre, Fantacalcio fantacalcio)
			throws Exception {
		System.out.println("Caricamento file delle statistiche");
		String pathFile = Utils.connectionFile(fantacalcio.getPath(), Costanti.FILE_CALENDARIO);
		FileInputStream inputStream = new FileInputStream(pathFile);

		System.out.println("Caricamento statistiche: " + pathFile);
		Workbook workbook = new XSSFWorkbook(inputStream);
		Sheet sheet = workbook.getSheetAt(0);

		for (Squadra squadra : squadre) {
			for (Giocatore g : squadra.getRosa()) {
				Iterator<Row> iterator = sheet.iterator();
				ArrayList<String> giornateDispari = new ArrayList<>();
				ArrayList<String> giornateCasaTrasfertaDispari = new ArrayList<>();
				ArrayList<String> giornatePari = new ArrayList<>();
				ArrayList<String> giornateCasaTrasfertaPari = new ArrayList<>();
				while (iterator.hasNext()) {
					Row nextRow = iterator.next();
					Iterator<Cell> cellIterator = nextRow.cellIterator();
					while (cellIterator.hasNext()) {
						Cell cell = cellIterator.next();
						cell.setCellType(CellType.STRING);
						try {
							if (cell.getColumnIndex() == 0 && g.getSquadra() != null && !g.getSquadra().isEmpty()
									&& cell.getStringCellValue().toUpperCase().trim().contains(g.getSquadra())) {
								String avversaria = cell.getStringCellValue().toUpperCase().trim()
										.replaceAll(g.getSquadra(), "");
								if (avversaria.endsWith("-"))
									giornateCasaTrasfertaDispari.add("Tras");
								else {
									giornateCasaTrasfertaDispari.add("Casa");
								}
								avversaria = avversaria.replaceAll("-", "");
								giornateDispari.add(avversaria);
							}

							if (cell.getColumnIndex() == 3 && g.getSquadra() != null && !g.getSquadra().isEmpty()
									&& cell.getStringCellValue().toUpperCase().trim().contains(g.getSquadra())) {
								String avversaria = cell.getStringCellValue().toUpperCase().trim()
										.replaceAll(g.getSquadra(), "");
								if (avversaria.endsWith("-"))
									giornateCasaTrasfertaPari.add("Tras");
								else {
									giornateCasaTrasfertaPari.add("Casa");
								}
								avversaria = avversaria.replaceAll("-", "");
								giornatePari.add(avversaria);
							}
						} catch (Exception e) {
							System.out.println("Errore calcola calendario: " + cell.getStringCellValue() + " squadra: "
									+ g.getSquadra());
						}
					}
				}

				if (squadra.getGiornateCampionato() == 0) {
					squadra.setGiornateCampionato(giornateDispari.size() + giornatePari.size());
				}

				int index = 0;
				while (index < giornateDispari.size() && index < giornatePari.size()) {

					if (index < giornateDispari.size()) {
						g.addCalendarioAvversaria(giornateDispari.get(index));
						g.addCasaTrasferta(giornateCasaTrasfertaDispari.get(index));
					}
					if (index < giornatePari.size()) {
						g.addCalendarioAvversaria(giornatePari.get(index));
						g.addCasaTrasferta(giornateCasaTrasfertaPari.get(index));
					}
					index++;
				}

			}
		}
		workbook.close();
		inputStream.close();

		System.out.println("Statistiche per ogni giocatore caricate");
		return squadre;
	}

	/**
	 * Cerco tutti i file dei voti e calcolo giornata per giornata le statistiche
	 * del giocatore.
	 *
	 * @param squadre - array delle squadre
	 * @param path    file
	 *
	 * @return array delle squadre aggiornato
	 *
	 * @throws Exception
	 */
	public static ArrayList<Squadra> calcolaVotiGiornate(ArrayList<Squadra> squadre, Fantacalcio fantacalcio)
			throws Exception {

		System.out.println("Caricamento file dei voti");
		ArrayList<String> pathFiles = Utils.connectionFiles(fantacalcio.getPath(), Costanti.FILE_VOTI);

		int numeroGiornata = 0;
		for (String pathFile : pathFiles) {
			numeroGiornata++;
			FileInputStream inputStream = new FileInputStream(pathFile);

			System.out.println("Caricamento voti: " + pathFile);
			Workbook workbook = new XSSFWorkbook(inputStream);
			Sheet sheet = workbook.getSheetAt(0);

			for (Squadra squadra : squadre) {
				squadra.setProssimaGiornataCampionato(numeroGiornata + 1);
				for (Giocatore g : squadra.getRosa()) {
					Voti voto = new Voti();
					Iterator<Row> iterator = sheet.iterator();
					while (iterator.hasNext()) {
						Row nextRow = iterator.next();
						Iterator<Cell> cellIterator = nextRow.cellIterator();
						int indexCol = 0;
						while (cellIterator.hasNext()) {
							Cell cell = cellIterator.next();
							cell.setCellType(CellType.STRING);
							indexCol++;
							try {
								// nella colonna 3 cerco il nome del giocatore
								if (indexCol == 3 && !g.getNome().isEmpty()
										&& g.getNome().equals(cell.getStringCellValue().toUpperCase().trim())) {

									cell = cellIterator.next(); // colum 4
									cell.setCellType(CellType.STRING);
									if ((String.valueOf(cell.getStringCellValue())).contains("*"))
										voto.setVoto(Double.valueOf(6));
									else
										voto.setVoto(Double.valueOf(cell.getStringCellValue()));

									cell = cellIterator.next(); // colum 5
									cell.setCellType(CellType.STRING);
									voto.setGolFatti(Integer.valueOf(cell.getStringCellValue()));

									cell = cellIterator.next(); // colum 6
									cell.setCellType(CellType.STRING);
									voto.setGolSubiti(Integer.valueOf(cell.getStringCellValue()));

									cell = cellIterator.next(); // colum 7
									cell.setCellType(CellType.STRING);
									voto.setRigoreParato(Integer.valueOf(cell.getStringCellValue()));

									cell = cellIterator.next(); // colum 8
									cell.setCellType(CellType.STRING);
									voto.setRigoreSbagalto(Integer.valueOf(cell.getStringCellValue()));

									cell = cellIterator.next(); // colum 9
									cell.setCellType(CellType.STRING);
									voto.setRigoreFatto(Integer.valueOf(cell.getStringCellValue()));

									cell = cellIterator.next(); // colum 10
									cell.setCellType(CellType.STRING);
									voto.setAutogol(Integer.valueOf(cell.getStringCellValue()));

									cell = cellIterator.next(); // colum 11
									cell.setCellType(CellType.STRING);
									voto.setAmmunizioni(Integer.valueOf(cell.getStringCellValue()));

									cell = cellIterator.next(); // colum 12
									cell.setCellType(CellType.STRING);
									voto.setEspulsioni(Integer.valueOf(cell.getStringCellValue()));

									cell = cellIterator.next(); // colum 13
									cell.setCellType(CellType.STRING);
									voto.setAssist(Integer.valueOf(cell.getStringCellValue()));

									cell = cellIterator.next(); // colum 14
									cell.setCellType(CellType.STRING);
									voto.setAssistDaFermo(Integer.valueOf(cell.getStringCellValue()));

									cell = cellIterator.next(); // colum 15
									cell.setCellType(CellType.STRING);
									voto.setGolDellaVittoria(Integer.valueOf(cell.getStringCellValue()));

									cell = cellIterator.next(); // colum 16
									cell.setCellType(CellType.STRING);
									voto.setGolDelPareggio(Integer.valueOf(cell.getStringCellValue()));

									// calcolo della valutazione
									Double valutazione = voto.getVoto() + voto.getGolFatti() * 3 - voto.getGolSubiti()
											+ voto.getRigoreParato() * 3 - voto.getRigoreSbagalto() * 3
											+ voto.getRigoreFatto() * 3 - voto.getAutogol() * 2
											- (double) voto.getAmmunizioni() / 2 - voto.getEspulsioni()
											+ voto.getAssist() + voto.getAssistDaFermo();

									voto.setValutazione(Math.round(valutazione * 10.0) / 10.0);
								}
							} catch (Exception e) {
								System.out.println("Errore voti giocatore: " + g.getNome());
							}
						}
					}
					g.addVoti(voto);

					// calcolo la media voti
					int countVoti = 0;
					if (!g.getVoti().isEmpty()) {
						Double mediaVoto = 0d;
						for (Voti v : g.getVoti()) {
							if (v.getValutazione() != null) {
								mediaVoto = mediaVoto + v.getValutazione();
								countVoti++;
							}
						}

						if (countVoti > 0) {
							g.setMediaVoto(mediaVoto / countVoti);
						}
					}
				}
			}
			workbook.close();
			inputStream.close();
		}

		System.out.println("Voti per ogni giocatore caricate");
		return squadre;
	}

	public static ArrayList<Squadra> calcolaProbabiliFormazioni(ArrayList<Squadra> squadre, Fantacalcio fantacalcio)
			throws Exception {
		System.out.println("Caricamento probabili formazioni");

		ArrayList<String> pathFiles = Utils.connectionFiles(fantacalcio.getPath(), Costanti.FILE_PROBABILI_FORMAZIONI);

		for(String pathFile : pathFiles) {
			System.out.println("Caricamento probabili formazioni: " + pathFile);

			StringBuilder testoSenzaTagSoloFormazioniTitolari = new StringBuilder();
			StringBuilder testoSenzaTagSoloFormazioniPanchina = new StringBuilder();

			try (Stream<String> stream = Files.lines(Paths.get(pathFile), StandardCharsets.UTF_8)) {
				StringBuilder testo = new StringBuilder();
				stream.forEach(s -> testo.append(s).append("\n"));
				if (testo.toString().isEmpty() || testo.toString().length() == 0) {
					for (Squadra squadra : squadre) {
						for (Giocatore g : squadra.getRosa()) {
							g.getProbabilitaDiGiocare().add("");
						}
					}
					System.out.println("Probabili formazioni non calcolate, file vuoto");
					continue;
				}
				String testoSenzaTag = Jsoup.parse(testo.toString()).text();

				while (testoSenzaTag.indexOf("TITOLARI") != -1) {
					testoSenzaTagSoloFormazioniTitolari.append(testoSenzaTag
							.substring(testoSenzaTag.indexOf("TITOLARI") + 9, testoSenzaTag.indexOf("PANCHINA")))
							.append("\n");

					testoSenzaTagSoloFormazioniPanchina
							.append(testoSenzaTag.substring(testoSenzaTag.indexOf("PANCHINA") + 9,
									testoSenzaTag.indexOf("ALTRI CALCIATORI SQUALIFICATI")))
							.append("\n");

					if (testoSenzaTag.indexOf("ALTRI CALCIATORI SQUALIFICATI") != -1) {
						testoSenzaTag = testoSenzaTag.substring(testoSenzaTag.indexOf("ALTRI CALCIATORI SQUALIFICATI") + 1);
					}
				}
			} catch (IOException e) {
				System.out.println("Errore calcolo probabili formazioni");
			}

			System.out.println("TITOLARI");
			System.out.print(testoSenzaTagSoloFormazioniTitolari.toString());
			System.out.println("PANCHINA");
			System.out.print(testoSenzaTagSoloFormazioniPanchina.toString());
			System.out.println("Probabili formazioni caricate");

			for (Squadra squadra : squadre) {
				for (Giocatore g : squadra.getRosa()) {

					if (testoSenzaTagSoloFormazioniTitolari.indexOf(g.getNome()) != -1) {
						//Titolare
						g.getProbabilitaDiGiocare().add(testoSenzaTagSoloFormazioniTitolari
								.substring(testoSenzaTagSoloFormazioniTitolari.indexOf(g.getNome()) + g.getNome().length(),
										testoSenzaTagSoloFormazioniTitolari.indexOf(g.getNome()) + g.getNome().length() + 4)
								.trim());
					} else if (testoSenzaTagSoloFormazioniPanchina.indexOf(g.getNome()) != -1) {
						//Panchina
						g.getProbabilitaDiGiocare().add(
								testoSenzaTagSoloFormazioniPanchina
										.substring(testoSenzaTagSoloFormazioniPanchina.indexOf(g.getNome()) - 4,
												testoSenzaTagSoloFormazioniPanchina.indexOf(g.getNome()))
										.trim());
					} else {
						//Non Convocato
						g.getProbabilitaDiGiocare().add("0%");
					}
				}
			}
		}
		return squadre;
	}

}