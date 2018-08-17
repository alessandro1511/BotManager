package Views;

import java.util.ArrayList;
import java.util.Map;

import org.apache.poi.ss.usermodel.Workbook;

import Commons.Grafica;
import Commons.Utils;
import Dao.ModuliDTO;
import Dao.SquadraDTO;
import Pojo.Formazioni;
import Pojo.Moduli;
import Pojo.Squadre;
import Pojo.Statistiche;

public abstract class Master {

    public static ArrayList<SquadraDTO> squadre = null;
    public static Map<String, ModuliDTO> moduli = null;
    public static Workbook workbook = null;
    public static String path = null;

    public static void main(String[] args) throws Exception
    {
    	try{
    		caricaInfo();
    	}
    	catch (Throwable e)
    	{
    		System.out.println("Errore programma: " + e);
    	}
    }

    public static void caricaInfo() throws Exception
    {
    	System.out.println("Caricamento programma in corso... (sistema: " + System.getProperty("os.name") + ")");

    	path = Utils.calcolaPath();

    	workbook = Utils.connectionWorkbook(path);

    	squadre = Squadre.creaSquadre();

		moduli = Moduli.createModuli();

		squadre = Statistiche.creaQuotazioni(squadre, path);

		squadre = Statistiche.creaStatistiche(squadre, path);

		squadre = Statistiche.votiGiornate(squadre, path);

    	//squadre = Formazioni.creaFormazioni(moduli, squadre);

    	Grafica.inferfaccia(squadre, path);
    }

    public static String getPath() {
		return path;
	}
}