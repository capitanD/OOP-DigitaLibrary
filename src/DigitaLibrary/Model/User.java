package DigitaLibrary.model;

import java.util.ArrayList;
import DigitaLibrary.DAO.UserDAO;

/* MODEL --> Class USER
 * - Costruttore vuoto,
 * - Costruttore inizializzato,
 * - Metodi get(),
 * - Metodi set(),
 * - Lista utente: ArrayList().
 */

public class User {
	
	private int id;
	private String username;
	private String password;
	private String email;
	private int role;
	
	/* USER - Costruttori */
	public User(){ }
	

	public User(int id, String uid, String pwd, String e, int r){
		this.id       = id;
		this.username = uid;
		this.password = pwd;
		this.email    = e;
		this.role     = r;
	}
	
	
	public User(int id){
		UserDAO DAO            = new UserDAO();
		ArrayList<String> data = DAO.retrieve(id);
		this.id       = Integer.parseInt(data.get(0));
		this.username = data.get(1);
		this.password = data.get(2);
		this.role     = Integer.parseInt(data.get(3));
		this.email    = data.get(4);
		
	}
	
	/* USER - Metodi get() */
	public int getId(){
		return this.id;
	}
	
	public String getUsername(){
		return this.username;
	}
	
	public String getPassword(){
		return this.password;
	}
	
	public String getEmail(){
		return this.email;
	}
	
	public int getRole(){
		return this.role;
	}
	
	/* USER - Metodi set() */
	public void setUsername(String u){
		this.username=u;
	}
	
	public void setPassword(String p){
		this.password=p;
	}
	
	public void setEmail(String e){
		this.email=e;
	}
	
	public void setRole(int r){
		this.role=r;
	}
	
	/* USER - Lista utente */
	public ArrayList<String> toArray(){
		ArrayList<String> data= new ArrayList<String>();
		data.add(Integer.toString(this.id));
		data.add(this.username);
		data.add(this.password);
		data.add(Integer.toString(this.role));
		data.add(this.email);
		return data;
	}
}
/*  END Class  User  */