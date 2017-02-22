package DigitaLibrary.DAO;

import java.sql.*;
import java.util.ArrayList;
import java.util.LinkedList;

/* DAO --> Class ACTIONDAO
 * - No costruttore,
 * - retrieve(int) 	-> Dato un ID, implementa l'estrazione delle righe corrispondenti della tabella "Action",
 * - retrieveAll()	-> Implementa l'estrazione di tutte le righe della tabella "Action" e le seleziona per ID,
 * - add(ArrayList<String>)			-> Aggiunge una nuova riga nella tabella "Action",
 * - edit(ArrayList<String>)		-> Implementa la modifica di una riga della tabella "Action",
 * - delete(int)	-> Dato un ID, implementa l'eliminazione di una riga specifica della tabella "Action".
 */


public class ActionDAO extends AbstractDAO {
	
	
	/*	RETRIEVE(int)
	 * 	Estrazione di righe specifiche della tabella "Action" nel database.
	 */
	public ArrayList<String> retrieve(int id){
		
		ArrayList<String> data = new ArrayList<String>();
		try{
			Connection con=connection();
			Statement st=con.createStatement();
			ResultSet rs=st.executeQuery("SELECT * FROM action WHERE id='"+ id +"' ");
			
			   if(rs.next()){ 
				 data.add(rs.getString("id"));
				 data.add(rs.getString("userID"));
			     data.add(rs.getString("operaID"));
			     data.add(rs.getString("page"));
			     data.add(rs.getString("type"));
			     data.add(rs.getString("status"));
			     data.add(rs.getString("action_report"));
			   }	
		} catch (Exception E){
			E.printStackTrace();
		}
		return data;
	}
	
	
	/*	RETRIEVEALL()
	 * 	Estrazione di tutte le righe della tabella "Action" nel database e selezionate per ID.
	 */
	public LinkedList<Integer> retrieveAll(){
		LinkedList<Integer> data = new LinkedList<Integer>();
		try{
			Connection con=connection();
			Statement st=con.createStatement();
			ResultSet rs=st.executeQuery("SELECT * FROM action");
			
			while(rs.next()){
				data.add(rs.getInt("id"));
			}
			
		} catch (Exception E){
			E.printStackTrace();
		}
		return data;
	}

	
	/*	ADD(ArrayList<String>)
	 * 	Aggiunta di una nuova riga nella tabella "Action" nel database.
	 */
	public void add(ArrayList<String> data){
		try{
			Connection con=connection();
			PreparedStatement pst=con.prepareStatement("INSERT INTO action(userID, operaID, page, type, status, action_report) VALUES ('"+ data.get(1) +"','"+ data.get(2) +"','"+ data.get(3) +"','"+ data.get(4) +"','"+ data.get(5) +"','"+ data.get(6) +"')");
			pst.executeUpdate();
			   	
		} catch (Exception E){
			E.printStackTrace();
		}
	}
	
	
	/*	EDIT(ArrayList<String>)
	 *  Modifica di una riga nella tabella "Action" nel database.
	 */	
	public void edit(ArrayList<String> data){
		try{
			Connection con=connection();
			PreparedStatement pst=con.prepareStatement("UPDATE action SET userID='"+ data.get(1) +"',operaID='"+ data.get(2) +"',page='"+ data.get(3) +"',type='"+ data.get(4) +"',status='"+ data.get(5) +"', action_report='"+ data.get(6) +"' WHERE id='"+ data.get(0) +"'");
			pst.executeUpdate();
			
		} catch (Exception E){
			E.printStackTrace();
		}
	}
	
	
	/*	DELETE(int)
	 *  Eliminazione di una riga specifica della tabella "Action" nel database.
	 */	
	public void delete(int i){
		try{
			Connection con=connection();
			PreparedStatement pst=con.prepareStatement("DELETE FROM action WHERE id='"+ i +"'");
			pst.executeUpdate();
			
		} catch (Exception E){
			E.printStackTrace();
		}
	}
}
/*  END Class  ActionDAO  */