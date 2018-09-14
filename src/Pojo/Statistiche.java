package Pojo;

import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.Iterator;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import Commons.Costanti;
import Commons.Utils;
import Dao.Giocatore;
import Dao.Squadra;
import Dao.Voti;

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
	public static ArrayList<Squadra> calcolaQuotazioni(ArrayList<Squadra> squadre, String path) throws Exception {
		System.out.println("Caricamento file quotazioni");
		String pathFile = Utils.connectionFile(path, Costanti.FILE_QUOTAZIONI);
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
					if (indexCol == 3) // nome giocatore
					{
						for (Giocatore g : squadra.getRosa()) {
							if (g.getNome().equals(cell.getStringCellValue().toUpperCase().trim())) {
								indexGiocatore = squadra.getRosa().indexOf(g);
								findGiocatore = true;
								break;
							}
						}
					} else if (indexCol == 5 && findGiocatore) // quotazione
																// attuale
					{
						squadra.getRosa().get(indexGiocatore)
								.setQuotazioneAttuale(Double.valueOf(cell.getNumericCellValue()).intValue());
					} else if (indexCol == 6 && findGiocatore) // quotazione
																// iniziale
					{
						squadra.getRosa().get(indexGiocatore)
								.setQuotazioneIniziale(Double.valueOf(cell.getNumericCellValue()).intValue());
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
	 * Cerco il file delle Statistiche e per ogni giocatore verifico i suoi dati.
	 *
	 * @param squadre - array delle squadre
	 * @param path    file
	 *
	 * @return array delle squadre aggiornato
	 *
	 * @throws Exception
	 */
	public static ArrayList<Squadra> calcolaStatistiche(ArrayList<Squadra> squadre, String path) throws Exception {
		System.out.println("Caricamento file delle statistiche");
		String pathFile = Utils.connectionFile(path, Costanti.FILE_STATISTICHE);
		FileInputStream inputStream = new FileInputStream(pathFile);

		System.out.println("Caricamento statistiche: " + pathFile);
		Workbook workbook = new XSSFWorkbook(inputStream);
		Sheet sheet = workbook.getSheetAt(0);

		try {
			for (Squadra squadra : squadre) {
				Iterator<Row> iterator = sheet.iterator();
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
							// partite giocate
							squadra.getRosa().get(indexGiocatore)
									.setPartiteGiocate(Double.valueOf(cell.getNumericCellValue()).intValue());
						}
					}
				}
			}
		} catch (Exception e) {
			System.out.println("Errore: " + e);
		}
		workbook.close();
		inputStream.close();

		System.out.println("Statistiche per ogni giocatore caricate");
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
	public static ArrayList<Squadra> calcolaCalendario(ArrayList<Squadra> squadre, String path) throws Exception {
		System.out.println("Caricamento file delle statistiche");
		String pathFile = Utils.connectionFile(path, Costanti.FILE_CALENDARIO);
		FileInputStream inputStream = new FileInputStream(pathFile);

		System.out.println("Caricamento statistiche: " + pathFile);
		Workbook workbook = new XSSFWorkbook(inputStream);
		Sheet sheet = workbook.getSheetAt(0);

		for (Squadra squadra : squadre) {
			for (Giocatore g : squadra.getRosa()) {
				Iterator<Row> iterator = sheet.iterator();
				ArrayList<String> giornateDispari = new ArrayList<>();
				ArrayList<String> giornatePari = new ArrayList<>();
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
									g.addCasaTrasferta("t");
								else {
									g.addCasaTrasferta("c");
								}
								avversaria = avversaria.replaceAll("-", "");
								giornateDispari.add(avversaria);
							}

							if (cell.getColumnIndex() == 3 && g.getSquadra() != null && !g.getSquadra().isEmpty()
									&& cell.getStringCellValue().toUpperCase().trim().contains(g.getSquadra())) {
								String avversaria = cell.getStringCellValue().toUpperCase().trim()
										.replaceAll(g.getSquadra(), "");
								if (avversaria.endsWith("-"))
									g.addCasaTrasferta("t");
								else {
									g.addCasaTrasferta("c");
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

				int index = 0;
				while (index < giornateDispari.size() && index < giornatePari.size() ) {

					if (index < giornateDispari.size()) {
						g.addCalendarioAvversaria(giornateDispari.get(index));
					}
					if (index < giornatePari.size()) {
						g.addCalendarioAvversaria(giornatePari.get(index));
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
	public static ArrayList<Squadra> calcolaVotiGiornate(ArrayList<Squadra> squadre, String path) throws Exception {

		System.out.println("Caricamento file dei voti");
		ArrayList<String> pathFiles = Utils.connectionFiles(path, Costanti.FILE_VOTI);

		int numeroGiornata = 0;
		for (String pathFile : pathFiles) {
			numeroGiornata++;
			FileInputStream inputStream = new FileInputStream(pathFile);

			System.out.println("Caricamento voti: " + pathFile);
			Workbook workbook = new XSSFWorkbook(inputStream);
			Sheet sheet = workbook.getSheetAt(0);

			for (Squadra squadra : squadre) {
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
											+ voto.getAssist() + voto.getAssistDaFermo() + voto.getGolDellaVittoria()
											+ (double) voto.getGolDelPareggio() / 2;
									voto.setValutazione(valutazione);
								}
							} catch (Exception e) {
								System.out.println("Errore voti giocatore: " + g.getNome());
							}
						}
					}
					g.addVoti(voto);
				}
			}
			workbook.close();
			inputStream.close();
		}

		System.out.println("Voti per ogni giocatore caricate");
		return squadre;
	}

}
