package Commons;

import java.io.File;
import java.io.FileInputStream;
import java.io.FilenameFilter;
import java.io.IOException;
import java.util.ArrayList;

import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import Dao.Giocatore;
import Dao.Squadra;
import Dao.Voti;
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
				path = "/home/alessandro.cappelli/Documents/Utility/FC/";
			} else {
				path = path.substring(0, path.lastIndexOf('/'));
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
		ArrayList<String> files = new ArrayList<>();

		File dir = new File(path);
		File[] foundFiles = dir.listFiles(new FilenameFilter() {
			public boolean accept(File dir, String name) {
				return name.startsWith(fileName);
			}
		});

		if (foundFiles != null && foundFiles.length > 0) {
			for (File f : foundFiles) {
				files.add(f.getPath());
			}
		} else {
			throw new Exception("File " + fileName + " non trovati");
		}

		// TODO ordinare per nome la lista

		return files;
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
			res = res + StringUtils.rightPad("NOME GIOCATORE", 20, " ") + "| " + StringUtils.rightPad("RUOLI", 10, " ")
					+ "| " + StringUtils.rightPad("SQUADRA", 15, " ") + "| " + StringUtils.rightPad("PRE.", 5, " ")
					+ "| " + StringUtils.rightPad("QuI.", 5, " ") + "| " + StringUtils.rightPad("QuA.", 5, " ") + "| "
					+ StringUtils.rightPad("Voti.", 5, " ") + System.lineSeparator();

			res = res + StringUtils.rightPad("-", 203, "-") + System.lineSeparator();
			for (Giocatore g : squadra.getRosa()) {
				res = res + StringUtils.rightPad(g.getNome(), 20, " ");
				String ruoli = "";
				for (String r : g.getRuoli())
					ruoli = ruoli + r + " ";
				res = res + "| " + StringUtils.rightPad(ruoli, 10, " ");

				if (g.getSquadra() != null)
					res = res + "| " + StringUtils.rightPad(g.getSquadra(), 15, " ");
				else
					res = res + "| " + StringUtils.rightPad("", 15, " ");
				if (g.getPartiteGiocate() != null)
					res = res + "| " + StringUtils.rightPad(String.valueOf(g.getPartiteGiocate()), 5, " ");
				else
					res = res + "| " + StringUtils.rightPad("", 5, " ");
				if (g.getQuotazioneIniziale() != null)
					res = res + "| " + StringUtils.rightPad(String.valueOf(g.getQuotazioneIniziale()), 5, " ");
				else
					res = res + "| " + StringUtils.rightPad("", 5, " ");
				if (g.getQuotazioneAttuale() != null)
					res = res + "| " + StringUtils.rightPad(String.valueOf(g.getQuotazioneAttuale()), 5, " ");
				else
					res = res + "| " + StringUtils.rightPad("", 5, " ");
				if (!g.getVoti().isEmpty()) {
					String votiTotali = "";
					for (Voti v : g.getVoti()) {
						votiTotali = StringUtils.rightPad(
								(v.getValutazione().doubleValue() == 0d ? "" : v.getValutazione().doubleValue()) + " ",
								5, " ") + votiTotali;
					}
					res = res + "| " + StringUtils.rightPad(votiTotali, 100, " ") + System.lineSeparator();
				} else {
					res = res + "| " + StringUtils.rightPad("", 5, " ") + System.lineSeparator();
				}
			}
			return res;
		} catch (Exception e) {
			System.out.println("Errore stampa rosa: " + e.getMessage());
			return null;
		}
	}

//	public static String printTutteFormazioni(SquadraDTO squadra) {
//		try {
//			String res = "";
//			res = "FORMAZIONE: " + squadra.getNome() + System.lineSeparator();
//			for (int i = 0; i < squadra.getFormazioni().size(); i++) {
//				res = res + StringUtils.rightPad(squadra.getFormazioni().get(i).getModulo(), 20, " ");
//			}
//			res = res + System.lineSeparator();
//
//			// righe da inserire
//			for (int g = 0; g < 36; g++) {
//				for (int i = 0; i < squadra.getFormazioni().size(); i++) {
//					if (squadra.getFormazioni().get(i).getFormazione().size() > g
//							&& squadra.getFormazioni().get(i).getFormazione().get(g) != null)
//						res = res + StringUtils
//								.rightPad(squadra.getFormazioni().get(i).getFormazione().get(g).getNome(), 20, " ");
//					else
//						res = res + StringUtils.rightPad(" ", 20, " ");
//				}
//				res = res + System.lineSeparator();
//				if (g == 10)
//					res = res + StringUtils.rightPad("-", 220, "-") + System.lineSeparator();
//			}
//
//			for (int i = 0; i < squadra.getFormazioni().size(); i++) {
//				res = res + StringUtils.rightPad(
//						"Val. " + String
//								.valueOf(String.format("%.2f", squadra.getFormazioni().get(i).getValutazione())),
//						20, " ");
//			}
//
//			res = res + System.lineSeparator();
//			return res;
//		} catch (Exception e) {
//			System.out.println("Errore stampa tutte le formazioni: " + e.getMessage());
//			return null;
//		}
//	}

}