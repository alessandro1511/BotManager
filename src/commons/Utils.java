package commons;

import java.io.File;
import java.io.FileInputStream;
import java.io.FilenameFilter;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;

import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.ClientAnchor;
import org.apache.poi.ss.usermodel.Comment;
import org.apache.poi.ss.usermodel.CreationHelper;
import org.apache.poi.ss.usermodel.Drawing;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import dao.Fantacalcio;
import dao.Giocatore;
import dao.Squadra;
import views.Master;

public class Utils {

	public static ArrayList<Fantacalcio> caricaListaFileFantacalcio() throws Exception {
		String path = jarPath();

		File file = new File(path);
		String[] directories = file.list(new FilenameFilter() {
			@Override
			public boolean accept(File current, String name) {
				return new File(current, name).isDirectory();
			}
		});

		ArrayList<Fantacalcio> fantacalcio = new ArrayList<>();
		for (String d : directories) {
			Fantacalcio f = new Fantacalcio();
			f.setPath(path + d + "/");
			System.out.println("Caricamento file " + f.getPath());

			// verifico esistenza file del fantacalcio
			File dir = new File(f.getPath());
			File[] foundFiles = dir.listFiles(new FilenameFilter() {
				public boolean accept(File dir, String name) {
					return name.startsWith(Costanti.FILE_SQUADRE);
				}
			});

			if (foundFiles != null && foundFiles.length > 0 && foundFiles[0].exists()) {
				FileInputStream inputStream = new FileInputStream(foundFiles[0].getPath());
				f.setWorkbook(new XSSFWorkbook(inputStream));
			} else {
				System.out.println("File non trovato");
				throw new Exception("File not found");
			}

			f.setAnno(f.getPath().substring(f.getPath().lastIndexOf("/") - 5, f.getPath().lastIndexOf("/")));

			fantacalcio.add(f);
		}

		Collections.sort(fantacalcio, new Comparator<Fantacalcio>() {

			public int compare(Fantacalcio f1, Fantacalcio f2) {
				return f2.getAnno().compareTo(f1.getAnno());
			}
		});

		return fantacalcio;
	}

	public static String jarPath() throws URISyntaxException {
		String path = Master.class.getProtectionDomain().getCodeSource().getLocation().toURI().getPath();
		if (!path.contains(".jar")) {
			path = "/home/alessandro.cappelli/Documents/Utility/BotManager/";
		} else {
			path = path.substring(0, path.lastIndexOf('/') + 1);
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

	public static void CLS() throws IOException, InterruptedException {
		final String os = System.getProperty("os.name");
		if (os.contains("Windows"))
			new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
		else
			System.out.println();
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

	/**
	 * Trova il valore in una cella di testo
	 *
	 * @param sheet
	 * @param cellContent
	 * @return
	 */
	public static int findRow(Sheet sheet, String cellContent) {
		for (Row row : sheet) {
			for (Cell cell : row) {
				if (cell.getCellTypeEnum() == CellType.STRING
						&& cell.getRichStringCellValue().getString().trim().equalsIgnoreCase(cellContent)) {
					return row.getRowNum();
				}
			}
		}
		return 0;
	}

	public static Cell getOrCreateCell(Sheet sheet, int rowIdx, int colIdx) {
		Row row = sheet.getRow(rowIdx);
		if (row == null) {
			row = sheet.createRow(rowIdx);
		}

		Cell cell = row.getCell(colIdx);
		if (cell == null) {
			cell = row.createCell(colIdx);
		}

		return cell;
	}

	public static void addComment(Workbook workbook, Sheet sheet, int rowIdx, int colIdx, String author, String commentText) {
		CreationHelper factory = workbook.getCreationHelper();
		// get an existing cell or create it otherwise:
		Cell cell = getOrCreateCell(sheet, rowIdx, colIdx);

		ClientAnchor anchor = factory.createClientAnchor();
		// i found it useful to show the comment box at the bottom right corner
		anchor.setCol1(cell.getColumnIndex() + 1); // the box of the comment starts at this given column...
		anchor.setCol2(cell.getColumnIndex() + 3); // ...and ends at that given column
		anchor.setRow1(rowIdx + 1); // one row below the cell...
		anchor.setRow2(rowIdx + 5); // ...and 4 rows high

		Drawing drawing = sheet.createDrawingPatriarch();
		Comment comment = drawing.createCellComment(anchor);
		// set the comment text and author
		comment.setString(factory.createRichTextString(commentText));
		comment.setAuthor(author);

		cell.setCellComment(comment);
	}

}