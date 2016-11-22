package DigitaLibrary.DAO;

import java.sql.*;
import java.util.ArrayList;
import java.util.LinkedList;

/* DAO --> Class PAGEDAO
 * - No costruttore,
 * - retrieve(int) 	-> Dato un ID, implementa l'estrazione delle righe corrispondenti della tabella "User",
 * - retrieveAll()	-> Implementa l'strazione di tutte le righe della tabella "User" e le seleziona per ID,
 * - add(ArrayList<String>)			-> Aggiunge una nuova riga nella tabella "User",
 * - edit(ArrayList<String>)		-> Implementa la modifica di una riga della tabella "User",
 * - delete(int)	-> Dato un ID, implementa l'eliminazione di una riga specifica della tabella "User".
 */


public class UserDAO extends AbstractDAO {
	
	
	/*	RETRIEVE(int)
	 * 	Estrazione di righe specifiche della tabella "User" nel database.
	 */
	public ArrayList<String> retrieve(int id){
		
		ArrayList<String> data = new ArrayList<String>();
		try{
			Connection con=connection();
			Statement st=con.createStatement();
			ResultSet rs=st.executeQuery("SELECT * FROM users WHERE id='"+ id +"' ");
			
			   if(rs.next()){ 
				 data.add(rs.getString("id"));
			     data.add(rs.getString("username"));
			     data.add(rs.getString("password"));
			     data.add(rs.getString("role"));
			     data.add(rs.getString("email"));
			   }
			   else {
				  
			   }	
		} catch (Exception E){
			E.printStackTrace();
		}
		return data;
	}
	
	
	/*	RETRIEVEALL()
	 * 	Estrazione di tutte le righe della tabella "User" nel database e selezionate per ID.
	 */
	public LinkedList<Integer> retrieveAll(){
		LinkedList<Integer> data = new LinkedList<Integer>();
		try{
			Connection con=connection();
			Statement st=con.createStatement();
			ResultSet rs=st.executeQuery("SELECT * FROM users");
			
			while(rs.next()){
				data.add(rs.getInt("id"));
			}
			
		} catch (Exception E){
			E.printStackTrace();
		}
		return data;
	}
	
	
	/*	ADD(ArrayList<String>)
	 * 	Aggiunta di una nuova riga nella tabella "User" nel database.
	 */
	public void add(ArrayList<String> data){
		try{
			Connection con=connection();
			PreparedStatement pst=con.prepareStatement("INSERT INTO users(username, password, role, email) VALUES ('"+ data.get(1) +"','"+ data.get(2) +"','"+ data.get(3) +"','"+ data.get(4) +"')");
			pst.executeUpdate();
			   	
		} catch (Exception E){
			E.printStackTrace();
		}
	}
	
	
	/*	EDIT(ArrayList<String>)
	 *  Modifica di una riga nella tabella "User" nel database.
	 */	
	public void edit(ArrayList<String> data){
		try{
			Connection con=connection();
			PreparedStatement pst=con.prepareStatement("UPDATE users SET username='"+ data.get(1) +"',password='"+ data.get(2) +"',role='"+ data.get(3) +"',email='"+ data.get(4) +"' WHERE id='"+ data.get(0) +"'");
			pst.executeUpdate();
			
		} catch (Exception E){
			E.printStackTrace();
		}
	}
	
	
	/*	DELETE(int)
	 *  Eliminazione di una riga specifica della tabella "User" nel database.
	 */	
	public void delete(int i){
		try{
			Connection con=connection();
			PreparedStatement pst=con.prepareStatement("DELETE FROM users WHERE id='"+ i +"'");
			pst.executeUpdate();
			
		} catch (Exception E){
			E.printStackTrace();
		}
	}
}
/*  END Class  UserDAO  */