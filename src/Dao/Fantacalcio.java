package Dao;

import org.apache.poi.ss.usermodel.Workbook;

public class Fantacalcio {

	private String path;
	private Workbook workbook;
	private String anno;

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public Workbook getWorkbook() {
		return workbook;
	}

	public void setWorkbook(Workbook workbook) {
		this.workbook = workbook;
	}

	public String getAnno() {
		return anno;
	}

	public void setAnno(String anno) {
		this.anno = anno;
	}

}
