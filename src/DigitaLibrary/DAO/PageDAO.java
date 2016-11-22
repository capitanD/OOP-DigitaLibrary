package DigitaLibrary.DAO;

import java.sql.*;
import java.util.ArrayList;
import java.util.LinkedList;

import com.sun.xml.internal.messaging.saaj.util.Base64;

/* DAO --> Class PAGEDAO
 * - No costruttore,
 * - retrieve(int) 	-> Dato un ID, implementa l'estrazione delle righe corrispondenti della tabella "Page",
 * - retrieveAll()	-> Implementa l'strazione di tutte le righe della tabella "Page" e le seleziona per ID,
 * - add(ArrayList<String>)			-> Aggiunge una nuova riga nella tabella "Page",
 * - edit(ArrayList<String>)		-> Implementa la modifica di una riga della tabella "Page",
 * - delete(int)	-> Dato un ID, implementa l'eliminazione di una riga specifica della tabella "Page".
 */


@SuppressWarnings("restriction")
public class PageDAO extends AbstractDAO {
	
	
	/*	RETRIEVE(int)
	 * 	Estrazione di righe specifiche della tabella "Page" nel database.
	 */
	public ArrayList<String> retrieve(int id){
		
		ArrayList<String> data = new ArrayList<String>();
		try{
			Connection con=connection();
			Statement st=con.createStatement();
			ResultSet rs=st.executeQuery("SELECT * FROM page WHERE id='"+ id +"' ");
			
			   if(rs.next()){ 
				 data.add(rs.getString("id"));
			     data.add(rs.getString("operaID"));
			     data.add(rs.getString("number"));
			     data.add(new String(Base64.base64Decode(rs.getString("text"))));
			     data.add(rs.getString("image"));
			     data.add(rs.getString("status"));
			     data.add(rs.getString("report"));
			   }
			   
		} catch (Exception E){
			E.printStackTrace();
		}
		return data;
	}
	
	
	/*	RETRIEVEALL()
	 * 	Estrazione di tutte le righe della tabella "Page" nel database e selezionate per ID.
	 */
	public LinkedList<Integer> retrieveAll(){
		LinkedList<Integer> data = new LinkedList<Integer>();
		try{
			Connection con=connection();
			Statement st=con.createStatement();
			ResultSet rs=st.executeQuery("SELECT * FROM page");
			
			while(rs.next()){
				data.add(rs.getInt("id"));
			}
			
		} catch (Exception E){
			E.printStackTrace();
		}
		return data;
	}
	
	
	/*	ADD(ArrayList<String>)
	 * 	Aggiunta di una nuova riga nella tabella "Page" nel database.
	 */
	public void add(ArrayList<String> data){
		try{
			Connection con=connection();
			PreparedStatement pst=con.prepareStatement("INSERT INTO page(operaID, number, text, image, status, report) VALUES ('"+ data.get(1) +"','"+ data.get(2) +"','"+ data.get(3) +"','"+ data.get(4) +"','"+ data.get(5) +"','"+ data.get(6) +"')");
			pst.executeUpdate();
			   	
		} catch (Exception E){
			E.printStackTrace();
		}
	}
	
	
	/*	EDIT(ArrayList<String>)
	 *  Modifica di una riga nella tabella "Page" nel database.
	 */	
	public void edit(ArrayList<String> data){
		try{
			Connection con=connection();
			PreparedStatement pst=con.prepareStatement("UPDATE page SET operaID='"+ data.get(1) +"',number='"+ data.get(2) +"',text='"+ data.get(3) +"',image='"+ data.get(4) +"',status='"+ data.get(5) +"',report='"+ data.get(6) +"' WHERE id='"+ data.get(0) +"'");
			pst.executeUpdate();
			
		} catch (Exception E){
			E.printStackTrace();
		}
	}
	
	
	/*	DELETE(int)
	 *  Eliminazione di una riga specifica della tabella "Page" nel database.
	 */	
	public void delete(int i){
		try{
			Connection con=connection();
			PreparedStatement pst=con.prepareStatement("DELETE FROM page WHERE id='"+ i +"'");
			pst.executeUpdate();
			
		} catch (Exception E){
			E.printStackTrace();
		}
	}
}
/*  END Class  PageDAO  */