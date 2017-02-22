package DigitaLibrary.controller;

import java.util.Iterator;
import java.util.LinkedList;

import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;

import DigitaLibrary.DAO.FTPUtility;
import DigitaLibrary.DAO.OperaDAO;
import DigitaLibrary.DAO.PageDAO;
import DigitaLibrary.model.Biblioteca;
import DigitaLibrary.model.Opera;
import DigitaLibrary.model.Page;

import java.io.StringWriter;
import java.io.File;
import java.io.StringReader;

/*
 * CONTROLLER --> GESTIONE OPERA
 * La classe GestioneOpera implementa tutte le funzioni necessarie per eseguire operazione sulle 
 * opere nuove oppure già presenti nel sistema. 
 *
 */

public class GestioneOpera implements Gestione {
	
	private Biblioteca data;
	private Object object_1;
	private Object object_2;
	private Object object_3;
	
	
	/* -- Costruttore -- */
	public GestioneOpera(Biblioteca d, Object o1, Object o2, Object o3){
		this.data = d;
		this.object_1 = o1;
		this.object_2 = o2;
		this.object_3 = o3;
	}
	

	
	/*  ADD()
	 * 	Aggiungi una nuova opera a DigitaLibrary.
	 */
	public boolean add() {
		
		boolean error = false;
		String titolo = (String)object_1;
		String autore = (String)object_2;
		int    npagine = Integer.parseInt( (String)object_3);
		
		LinkedList<Opera> operaList = data.getOperaList();
		Iterator<Opera> itr = operaList.iterator();
		while(itr.hasNext()){
			if(itr.next().getTitle().equals(titolo))
				error = true;
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
		}	
		return error;
	}

	

	/*  REMOVE()
	 *  Rimuovi opera dal sistema.  
	 */
	public boolean remove() {
		
		int result = (int)object_1;
		String selected = (String)object_2;
		int id = 0;	
		
		if(result == 0){
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
		return true;
	}
	
	
	/* EDIT()
	 * Modifica dell'opera da Pubblica a Privata e viceversa.  
	 */
	public boolean edit() {
		
		boolean stats = false;
		String select = (String)object_1;
		LinkedList<Opera> operaList = data.getOperaList();
		Iterator<Opera> itr = operaList.iterator();	
        
		while(itr.hasNext()){
			Opera next = itr.next();
			
			if(next.getTitle().equals(select)){
				if(next.getStatus() == false) {
					next.setStatus(true);
					stats = true;
					OperaDAO DAO = new OperaDAO();
					DAO.edit(next.toArray());
				}else {
					next.setStatus(false);
					OperaDAO DAO = new OperaDAO();
					DAO.edit(next.toArray());
					stats = false;
				}
				break;
			}
		}	  
		return stats;
	}
	
	
	/* 	HASNEXTPAGE()
	 * 	Controllo se la pagina inserita dall'utente esiste ed e visibilie.
	 */
	public boolean hasNextPage(){
		
		LinkedList<Page> pages = data.getPageList(); 
		Iterator<Page> itr_1 = pages.iterator();
		int count = 0;
		boolean error = false;
		
		while(itr_1.hasNext()){
			Page next_1 = itr_1.next();
			
			if(next_1.getOperaID() == (int)object_2 && next_1.getStatus() > 0){
				count ++;
			}			
		}
		if(count >= (int)object_3)
			error = false;
		else
			error = true;
		
		return error;
	}
	
	
	/*	VIEW(int, int)
	 *  Apre una nuova finestra per visualizzare l'intera opera, pagina per pagina.  
	 */
	public LinkedList<String> view(int page, int close) throws Exception {
		
		LinkedList<String> operaShow = new LinkedList<String>();
		LinkedList<Opera> operaList = data.getOperaList();
		Iterator<Opera> itr = operaList.iterator();
		Opera selectedOp = null;
		int oid = 0;
		String titolo;
		String select = (String)object_1;
		
				
		if(close == 0) {
			while(itr.hasNext()){
				Opera next = itr.next();
				if(next.getTitle().equals(select)) 
					selectedOp = next;
			}
			oid = selectedOp.getId();
			titolo = selectedOp.getTitle();
		} else {
			titolo = (String)object_1;
			oid = (int)object_2;
		}
		
		boolean found = false;
		String xml = "";
		String html = "";
		Iterator<Page> itrPage = data.getPageList().iterator();
		while(itrPage.hasNext()){
			Page next = itrPage.next();
			if(next.getOperaID() == oid && next.getNumber() == page){
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
			operaShow.add(html);
			operaShow.add(titolo);
			operaShow.add(Integer.toString(oid));
			operaShow.add(Integer.toString(page));
			if(close == 1){
				operaShow.addLast("close");
			}
		}else{
			return operaShow;
		}
				
		return operaShow;
	}

	
	/*  ACQUISISCIIMG()
	 * 	Carica una nuova immagine per un'opera.
	 */
	public Opera acquisisciImg() throws Exception{
         
		LinkedList<Opera> operaList = data.getOperaList();
		Iterator<Opera> itr = operaList.iterator();
		Opera selected = null;	
       
		while(itr.hasNext()){
			Opera next = itr.next();
			if(next.getTitle().equals((String)object_1)) 
				selected= next;
		}	
		return selected;
	}
	
	
	/*  REFRESHLIST()
	 * 	Ricarica lista opere.
	 */
	public LinkedList<String> refreshList(){
		LinkedList<String> newlist = new LinkedList<String>();
		LinkedList<Opera> operaList = data.getOperaList();
		Iterator<Opera> itr = operaList.iterator();
		while(itr.hasNext()){
			newlist.add(itr.next().getTitle());
		}	
		return newlist;
	}
	
	
	/*  SEARCH()
	 *  Cerca opera in base al titolo o al nome dell'autore.
	 */
	public LinkedList<String> search(boolean filter){
		
		LinkedList<String> filteredList = new LinkedList<String>();
		String search = (String)object_1;
		LinkedList<Opera> operaList = data.getOperaList();
		Iterator<Opera> itr = operaList.iterator();
		
		if(filter){
			while(itr.hasNext()){	//cerca in base al titolo
				String operaTitle= itr.next().getTitle();
				for(int i=0; i<search.length(); i++){
					if(Character.toLowerCase(search.charAt(i)) == Character.toLowerCase(operaTitle.charAt(i))){
						if( i == search.length()-1 ) 
							filteredList.add(operaTitle);
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
							filteredList.add(o.getTitle());
						continue;
					}else{
						break;
					}
				}
			 }
		}
		return filteredList;
	}
	
}
/*  END class GESTIONEOPERA  */
