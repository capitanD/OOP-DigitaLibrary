package DigitaLibrary.controller;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.ArrayList;

import DigitaLibrary.model.Action;
import DigitaLibrary.model.Biblioteca;
import DigitaLibrary.model.User;



/*
 * CONTROLLER --> GESTIONE ACTION
 * La classe GestioneAction ci permette di gestire gli eventi generati dalla View, relativi a tutte le omponenti del nostro sistema.
 *
 */


public class GestioneAction implements Gestione {
	
	private Biblioteca data;
	private Object object_1;
	
	
	/* -- Costruttore -- */
	public GestioneAction(Biblioteca data, Object o1){
		this.data 		= data;
		this.object_1   = o1;

	}
	
	
	
	/* 	RETRIEVE()
	 * 	Ottenere tutte le informazioni necessarie al completamento della tabella di "Cronologia Operazioni".
	 */
	public LinkedList<ArrayList<String>> retrieve() {
		User user    = ((User)object_1);
	    LinkedList<ArrayList<String>> table = new LinkedList<ArrayList<String>>();
	    
		LinkedList<Action> actionList = data.getActionList();
		Iterator<Action> itr = actionList.iterator();
		while(itr.hasNext()){
			Action next = itr.next();
			ArrayList<String> element = new ArrayList<String>();
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
				element.add(Integer.toString(next.getUserID()));	//user ID
				element.add(Integer.toString(next.getOperaID()));	//opera ID
				element.add(Integer.toString(next.getPage()));		//page
				element.add(type);
				element.add(status);
				element.add(report);
				table.add(element);
			}
		}
		return table;
	}
	
	
	
	/* 	RETRIEVEALL()
	 * 	Ottenere tutte le informazioni necessarie al completamento della tabella di "Cronologia Operazioni".
	 */
	public LinkedList<ArrayList<String>> retrieveAll(){
		
		LinkedList<Action> actionList = data.getActionList();
		LinkedList<ArrayList<String>> table = new LinkedList<ArrayList<String>>();
		Iterator<Action> itr_action = actionList.iterator();

			while(itr_action.hasNext()){
				Action nextAction = itr_action.next();				
				String type	= "";
				String status = "";
				ArrayList<String> elementList = new ArrayList<String>();
				
						switch(nextAction.getType()){
							case 0  : type="Acquisizione"; break;
							case 1  : type="Trascrizione"; break;
							default : break;
						}
			
						switch(nextAction.getStatus()){
							case 0  : status="Da Revisionare"; break;
							case 1  : status="Accettato"; break;
							case 2  : status="Rifiutato";break;
							default : break;
						}
					
						String report=""+nextAction.getAction_report();
						elementList.add(Integer.toString(nextAction.getUserID()));	//user ID
						elementList.add(Integer.toString(nextAction.getOperaID()));	//opera ID
						elementList.add(Integer.toString(nextAction.getPage()));		//page
						elementList.add(type);
						elementList.add(status);
						elementList.add(report);
						table.add(elementList);
			}
			return table;
	}
	
	/* ADD() - REMOVE() - EDIT() 
	 * Metodi NON implementati per questa classe.
	 */
	public boolean add() {
		return true;
	}
	public boolean remove() {
		return true;
	}
	public boolean edit() {
		return true;
	}
	
}
