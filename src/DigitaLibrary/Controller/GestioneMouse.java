package DigitaLibrary.Controller;

import java.awt.event.*;
import java.util.Iterator;
import java.util.LinkedList;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JList;

import DigitaLibrary.Model.Biblioteca;
import DigitaLibrary.Model.Opera;
import DigitaLibrary.Model.Page;
import DigitaLibrary.Model.User;

/*  CONTROLLER --> GESTIONE MOUSE
 * 	Gestione degli eventi generati dal click del mouse.
 * 	- pageList()	-> Lista pagine con i relativi status e report,
 *  - userList()	-> Lista del ruole che l'utente selezionato ha all'interno del sistema.
 *  - mainList()	-> Lista informazioni relative all'opera selezionata.
 */

public class GestioneMouse implements MouseListener {
	
	private Biblioteca data;
	private JList list;
	private String type;
	private JLabel label_1;
	private JLabel label_2;
	private JButton btn;
	private Object object_1;
	
	/* -- Costruttore -- */
	public GestioneMouse(Biblioteca data, String t, JList list, JLabel l, JLabel l2, JButton b, Object o){
		this.data 		= data;
		this.type 		= t;
		this.list 		= list;
		this.label_1    = l;
		this.label_2   	= l2;
		this.btn		= b;
		this.object_1   = o;
	}

	
	/* 	MOUSEPRESSED(MouseEvent)
	 * 	Gestione delle azioni provenienti dal click del mouse nella View.
	 */
	public void mousePressed(MouseEvent e) {
		
		switch(type){
		case "MainFrame" : 	mainList();
							break;
							
		case "UserFrame" : 	userList();
							break;
							
		case "PageFrame" : 	pageList();
							break;
							
		default          : 	break;
		}
	}
	
	/*  PAGELIST()
	 *  Mostra la lista delle pagine disponibili di un opera e il loro relativo stato.
	 */
	public void pageList(){
		int selected = (int) list.getSelectedValue();
		Iterator<Page> itr = data.getPageList().iterator();
		while(itr.hasNext()){
			Page next = itr.next();
			if(next.getNumber() == selected){
				switch(next.getStatus()){
			   		case 0 : label_1.setText("Immagine in attesa");break;
			   		case 1 : label_1.setText("Immagine convalidata");break;
			   		case 2 : label_1.setText("Testo in attesa");break;
			   		case 3 : label_1.setText("Testo convalidato");break;
			   		default: break;
				}
				label_2.setText(next.getReport());
			}
		}
	}
	
	/*  USERLIST()
	 *  Mostra la lista degli utenti registrati ed il loro relativo ruolo.
	 */
	public void userList(){
		   String selected = (String) list.getSelectedValue();
	       Iterator<User> itr = data.getUserList().iterator();
	       
	       while(itr.hasNext()){
	    	   User next = itr.next();
	    	   if(next.getUsername().equals(selected)){
	    		   switch(next.getRole()){
	    		   case 0: label_1.setText("Utente Base");break;
	    		   case 1: label_1.setText("Utente Avanzato");break;
	    		   case 2: label_1.setText("Acquisitore");break;
	    		   case 3: label_1.setText("Revisore Img");break;
	    		   case 4: label_1.setText("Trascrittore");break;
	    		   case 5: label_1.setText("Revisore Txt");break;
	    		   case 6: label_1.setText("Admin");break;
	    		   default : break;
	    		   }
	    	   }
	       }
	}
	
	/*  MAINLIST()
	 *  Mostra la descrizione di un'opera nella finestra principale di ogni opera (titolo, autore, ..).
	 */
	public void mainList(){
		String titolo = (String) list.getSelectedValue();
		LinkedList<Opera> operaList = data.getOperaList();
		Iterator<Opera> itr = operaList.iterator();
		
		while(itr.hasNext()){
			String status = "Pubblica";
			Opera op = itr.next();
			if(op.getTitle().equals(titolo)){
				label_1.setText(op.getAuthor());
				label_2.setText(Integer.toString(op.getPages()));
				if(!op.getStatus()) status = "Privata";
				if(this.object_1 != null)((JLabel)this.object_1).setText(status);
				break;
			}
		}
		btn.setEnabled(true);
	}
	
	public void mouseClicked(MouseEvent e) {}
	public void mouseEntered(MouseEvent e) {}
	public void mouseExited(MouseEvent e) {}
	public void mouseReleased(MouseEvent e) {}

}
/*  END Class  GestioneMouse  */