package Commons;

import java.io.File;
import java.io.FileInputStream;
import java.io.FilenameFilter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;

import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import Dao.Giocatore;
import Dao.Squadra;
import Views.Master;

public class Utils {

	/**
	 * Se non contiene il file jar vuol dire che sono su Eclipse, quindi metto un
	 * path fisso.
	 * <p>
	 * Altrimenti sto eseguendo il jar e quindi dal path rimuovo il file.
	 *
	 * @return path
	 */
	public static String calcolaPath() {
		String path = null;
		try {
			path = Master.class.getProtectionDomain().getCodeSource().getLocation().toURI().getPath();
			if (!path.contains(".jar")) {
				path = "/home/alessandro.cappelli/Documents/Utility/FC/18-19/";
			} else {
				path = path.substring(0, path.lastIndexOf('/')+1) + "18-19/";
			}
			System.out.println("Path di riferimento bis: " + path);
		} catch (Exception e) {
			System.out.println("Errore path");
		}
		return path;
	}

	/**
	 * Connessione al File.
	 *
	 * @param path
	 * @param fileName
	 *
	 * @return
	 *
	 * @throws Exception
	 */
	public static String connectionFile(String path, String fileName) throws Exception {

		File dir = new File(path);
		File[] foundFiles = dir.listFiles(new FilenameFilter() {
			public boolean accept(File dir, String name) {
				return name.startsWith(fileName);
			}
		});

		if (foundFiles != null && foundFiles.length > 0) {
			return foundFiles[0].getPath();
		} else {
			throw new Exception("File " + fileName + " non caricato");
		}
	}

	/**
	 * Connessione ai File.
	 *
	 * @param path
	 * @param fileName
	 *
	 * @return array
	 *
	 * @throws Exception
	 */
	public static ArrayList<String> connectionFiles(String path, String fileName) throws Exception {
		ArrayList<String> filesPath = new ArrayList<>();

		File dir = new File(path);
		File[] foundFiles = dir.listFiles(new FilenameFilter() {
			public boolean accept(File dir, String name) {
				return name.startsWith(fileName);
			}
		});

		foundFiles = sortByNumber(foundFiles);
		for (File f : foundFiles) {
			filesPath.add(f.getPath());
		}

		return filesPath;
	}

	/**
	 * Verifico se il file del Fantacalcio e' presente e in tal caso lo carico.
	 *
	 * @return Workbook
	 *
	 * @throws Exception
	 */
	public static Workbook connectionWorkbook(String path) throws Exception {
		// cerco il file del fantacalcio
		File dir = new File(path);
		File[] foundFiles = dir.listFiles(new FilenameFilter() {
			public boolean accept(File dir, String name) {
				return name.startsWith(Costanti.FILE_SQUADRE);
			}
		});

		// carico il file se presente
		if (foundFiles != null && foundFiles.length > 0 && foundFiles[0].exists()) {
			System.out.println("Caricamento file " + foundFiles[0].getName());
			FileInputStream inputStream = new FileInputStream(foundFiles[0].getPath());
			Workbook wb = new XSSFWorkbook(inputStream);
			return wb;
		} else {
			System.out.println("File non trovato");
			throw new Exception("File not found");
		}
	}

	public static void CLS() throws IOException, InterruptedException {
		final String os = System.getProperty("os.name");
		if (os.contains("Windows"))
			new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
		else
			System.out.println();
	}

	public static String printRosa(Squadra squadra) {
		try {
			String res = "";
			res = "ROSA: " + squadra.getNome() + System.lineSeparator();
			res = res + StringUtils.rightPad("GIOCATORE", 16, " ") + "| " + StringUtils.rightPad("RUOLI", 9, " ")
					+ "| " + StringUtils.rightPad("SQ.", 4, " ") + "| " + StringUtils.rightPad("P.", 3, " ")
					+ "| " + StringUtils.rightPad("QI.", 4, " ") + "| " + StringUtils.rightPad("QA.", 4, " ") + "| "
					+ StringUtils.rightPad("VOTI", 5, " ") + System.lineSeparator();

			res = res + StringUtils.rightPad("-", 201, "-") + System.lineSeparator();
			for (Giocatore g : squadra.getRosa()) {
				res = res + StringUtils.rightPad(g.getNome(), 16, " ");
				String ruoli = "";
				for (String r : g.getRuoli())
					ruoli = ruoli + r + " ";
				res = res + "| " + StringUtils.rightPad(ruoli, 9, " ");

				if (g.getSquadra() != null)
					res = res + "| " + StringUtils.rightPad(g.getSquadra().substring(0, 3), 4, " ");
				else
					res = res + "| " + StringUtils.rightPad("", 4, " ");
				if (g.getPartiteGiocate() != null)
					res = res + "| " + StringUtils.rightPad(String.valueOf(g.getPartiteGiocate()), 3, " ");
				else
					res = res + "| " + StringUtils.rightPad("", 3, " ");
				if (g.getQuotazioneIniziale() != null)
					res = res + "| " + StringUtils.rightPad(String.valueOf(g.getQuotazioneIniziale()), 4, " ");
				else
					res = res + "| " + StringUtils.rightPad("", 4, " ");
				if (g.getQuotazioneAttuale() != null)
					res = res + "| " + StringUtils.rightPad(String.valueOf(g.getQuotazioneAttuale()), 4, " ");
				else
					res = res + "| " + StringUtils.rightPad("", 4, " ");
				if (!g.getCalendarioAvversarie().isEmpty()) {
					String avversarie = "";
					int index = 0;
					for (String avversaria : g.getCalendarioAvversarie()) {
						avversarie = avversarie + StringUtils.rightPad((index<9 ? "0" + (index+1) : (index+1))  + (g.getCasaTrasferta().get(index) == null ? "" : g.getCasaTrasferta().get(index)), 4, " ");
						avversarie = avversarie + StringUtils.rightPad((avversaria == null ? "" : avversaria.substring(0, 3)) + " ", 4, " ") ;

						if (index < g.getVoti().size()) {
							avversarie = avversarie + StringUtils.rightPad((g.getVoti().get(index).getValutazione() == null ? " " : g.getVoti().get(index).getValutazione().doubleValue()) + " ", 5, " ") + "| ";
						} else {
							avversarie = avversarie + StringUtils.rightPad("", 5, " ") + "| ";
						}

						index++;
						if (index == 10 || index == 20 || index == 30 ) {
							avversarie = avversarie + System.lineSeparator() + StringUtils.rightPad("", 50, " ") + "| ";
						}
					}
					res = res + "| " + StringUtils.rightPad(avversarie, 500, " ") + System.lineSeparator() + System.lineSeparator();
				} else {
					res = res + "| " + StringUtils.rightPad("", 500, " ") + System.lineSeparator() + System.lineSeparator();
				}
			}
			return res;
		} catch (Exception e) {
			System.out.println("Errore stampa rosa: " + e.getMessage());
			return null;
		}
	}

	/**
	 * Ordinamento file dei voti.
	 *
	 * @param files
	 * @return
	 * @throws Exception
	 */
	public static File[] sortByNumber(File[] files) throws Exception {
		if (files != null && files.length > 0) {
			Arrays.sort(files, new Comparator<File>() {
				@Override
				public int compare(File o1, File o2) {
					int n1 = extractNumber(o1.getName());
					int n2 = extractNumber(o2.getName());
					return n1 - n2;
				}

				private int extractNumber(String name) {
					int i = 0;
					try {
						int s = name.lastIndexOf('_') + 1;
						int e = name.lastIndexOf('.');
						String number = name.substring(s, e);
						i = Integer.parseInt(number);
					} catch (Exception e) {
						i = 0; // if filename does not match the format
								// then default to 0
					}
					return i;
				}
			});
			return files;
		} else {
			throw new Exception("File non trovati");
		}

	}

}