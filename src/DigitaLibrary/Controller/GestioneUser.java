package DigitaLibrary.controller;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedList;

import DigitaLibrary.DAO.UserDAO;
import DigitaLibrary.model.Biblioteca;
import DigitaLibrary.model.User;


/*
 * CONTROLLER --> GESTIONE USER
 * La classe GestioneUser fornisce i metodi per la registrazione ed il login a DigitaLibrary e 
 * tutto ciò di cui ha bisogno l'amministratore per gestire gli utenti registrati.
 *
 */

public class GestioneUser implements Gestione{

	private Biblioteca data;
	private Object user;
	private Object pwd;
	private Object pwd_repeat;
	private Object mail;
	
	
	/* -- Costruttore Login -- */
	public GestioneUser (Biblioteca d, Object u, Object p){
		
		this.data    	 = d;
		this.user    	 = u;
		this.pwd      	 = p;
	}
	
	/* -- Costruttore Registrazione -- */
	public GestioneUser (Biblioteca d, Object u, Object p, Object rp, Object e){
		
		this.data    	 = d;
		this.user    	 = u;
		this.pwd      	 = p;
		this.pwd_repeat	 = rp;
		this.mail        = e;	
	}
	
	
	/*  LOGIN()
	 * 	Prende i dati dalle TextField e li confronta con quelli estratti dal database.
	 */
	public int login(){
			
			boolean error   = false;
			
			LinkedList<User> users = data.getUserList();
			Iterator<User> itr = users.iterator();
			int idUser = 0;
			User u = new User();
			
			while(itr.hasNext()){
				u = itr.next();

				if( u.getUsername().equals( (String)this.user)){
					if( u.getPassword().equals((String)this.pwd)){
						idUser = u.getId();
						error = false;		
					} else 				
						break;					
				}
			}
			if(error){
				return idUser;
			}
			return idUser;
	}
	
	
	/*  FIELDCONTROL()
	 * 	Controllo campi per la registrazione.
	 */
	public int fieldControl(){
		
		int typeError = 0;
		if(!((String)pwd).equals((String)pwd_repeat) )
			typeError = 1;		
		
		LinkedList<User> users = data.getUserList();
		Iterator<User> itr = users.iterator();
		
		while(itr.hasNext()){
			User u = itr.next();
			if(u.getUsername().equals( (String)user)){
				typeError = 2;
				break;
			}
			if(u.getEmail().equals( (String)mail)){
				typeError = 3;
				break;
			}
		}
		return typeError;
	}
	
	
	/*	ADD()
	 * 	Esegue un controllo delle password,
	 *  Prende i dati dalle TextField, estrae i dati dal DB e controlla se esistono altri utenti con le stesse username e stesse email.
	 *  Tramite il DAO inserisce i nuovi dati nel database.
	 */
	public boolean add() {
		
		User signup = new User(0, (String)user, (String)pwd, (String)mail, 0);
		UserDAO DAO = new UserDAO();
		DAO.add(signup.toArray());	
		data.getUserList().add(signup);
			
		return true;
	}

	
	/*  REMOVE()
	 * 	Rimozione di un utente dal sistema.
	 */
	public boolean remove() {
		
		boolean error = true;
		Iterator<User> itr = data.getUserList().iterator();
		
		while(itr.hasNext()){
			User next = itr.next();
			if(next.getUsername().equals(user)){
				UserDAO DAO = new UserDAO();
				DAO.delete(next.getId());
				error = false;
				break;
			}
		}
		data.setUserList();
		LinkedList<String> newUserList = new LinkedList<String>();
		for(User u : data.getUserList()){
			newUserList.add(u.getUsername());
		}
		refresh(); 
		return error;
	}

	
	/*  EDIT()
	 *  Presa la lista degli utenti, esegue una modifica dell'attributo "role" del database.
	 */
	public boolean edit() {
		 String select = (String)user;
	     Iterator<User> itr = data.getUserList().iterator();

	     while(itr.hasNext()){
	    	 User next = itr.next();
	    	 if(next.getUsername().equals(select)){
	    		   int role = next.getRole();
	    		   next.setRole(role+(int)pwd);
	    		   UserDAO DAO = new UserDAO();
	    		   DAO.edit(next.toArray());
	    	   }
	       }
	       data.setUserList();
	       return true;
	}
	
	
	/*  REFRESH() 
	 *  Ricarica la lista utenti aggiornata in base al click del mouse.
	 */
	public LinkedList<String> refresh(){
		
		ArrayList<String> listaUser = new ArrayList<String>();
		LinkedList<User> utenti = data.getUserList();
		Iterator<User> itr = utenti.iterator();
		
		while(itr.hasNext()){
			listaUser.add(itr.next().getUsername());
		}
		Collections.sort(listaUser);
		LinkedList<String> refreshList = new LinkedList<String>();
		for(String u : listaUser){
			refreshList.add(u);
		}
		return refreshList;
	}

}
/*  END Class  GestioneUser  */