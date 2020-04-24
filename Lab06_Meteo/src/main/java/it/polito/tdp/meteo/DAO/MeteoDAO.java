package it.polito.tdp.meteo.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.Month;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import it.polito.tdp.meteo.model.Citta;
import it.polito.tdp.meteo.model.Rilevamento;

public class MeteoDAO {
	
	public double getUmidita (int mese, Citta citta) {
		
		String sql = "SELECT AVG(Umidita) AS U FROM situazione " + 
				"WHERE localita=? " + 
				"AND MONTH(data)=? ";
		try {
			Connection conn = ConnectDB.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);

			st.setString(1, citta.getNome());
			st.setInt(2, mese);
			
			ResultSet rs = st.executeQuery();
			
			rs.next(); 
			
			Double media = rs.getDouble("U");

			conn.close();
			return media;

		} catch (SQLException e) {

			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}
	
	public List<Citta> getAllCitta (){
		
		String sql = "SELECT DISTINCT s.Localita FROM situazione AS s ";
		
		List<Citta> citta = new ArrayList<>();
		
		try {
			Connection conn = ConnectDB.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			
			ResultSet rs = st.executeQuery();
			
			while (rs.next()) {
				Citta c = new Citta(rs.getString("localita"));
				citta.add(c);
			}
			conn.close();
			
			return citta;

		} catch (SQLException e) {

			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}
	
	public List<Rilevamento> getAllRilevamenti() {

		final String sql = "SELECT Localita, Data, Umidita FROM situazione ORDER BY data ASC";

		List<Rilevamento> rilevamenti = new ArrayList<Rilevamento>();		

		try {
			Connection conn = ConnectDB.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);

			ResultSet rs = st.executeQuery();

			while (rs.next()) {

				Rilevamento r = new Rilevamento(rs.getString("Localita"), rs.getDate("Data"), rs.getInt("Umidita"));
				rilevamenti.add(r);
			}

			conn.close();
			return rilevamenti;

		} catch (SQLException e) {

			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}

	
	public List<Rilevamento> getAllRilevamentiLocalitaMese(int mese, String localita) {
		
		String sql = "SELECT s.Localita, s.`Data`, s.Umidita FROM  situazione AS s WHERE MONTH(DATA)=? and localita= ?  "+
		"ORDER BY data ASC";

		List<Rilevamento> rilevamentiLocalitaMese = new ArrayList<Rilevamento>();		
		
		try {
			Connection conn = ConnectDB.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);

			st.setInt(1, mese);
			st.setString(2, localita);
			
			
			ResultSet rs = st.executeQuery();

			while (rs.next()) {

				Rilevamento r = new Rilevamento(rs.getString("Localita"), rs.getDate("Data"), rs.getInt("Umidita"));
				
				rilevamentiLocalitaMese.add(r);
			}

			conn.close();
			
			return rilevamentiLocalitaMese;

		} catch (SQLException e) {

			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}
}
