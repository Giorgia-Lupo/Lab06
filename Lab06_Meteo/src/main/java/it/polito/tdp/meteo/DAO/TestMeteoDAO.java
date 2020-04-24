package it.polito.tdp.meteo.DAO;

import java.time.Month;
import java.util.Date;
import java.util.List;

import it.polito.tdp.meteo.model.Citta;
import it.polito.tdp.meteo.model.Rilevamento;

public class TestMeteoDAO {

	public static void main(String[] args) {
		
		MeteoDAO dao = new MeteoDAO();

		List<Rilevamento> list = dao.getAllRilevamenti();

		// STAMPA: localita, giorno, mese, anno, umidita (%)
		for (Rilevamento r : list) {
			System.out.format("%-10s %2td/%2$2tm/%2$4tY %3d%%\n", r.getLocalita(), r.getData(), r.getUmidita());			
		}
		System.out.println("++++++++++++++++++++++");		
			
		System.out.println(dao.getAllRilevamentiLocalitaMese(01, "Genova"));
		
		System.out.println("++++++++++++++++++++++");
		
		Citta c1 = new Citta("Genova");
		System.out.println("La media dell'umidita' a "+ c1.getNome() +" e': "+ dao.getUmidita(01, c1));
		
		Citta c2 = new Citta("Torino");
		System.out.println("La media dell'umidita' a "+ c2.getNome() +" e': "+ dao.getUmidita(01, c2));
		
		System.out.println("++++++++++++++++++++++");
		
		System.out.println(dao.getAllCitta());
		
		
		
		
		
		
	//System.out.println(dao.getAllRilevamentiLocalitaMese(1, "Genova"));
//		System.out.println(dao.getAvgRilevamentiLocalitaMese(1, "Genova"));
//		
//		System.out.println(dao.getAllRilevamentiLocalitaMese(5, "Milano"));
//		System.out.println(dao.getAvgRilevamentiLocalitaMese(5, "Milano"));
//		
//		System.out.println(dao.getAllRilevamentiLocalitaMese(5, "Torino"));
//		System.out.println(dao.getAvgRilevamentiLocalitaMese(5, "Torino"));
		

	}

}
