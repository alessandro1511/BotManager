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
import Dao.GiocatoreDTO;
import Dao.SquadraDTO;
import Dao.Voti;

public class Statistiche {

	/**
	 * Cerco il file delle Quotazioni e carico tutte le quotazioni per ogni
	 * giocatore.
	 *
	 * @param squadre
	 *            - array delle squadre
	 *
	 * @return ArrayList - array delle squadre aggiornato
	 *
	 * @throws Exception
	 */
	public static ArrayList<SquadraDTO> creaQuotazioni(ArrayList<SquadraDTO> squadre, String path) throws Exception {
		System.out.println("Caricamento file quotazioni");
		String pathFile = Utils.connectionFile(path, Costanti.FILE_QUOTAZIONI);
		FileInputStream inputStream = new FileInputStream(pathFile);

		System.out.println("Caricamento delle quotazioni: " + pathFile);
		Workbook workbook = new XSSFWorkbook(inputStream);
		Sheet firstSheet = workbook.getSheetAt(0);

		for (SquadraDTO squadra : squadre) {
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
						for (GiocatoreDTO g : squadra.getRosa()) {
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
	 * Cerco il file delle Statistiche e per ogni giocatore verifico i suoi
	 * dati.
	 *
	 * @param squadre
	 *            - array delle squadre
	 * @param path
	 *            file
	 *
	 * @return array delle squadre aggiornato
	 *
	 * @throws Exception
	 */
	public static ArrayList<SquadraDTO> creaStatistiche(ArrayList<SquadraDTO> squadre, String path) throws Exception {
		System.out.println("Caricamento file delle statistiche");
		String pathFile = Utils.connectionFile(path, Costanti.FILE_STATISTICHE);
		FileInputStream inputStream = new FileInputStream(pathFile);

		System.out.println("Caricamento statistiche: " + pathFile);
		Workbook workbook = new XSSFWorkbook(inputStream);
		Sheet sheet = workbook.getSheetAt(0);

		try {
			for (SquadraDTO squadra : squadre) {
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
							for (GiocatoreDTO g : squadra.getRosa()) {
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
	 * Cerco tutti i file dei voti e calcolo giornata per giornata le
	 * statistiche del giocatore.
	 *
	 * @param squadre
	 *            - array delle squadre
	 * @param path
	 *            file
	 *
	 * @return array delle squadre aggiornato
	 *
	 * @throws Exception
	 */
	public static ArrayList<SquadraDTO> votiGiornate(ArrayList<SquadraDTO> squadre, String path) throws Exception {

		System.out.println("Caricamento file dei voti");
		ArrayList<String> pathFiles = Utils.connectionFiles(path, Costanti.FILE_VOTI);

		int indexFileVoti = 0;
		for (String pathFile : pathFiles) {

			FileInputStream inputStream = new FileInputStream(pathFile);

			System.out.println("Caricamento voti: " + pathFile);
			Workbook workbook = new XSSFWorkbook(inputStream);
			Sheet sheet = workbook.getSheetAt(0);

			try {
				for (SquadraDTO squadra : squadre) {
					Iterator<Row> iterator = sheet.iterator();
					while (iterator.hasNext()) {
						Row nextRow = iterator.next();
						Iterator<Cell> cellIterator = nextRow.cellIterator();

						int indexCol = 0;
						int indexGiocatore = -1;
						boolean findGiocatore = false;
						Voti voti = new Voti();
						while (cellIterator.hasNext()) {
							Cell cell = cellIterator.next();
							cell.setCellType(CellType.STRING);
							indexCol++;
							if (indexCol == 3) {
								// nome giocatore
								for (GiocatoreDTO g : squadra.getRosa()) {
									if (g.getNome().equals(cell.getStringCellValue().toUpperCase().trim())) {
										indexGiocatore = squadra.getRosa().indexOf(g);
										findGiocatore = true;
										break;
									}
								}
							} else if (indexCol == 4 && findGiocatore) {
								// voto giornata
								if ((String.valueOf(cell.getStringCellValue())).contains("*"))
									voti.setVoto(Double.valueOf(6));
								else
									voti.setVoto(Double.valueOf(cell.getStringCellValue()));
							} else if (indexCol == 5 && findGiocatore) {
								// gol fatti
								voti.setGolFatti(Integer.valueOf(cell.getStringCellValue()));
							} else if (indexCol == 6 && findGiocatore) {
								// gol subiti
								voti.setGolSubiti(Integer.valueOf(cell.getStringCellValue()).intValue());
							} else if (indexCol == 7 && findGiocatore) {
								// rigore parato
								voti.setRigoreParato(Integer.valueOf(cell.getStringCellValue()).intValue());
							} else if (indexCol == 8 && findGiocatore) {
								// rigore sbagliato
								voti.setRigoreSbagalto(Integer.valueOf(cell.getStringCellValue()).intValue());
							} else if (indexCol == 9 && findGiocatore) {
								// rigore fatto
								voti.setRigoreFatto(Integer.valueOf(cell.getStringCellValue()).intValue());
							} else if (indexCol == 10 && findGiocatore) {
								// autogol
								voti.setAutogol(Integer.valueOf(cell.getStringCellValue()).intValue());
							} else if (indexCol == 11 && findGiocatore) {
								// ammunizioni
								voti.setAmmunizioni(Integer.valueOf(cell.getStringCellValue()).intValue());
							} else if (indexCol == 12 && findGiocatore) {
								// espulsioni
								voti.setEspulsioni(Integer.valueOf(cell.getStringCellValue()).intValue());
							} else if (indexCol == 13 && findGiocatore) {
								// assist
								voti.setAssist(Integer.valueOf(cell.getStringCellValue()).intValue());
							} else if (indexCol == 14 && findGiocatore) {
								// assist da fermo
								voti.setAssistDaFermo(Integer.valueOf(cell.getStringCellValue()).intValue());
							} else if (indexCol == 15 && findGiocatore) {
								// gol della vittoria
								voti.setGolDellaVittoria(Integer.valueOf(cell.getStringCellValue()).intValue());
							} else if (indexCol == 16 && findGiocatore) {
								// gol del pareggio
								voti.setGolDelPareggio(Integer.valueOf(cell.getStringCellValue()).intValue());
							}
						}

						// calcolo valutazione giocatore
						if (indexGiocatore != -1 && voti.getVoto() != null
								&& squadra.getRosa().get(indexGiocatore).getQuotazioneAttuale() != null) {

							Double valutazione = voti.getVoto() + voti.getGolFatti() * 3 - voti.getGolSubiti()
									+ voti.getRigoreParato() * 3 - voti.getRigoreSbagalto() * 3
									+ voti.getRigoreFatto() * 3 - voti.getAutogol() * 2
									- (double) voti.getAmmunizioni() / 2 - voti.getEspulsioni() + voti.getAssist()
									+ voti.getAssistDaFermo() + voti.getGolDellaVittoria()
									+ (double) voti.getGolDelPareggio() / 2;

							voti.setValutazione(valutazione);
							squadra.getRosa().get(indexGiocatore).addVoti(voti);
						}
					}
				}
			} catch (Exception e) {
				System.out.println("Errore: " + e);
			}

			workbook.close();
			inputStream.close();
			indexFileVoti++;
		}

		System.out.println("Voti per ogni giocatore caricate");
		return squadre;
	}
}
