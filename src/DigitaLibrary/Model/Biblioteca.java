package DigitaLibrary.Model;

import DigitaLibrary.DAO.*;

import java.util.Iterator;
import java.util.LinkedList;

/* MODEL --> Class BIBLIOTECA
 * Modello completo del sistema (opere, pagine, utenti e azioni).
 * - Costruttore
 * - Metodi set():
 * 		1. SetOperaList(),
 * 		2. SetUserList(),
 * 		3. SetPageList(),
 * 		4. SetActionList(),
 * - Metodi get().
 */

public class Biblioteca {
	
	private LinkedList<Opera> operaList   = new LinkedList<Opera>();
	private LinkedList<User> userList     = new LinkedList<User>();
	private LinkedList<Page> pageList     = new LinkedList<Page>();
	private LinkedList<Action> actionList = new LinkedList<Action>();
	
	/* BIBLIOTECA - Costruttore del sistema */
	public Biblioteca(){
		
		setOperaList();
		setUserList();
		setPageList();
		setActionList();			
	}
	
	/* SETOPERALIST() - Inizializzazione lista opere (attraverso il DAO) */
	public void setOperaList(){
		operaList.clear();
		OperaDAO ODAO = new OperaDAO();
		LinkedList<Integer> OIDs = ODAO.retrieveAll();
		Iterator<Integer> itr = OIDs.iterator();
		
		while(itr.hasNext())
			operaList.add(new Opera(itr.next()));
		
	}
	
	/* SETUSERLIST() - Inizializzazione lista utenti (attraverso il DAO) */
	public void setUserList(){
		userList.clear();
		UserDAO UDAO = new UserDAO();
		LinkedList<Integer> UIDs = UDAO.retrieveAll();
		Iterator<Integer> itr = UIDs.iterator();
		
		while(itr.hasNext())
			userList.add(new User(itr.next()));
		
	}
	
	/* SETPAGELIST() - Inizializzazione lista pagine (attraverso il DAO) */
	public void setPageList(){
		pageList.clear();
		PageDAO PDAO = new PageDAO();
		LinkedList<Integer> PIDs = PDAO.retrieveAll();
		Iterator<Integer> itr = PIDs.iterator();
		
		while(itr.hasNext())
			pageList.add(new Page(itr.next()));
		
	}
	
	/* SETACTIONLIST() - Inizializzazione lista azioni (attraverso il DAO) */
	public void setActionList(){
		actionList.clear();
		ActionDAO ADAO = new ActionDAO();
		LinkedList<Integer> AIDs = ADAO.retrieveAll();
		Iterator<Integer> itr = AIDs.iterator();
		
		while(itr.hasNext())
			actionList.add(new Action(itr.next()));
		
	}
	
	/* BIBLIOTECA - Metodi get() */
	public LinkedList<Opera> getOperaList(){
		return this.operaList;
	}
	
	public LinkedList<User> getUserList(){
		return this.userList;
	}
	
	public LinkedList<Page> getPageList(){
		return this.pageList;
	}
	
	public LinkedList<Action> getActionList(){
		return this.actionList;
	}
}
/*  END Class  Biblioteca  */