package DigitaLibrary.Controller;

import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedList;

import javax.swing.AbstractAction;
import javax.swing.DefaultListModel;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import DigitaLibrary.DAO.UserDAO;
import DigitaLibrary.Model.Biblioteca;
import DigitaLibrary.Model.User;
import DigitaLibrary.View.MainFrame;
import DigitaLibrary.View.RegistrazioneFrame;

/*
 * CONTROLLER --> GESTIONE USER
 * La classe GestioneUser fornisce i metodi per la registrazione ed il login a DigitaLibrary e 
 * tutto ciò di cui ha bisogno l'amministratore per gestire gli utenti registrati.
 * - Costruttore per il Login e costruttore per la Registrazione,
 * - actionPerformed(ActionEvent) -> gestione eventi della View,
 * - login()	-> Confronta i dati inseriti dall'utente con quelli contenuti nel database e permette o meno l'accesso al sistema,
 * - add()		-> Confronta tra loro le password inserite, poi controlla che che non ci siano email o username duplicati e infine aggiunge un nuovo utente al sistema,
 * - remove()	-> Rimuove un utente dal sistema,
 * - edit()		-> Modifica i permessi dell'utente selezionato,
 * - refresh()	-> Ricarica la lista utenti aggiornata.
 */

public class GestioneUser extends AbstractAction  implements Gestione{

	private static final long serialVersionUID = 3931651081791068737L;
	private JFrame frame;
	private Biblioteca data;
	private Object user;
	private Object pwd;
	private Object pwd_repeat;
	private Object mail;
	int editrole = 0;
	
	/* -- Costruttore Login -- */
	public GestioneUser (JFrame f, Biblioteca d, Object u, Object p){
		
		this.frame   	 = f;
		this.data    	 = d;
		this.user    	 = u;
		this.pwd      	 = p;
	}
	
	/* -- Costruttore Registrazione -- */
	public GestioneUser (JFrame f, Biblioteca d, Object u, Object p, Object rp, Object e){
		
		this.frame   	 = f;
		this.data    	 = d;
		this.user    	 = u;
		this.pwd      	 = p;
		this.pwd_repeat	 = rp;
		this.mail        = e;	
	}
	
	
	/* 	ACTIONPERFORMED(ActionEvent)
	 * 	Gestione delle azioni provenienti dalla View.
	 */
	public void actionPerformed(ActionEvent e) {
            switch(e.getActionCommand()){
            case "Login"         : 	login();
            						break;
            case "Registrati"    : 	RegistrazioneFrame f = new RegistrazioneFrame(data); 
            						f.setVisible(true);
            						break;
            case "Conferma"      : 	add();
            						break;
            case "Promuovi"      : 	if(((JList)user).getSelectedValue()==null){
            							JOptionPane.showMessageDialog(new JFrame(), "Seleziona utente","Attenzione!", JOptionPane.WARNING_MESSAGE);
            							break;
            						}
            						editrole = 1; edit();
            						break;
            case "Declassa"      : 	if(((JList)user).getSelectedValue()==null){
            							JOptionPane.showMessageDialog(new JFrame(), "Seleziona utente","Attenzione!", JOptionPane.WARNING_MESSAGE);
            							break;
            						}
            						editrole = -1;edit();
            						break;
            case "Elimina Utente": 	if(((JList)user).getSelectedValue()==null){
            							JOptionPane.showMessageDialog(new JFrame(), "Seleziona utente","Attenzione!", JOptionPane.WARNING_MESSAGE);
            							break;
            						}
            						remove();
            						break;
            default : break;
            }	  
	}
	
	
	/*  LOGIN()
	 * 	Prende i dati dalle TextField e li confronta con quelli estratti dal database.
	 */
	public void login(){
		    String username = ((JTextField)user).getText();
			char[] chars    = ((JPasswordField)pwd).getPassword();
			String password = new String(chars);
			boolean error   = true;
			
			LinkedList<User> users = data.getUserList();
			Iterator<User> itr = users.iterator();
			
			while(itr.hasNext()){
				User u = itr.next();

				if( u.getUsername().equals(username)){
					if( u.getPassword().equals(password)){
						error = false;
						MainFrame f = new MainFrame(data, u);
						f.setVisible(true);
						JOptionPane.showMessageDialog(new JFrame(), "   Bentornato " + username + "!");
						frame.dispose();
					} else {
						break;
					}
				} 	
			}
			if(error)JOptionPane.showMessageDialog(new JFrame(), "username e/o password errati","Login error", JOptionPane.OK_OPTION);
	}
	
	
	/*	ADD()
	 * 	Esegue un controllo delle password,
	 *  Prende i dati dalle TextField, estrae i dati dal DB e controlla se esistono altri utenti con le stesse username e stesse email.
	 *  Tramite il DAO inserisce i nuovi dati nel database.
	 */
	public void add() {
		String username = ((JTextField)user).getText();
		String password = ((JTextField)pwd).getText();
		String repeat_password = ((JTextField)pwd_repeat).getText();
		String email    = ((JTextField)mail).getText();
		boolean error   = false;
		
		/*  Controllo delle password  */
		if(!password.equals(repeat_password)){
			error = true;
			JOptionPane.showMessageDialog(new JFrame(), "Le password non coincidono, ripetere l'operazione.","Error", JOptionPane.OK_OPTION);
		}
		
		LinkedList<User> users = data.getUserList();
		Iterator<User> itr = users.iterator();
		
		while(itr.hasNext()){
			User u = itr.next();
			if(u.getUsername().equals(username)){
				error = true;
				JOptionPane.showMessageDialog(new JFrame(), "Username già esistente, sceglierne un altro","Attenzione!", JOptionPane.WARNING_MESSAGE);
				break;
			}
			if(u.getEmail().equals(email)){
				error = true;
				JOptionPane.showMessageDialog(new JFrame(), "Email già esistente, sceglierne un'altra","Attenzione!", JOptionPane.WARNING_MESSAGE);
				break;
			}
		}
		
		if(!error){
			User signup = new User(0, username, password, email, 0);
			UserDAO DAO = new UserDAO();
			DAO.add(signup.toArray());	
			data.getUserList().add(signup);
			JOptionPane.showMessageDialog(new JFrame(), "  Registrazione Completata!");
			frame.dispose();
		}		
	}

	
	/*  REMOVE()
	 * 	Rimozione di un utente dal sistema.
	 */
	public void remove() {
       String selected = (String) ((JList)user).getSelectedValue();
       Iterator<User> itr = data.getUserList().iterator();
       int count = 0;
       while(itr.hasNext()){
    	   count++;
    	   User next = itr.next();
    	   if(next.getUsername().equals(selected)){
    		   UserDAO DAO = new UserDAO();
    		   DAO.delete(next.getId());
    		   break;
    	   }
       }
       data.setUserList();
       DefaultListModel model = new DefaultListModel();
       for(User u : data.getUserList()){
    	   model.addElement(u.getUsername());
       }
       refresh();  
	}

	
	/*  EDIT()
	 *  Presa la lista degli utenti, esegue una modifica dell'attributo "role" del database.
	 */
	public void edit() {
		 String selected = (String) ((JList)user).getSelectedValue();
	       Iterator<User> itr = data.getUserList().iterator();
	       int count = 0;
	       while(itr.hasNext()){
	    	   count++;
	    	   User next = itr.next();
	    	   if(next.getUsername().equals(selected)){
	    		   int role = next.getRole();
	    		   next.setRole(role+editrole);
	    		   UserDAO DAO = new UserDAO();
	    		   DAO.edit(next.toArray());
	    		   if(editrole == 1) JOptionPane.showMessageDialog(new JFrame(), "Promosso!");
	    		   else JOptionPane.showMessageDialog(new JFrame(), "Declassato!");
	    		   break;
	    	   }
	       }
	       data.setUserList();
	}
	
	
	/*  REFRESH() 
	 *  Ricarica la lista utenti aggiornata in base al click del mouse.
	 */
	public void refresh(){
		ArrayList<String> listaUser = new ArrayList<String>();
		LinkedList<User> utenti = data.getUserList();
		Iterator<User> itr = utenti.iterator();
		while(itr.hasNext()){
			listaUser.add(itr.next().getUsername());
		}
		Collections.sort(listaUser);
		DefaultListModel model = new DefaultListModel();
		for(String u : listaUser){
			model.addElement(u);
		}
		((JList)user).setModel(model);
	}

}
/*  END Class  GestioneUser  */