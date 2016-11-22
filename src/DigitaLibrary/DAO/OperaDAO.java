package DigitaLibrary.DAO;

import java.sql.*;
import java.util.ArrayList;
import java.util.LinkedList;

/* DAO --> Class OPERADAO
 * - No costruttore,
 * - retrieve(int) 	-> Dato un ID, implementa l'estrazione delle righe corrispondenti della tabella "Opera",
 * - retrieveAll()	-> Implementa l'strazione di tutte le righe della tabella "Opera" e le seleziona per ID,
 * - add(ArrayList<String>)			-> Aggiunge una nuova riga nella tabella "Opera",
 * - edit(ArrayList<String>)		-> Implementa la modifica di una riga della tabella "Opera",
 * - delete(int)	-> Dato un ID, implementa l'eliminazione di una riga specifica della tabella "Opera".
 */


public class OperaDAO extends AbstractDAO {
	
	
	/*	RETRIEVE(int)
	 * 	Estrazione di righe specifiche della tabella "Opera" nel database.
	 */
	public ArrayList<String> retrieve(int id){
		
		ArrayList<String> data = new ArrayList<String>();
		try{
			Connection con=connection();
			Statement st=con.createStatement();
			ResultSet rs=st.executeQuery("SELECT * FROM opera WHERE id='"+ id +"' ");
			
			   if(rs.next()){ 
				 data.add(rs.getString("id"));
			     data.add(rs.getString("title"));
			     data.add(rs.getString("author"));
			     data.add(rs.getString("pages"));
			     data.add(rs.getString("status"));
			   }	
		} catch (Exception E){
			E.printStackTrace();
		}
		return data;
	}
	
	
	/*	RETRIEVEALL()
	 * 	Estrazione di tutte le righe della tabella "Opera" nel database e selezionate per ID.
	 */
	public LinkedList<Integer> retrieveAll(){
		LinkedList<Integer> data = new LinkedList<Integer>();
		try{
			Connection con=connection();
			Statement st=con.createStatement();
			ResultSet rs=st.executeQuery("SELECT * FROM opera");
			
			while(rs.next()){
				data.add(rs.getInt("id"));
			}
			
		} catch (Exception E){
			E.printStackTrace();
		}
		return data;
	}
	
	
	/*	ADD(ArrayList<String>)
	 * 	Aggiunta di una nuova riga nella tabella "Opera" nel database.
	 */
	public void add(ArrayList<String> data){
		try{
			Connection con=connection();
			int status = 0;
			if(data.get(4).equals("true")) status = 1;
			PreparedStatement pst=con.prepareStatement("INSERT INTO opera(title, author, pages, status) VALUES ('"+ data.get(1) +"','"+ data.get(2) +"','"+ data.get(3) +"','"+ status +"')");
			pst.executeUpdate();
			   	
		} catch (Exception E){
			E.printStackTrace();
		}
	}
	
	
	/*	EDIT(ArrayList<String>)
	 *  Modifica di una riga nella tabella "Opera" nel database.
	 */	
	public void edit(ArrayList<String> data){
		try{
			Connection con=connection();
			int status = 0;
			if(data.get(4).equals("true")) status = 1;
			PreparedStatement pst=con.prepareStatement("UPDATE opera SET title='"+ data.get(1) +"',author='"+ data.get(2) +"',pages='"+ data.get(3) +"',status='"+ status +"' WHERE id='"+ data.get(0) +"'");
			pst.executeUpdate();
			
		} catch (Exception E){
			E.printStackTrace();
		}
	}
	
	
	/*	DELETE(int)
	 *  Eliminazione di una riga specifica della tabella "Opera" nel database.
	 */	
	public void delete(int i){
		try{
			Connection con=connection();
			PreparedStatement pst=con.prepareStatement("DELETE FROM opera WHERE id='"+ i +"'");
			pst.executeUpdate();
			
		} catch (Exception E){
			E.printStackTrace();
		}
	}
}
/*  END Class OperaDAO  */