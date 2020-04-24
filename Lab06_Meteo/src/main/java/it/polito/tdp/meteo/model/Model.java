package it.polito.tdp.meteo.model;

import java.time.Month;
import java.util.ArrayList;
import java.util.List;

import it.polito.tdp.meteo.DAO.MeteoDAO;

public class Model {
	
	private final static int COST = 100;
	private final static int NUMERO_GIORNI_CITTA_CONSECUTIVI_MIN = 3;
	private final static int NUMERO_GIORNI_CITTA_MAX = 6;
	private final static int NUMERO_GIORNI_TOTALI = 15;
		
	private List<Citta> leCitta;
	private List<Citta> soluzione;

	private MeteoDAO mDao;	
	
	public Model() {
		mDao = new MeteoDAO();
		this.leCitta = mDao.getAllCitta();
	}

	public String getUmiditaMedia(int mese) {
		
		String media = "";
		for (Citta ci : mDao.getAllCitta()) {
			media += "La media dell'umidita' di "+ci.getNome() +" e': "+ mDao.getUmidita(mese, ci) +"\n";
		}
		return media;
	}
	
	public List<Citta> calcolaSequenza(int mese) {
		List<Citta> parziale = new ArrayList<>();
		this.soluzione = null;

		for (Citta c : leCitta) {
			c.setRilevamenti(mDao.getAllRilevamentiLocalitaMese(mese, c.getNome()));
		}
	
		ricorsiva(parziale, 0);
		return soluzione;
	}

	
	public void ricorsiva(List<Citta> parziale, int livello) {		
		
		if(livello == this.NUMERO_GIORNI_TOTALI) { //caso terminale		 
			
			Double costo = calcolaCosto(parziale);
			
			if( soluzione == null || costo < calcolaCosto(soluzione)) {
				soluzione = new ArrayList<>(parziale);
			}
			
		}else {
			for(Citta nuova : leCitta) {
				if (isOK(nuova, parziale)) {				
					
					parziale.add(nuova);
					ricorsiva(parziale, livello+1);
					parziale.remove(parziale.size()-1);					
				}
			}			
		}	
	}

	private double calcolaCosto(List<Citta> parziale) {
		
		double costo=0.0;
		
		for(int giorno = 1; giorno<this.NUMERO_GIORNI_TOTALI; giorno++) {
			Citta c = parziale.get(giorno-1); //quindi in posizione 0.
			double umidita = c.getRilevamenti().get(giorno-1).getUmidita();
			costo += umidita;
		}
		
		for(int i = 2; i<= this.NUMERO_GIORNI_TOTALI; i++) {
			if(!parziale.get(i-1).equals(parziale.get(i-2))){
				costo += this.COST;
			}
		}		
		
		return costo;
	}
	
	private boolean isOK (Citta nuova, List<Citta> parziale) {
		
		int contatore = 0;
		for(Citta presente : parziale) {
			if(presente.equals(nuova))
				contatore++;
		}
		
		if(contatore>=this.NUMERO_GIORNI_CITTA_MAX)
			return false; //non la posso aggiungere
		
		if(parziale.size()==0)
			return true; //se non ho ancora citta', posso aggiungela sicuro.
		
		if(parziale.size()==1 || parziale.size()==2)
			return parziale.get(parziale.size()-1).equals(nuova); //se ci sono solo
		// 1 o 2 citta', la seconda o la terza dovranno essere uguali alla prima
		
		if (parziale.get(parziale.size() - 1).equals(nuova)) 
			return true;
		
		if (parziale.get(parziale.size() - 1).equals(parziale.get(parziale.size() - 2))
				&& parziale.get(parziale.size() - 2).equals(parziale.get(parziale.size() - 3)))
			return true;

		return false;
		
	}
		

}

