package Pojo;

import java.util.ArrayList;
import java.util.Map;

import Dao.FormazioniDTO;
import Dao.GiocatoreDTO;
import Dao.ModuliDTO;
import Dao.SquadraDTO;

public class Formazioni {

	/**
	 * Sulla base della squadra e del modulo, credo la formazione ideale
	 *
	 * @param moduli
	 * @param squadre
	 *
	 * @return ArrayList - delle quadre con la formazione ideale
	 *
	 * @throws Exception

	public static ArrayList<SquadraDTO> creaFormazioni(Map<String, ModuliDTO> moduli, ArrayList<SquadraDTO> squadre) throws Exception
	{
		System.out.println("Creazione di tutte le formazioni possibili e ordinamento da quella fiu' forte a quella meno");

		for (SquadraDTO squadra : squadre)
		{
			ArrayList<FormazioniDTO> formazioni = new ArrayList<FormazioniDTO>();
			for (Map.Entry<String, ModuliDTO> modulo : moduli.entrySet())
			{
				FormazioniDTO formazione = new FormazioniDTO();
				int countTitolari = 11;
				formazione.setModulo(modulo.getKey());
				ArrayList<GiocatoreDTO> rosaTemp = new ArrayList<GiocatoreDTO>(squadra.getRosa());
				for (ArrayList<String> m : modulo.getValue().getModulo())
				{
					try{
						GiocatoreDTO f = new GiocatoreDTO();
						int index = -1;
						for (String ruolo: m )
						{
							for (GiocatoreDTO g : rosaTemp)
							{
								try{
									//System.out.println("Giocatore: " + g.getNome() + " Ruolo: " + g.getRuoli() + " Valutazione: " + g.getValutazione());

									//Scorrendo tutta la rosa, cerco il giocatore migliore in quel ruolo
									if (g.getRuoli().contains(ruolo) && (f.getValutazione() == null || (g.getValutazione() != null && g.getValutazione() > f.getValutazione())))
									{
										f.setNome(g.getNome());
										f.getRuoli().add(ruolo);
										f.setSquadra(g.getSquadra());
										f.setMediaFantacalcio(g.getMediaFantacalcio());
										f.setValutazione(g.getValutazione());
										index = rosaTemp.indexOf(g);
									}
								} catch (Exception e){
									System.out.println("Errore Giocatore: " + g.getNome());
								}
							}
						}
						if (index != -1)
						{
							formazione.getFormazione().add(f);
							rosaTemp.remove(index);
							if (countTitolari > 0)
							{
								formazione.setValutazione(formazione.getValutazione() + (f != null && f.getValutazione() != null ? f.getValutazione().doubleValue() : 0 ));
								countTitolari--;
							}
						}
					} catch (Exception e){
						System.out.println("Squadra: " + squadra.getNome() + " errore Ruolo: " + m);
					}
				}

				//Ordinamento fomazioni dalla piu' forte alla meno forte
				if (formazioni.size() == 0)
				{
					formazioni.add(formazione);
				}
				else
				{
					boolean addFormazione = false;
					for (FormazioniDTO f : formazioni)
					{
						if (formazione.getValutazione() > f.getValutazione())
						{
							formazioni.add(formazioni.indexOf(f), formazione);
							addFormazione = true;
							break;
						}
					}
					if (!addFormazione)
						formazioni.add(formazione);
				}
			}
			squadra.setFormazioni(formazioni);
		}

		System.out.println("Formazioni create e ordinate correttamente");
		return squadre;
	}
	*/
}