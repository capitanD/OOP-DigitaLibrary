package DigitaLibrary.view.listener;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.Iterator;
import java.util.LinkedList;

import javax.swing.AbstractAction;
import javax.swing.ButtonGroup;
import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.table.DefaultTableModel;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JTable;
import javax.swing.JTextField;


import DigitaLibrary.model.Biblioteca;
import DigitaLibrary.model.Opera;
import DigitaLibrary.model.Page;
import DigitaLibrary.model.User;
import DigitaLibrary.view.frame.CaricaFrame;
import DigitaLibrary.view.frame.CronologiaFrame;
import DigitaLibrary.view.frame.GestisciUtenzaPagineFrame;
import DigitaLibrary.view.frame.HistoryFrame;
import DigitaLibrary.view.frame.MainFrame;
import DigitaLibrary.view.frame.NuovaOperaFrame;
import DigitaLibrary.view.frame.RegistrazioneFrame;
import DigitaLibrary.view.frame.ReportFrame;
import DigitaLibrary.view.frame.RevisioneImgFrame;
import DigitaLibrary.view.frame.RevisioneListFrame;
import DigitaLibrary.view.frame.RevisioneTxtFrame;
import DigitaLibrary.view.frame.TrascriviFrame;
import DigitaLibrary.view.frame.TrascriviListFrame;
import DigitaLibrary.view.frame.VisualizzaFrame;
import DigitaLibrary.controller.GestioneAction;
import DigitaLibrary.controller.GestioneOpera;
import DigitaLibrary.controller.GestionePagina;
import DigitaLibrary.controller.GestioneUser;

/*
 * VIEW --> ControllerInterface
 */

public class ControllerInterface extends AbstractAction{


	private static final long serialVersionUID = -5796011512728364456L;
	private JFrame frame;
	private Biblioteca data;
	private DefaultListModel<String> list;
	private Component component_1;
	private Object object_1;	
	private Object object_2;
	private Object object_3;
	private int editrole = 0;
	private int type_operation = 0;
	private int pageNum;
	private String username, password, repeat_password;
	private char [] charsHide_1, charsHide_2;
	private String selected_user;
	private String operaTitle;
	private String report_operation;

	
	
	
	/* -- Costruttore -- */
	public ControllerInterface(JFrame f, Biblioteca data, DefaultListModel<String> l, Component c, Object o1, Object o2, Object o3){
		this.frame= f;
		this.data = data;
		this.list = l;
		this.component_1    = c;
		this.object_1   = o1;
		this.object_2   = o2;
		this.object_3	=o3;
	}
	
	public ControllerInterface(Biblioteca data, Object o1, Object o2){
		this.data = data;
		this.object_1   = o1;
		this.object_2   = o2;
	}
	
	
	/* 	ACTIONPERFORMED(ActionEvent)
	 * 	Gestione delle azioni provenienti dalla View.
	 */
	
	public void actionPerformed(ActionEvent e) {
		switch(e.getActionCommand()){
		
		/* -- Action Command USER -- */
		
		case "Login"         : 	username = (String)((JTextField)object_1).getText();
								charsHide_1 = ((JPasswordField)object_2).getPassword();
								password = new String(charsHide_1);
								int idu;
																
								GestioneUser log = new GestioneUser(data, username, password);
								if( (idu = log.login()) > 0){
									User utente = new User(idu);
									MainFrame mainF = new MainFrame(data, utente);
									mainF.setVisible(true);
									JOptionPane.showMessageDialog(new JFrame(), "   Bentornato " + username + "!");
									this.frame.dispose();
								}else{
									JOptionPane.showMessageDialog(new JFrame(), "username e/o password errati","Login error", JOptionPane.OK_OPTION);
								}
								break;
								
		case "Registrati"    : 	this.frame.dispose();
								RegistrazioneFrame regF = new RegistrazioneFrame(data); 
								regF.setVisible(true);
								break;
								
		case "Invia Dati"    :	username = (String)((JTextField)component_1).getText();
								charsHide_1 = ((JPasswordField)object_1).getPassword();
								password = new String(charsHide_1);
								charsHide_2 = ((JPasswordField)object_2).getPassword();
								repeat_password = new String(charsHide_2);
								String email    = (String)((JTextField)object_3).getText();
								GestioneUser reg = new GestioneUser(data, username, password, repeat_password, email);
								
								int control;
								if( (control = reg.fieldControl()) > 0){
									switch(control){
									case 1:	JOptionPane.showMessageDialog(new JFrame(), "Le password non coincidono, ripetere l'operazione.","Error", JOptionPane.OK_OPTION);
											break;
									case 2: JOptionPane.showMessageDialog(new JFrame(), "Username già esistente, sceglierne un altro","Attenzione!", JOptionPane.WARNING_MESSAGE);
											break;
									case 3: JOptionPane.showMessageDialog(new JFrame(), "Email già esistente, sceglierne un'altra","Attenzione!", JOptionPane.WARNING_MESSAGE);
											break;
									default: break;
									}
								}else
									if(reg.add()){
										JOptionPane.showMessageDialog(new JFrame(), "  Registrazione Completata!");
										frame.dispose();
									}				
								break;
								
		case "Promuovi"      : 	selected_user = (String) ((JList)component_1).getSelectedValue();
								if(((JList)component_1).getSelectedValue() == null){
									JOptionPane.showMessageDialog(new JFrame(), "Seleziona utente","Attenzione!", JOptionPane.WARNING_MESSAGE);
									break;
								}else{
									editrole = 1;
									GestioneUser prom = new GestioneUser(data, selected_user, editrole);
									if(prom.edit())
										JOptionPane.showMessageDialog(new JFrame(), "Utente promosso!");
									prom.refresh();
								}									
								break;
								
		case "Declassa"      : 	selected_user = (String) ((JList)component_1).getSelectedValue();
								if(((JList)component_1).getSelectedValue() == null){
									JOptionPane.showMessageDialog(new JFrame(), "Seleziona utente","Attenzione!", JOptionPane.WARNING_MESSAGE);
									break;
								}else{
									editrole = -1;
									GestioneUser decl = new GestioneUser(data, selected_user, editrole);
									if(decl.edit())
										JOptionPane.showMessageDialog(new JFrame(), "Utente Declassato!");
									decl.refresh();
								}
								break;
								
		case "Elimina Utente": 	selected_user = (String) ((JList)component_1).getSelectedValue();

								if(((JList)component_1).getSelectedValue() == null){
									JOptionPane.showMessageDialog(new JFrame(), "Seleziona utente","Attenzione!", JOptionPane.WARNING_MESSAGE);
									break;
								}
								GestioneUser del = new GestioneUser(data, selected_user, null);
								if(!del.remove()){
									JOptionPane.showMessageDialog(new JFrame(), "Utente Eliminato","Success", JOptionPane.WARNING_MESSAGE);

									DefaultListModel<String> newListaUtenti = new DefaultListModel<String>();
									LinkedList<String> listUser = del.refresh();
									Iterator<String> itr = listUser.iterator();
									while(itr.hasNext()){
										newListaUtenti.addElement(itr.next());
									}
									((JList)component_1).setModel(newListaUtenti);
								}

								break;
								
		case "Gestisci Utenti" : 	GestisciUtenzaPagineFrame manageusF = new GestisciUtenzaPagineFrame(data, "user", null); 
									manageusF.setVisible(true); 
									break;
									
			
		/* -- Action Command ACTION -- */
		
		case "Cronologia Operazioni": 	GestioneAction cronoAction = new GestioneAction(data, ((User)object_1));							
										LinkedList<ArrayList<String>> table = new LinkedList<ArrayList<String>>();
										table = cronoAction.retrieve();
										DefaultTableModel tableFill = new DefaultTableModel(
												new Object[][] {
													
												},
												new String[] {
													"User", "Opera", "Pagina", "Tipo", "Status", "Report"
												}
											);
										
										Iterator<ArrayList<String>> itrTable = table.iterator();
										while(itrTable.hasNext()){
											ArrayList<String> elements = itrTable.next();
											tableFill.addRow(new Object[]{new User(Integer.parseInt(elements.get(0))).getUsername(), new Opera(Integer.parseInt(elements.get(1))).getTitle(), elements.get(2), elements.get(3), elements.get(4), elements.get(5)});									
										}
													
										CronologiaFrame cronoF = new CronologiaFrame(data, ((User)object_1), tableFill);
										cronoF.setVisible(true);
										break;
		
		case "Storico Operazioni":		GestioneAction histAction = new GestioneAction(data, ((User)object_1));
										
										LinkedList<ArrayList<String>> completeTable = new LinkedList<ArrayList<String>>();
										completeTable = histAction.retrieveAll();
										
										DefaultTableModel completeTableFill = new DefaultTableModel(
												new Object[][] {
													
												},
												new String[] {
													"User", "Opera", "Pagina", "Tipo", "Status", "Report"
												}
										);

										Iterator<ArrayList<String>> itrCTable = completeTable.iterator();
										while(itrCTable.hasNext()){
											ArrayList<String> elements = itrCTable.next();
											completeTableFill.addRow(new Object[]{new User(Integer.parseInt(elements.get(0))).getUsername(), new Opera(Integer.parseInt(elements.get(1))).getTitle(), elements.get(2), elements.get(3), elements.get(4), elements.get(5)});									
										}
										HistoryFrame historyF = new HistoryFrame(data, completeTableFill);
										historyF.setVisible(true);
										break;

		
		/* -- Action Command OPERA -- */
		
		  case "Cerca"	:	
			  				String searchString = ((JTextField)component_1).getText();
			  				if(searchString.equals("")) {
			  					GestioneOpera opera = new GestioneOpera(data, null, null, null);
			  					list.removeAllElements();
			  					LinkedList<String> newList = opera.refreshList();
			  					Iterator<String> itr = newList.iterator();
			  					while(itr.hasNext()){
			  						list.addElement(itr.next());
			  					}
			  					
			  				}else{
			  					GestioneOpera opera = new GestioneOpera(data, ((JTextField)component_1).getText(), null, null);
			  					list.removeAllElements();
		      					LinkedList<String> filteredList = opera.search(((ButtonGroup)object_1).getElements().nextElement().isSelected());
		      					Iterator<String> itr = filteredList.iterator();
		      					while(itr.hasNext()){
		      						list.addElement(itr.next());
		      					}
		      					
		      				}
		      				break;
		      				
		  case "Visualizza"      : 	try{
										if( ((JList)component_1).getSelectedValue() == null )
											JOptionPane.showMessageDialog(new JFrame(), "Per favore, seleziona l'opera", "Attenzione!", JOptionPane.WARNING_MESSAGE);
										else{
											String selected = (String)((JList)component_1).getSelectedValue();
										
											GestioneOpera opera = new GestioneOpera(data, selected, null, null);
											LinkedList<String> show = new LinkedList<String>();
											show = opera.view(1,0);
										
											if(show.size() != 0){
												VisualizzaFrame viewFrame;
												viewFrame = new VisualizzaFrame( data, show.get(1), Integer.parseInt(show.get(3)), show.get(0), Integer.parseInt(show.get(2)));
												if(show.getLast().equals("close") )
													frame.dispose();
											}else
												JOptionPane.showMessageDialog(new JFrame(), "Pagine non esistenti", "Error", JOptionPane.OK_OPTION);
										}

									}catch (Exception e1){
										e1.printStackTrace();
									} 
									break;	
									
		  case "Go"             : 	try{
										String showPage = (String)((JTextField)component_1).getText();
										
										int nPage = Integer.parseInt(((JTextField)component_1).getText());
										GestioneOpera opera = new GestioneOpera(data, (String)object_1, (int)object_2, nPage);
										if(!opera.hasNextPage()){
											this.frame.dispose();
										
											LinkedList<String> nextPage = new LinkedList<String>();
											nextPage = opera.view(Integer.parseInt(showPage), 1);
										
											VisualizzaFrame viewFrameNext = new VisualizzaFrame( data, nextPage.get(1), Integer.parseInt(nextPage.get(3)), nextPage.get(0), Integer.parseInt(nextPage.get(2)));
											/*if(nextPage.getLast().equals("close") )
												frame.dispose();	*/
										}else
											JOptionPane.showMessageDialog(new JFrame(), "Pagina non esistente", "Error", JOptionPane.OK_OPTION);

									}catch (Exception e1){
										e1.printStackTrace();
									} 
									break;
		      
		  case "Acquisisci" :	try{
			  						String selected = (String)((JList)component_1).getSelectedValue();
			  						if((((JList)component_1).getSelectedValue()) == null) {
			  							JOptionPane.showMessageDialog(new JFrame(), "Selezionare l'opera", "Attenzione!", JOptionPane.WARNING_MESSAGE);
			  							break;
			  						}else{		
			  							GestioneOpera opera = new GestioneOpera(data, selected, null, null);		
										CaricaFrame updateF = new CaricaFrame( data, opera.acquisisciImg(), (User)object_1);
										updateF.setVisible(true);
			  						}
								}catch (Exception e1){
									e1.printStackTrace();
								} 
		  						break;
			
		  case "Trascrivi"  : 	TrascriviListFrame writeLF = new TrascriviListFrame(data, ((User)object_1)); 
		  						writeLF.setVisible(true);
		  						break;
			
		  case "Revisiona Acquisizioni" :	RevisioneListFrame imgLF = new RevisioneListFrame(data,"img");
			  								imgLF.setVisible(true);
			  								break;
			  
		  case "Revisiona Trascrizioni" :	RevisioneListFrame textLF = new RevisioneListFrame(data,"text");
			  								textLF.setVisible(true);
			  								break;
	

	  									  
		  							
		  case "Elimina Opera"   : 	
			  						if((((JList)component_1).getSelectedValue()) == null) {
			  							JOptionPane.showMessageDialog(new JFrame(), "Selezionare l'opera da eliminare", "Attenzione!", JOptionPane.WARNING_MESSAGE);
			  							break;
			  						}else{
			  							int dialogButton = JOptionPane.YES_NO_OPTION;
			  							int result = JOptionPane.showConfirmDialog (null, "Sei sicuro di voler eliminare l'opera selezionata?","Attenzione!",dialogButton);
			  							String selected = (String)((JList)component_1).getSelectedValue();
			  							GestioneOpera opera = new GestioneOpera(data, result, selected, null);
			  							if( opera.remove()) {
				  							JOptionPane.showMessageDialog(new JFrame(), "Opera eliminata","Information", JOptionPane.INFORMATION_MESSAGE);
				  							
				  							LinkedList<String> newList = opera.refreshList();											
				  							Iterator<String> itrNewList = newList.iterator();
				  							list.removeAllElements();
											while(itrNewList.hasNext()){
												list.addElement(itrNewList.next());
											}
			  							}
			  						}
		  							break;
		  							
		  case "Nuova Opera"     : 	NuovaOperaFrame newOpF = new NuovaOperaFrame(data, list); 
		  							newOpF.setVisible(true);	  							
		  							break;
		  							
		  case "Crea"            : 	
			  						String titolo = ((JTextField)component_1).getText();
			  						String autore = ((JTextField)object_1).getText();
			  						String pagine = ((JTextField)object_2).getText();
			  						if( titolo.equals("") || autore.equals("") || pagine.equals("") ){
			  							JOptionPane.showMessageDialog(new JFrame(), "Tutti i campi sono obbligatori", "Attenzione!", JOptionPane.WARNING_MESSAGE);
			  							break;
			  						}else{
			  							GestioneOpera opera = new GestioneOpera(data, titolo, autore, pagine);
				  						if(opera.add())
				  							JOptionPane.showMessageDialog(new JFrame(), "Opera già esistente", "Error", JOptionPane.OK_OPTION);
				  						else{
				  							JOptionPane.showMessageDialog(new JFrame(), "Opera inserita con successo", "Success", JOptionPane.INFORMATION_MESSAGE);
				  							
				  							LinkedList<String> newList = opera.refreshList();											
				  							Iterator<String> itrNewList = newList.iterator();
				  							list.removeAllElements();
											while(itrNewList.hasNext()){
												list.addElement(itrNewList.next());
											}
				  						}				  							
				  						this.frame.dispose();
			  						}
			  						
		  							break;
		  							
		  case "Pubblica/Privata": 	
			  						if((((JList)component_1).getSelectedValue()) == null) {
			  							JOptionPane.showMessageDialog(new JFrame(), "Selezionare l'opera", "Attenzione!", JOptionPane.WARNING_MESSAGE);
			  							break;
			  						}else{
			  							String selected = (String)((JList)component_1).getSelectedValue();
			  							GestioneOpera opera = new GestioneOpera(data, selected, null, null);
				  						if(opera.edit())
				  							JOptionPane.showMessageDialog(frame, "L'Opera è ora Pubblica");
				  						else
				  							JOptionPane.showMessageDialog(frame, "L'Opera è ora Privata");
			  						}
		  							break;
		  							
		  case "Gestisci Pagine" : 	if(((JList)component_1).getSelectedValue()==null){
			  							JOptionPane.showMessageDialog(new JFrame(), "Seleziona l'opera", "Attenzione!", JOptionPane.WARNING_MESSAGE);
			  						}
		  							GestisciUtenzaPagineFrame managePF = new GestisciUtenzaPagineFrame(data, "page",(String)((JList)component_1).getSelectedValue()); 
		  							managePF.setVisible(true); 
		  							break;
		  							
		  							
		  							
		  /* -- Action Command PAGE -- */
		  							
		  case "Scegli File"     : 	BufferedImage preview = null;
		  							JFileChooser fileChooser = new JFileChooser();
		  							int n = fileChooser.showOpenDialog(this.frame);
			
		  							if (n == JFileChooser.APPROVE_OPTION) {
		  								File pathF = fileChooser.getSelectedFile();
		  								((JTextField) component_1).setText(pathF.getAbsolutePath());
		  								GestionePagina carica = new GestionePagina(data, null, null, null, null);
		  								
		  								ImageIcon imageIcon = new ImageIcon(carica.loadImage(preview, pathF));
		  								((JLabel)object_1).setIcon(imageIcon);

		  								 this.frame.revalidate();
		  								 this.frame.repaint();
		  							}
			  						break;		  						
			  								
		  case "Carica"          : 	if (((JTextField)component_1).getText().equals("") || ((JTextField)object_1).getText().equals("")){
			  							JOptionPane.showMessageDialog(new JFrame(), "Tutti i campi sono obbligatori","Attenzione", JOptionPane.WARNING_MESSAGE);
			  							break;
		  							}else{  								
		  								GestionePagina update = new GestionePagina(data, (String)((JTextField)component_1).getText(), (String)((JTextField)object_1).getText(), (User)object_2, (Opera)object_3);
		  								if(!update.add()){
		  									JOptionPane.showMessageDialog(new JFrame(), "Pagina già esistente","Attenzione", JOptionPane.WARNING_MESSAGE);
		  								}else{
		  									JOptionPane.showMessageDialog(new JFrame(), "      Caricamento completato");
		  									this.frame.dispose();
		  								}
		  							}
			  						break;
			  						
		  case "Elimina Pagina"  : 	if(((JList)component_1).getSelectedValue()==null){
										JOptionPane.showMessageDialog(new JFrame(), "Seleziona pagina!","Attenzione", JOptionPane.WARNING_MESSAGE);
										break;
									}
									int pageSelectedD = (int)((JList)component_1).getSelectedValue();
									GestionePagina delPage = new GestionePagina(data, null, pageSelectedD, null, null);
				
									if(delPage.remove())
										JOptionPane.showMessageDialog(new JFrame(), "Pagina non trovata","System error", JOptionPane.ERROR_MESSAGE);
									else{
										DefaultListModel model = new DefaultListModel();
										ArrayList<Integer> arr = new ArrayList<Integer>();
										for (Page p: data.getPageList()){
											arr.add(p.getNumber());
										}
										Collections.sort(arr);
										for (int i: arr){
											model.addElement(i);
										}
										((JList)component_1).setModel(model);	
									}
									break;
									
		  case "Elimina Testo"   : 	if(((JList)component_1).getSelectedValue()==null){
										JOptionPane.showMessageDialog(new JFrame(), "Seleziona pagina!","Attenzione", JOptionPane.WARNING_MESSAGE);
										break;
									}	
									int pageSelected = (int)((JList)component_1).getSelectedValue();
									GestionePagina delTxt = new GestionePagina(data, null, pageSelected, null, null);
									
									if(delTxt.edit())
										JOptionPane.showMessageDialog(new JFrame(), "Pagina non ancora trascritta","Attenzione", JOptionPane.WARNING_MESSAGE);
									else
										JOptionPane.showMessageDialog(new JFrame(), "Testo pagina eliminato","Success", JOptionPane.WARNING_MESSAGE);

									break;
		  							
		  case "Esegui Trascrizione"    : 	try{
			  									JTable tableTei = ((JTable)component_1);
			  									DefaultTableModel model = ((DefaultTableModel)object_2);
			  									int row = tableTei.getSelectedRow();
		  										GestionePagina writeT = new GestionePagina();
		  										
		  										
		  										if(writeT.writeText(row)){
		  											JOptionPane.showMessageDialog(new JFrame(), "Seleziona una pagina da trascrivere","Attenzione", JOptionPane.WARNING_MESSAGE);
		  										}else{
		  											TrascriviFrame writeTei = new TrascriviFrame(data, ((User)object_3), (String)model.getValueAt(row, 0), (int)model.getValueAt(row, 1));
			  										writeTei.setVisible(true);
		  										}
		  										this.frame.dispose();
		  										
		  									} catch (Exception e2) {
		  										e2.printStackTrace();
		  									} 
		  									break;
		  									
		  case "Carica Trascrizione"    : 	try{
												JFileChooser fileChooserTei = new JFileChooser();
												int num = fileChooserTei.showOpenDialog(this.frame);
												if (num == JFileChooser.APPROVE_OPTION) {
													File path = fileChooserTei.getSelectedFile();
													BufferedReader br = new BufferedReader(new FileReader(new File(path.getAbsolutePath())));
													String line;
													StringBuilder sByte = new StringBuilder();

													while((line=br.readLine())!= null){
														sByte.append(line.trim());
													}
													GestionePagina upText = new GestionePagina(data, (User)object_1, (String)object_2, (int)object_3, null);
													
													if (!upText.uploadText(sByte))
														JOptionPane.showMessageDialog(new JFrame(), "      Upload testo completato");
													else
			  											JOptionPane.showMessageDialog(new JFrame(), "Nessuna corrispondenza trovata","System Error", JOptionPane.ERROR_MESSAGE);

													this.frame.dispose();
													br.close();
												}													
											}catch (Exception e1){ 
												e1.printStackTrace();
											}     
											break;
		  
		  case "Revisiona Acquisizione" : 	try{	
			  									JTable tableAcq = ((JTable)component_1);
			  									DefaultTableModel model = ((DefaultTableModel)object_2);
			  									int row = tableAcq.getSelectedRow();
		  										GestionePagina rwAcq = new GestionePagina(data, null, (String)model.getValueAt(row, 0), (int)model.getValueAt(row, 2), null);

		  										if(rwAcq.reviewImg(row))
		  											JOptionPane.showMessageDialog(new JFrame(), "Seleziona un acquisizione","Attenzione", JOptionPane.WARNING_MESSAGE);
		  										else{
		  											RevisioneImgFrame imgRF = new RevisioneImgFrame(data, (String)model.getValueAt(row, 0), (int)model.getValueAt(row, 2));
		  											imgRF.setVisible(true);
		  										}
		  										frame.dispose();
		  										
		  									} catch (Exception e1) {
		  										e1.printStackTrace();
		  									} 
		  									break;
		  																
		  case "Revisiona Trascrizione"   : try{	
			  									JTable tableTeiRv = ((JTable)component_1);
			  									DefaultTableModel model = ((DefaultTableModel)object_2);
			  									int row = tableTeiRv.getSelectedRow();
		  										
		  										GestionePagina rwTxt = new GestionePagina(data, (String)model.getValueAt(row, 0), (int)model.getValueAt(row, 2), null, null);
		  										if(row == -1)
		  			  								JOptionPane.showMessageDialog(new JFrame(), "Seleziona un acquisizione","Attenzione", JOptionPane.WARNING_MESSAGE);
		  										else{
		  											String html = rwTxt.reviewTxt(row);
		  											RevisioneTxtFrame rwF = new RevisioneTxtFrame(data, (String)model.getValueAt(row, 0), (int)model.getValueAt(row, 2), html);
		  										}
		  										this.frame.dispose();
		  									} catch (Exception e1) {
		  										e1.printStackTrace();
		  									} 
		  									break;
		  															
		  case "Approva"  				  : try{
			  									this.frame.dispose();
		  										type_operation = 1;											
		  										operaTitle = ((String)object_1);
		  										pageNum    = ((int)object_2);
		  																	
		  										ReportFrame rp = new ReportFrame(data, operaTitle, pageNum, type_operation, (JFrame)object_3);
		  										rp.setVisible(true);
		  									} catch (Exception e1){
		  										e1.printStackTrace();
		  									}
		  									break;
		  																
		  case "Rifiuta"                  : try{
			  									this.frame.dispose();
		  										type_operation = 2;
		  										operaTitle = ((String)object_1);
		  										pageNum    = ((int)object_2);
		  																	
		  										ReportFrame rp = new ReportFrame(data, operaTitle, pageNum, type_operation, (JFrame)object_3);
		  										} catch (Exception e1){
		  											e1.printStackTrace();
		  										}
		  										break;
		  																
		  case "Approva TEI"             : 	try{ 
			  									this.frame.dispose();
		  										type_operation = 3;
		  										operaTitle = ((String)object_1);
		  										pageNum    = ((int)object_2);

		  										ReportFrame rp = new ReportFrame(data, operaTitle, pageNum, type_operation, (JFrame)object_3);
		  										rp.setVisible(true);
		  										} catch (Exception e1) {
		  											e1.printStackTrace();
		  										} 
		  										break;
		  																
		  case "Rifiuta TEI"             : 	try{ 
			  									this.frame.dispose();
		  										type_operation = 4;
		  										operaTitle = ((String)object_1);
		  										pageNum    = ((int)object_2);
		  																	
		  										ReportFrame rp = new ReportFrame(data, operaTitle, pageNum, type_operation, (JFrame)object_3);
		  										rp.setVisible(true);
		  										} catch (Exception e1) {
		  											e1.printStackTrace();
		  										} 
		  										break;
			/* conferma report */
		  	case "Conferma"                  : 	try{ 
		  											Date now = new Date();
		  											SimpleDateFormat ft =  new SimpleDateFormat ("dd-MM-yy");
		  											report_operation = (String)((JTextField) component_1).getText() + " ";																		
		  											type_operation = (int)object_3;
		  											operaTitle = ((String)object_1);
		  											pageNum    = ((int)object_2);
		  											
		  											GestionePagina repo = new GestionePagina(data, report_operation, null, null, null);
		  											if(repo.report()){ 
		  												JOptionPane.showMessageDialog(new JFrame(), "Attenzione! Completa il responso. ","Attenzione", JOptionPane.OK_OPTION);
		  												break;
		  											}else{													
		  												String final_report = "["+ft.format(now) +"] " + report_operation.substring(0, 1).toUpperCase() + report_operation.substring(1, report_operation.length()).toLowerCase()+"";
		  												JOptionPane.showMessageDialog(new JFrame(), "Operazione eseguita!", "Success", JOptionPane.WARNING_MESSAGE);
			  											GestionePagina frepo = new GestionePagina(data, final_report, type_operation, operaTitle, pageNum);
		  												frepo.choose_operation();
		  												this.frame.dispose();
		  											}
		  																	
		  										} catch (Exception e1) {
		  											e1.printStackTrace();
		  										} 
		  										break;

		
		  default      :						break;
		}
	}
}