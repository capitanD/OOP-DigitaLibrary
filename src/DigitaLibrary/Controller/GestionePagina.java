package DigitaLibrary.controller;

import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;

import java.io.File;
import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;

import java.util.LinkedList;
import java.util.Iterator;
import java.util.Date;

import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;

import com.sun.org.apache.xerces.internal.impl.dv.util.*;
import java.text.*;
import java.lang.Runtime;

import DigitaLibrary.DAO.*;
import DigitaLibrary.model.Action;
import DigitaLibrary.model.Biblioteca;
import DigitaLibrary.model.Opera;
import DigitaLibrary.model.Page;
import DigitaLibrary.model.User;


/*
 * CONTROLLER --> GESTIONE PAGINA
 * La classe GestionePagina permette di effettuare diverse operazioni su una qualsiasi pagina di una qualunque opera.
 *
 */


public class GestionePagina implements Gestione  {
		
	private Biblioteca 		data;
	private BufferedImage 	image;
	private int 	zoom = 1;
	private Object 	object_1;
	private Object 	object_2;
	private Object 	object_3;
	private Object	object_4;


	
	/* -- Costruttori -- */
	public GestionePagina(Biblioteca data, Object o1, Object o2, Object o3, Object o4){
		this.data  		= data;
		this.object_1   = o1;
		this.object_2   = o2;
		this.object_3   = o3;
		this.object_4	= o4;
	}
	
	public GestionePagina(){ }
	


	/*  ADD()
     * 	Aggiunta di una nuova pagina.
     * 	Dovranno essere specificate l'opera di appartenenza e il numero della pagina.
     */
	public boolean add() {
		
		boolean flag = true;
		
		if(flag){
			int pagenum = Integer.parseInt( (String)object_2);
			Opera toAdd = ((Opera)object_4);
			
			if(pagenum > toAdd.getPages() )
				flag = false;
			
			LinkedList<Page> pages = data.getPageList();
			Iterator<Page> itr = pages.iterator();
			while(itr.hasNext()){
				Page next = itr.next();
				if(next.getNumber() == pagenum && next.getOperaID() == toAdd.getId() )
					flag = false;		
			}
			
			if(flag){
				FTPUtility ftp = new FTPUtility();
				
				//connessione al database per il trasferimento file.
				try {
					ftp.connect();
					ftp.uploadFile((String)object_1, toAdd.getTitle(), pagenum+".jpg");
					ftp.disconnect();
				} catch (Exception e) {
					e.printStackTrace();
				}
				Date now = new Date();
				SimpleDateFormat ft =  new SimpleDateFormat ("dd-MM-yy");
				
				Page newpage = new Page(0, toAdd.getId(), pagenum, "" , toAdd.getTitle()+"/"+pagenum+".jpg", 0 ,"["+ft.format(now) +"] "+"[No report]");
				data.getPageList().add(newpage);
				
				PageDAO DAOpage = new PageDAO();
				DAOpage.add(newpage.toArray());
				
				Action newaction = new Action(0, ((User)object_3).getId(), ((Opera)object_4).getId(), pagenum, 0, 0, "["+ft.format(now) +"] "+"[No report]");
				data.getActionList().add(newaction);
				ActionDAO DAOaction = new ActionDAO();
				DAOaction.add(newaction.toArray());
			}
		}
		return flag;
	}

	
	/*  REMOVE()
	 * 	Rimozione di una pagina di un'opera dal sistema (operazione concessa solo all'admin).
	 */
	public boolean remove() {
		
		boolean error = false;

		Iterator<Page> itr = data.getPageList().iterator();
		while(itr.hasNext()){
			Page next = itr.next();
			if(next.getNumber() == (int)object_1){
				PageDAO DAO = new PageDAO();
				DAO.delete(next.getID());
				FTPUtility ftp = new FTPUtility();
				try {
					ftp.connect();
					ftp.deleteFile(next.getImage());
					ftp.disconnect();
				} catch (Exception e) {
					e.printStackTrace();
				}
				data.setPageList();
				break;
			}else
				error = true;
		}
		return error;
	}

	
	/*  EDIT()
	 *  Elimina trascrizione. 
	 */
	public boolean edit() {
		
		boolean error = false;
		Iterator<Page> itr = data.getPageList().iterator();
		
		while(itr.hasNext()){
			Page pagina = itr.next();
			if(pagina.getNumber() == (int)object_1){
				if(pagina.getStatus()>1){
					pagina.setText("");
					pagina.setStatus(1);
					pagina.setReport("Admin: delete TEI. Last report: "+pagina.getReport());
					PageDAO DAO = new PageDAO();
					DAO.edit(pagina.toArray());
					data.getPageList();
				}else 
					error = true;
				break;
			}
		}	
		return error;
	}
	
	
	/*  CHANGESTATUSIMG(boolean)
	 *  cambia lo sato della pagina (immagine) all'interno del database,
	 *  in questo modo possiamo effettuarvi altre operazioni.
	 */
	public void changeStatusImg(boolean status) throws Exception {
		
		LinkedList<Opera> operaList 	= data.getOperaList();
		LinkedList<Page> pageList 		= data.getPageList();
		LinkedList<Action> actionList 	= data.getActionList();
		Opera opera = null;
		Page page   = null;
		
		Iterator<Opera> itr_op = operaList.iterator();		
		while(itr_op.hasNext()){
			Opera next = itr_op.next();
			if(next.getTitle().equals((String)object_3)) opera = next;
		}
		
		int count = 0;
		Iterator<Page> itr_page = pageList.iterator();
		while(itr_page.hasNext()){
			Page next = itr_page.next();
			if(next.getNumber() == (int)object_4 && next.getOperaID() == opera.getId()){
				page = next;
				break;
			}
			count++;
		}
		
		Iterator<Action> itr_act = actionList.iterator();
		while(itr_act.hasNext()){
			Action next = itr_act.next();
			if(next.getOperaID() == opera.getId() && next.getPage() == page.getNumber() ){
				if(status){
					page.setStatus(1);
					page.setReport((String)object_1);
					next.setStatus(1);
					next.setAction_report((String)object_1);
					PageDAO DAO = new PageDAO();
					DAO.edit(page.toArray());
					ActionDAO DAOact = new ActionDAO();
					DAOact.edit(next.toArray());
				} else {
					next.setStatus(2);
					next.setAction_report((String)object_1);
					PageDAO DAO = new PageDAO();
					DAO.delete(page.getID());
					pageList.remove(count);
					ActionDAO DAOact = new ActionDAO();
					DAOact.edit(next.toArray());
					FTPUtility ftp = new FTPUtility();
					ftp.connect();
					ftp.deleteFile(page.getImage());
					ftp.disconnect();
				}
				break;
			}
		}		
	}
	
	
	/*  CHANGESTATUSTXT(boolean)
	 *  cambia lo sato della pagina (testo) all'interno del database,
	 *  in questo modo possiamo effettuarvi altre operazioni.
	 */
	public void changeStatusTxt(boolean status) throws Exception {
		
		LinkedList<Opera> operaList 	= data.getOperaList();
		LinkedList<Page> pageList 		= data.getPageList();
		LinkedList<Action> actionList 	= data.getActionList();
		Opera opera = null;
		Page page   = null;

		Iterator<Opera> itr_op = operaList.iterator();
		while(itr_op.hasNext()){
			Opera next = itr_op.next();
			if(next.getTitle().equals((String)object_3)) 
				opera = next;
		}
		
		Iterator<Page> itr_page = pageList.iterator();
		while(itr_page.hasNext()){
			Page next = itr_page.next();
			if(next.getNumber() == (int)object_4 && next.getOperaID() == opera.getId()){
				page = next;
				break;
			}
		}
		
		Iterator<Action> itr_act = actionList.iterator();
		while(itr_act.hasNext()){
			Action next_act = itr_act.next();
			if(next_act.getOperaID() == opera.getId() && next_act.getPage() == page.getNumber() && next_act.getType() == 1 /*trascrizione*/){
				
				if(status){
					page.setStatus(3);
					next_act.setStatus(1);
					page.setReport((String)object_1);
					next_act.setAction_report((String)object_1);
					@SuppressWarnings("restriction")
					String text = new String(Base64.encode(page.getText().getBytes()));
					page.setText(text);
					PageDAO DAO = new PageDAO();
					DAO.edit(page.toArray());
					ActionDAO DAOact = new ActionDAO();
					DAOact.edit(next_act.toArray());
				} else {
					next_act.setStatus(2);
					next_act.setAction_report((String)object_1);
					page.setStatus(1);
					page.setText("");
					page.setReport((String)object_1);
					PageDAO DAO = new PageDAO();
					DAO.edit(page.toArray());
					ActionDAO DAOact = new ActionDAO();
					DAOact.edit(next_act.toArray());
				}
				break;
			}
		}
		data.setPageList();
	}
	
	
	/*  REPORT()
	 *  Controllo inserimento report.
	 */
	public boolean report() throws Exception{
		
		boolean control = false;
		
		if( ( ((String)object_1).equals("")) || ( ((String)object_1).length() < 5) )
			control = true;
		else
			control = false;
		
		return control;
		
	}
	
	
	/*	CHOOSE_OPERATION(String)
	 * 	Esegue l'operazione selezionata solo se inserito il report.
	 */
	public void choose_operation(){
		
		switch((int)object_2){
		
			case 1: 	try{	//Approva img
								changeStatusImg(true);
							} catch (Exception e1) {
								e1.printStackTrace();
							} 
							break;
							
			case 2: 	try{ 	//Rifiuta img
								changeStatusImg(false);
							} catch (Exception e1) {
								e1.printStackTrace();
							} 
							break;
								
			case 3:		try{ 	//Approva TEI
								changeStatusTxt(true);	
							} catch (Exception e1) {
								e1.printStackTrace();
							} 
							break;
			
			case 4: 	try{ 	//Rifiuta TEI
								changeStatusTxt(false);
							} catch (Exception e1) {
								e1.printStackTrace();
							} 
							break;
			
			default: 	break;
		}
	}
	
	
	/*  WRITETEXT()
	 *  Trascrivi testo pagina.
	 */
	public boolean writeText(int row) throws Exception{
		
		boolean error = false;
		
		if(row != -1){
			error = false;
			
			/* -- Avvio JEdit -- */
			String jeditPath = "/Applications/jEdit.app/Contents/MacOS/jedit ; exit;";
					 	  						
			try{						
				Runtime run = Runtime.getRuntime();	
				run.exec(jeditPath);
			}catch (Exception e1){
				e1.printStackTrace();
			}
			
		}else
			error = true;

		return error;
	}
	
	
	/*  REVIEWIMG()
	 *  Seleziona e revisiona immagine.
	 */
	public boolean reviewImg(int row) throws Exception {
		
		boolean error = false;
		if(row != -1){
			error = false;
		}else{
			error = true;
		}
		return error;		
	}
	
	
	/*  REVIEWTXT()
	 *  Seleziona e revisiona testo.
	 */
	public String reviewTxt(int row) throws Exception {
		
		String xml = "";
		String html = "";
		
		int operaid = 0;
		Iterator<Opera> itrOp = data.getOperaList().iterator();
		while(itrOp.hasNext()){
			Opera next = itrOp.next();
			if(next.getTitle().equals( (String)object_1)){
				operaid = next.getId();
				break;
			}
		}
			
		Iterator<Page> itrp = data.getPageList().iterator();
		while(itrp.hasNext()){
			Page next = itrp.next();
			if(next.getOperaID() == operaid && next.getNumber() == (int)object_2){
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
		return html;
	}
	
	
	/*  LOADIMAGE()
	 *  Scelta del file da caricare.
	 */
	public BufferedImage loadImage(BufferedImage preview, File path){
			
		try {
			image = ImageIO.read(path);
			preview = scaleImage(526,601,image);
		} catch (Exception e1) {
			e1.printStackTrace();
		}	   
		return preview;
	}
	
	
	/*  UPLOADTEXT()
	 *  Caricamento della trascrizione.
	 */
	public boolean uploadText(StringBuilder sb) throws IOException{
		
		boolean error = false;
		
		for(int i = 0; i<data.getPageList().size(); i++){

			Page next = data.getPageList().get(i);
			Date now = new Date();
			SimpleDateFormat ft =  new SimpleDateFormat ("dd-MM-yy");
			
			if(next.getNumber() == ((int)object_3)){
				Opera nextopera = new Opera(next.getOperaID());
				if(nextopera.getTitle().equals(((String)object_2))){
					@SuppressWarnings("restriction")
					String str = Base64.encode(sb.toString().getBytes());
					next.setText(str);
					next.setStatus(2);
					Action azione = new Action(0, ((User)object_1).getId(), next.getOperaID(), next.getNumber(), 1, 0, "["+ft.format(now) +"] " + "[No report]");
					ActionDAO DAOact = new ActionDAO();
					DAOact.add(azione.toArray());
					PageDAO DAO = new PageDAO();
					DAO.edit(next.toArray());
					data.setPageList();
					data.setActionList();
					error = false;
				}else
					error = true;
			}
		}

		return error;
	}
	
	/*  SETIMAGE(BufferedImage)
	 * 	Imposta l'immagine.
	 */
	public void setImage(BufferedImage i){
		this.image = i;
	}
	
	
	/*  SCALEIMAGE(int, int, BufferedImage)
	 *  Ridimensionamento e adattamento dell'immagine al frame.
	 */
	public BufferedImage scaleImage(int w, int h, BufferedImage img) throws Exception {
	    BufferedImage bi;
	    bi = new BufferedImage(w, h, BufferedImage.TRANSLUCENT);
	    Graphics2D g2d = (Graphics2D) bi.createGraphics();
	    g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
	    g2d.addRenderingHints(new RenderingHints(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY));
	    g2d.drawImage(img, 0, 0, w, h, null);
	    g2d.dispose();
	    return bi;
	}
	
	
	/*  ZOOMIN() - ZOOMOUT()
	 *  Permette al revisore di eseguire un controllo più accurato.
	 */
	public BufferedImage zoomIn(){
		
		BufferedImage preview = null;
		try {
			zoom+=1;
			preview = scaleImage(526*zoom, 601*zoom, (BufferedImage)object_1);
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		
		return preview;
	}
	
	public BufferedImage zoomOut(){
		BufferedImage preview = null;
		 if(zoom!=1){
			try {
				zoom-=1;
				preview = scaleImage(526*zoom,601*zoom, (BufferedImage)object_1);
			} catch (Exception e1) {
				e1.printStackTrace();
			}
			
		 }
		 return preview;
	}
	
}
/*  END class GESTIONEPAGINA  */
