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
import Pojo.Quotazioni;
import Pojo.Squadre;
import Pojo.Statistiche;

public abstract class Master {

    public static ArrayList<SquadraDTO> squadre = null;
    public static Map<String, ModuliDTO> moduli = null;
    public static Workbook workbook;

    public static void main(String[] args) throws Exception
    {
    	try{
    		caricaInfo();
    	}
    	catch (Throwable e)
    	{
    		System.out.println("Errore programma");
    	}
    }

    public static void caricaInfo() throws Exception
    {
    	System.out.println("Caricamento programma in corso... (sistema: " + System.getProperty("os.name") + ")");

    	workbook = Utils.connectionWorkbook();

    	squadre = Squadre.creaSquadre();

		moduli = Moduli.createModuli();

		//infoSerieA = InfoSerieA.createInfo();
		//squadre = Giocatori.createGiocatori(squadre);

		squadre = Quotazioni.creaQuotazioni(squadre);

		squadre = Statistiche.creaStatistiche(squadre);

    	squadre = Formazioni.creaFormazioni(moduli, squadre);

    	Grafica.inferfaccia(squadre);
    }
}