package DigitaLibrary.Controller;

import java.awt.Component;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.image.BufferedImage;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;

import java.util.LinkedList;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.Date;

import javax.swing.JList;
import javax.imageio.ImageIO;
import javax.swing.AbstractAction;
import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;

import com.sun.org.apache.xerces.internal.impl.dv.util.*;
import java.text.*;
import java.lang.Runtime;

import DigitaLibrary.DAO.*;
import DigitaLibrary.Model.Action;
import DigitaLibrary.Model.Biblioteca;
import DigitaLibrary.Model.Page;
import DigitaLibrary.Model.User;
import DigitaLibrary.View.ReportFrame;
import DigitaLibrary.View.RevisioneImgFrame;
import DigitaLibrary.View.RevisioneTxtFrame;
import DigitaLibrary.View.TrascriviFrame;
import DigitaLibrary.View.RevisioneListFrame;
import DigitaLibrary.Model.Opera;

/*
 * CONTROLLER --> GESTIONE PAGINA
 * La classe GestionePagina permette di effettuare diverse operazioni su una qualsiasi pagina di una qualunque opera.
 * - Costruttore,
 * - actionPerformed(ActionEvent) -> gestione eventi della View,
 * - add() 		-> Aggiunta di una nuova pagina di un'opera.
 * - remove() 	-> Rimozione intera pagina di un'opera.
 * - edit() 	->Eliminazione di una trascrizione.
 * - changeStatusImg(boolean) 	-> Cambio di stato di un'immagine.
 * - changeStatusTxt(boolean) 	-> Cambio di stato di una trascrizione.
 * - report()	-> Controllo inserimento report.
 * - choose_operation()	-> Scelta dell'operazione da eseguire in base al report.
 * - writeText()	-> Stesura del testo che vale la trascrizione.
 * - reviewImg()	-> Revisione di un'immagine.
 * - reviewTxt()	-> Revisione di un testo in formato TEI.
 * - loadImage()	-> Scelta del file immagine da caricare.
 * - uploadText()	-> Salvataggio e caricamento di una trascrizione.
 * - scaleImage(int, int, BufferedImage)	-> Adattamento dell'immagine al frame.
 * - setImage(BufferedImage)	-> Imposta l'immagine.
 * - zoomIn()/Out()	-> Zoom per poter effettuare un controllo più accurato.
 */


public class GestionePagina extends AbstractAction implements Gestione  {
	
	private static final long serialVersionUID = 9048207555952726122L;
	
	private Biblioteca 		data;
	private JFrame 			frame;
	private BufferedImage 	image;
	private int 	zoom = 1;
	private int 	type_operation = 0;
	private Component c;
	private Object 	object_3;
	private Object 	object_1;
	private Object 	object_2;
	private String 	operaTitle;
	private String	report_operation;
	private int 	pageNum;

	
	/* -- Costruttore -- */
	public GestionePagina(JFrame f, Biblioteca data, Component c, Object o3, Object o1, Object o2){
		this.frame 		= f;
		this.data  		= data;
		this.c    	 	= c;
		this.object_3   = o3;
		this.object_1   = o1;
		this.object_2   = o2;
	}
	
	
	/* 	ACTIONPERFORMED(ActionEvent)
	 * 	Gestione delle azioni provenienti dalla View.
	 */
    public void actionPerformed(ActionEvent e) {
		
		switch (e.getActionCommand()){
		case "Scegli File"              : 	loadImage(); break;
		case " + "                   	: 	zoomIn();    break;
		case " - "                   	: 	zoomOut();   break;
		case "Carica"                   : 	add();       break;
		
		case "Esegui Trascrizione"     	: 	try{	
												writeText();												
											} catch (Exception e2) {
												e2.printStackTrace();
											} 
											break;
											
		case "Revisiona Acquisizione"   : 	try{	
												reviewImg();
											} catch (Exception e1) {
												e1.printStackTrace();
											} 
											break;
											
		case "Revisiona Trascrizione"   : 	try{	
												reviewTxt();
											} catch (Exception e1) {
												e1.printStackTrace();
											} 
											break;
										
		case "Approva"                  : 	try{
												type_operation = 1;											
												operaTitle = ((String)object_1);
												pageNum    = ((int)object_2);
												
												ReportFrame rp = new ReportFrame(data, operaTitle, pageNum, type_operation);
												rp.setVisible(true);
											} catch (Exception e1){
												e1.printStackTrace();
											}
												break;
											
		case "Rifiuta"                  : 	try{
												type_operation = 2;
												operaTitle = ((String)object_1);
												pageNum    = ((int)object_2);
												
												ReportFrame rp = new ReportFrame(data, operaTitle, pageNum, type_operation);
												rp.setVisible(true);
											} catch (Exception e1){
												e1.printStackTrace();
											}
											break;
											
		case "Approva TEI"             : 	try{ 
												type_operation = 3;
												operaTitle = ((String)object_1);
												pageNum    = ((int)object_2);

												ReportFrame rp = new ReportFrame(data, operaTitle, pageNum, type_operation);
												rp.setVisible(true);
											} catch (Exception e1) {
												e1.printStackTrace();
											} 
											break;
											
		case "Rifiuta TEI"             : 	try{ 
												type_operation = 4;
												operaTitle = ((String)object_1);
												pageNum    = ((int)object_2);
												
												ReportFrame rp = new ReportFrame(data, operaTitle, pageNum, type_operation);
												rp.setVisible(true);
											} catch (Exception e1) {
												e1.printStackTrace();
											} 
											break;
		/* conferma report */
		case "Conferma"                  : 	try{ 
												Date now = new Date();
												SimpleDateFormat ft =  new SimpleDateFormat ("dd-MM-yy");
												report_operation = (String)((JTextField) c).getText() + " ";
																							
												type_operation = (int)object_2;
												operaTitle = ((String)object_3);
												pageNum    = ((int)object_1);
												
												if(!report()){ 
													JOptionPane.showMessageDialog(new JFrame(), "Attenzione! Completa il responso. ","Attenzione", JOptionPane.OK_OPTION);
													break;
												}else{													
													report_operation = "["+ft.format(now) +"] " + report_operation.substring(0, 1).toUpperCase() + report_operation.substring(1, report_operation.length()).toLowerCase()+"";
													JOptionPane.showMessageDialog(new JFrame(), "Operazione eseguita!", "Success", JOptionPane.WARNING_MESSAGE);
													choose_operation();
												}
												
												this.frame.dispose();

											} catch (Exception e1) {
												e1.printStackTrace();
											} 
											break;
											
		case "Elimina Pagina"           : 	if(((JList)c).getSelectedValue()==null){
												JOptionPane.showMessageDialog(new JFrame(), "Seleziona pagina!","Attenzione", JOptionPane.WARNING_MESSAGE);
												break;
											}
											remove();    
											break;
											
		case "Elimina Testo"            : 	if(((JList)c).getSelectedValue()==null){
												JOptionPane.showMessageDialog(new JFrame(), "Seleziona pagina!","Attenzione", JOptionPane.WARNING_MESSAGE);
												break;
											};
											edit();      
											break;
											
		case "Carica Trascrizione"      : 	try{
												uploadText();
											}catch (Exception e1){ 
												e1.printStackTrace();
											}     
											break;
											
		default                         :   break;
		}
	}


	/*  ADD()
     * 	Aggiunta di una nuova pagina.
     * 	Dovranno essere specificate l'opera di appartenenza e il numero della pagina.
     */
	public void add() {
		
		boolean flag = true;
		
		if (((JTextField) c).getText().equals("") || ((JTextField) object_3).getText().equals("")){
			flag = false;
			JOptionPane.showMessageDialog(new JFrame(), "Tutti i campi sono obbligatori","Attenzione", JOptionPane.WARNING_MESSAGE);
		}
		
		if(flag){
			Opera toAdd = ((Opera)object_2);
			int pagenum = Integer.parseInt(((JTextField) object_3).getText());
			if(pagenum > toAdd.getPages() ){
				JOptionPane.showMessageDialog(new JFrame(), "L'opera ha "+toAdd.getPages()+" pagine","Attenzione", JOptionPane.WARNING_MESSAGE);
				flag = false;
			}
			LinkedList<Page> pages = data.getPageList();
			Iterator<Page> itr = pages.iterator();
			while(itr.hasNext()){
				Page next = itr.next();
				if(next.getNumber() == pagenum && next.getOperaID() == toAdd.getId() ){
					JOptionPane.showMessageDialog(new JFrame(), "Pagina già esistente","Attenzione", JOptionPane.WARNING_MESSAGE);
					flag = false;
				}
			}
			if(flag){
				FTPUtility ftp = new FTPUtility();
				
				//connessione al database per il trasferimento file.
				try {
					ftp.connect();
					ftp.uploadFile(((JTextField) c).getText(), toAdd.getTitle(), pagenum+".jpg");
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
				Action newaction = new Action(0, ((User)object_1).getId(), ((Opera)object_2).getId(), pagenum, 0, 0, "["+ft.format(now) +"] "+"[No report]");
				data.getActionList().add(newaction);
				ActionDAO DAOaction = new ActionDAO();
				DAOaction.add(newaction.toArray());
				JOptionPane.showMessageDialog(new JFrame(), "      Caricamento completato");
				frame.dispose();
			}
		}
	}

	
	/*  REMOVE()
	 * 	Rimozione di una pagina di un'opera dal sistema (operazione concessa solo all'admin).
	 */
	public void remove() {
		int selected = (int)((JList)c).getSelectedValue();
		Iterator<Page> itr = data.getPageList().iterator();
		while(itr.hasNext()){
			Page next = itr.next();
			if(next.getNumber() == selected){
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
			}
		}
		DefaultListModel model = new DefaultListModel();
		ArrayList<Integer> arr = new ArrayList<Integer>();
		for (Page p: data.getPageList()){
			arr.add(p.getNumber());
		}
		Collections.sort(arr);
		for (int i: arr){
			model.addElement(i);
		}
		((JList)c).setModel(model);	
	}

	
	/*  EDIT()
	 *  Elimina trascrizione. 
	 */
	public void edit() {
		
		int selected = (int)((JList)c).getSelectedValue();
		Iterator<Page> itr = data.getPageList().iterator();
		
		while(itr.hasNext()){
			Page pagina = itr.next();
			if(pagina.getNumber() == selected){
				if(pagina.getStatus()>1){
					pagina.setText("");
					pagina.setStatus(1);
					pagina.setReport("Admin: delete TEI. Last report: "+pagina.getReport());
					PageDAO DAO = new PageDAO();
					DAO.edit(pagina.toArray());
					data.getPageList();
				}else 
					JOptionPane.showMessageDialog(new JFrame(), "Pagina non ancora trascritta","Attenzione", JOptionPane.WARNING_MESSAGE);
				break;
			}
		}					
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
			if(next.getTitle().equals(operaTitle)) opera = next;
		}
		
		int count = 0;
		Iterator<Page> itr_page = pageList.iterator();
		while(itr_page.hasNext()){
			Page next = itr_page.next();
			if(next.getNumber() == pageNum && next.getOperaID() == opera.getId()){
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
					page.setReport(report_operation);
					next.setStatus(1);
					next.setAction_report(report_operation);
					PageDAO DAO = new PageDAO();
					DAO.edit(page.toArray());
					ActionDAO DAOact = new ActionDAO();
					DAOact.edit(next.toArray());
				} else {
					next.setStatus(2);
					next.setAction_report(report_operation);
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
		this.frame.dispose();
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
			if(next.getTitle().equals(operaTitle)) 
				opera = next;
		}
		
		Iterator<Page> itr_page = pageList.iterator();
		while(itr_page.hasNext()){
			Page next = itr_page.next();
			if(next.getNumber() == pageNum && next.getOperaID() == opera.getId()){
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
					page.setReport(report_operation);
					next_act.setAction_report(report_operation);
					String text = new String(Base64.encode(page.getText().getBytes()));
					page.setText(text);
					PageDAO DAO = new PageDAO();
					DAO.edit(page.toArray());
					ActionDAO DAOact = new ActionDAO();
					DAOact.edit(next_act.toArray());
				} else {
					next_act.setStatus(2);
					next_act.setAction_report(report_operation);
					page.setStatus(1);
					page.setText("");
					page.setReport(report_operation);
					PageDAO DAO = new PageDAO();
					DAO.edit(page.toArray());
					ActionDAO DAOact = new ActionDAO();
					DAOact.edit(next_act.toArray());
				}
				break;
			}
		}
		data.setPageList();
		this.frame.dispose();
	}
	
	
	/*  REPORT()
	 *  Controllo inserimento report.
	 */
	public boolean report() throws Exception{
		
		boolean control = false;
		
		if( (report_operation.equals("")) || (report_operation.length() < 5) )
			control = false;
		else
			control = true;
		
		frame.dispose();
		
		return control;
		
	}
	
	
	/*	CHOOSE_OPERATION(String)
	 * 	Esegue l'operazione selezionata solo se inserito il report.
	 */
	public void choose_operation(){
		
		switch(type_operation){
		
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
	public void writeText() throws Exception{
		JTable table = ((JTable)c);
		DefaultTableModel model = ((DefaultTableModel)object_1);
		int row = table.getSelectedRow();
		if(row != -1){
			TrascriviFrame f = new TrascriviFrame(data, ((User)object_2), (String)model.getValueAt(row, 0), (int)model.getValueAt(row, 1));
			f.setVisible(true);
			
			/* -- Avvio JEdit -- */
			String jeditPath = "/Applications/jEdit.app/Contents/MacOS/jedit ; exit;";
					 	  						
			try{						
				Runtime run = Runtime.getRuntime();	
				run.exec(jeditPath);
			}catch (Exception e1){
				e1.printStackTrace();
			}
			
		}else{
			JOptionPane.showMessageDialog(new JFrame(), "Seleziona una pagina da trascrivere","Attenzione", JOptionPane.WARNING_MESSAGE);
		}
		frame.dispose();
	}
	
	
	/*  REVIEWIMG()
	 *  Seleziona e revisiona immagine.
	 */
	public void reviewImg() throws Exception {
		JTable table = ((JTable)c);
		DefaultTableModel model = ((DefaultTableModel)object_1);
		int row = table.getSelectedRow();
		if(row != -1){
			RevisioneImgFrame f = new RevisioneImgFrame(data, (String)model.getValueAt(row, 0), (int)model.getValueAt(row, 2));
			f.setVisible(true);
		}else{
			JOptionPane.showMessageDialog(new JFrame(), "Seleziona un acquisizione","Attenzione", JOptionPane.WARNING_MESSAGE);
		}
		frame.dispose();
	}
	
	
	/*  REVIEWTXT()
	 *  Seleziona e revisiona testo.
	 */
	public void reviewTxt() throws Exception {
		JTable table = ((JTable)c);
		DefaultTableModel model = ((DefaultTableModel)object_1);
		int row = table.getSelectedRow();
		if(row != -1){
			
			String xml = "";
			String html = "";
			int operaid = 0;
			Iterator<Opera> itro = data.getOperaList().iterator();
			while(itro.hasNext()){
				Opera next = itro.next();
				if(next.getTitle().equals((String)model.getValueAt(row, 0))){
					operaid = next.getId();
					break;
				}
			}
			
			Iterator<Page> itrp = data.getPageList().iterator();
			while(itrp.hasNext()){
				Page next = itrp.next();
				if(next.getOperaID() == operaid && next.getNumber() == (int)model.getValueAt(row, 2)){
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
			
		RevisioneTxtFrame f = new RevisioneTxtFrame(data, (String)model.getValueAt(row, 0), (int)model.getValueAt(row, 2), html);
		}else{
			JOptionPane.showMessageDialog(new JFrame(), "Seleziona un acquisizione","Attenzione", JOptionPane.WARNING_MESSAGE);
		}
		frame.dispose();
	}
	
	
	/*  LOADIMAGE()
	 *  Scelta del file da caricare.
	 */
	public void loadImage(){
		BufferedImage preview= null;
		JFileChooser fileChooser = new JFileChooser();
		int n = fileChooser.showOpenDialog(this.frame);
		if (n == JFileChooser.APPROVE_OPTION) {
			File path = fileChooser.getSelectedFile();
		    ((JTextField) c).setText(path.getAbsolutePath());
			
			try {
				image= ImageIO.read(path);
				preview = scaleImage(526,601,ImageIO.read(path));
			} catch (Exception e1) {
				e1.printStackTrace();
			}
			ImageIcon imageIcon = new ImageIcon(preview);
		    ((JLabel)object_3).setIcon(imageIcon);
		    ((JButton)object_1).setEnabled(true);
		    ((JButton)object_2).setEnabled(true);
		    this.frame.revalidate();
			this.frame.repaint();
		}
	}
	
	
	/*  UPLOADTEXT()
	 *  Caricamento della trascrizione.
	 */
	public void uploadText() throws IOException{
		JFileChooser fileChooser = new JFileChooser();
		int n = fileChooser.showOpenDialog(this.frame);
		if (n == JFileChooser.APPROVE_OPTION) {
			File path = fileChooser.getSelectedFile();
			BufferedReader br = new BufferedReader(new FileReader(new File(path.getAbsolutePath())));
			String line;
			StringBuilder sb = new StringBuilder();

			while((line=br.readLine())!= null){
			    sb.append(line.trim());
			}
			
	
			for(int i = 0; i<data.getPageList().size(); i++){
				//lista degli ID di tutte le pagine
				Page next = data.getPageList().get(i);
				Date now = new Date();
				SimpleDateFormat ft =  new SimpleDateFormat ("dd-MM-yy");
				
				if(next.getNumber() == ((int)object_2)){
					Opera nextopera = new Opera(next.getOperaID());
					String repo = ""+next.getReport();
					if(nextopera.getTitle().equals(((String)object_1))){
						String str = Base64.encode(sb.toString().getBytes());
						next.setText(str);
						next.setStatus(2);
						Action azione = new Action(0, ((User)object_3).getId(), next.getOperaID(), next.getNumber(), 1, 0, "["+ft.format(now) +"] " + "[No report]");
						ActionDAO DAOact = new ActionDAO();
						DAOact.add(azione.toArray());
						PageDAO DAO = new PageDAO();
						DAO.edit(next.toArray());
						JOptionPane.showMessageDialog(new JFrame(), "      Upload testo completato");
						data.setPageList();
						data.setActionList();
						this.frame.dispose();
					}
				}
			}
			PageDAO DAO = new PageDAO();		
		}			
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
	public void zoomIn(){
		BufferedImage preview = null;
		try {
			zoom+=1;
			preview = scaleImage(526*zoom, 601*zoom, image);
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		ImageIcon imageIcon = new ImageIcon(preview);
		((JLabel)object_3).setIcon(imageIcon);
	}
	
	public void zoomOut(){
		BufferedImage preview = null;
		 if(zoom!=1){
			try {
				zoom-=1;
				preview = scaleImage(526*zoom,601*zoom,image);
			} catch (Exception e1) {
				e1.printStackTrace();
			}
			ImageIcon imageIcon = new ImageIcon(preview);
			((JLabel)object_3).setIcon(imageIcon);
		 }
	}
	
}
/*  END class GESTIONEPAGINA  */
