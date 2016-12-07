package DigitaLibrary.Controller;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.util.Iterator;
import java.util.LinkedList;

import javax.swing.AbstractAction;
import javax.swing.ButtonGroup;
import javax.swing.DefaultListModel;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;

import DigitaLibrary.DAO.FTPUtility;
import DigitaLibrary.DAO.OperaDAO;
import DigitaLibrary.DAO.PageDAO;
import DigitaLibrary.Model.Biblioteca;
import DigitaLibrary.Model.Opera;
import DigitaLibrary.Model.Page;
import DigitaLibrary.Model.User;
import DigitaLibrary.View.NuovaOperaFrame;
import DigitaLibrary.View.GestisciUtenzaPagineFrame;
import DigitaLibrary.View.RevisioneListFrame;
import DigitaLibrary.View.TrascriviListFrame;
import DigitaLibrary.View.CaricaFrame;
import DigitaLibrary.View.VisualizzaFrame;

import java.io.StringWriter;
import java.io.File;
import java.io.StringReader;

/*
 * CONTROLLER --> GESTIONE OPERA
 * La classe GestioneOpera implementa tutte le funzioni necessarie per eseguire operazione sulle 
 * opere nuove oppure già presenti nel sistema. 
 * - Costruttore per il Login e costruttore per la Registrazione,
 * - actionPerformed(ActionEvent) -> gestione eventi della View,
 * - add()		-> Aggiungi una nuova opera al sistema (nome, autore e numero di pagine),
 * - remove()	-> Rimuove un'opera dal sistema,
 * - edit()		-> Modifica l'opera da pubblica a privata e viceversa,
 * - view(int, int)	-> Apre una finestra per visualizzare l'intera opera, pagina per pagina,
 * - acquisisciImg()	-> Selezionata un'opera, carica una nuova immagine per essa,
 * - refreshList()		-> Ricarica la lista opere aggiornate,
 * - search()		-> Cerca l'opera desiderata in base al titolo o all'autore.
 */

public class GestioneOpera extends AbstractAction implements Gestione {


	private static final long serialVersionUID = -5796011512728364456L;
	private JFrame frame;
	private Biblioteca data;
	private DefaultListModel<String> list;
	private Component c;
	private Object object_1;	
	private Object object_2;
	
	/* -- Costruttore -- */
	public GestioneOpera( JFrame f, Biblioteca data, DefaultListModel<String> l, Component c, Object o1, Object o2){
		this.frame= f;
		this.data = data;
		this.list = l;
		this.c    = c;
		this.object_1   = o1;
		this.object_2   = o2;
	}
	
	
	/* 	ACTIONPERFORMED(ActionEvent)
	 * 	Gestione delle azioni provenienti dalla View.
	 */
	public void actionPerformed(ActionEvent e) {
		switch(e.getActionCommand()){
		
		  case "Cerca"	:	if(((JTextField)c).getText().equals("")) 
			  					refreshList();
		      				else{
		      					search(((ButtonGroup)object_1).getElements().nextElement().isSelected());	   
		      				}
		      				break;
		      
		  case "Acquisisci" :	try{
									acquisisciImg();
								}catch (Exception e1){
									e1.printStackTrace();
								} 
		  						break;
			
		  case "Trascrivi"  : 	TrascriviListFrame ft = new TrascriviListFrame(data, ((User)object_1)); 
		  						ft.setVisible(true);
		  						break;
			
		  case "Revisiona Acquisizioni" :	RevisioneListFrame fimg = new RevisioneListFrame(data,"img");
			  								fimg.setVisible(true);
			  								break;
			  
		  case "Revisiona Trascrizioni" :	RevisioneListFrame ftext = new RevisioneListFrame(data,"text");
			  								ftext.setVisible(true);
			  								break;
		  case "Go"             : 	try{
										view(Integer.parseInt(((JTextField)c).getText()),1);	
									}catch (Exception e1){
										e1.printStackTrace();
									} 
									break;
									
		  /* Opzioni Admin  */	  								
		  case "Visualizza"      : 	try{
			  							view(1,0);			
			  						}catch (Exception e1){
			  							e1.printStackTrace();
			  						} 
		  							break;		  									  
		  							
		  case "Elimina Opera"   : 	remove(); 
		  							break;
		  							
		  case "Nuova Opera"     : 	NuovaOperaFrame f = new NuovaOperaFrame(data, list); 
		  							f.setVisible(true); 
		  							break;
		  							
		  case "Crea"            : 	add(); 
		  							break;
		  							
		  case "Pubblica/Privata": 	edit(); 
		  							break;
		  							
		  case "Gestisci Pagine" : 	if(((JList)c).getSelectedValue()==null){
			  							JOptionPane.showMessageDialog(new JFrame(), "Seleziona l'opera", "Attenzione!", JOptionPane.WARNING_MESSAGE);
			  						}
		  							GestisciUtenzaPagineFrame fp = new GestisciUtenzaPagineFrame(data, "page",(String)((JList)c).getSelectedValue()); 
		  							fp.setVisible(true); 
		  							break;
		  							
		  case "Gestisci Utenti" : 	GestisciUtenzaPagineFrame fu = new GestisciUtenzaPagineFrame(data, "user", null); 
		  							fu.setVisible(true); 
		  							break;
			  
		  default      :	break;
		}
	}
	
	
	/*  ADD()
	 * 	Aggiungi una nuova opera a DigitaLibrary.
	 */
	public void add() {
		boolean error = false;
		if(((JTextField)c).getText().equals("") || ((JTextField)object_1).getText().equals("") || ((JTextField)object_2).getText().equals("") ){
			JOptionPane.showMessageDialog(new JFrame(), "Tutti i campi sono obbligatori", "Attenzione!", JOptionPane.WARNING_MESSAGE);
			error = true;
		}
		String titolo = ((JTextField)c).getText();
		String autore = ((JTextField)object_1).getText();
		int   npagine = Integer.parseInt(((JTextField)object_2).getText());
		
		LinkedList<Opera> operaList = data.getOperaList();
		Iterator<Opera> itr = operaList.iterator();
		while(itr.hasNext()){
			if(itr.next().getTitle().equals(titolo)){
				JOptionPane.showMessageDialog(new JFrame(), "Opera già esistente", "Error", JOptionPane.OK_OPTION);
				error = true;
			}
		}
		
		if(!error){
			
			titolo = titolo.substring(0, 1).toUpperCase() + titolo.substring(1, titolo.length());
			autore = autore.substring(0, 1).toUpperCase() + autore.substring(1, autore.length());
			
			Opera o = new Opera(0, titolo, autore, npagine, false);
			OperaDAO DAO = new OperaDAO();
			DAO.add(o.toArray());
			data.setOperaList();
			FTPUtility ftp = new FTPUtility();
			try {
				ftp.connect();
				ftp.createDir(titolo);
				ftp.disconnect();
			} catch (Exception e) {
				e.printStackTrace();
			}
			refreshList();
			frame.dispose();
		}	
	}
	

	/*  REMOVE()
	 *  Rimuovi opera dal sistema.  
	 */
	public void remove() {
		boolean error = false;
		if((((JList)c).getSelectedValue()) == null) {
        	error = true;
        	JOptionPane.showMessageDialog(new JFrame(), "Selezionare l'opera da eliminare", "Attenzione!", JOptionPane.WARNING_MESSAGE);
        }
		
		if(!error){
			int id = 0;
			int dialogButton = JOptionPane.YES_NO_OPTION;
			int result = JOptionPane.showConfirmDialog (null, "Sei sicuro di voler eliminare l'opera selezionata?","Attenzione!",dialogButton);
			if(result == 0){
				String selected = (String)((JList)c).getSelectedValue();
				LinkedList<Opera> operaList = data.getOperaList();
				Iterator<Opera> itr = operaList.iterator();
				while(itr.hasNext()){
					Opera next = itr.next();
					if(next.getTitle().equals(selected)){
						id = next.getId();
						OperaDAO DAO = new OperaDAO();
						DAO.delete(id);
						break;
					}
				}
				LinkedList<Page> pageList = data.getPageList();
				
				Iterator<Page> itr2 = pageList.iterator();
				while(itr2.hasNext()){
					Page next = itr2.next();
					if(next.getOperaID() == id){
						PageDAO DAOP = new PageDAO();
						DAOP.delete(next.getID());
					}
				}
				data.setOperaList();
				data.setPageList();
				refreshList();
				FTPUtility ftp = new FTPUtility();
				try {
					ftp.connect();
					ftp.removeDir(selected);
					ftp.disconnect();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	
	/* EDIT()
	 * Modifica dell'opera da Pubblica a Privata e viceversa.  
	 */
	public void edit() {
		boolean error = false;
		LinkedList<Opera> operaList = data.getOperaList();
		Iterator<Opera> itr = operaList.iterator();	
        if((((JList)c).getSelectedValue()) == null) {
        	error = true;
        	JOptionPane.showMessageDialog(new JFrame(), "Selezionare l'opera", "Attenzione!", JOptionPane.WARNING_MESSAGE);
        }
        if(!error){
			while(itr.hasNext()){
				Opera next = itr.next();
				
				if(next.getTitle().equals(((JList)c).getSelectedValue())){
					if(next.getStatus() == false) {
						next.setStatus(true);
						JOptionPane.showMessageDialog(frame, "L'Opera è ora Pubblica");
						OperaDAO DAO = new OperaDAO();
						DAO.edit(next.toArray());
					}else {
						next.setStatus(false);
						OperaDAO DAO = new OperaDAO();
						DAO.edit(next.toArray());
						JOptionPane.showMessageDialog(frame, "L'Opera è ora Privata");
					}
					break;
				}
			}	
        }
	}
	
	
	/*	VIEW(int, int)
	 *  Apre una nuova finestra per visualizzare l'intera opera, pagina per pagina.  
	 */
	public void view(int p, int close) throws Exception {
		boolean error = false;
		LinkedList<Opera> operaList = data.getOperaList();
		Iterator<Opera> itr = operaList.iterator();
		Opera selected = null;
		int oid = 0;
		String titolo;
		
		if( close == 0){
			if((((JList)c).getSelectedValue()) == null) {
				error = true;
				JOptionPane.showMessageDialog(new JFrame(), "Per favore, seleziona l'opera", "Attenzione!", JOptionPane.WARNING_MESSAGE);
			}
		}
		
		if(!error){
				
			if(close == 0) {
				while(itr.hasNext()){
					Opera next = itr.next();
					if(next.getTitle().equals(((JList)c).getSelectedValue())) selected= next;
				}
				oid = selected.getId();
				titolo = selected.getTitle();
			} else {
				titolo = (String)object_1;
				oid= (int)object_2;
			}
			boolean found = false;
			String xml = "";
			String html = "";
			Iterator<Page> itrp = data.getPageList().iterator();
			while(itrp.hasNext()){
				Page next = itrp.next();
				if(next.getOperaID() == oid && next.getNumber() == p){
					if(next.getStatus() > 0) 
						found = true;
				
					if(next.getStatus() == 3){
						xml = next.getText();
						if(xml.length() > 0 ){
							TransformerFactory fact = new net.sf.saxon.TransformerFactoryImpl();
							Source xslt = new StreamSource(new File("Editor/Text_Conversion/html/html.xsl"));
							Transformer transformer = fact.newTransformer(xslt);
		        
							StringWriter outWriter = new StringWriter();
							StreamResult result = new StreamResult( outWriter );
							Source text = new StreamSource(new StringReader(xml));
							transformer.transform(text, result);
							html = outWriter.getBuffer().toString();
						}
					}
				}
			}
			if(found){
				VisualizzaFrame f = new VisualizzaFrame( data, titolo, p, html, oid);
				if(close == 1) 
					this.frame.dispose();
			}else{
				JOptionPane.showMessageDialog(new JFrame(), "Pagine non esistenti", "Error", JOptionPane.OK_OPTION);
			}	
		}
	}
	
	
	/*  ACQUISISCIIMG()
	 * 	Carica una nuova immagine per un'opera.
	 */
	public void acquisisciImg() throws Exception{
         
		boolean error = false;
		LinkedList<Opera> operaList = data.getOperaList();
		Iterator<Opera> itr = operaList.iterator();
		Opera selected = null;	
       
		if((((JList)c).getSelectedValue()) == null) {
        	error = true;
        	JOptionPane.showMessageDialog(new JFrame(), "Selezionare l'opera", "Attenzione!", JOptionPane.WARNING_MESSAGE);
        }
		
		if(!error){
			while(itr.hasNext()){
				Opera next = itr.next();
				if(next.getTitle().equals(((JList)c).getSelectedValue())) 
					selected= next;
			}	
		
			CaricaFrame f = new CaricaFrame( data, selected, ((User)object_1));
			f.setVisible(true);
		}
	}
	
	
	/*  REFRESHLIST()
	 * 	Ricarica lista opere.
	 */
	public void refreshList(){
		list.removeAllElements();
		LinkedList<Opera> operaList = data.getOperaList();
		Iterator<Opera> itr = operaList.iterator();
		while(itr.hasNext()){
			list.addElement(itr.next().getTitle());
		}	
	}
	
	
	/*  SEARCH()
	 *  Cerca opera in base al titolo o al nome dell'autore.
	 */
	public void search(boolean filter){
		String search = ((JTextField)c).getText();
		list.removeAllElements();
		LinkedList<Opera> operaList = data.getOperaList();
		Iterator<Opera> itr = operaList.iterator();
		
		if(filter){
			while(itr.hasNext()){	//cerca in base al titolo
				String operaTitle= itr.next().getTitle();
				for(int i=0; i<search.length(); i++){
					if(Character.toLowerCase(search.charAt(i)) == Character.toLowerCase(operaTitle.charAt(i))){
						if( i == search.length()-1 ) 
							list.addElement(operaTitle);
						continue;
					}else{
						break;
					}
				}
			}
		} else {
			while(itr.hasNext()){	//cerca in base all'autore
				Opera o = itr.next();
				String operaAuthor= o.getAuthor();
				for(int i=0; i<search.length(); i++){
					if(Character.toLowerCase(search.charAt(i)) == Character.toLowerCase(operaAuthor.charAt(i))){
						if( i == search.length()-1 ) 
							list.addElement(o.getTitle());
						continue;
					}else{
						break;
					}
				}
			 }
		}
	}
	
}
/*  END class GESTIONEOPERA  */
