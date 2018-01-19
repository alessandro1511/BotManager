package Commons;

import java.io.File;
import java.io.FileInputStream;
import java.io.FilenameFilter;
import java.io.IOException;

import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import Dao.GiocatoreDTO;
import Dao.SquadraDTO;

public class Utils {

	public static String connectionFile(String path, String fileName) throws Exception
	{
		//cerco il file
		File dir = new File(path);
		File[] foundFiles = dir.listFiles(new FilenameFilter() {
		    public boolean accept(File dir, String name) {
		        return name.startsWith(fileName);
		    }
		});

		//carico il file
        if (foundFiles != null && foundFiles.length > 0 && foundFiles[0].exists())
        {
        	return foundFiles[0].getPath();
        }
        else
        {
        	//carico il file debug
        	foundFiles = dir.listFiles(new FilenameFilter() {
    		    public boolean accept(File dir, String name) {
    		        return name.startsWith(fileName);
    		    }
    		});
        	if (foundFiles != null && foundFiles.length > 0 && foundFiles[0].exists())
            {
            	return foundFiles[0].getPath();
            }
        	else
        	{
        		throw new Exception("File " + fileName + " non caricato");
        	}
        }
	}

	/**
	 * Verifico se il file del Fantacalcio e' presente e in tal caso lo carico.
	 *
	 * @return Workbook
	 *
	 * @throws Exception
	 */
	public static Workbook connectionWorkbook() throws Exception
	{
		//cerco il file del fantacalcio
		File dir = new File(Costanti.PATH);
		File[] foundFiles = dir.listFiles(new FilenameFilter() {
		    public boolean accept(File dir, String name) {
		        return name.startsWith(Costanti.FILE_SQUADRE);
		    }
		});

		//carico il file se presente
        if (foundFiles != null && foundFiles.length > 0 && foundFiles[0].exists())
        {
        	System.out.println("Caricamento file " + foundFiles[0].getName());
        	FileInputStream inputStream = new FileInputStream(foundFiles[0].getPath());
    		Workbook wb = new XSSFWorkbook(inputStream);
        	return wb;
        }
        else
        {
        	System.out.println("File non trovato");
        	throw new Exception("File not found");
        }
	}

	public static void CLS() throws IOException, InterruptedException
	{
		final String os = System.getProperty("os.name");
        if (os.contains("Windows"))
            new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
        else
        	System.out.println("\033[2J\n");
    }

	public static String printRosa(SquadraDTO squadra)
    {
		try{
			String res = "";
			res = "ROSA: " + squadra.getNome() + System.lineSeparator();
			res = res + StringUtils.rightPad("NOME GIOCATORE",20," ") + "| " +
	    					 StringUtils.rightPad("RUOLI",10," ") + "| " +
	    					 StringUtils.rightPad("SQUADRA",15," ") + "| " +
	    					 StringUtils.rightPad("PRE.",5," ") + "| " +
	    					 StringUtils.rightPad("MdV.",5," ") + "| " +
	    					 StringUtils.rightPad("MdF.",5," ") + "| " +
	    					 StringUtils.rightPad("GoF.",5," ") + "| " +
	    					 StringUtils.rightPad("ASS.",5," ") + "| " +
	    					 StringUtils.rightPad("AMM.",5," ") + "| " +
	    					 StringUtils.rightPad("ESP.",5," ") + "| " +
	    					 StringUtils.rightPad("QuI.",5," ") + "| " +
	    					 StringUtils.rightPad("QuA.",5," ") + "| " +
	    					 StringUtils.rightPad("Val.",5," ") + System.lineSeparator();
			res = res + StringUtils.rightPad("-",203,"-") + System.lineSeparator();
			for (GiocatoreDTO g : squadra.getRosa())
			{
				res = res + StringUtils.rightPad(g.getNome(),20," ");
				String ruoli = "";
				for (String r : g.getRuoli())
					ruoli = ruoli + r + " ";
				res = res + "| " + StringUtils.rightPad(ruoli,10," ");

				if (g.getSquadra() != null)
					res = res + "| " + StringUtils.rightPad(g.getSquadra(),15," ");
				else
					res = res + "| " + StringUtils.rightPad("",15, " ");
				if (g.getPartiteGiocate() != null)
					res = res + "| " + StringUtils.rightPad(String.valueOf(g.getPartiteGiocate()),5, " ");
				else
					res = res + "| " + StringUtils.rightPad("",5, " ");
				if (g.getMediaVoto() != null)
					res = res + "| " + StringUtils.rightPad(String.valueOf(g.getMediaVoto()),5, " ");
				else
					res = res + "| " + StringUtils.rightPad("",5, " ");
				if (g.getMediaFantacalcio() != null)
					res = res + "| " + StringUtils.rightPad(String.valueOf(g.getMediaFantacalcio()),5, " ");
				else
					res = res + "| " + StringUtils.rightPad("",5, " ");
				if (g.getGolFatti() != null)
					res = res + "| " + StringUtils.rightPad(String.valueOf(g.getGolFatti()),5, " ");
				else
					res = res + "| " + StringUtils.rightPad("",5, " ");
				if (g.getAssist() != null)
					res = res + "| " + StringUtils.rightPad(String.valueOf(g.getAssist()),5, " ");
				else
					res = res + "| " + StringUtils.rightPad("",5, " ");
				if (g.getAmmunizioni() != null)
					res = res + "| " + StringUtils.rightPad(String.valueOf(g.getAmmunizioni()),5, " ");
				else
					res = res + "| " + StringUtils.rightPad("",5, " ");
				if (g.getEspulsioni() != null)
					res = res + "| " + StringUtils.rightPad(String.valueOf(g.getEspulsioni()),5, " ");
				else
					res = res + "| " + StringUtils.rightPad("",5, " ");
				if (g.getQuotazioneIniziale() != null)
					res = res + "| " + StringUtils.rightPad(String.valueOf(g.getQuotazioneIniziale()),5, " ");
				else
					res = res + "| " + StringUtils.rightPad("",5, " ");
				if (g.getQuotazioneAttuale() != null)
					res = res + "| " + StringUtils.rightPad(String.valueOf(g.getQuotazioneAttuale()),5, " ");
				else
					res = res + "| " + StringUtils.rightPad("",5, " ");
				if (g.getValutazione() != null)
					res = res + "| " + StringUtils.rightPad(String.valueOf(String.format("%.2f", g.getValutazione())),5, " ") + System.lineSeparator();
				else
					res = res + "| " + StringUtils.rightPad("",5, " ") + System.lineSeparator();
			}
			return res;
		}
		catch(Exception e)
		{
			System.out.println("Errore stampa rosa: " + e.getMessage());
			return null;
		}
    }

	public static String printTutteFormazioni(SquadraDTO squadra)
    {
		try{
			String res = "";
			res = "FORMAZIONE: " + squadra.getNome() + System.lineSeparator();
			for (int i=0; i < squadra.getFormazioni().size(); i++)
			{
				res = res + StringUtils.rightPad(squadra.getFormazioni().get(i).getModulo(),20, " ");
			}
			res = res + System.lineSeparator();

			//righe da inserire
	    	for (int g = 0; g < 23; g++)
	    	{
	    		for (int i=0; i < squadra.getFormazioni().size(); i++)
	    		{
	    			if (squadra.getFormazioni().get(i).getFormazione().size() > g && squadra.getFormazioni().get(i).getFormazione().get(g) != null)
	    				res = res + StringUtils.rightPad(squadra.getFormazioni().get(i).getFormazione().get(g).getNome(),20," ");
	    			else
	    				res = res + StringUtils.rightPad(" ",20," ");
	    		}
	    		res = res + System.lineSeparator();
				if (g == 10) res = res + StringUtils.rightPad("-",220,"-") + System.lineSeparator();
			}

	    	for (int i=0; i < squadra.getFormazioni().size(); i++)
			{
	    		res = res + StringUtils.rightPad("Val. " + String.valueOf(String.format("%.2f",squadra.getFormazioni().get(i).getValutazione())),20," ");
			}

			res = res + System.lineSeparator();
	    	return res;
		}
		catch(Exception e)
		{
			System.out.println("Errore stampa tutte le formazioni: " + e.getMessage());
			return null;
		}
    }

}