package DigitaLibrary.Controller;

import java.awt.event.ActionEvent;
import java.util.Iterator;
import java.util.LinkedList;

import javax.swing.AbstractAction;
import javax.swing.table.DefaultTableModel;

import DigitaLibrary.Model.Action;
import DigitaLibrary.Model.Biblioteca;
import DigitaLibrary.Model.User;
import DigitaLibrary.Model.Opera;
import DigitaLibrary.View.CronologiaFrame;
import DigitaLibrary.View.HistoryFrame;


/*
 * CONTROLLER --> GESTIONE ACTION
 * La classe GestioneAction ci permette di gestire gli eventi generati dalla View, relativi a tutte le omponenti del nostro sistema.
 * - Costruttore,
 * - actionPerformed(ActionEvent) -> gestione eventi della View,
 * - retrieve() 			-> completamento della tabella di Cronologia Operazioni,
 * - retrieveAll()			-> completamento della tabella dello storico operazioni,
 * - add()/remove()/edit()	-> Non implementati per questa classe.
 */


public class GestioneAction extends AbstractAction implements Gestione {
	
	private static final long serialVersionUID = 3939558628081151887L;
	private Biblioteca data;
	private Object object_1;
	private Object object_2;
	
	/* -- Costruttore -- */
	public GestioneAction(Biblioteca data, Object o, Object o2){
		this.data 		= data;
		this.object_1   = o;
		this.object_2   = o2;
	}
	
	
	/* 	ACTIONPERFORMED(ActionEvent)
	 * 	Gestione della cronologia operazioni effettuate nel nostro sistema.
	 */
	public void actionPerformed(ActionEvent e) {
		
		switch(e.getActionCommand()){
			case "Cronologia Operazioni": 	CronologiaFrame cf = new CronologiaFrame(data, ((User)object_1));
											cf.setVisible(true);
											break;
											
			case "Storico Operazioni":		HistoryFrame h = new HistoryFrame(data);
											h.setVisible(true);
											break;
			default:	break;
		}
	}

	
	/* 	RETRIEVE()
	 * 	Ottenere tutte le informazioni necessarie al completamento della tabella di "Cronologia Operazioni".
	 */
	public void retrieve() {
		User user    = ((User)object_1);
	    DefaultTableModel table = ((DefaultTableModel)object_2);
		LinkedList<Action> actionList = data.getActionList();
		Iterator<Action> itr = actionList.iterator();
		while(itr.hasNext()){
			Action next = itr.next();
			if(next.getUserID() == user.getId()){
				String type="";
				switch(next.getType()){
					case 0  : type="Acquisizione"; break;
					case 1  : type="Trascrizione"; break;
					default : break;
				}
				String status="";
				switch(next.getStatus()){
					case 0  : status="Da Revisionare"; break;
					case 1  : status="Accettato"; break;
					case 2  : status="Rifiutato";break;
					default : break;
				}
				String report=""+next.getAction_report();
				table.addRow(new Object[]{new User(next.getUserID()).getUsername(), new Opera(next.getOperaID()).getTitle(), next.getPage(), type, status, report});
			}
		}
	}
	
	
	/* 	RETRIEVEALL()
	 * 	Ottenere tutte le informazioni necessarie al completamento della tabella di "Cronologia Operazioni".
	 */
	public void retrieveAll(){
		
		LinkedList<Action> actionList = data.getActionList();
	    DefaultTableModel table = ((DefaultTableModel)object_2);
		
		Iterator<Action> itr_action = actionList.iterator();

			while(itr_action.hasNext()){
				Action next_action = itr_action.next();				
				String type	= "";
				String status = "";
				
						switch(next_action.getType()){
							case 0  : type="Acquisizione"; break;
							case 1  : type="Trascrizione"; break;
							default : break;
						}
			
						switch(next_action.getStatus()){
							case 0  : status="Da Revisionare"; break;
							case 1  : status="Accettato"; break;
							case 2  : status="Rifiutato";break;
							default : break;
						}
					
						String report=""+next_action.getAction_report();
						table.addRow(new Object[]{new User(next_action.getUserID()).getUsername(), new Opera(next_action.getOperaID()).getTitle(), next_action.getPage(), type, status, report});														
			}
	}
	
	/* ADD() - REMOVE() - EDIT() 
	 * Metodi NON implementati per questa classe.
	 */
	public void add() {
	}
	public void remove() {
	}
	public void edit() {
	}
	
}
