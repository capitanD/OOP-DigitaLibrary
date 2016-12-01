package DigitaLibrary.View;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedList;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.EmptyBorder;

import DigitaLibrary.Controller.GestionePagina;
import DigitaLibrary.Controller.GestioneUser;
import DigitaLibrary.Controller.GestioneMouse;
import DigitaLibrary.Model.Biblioteca;
import DigitaLibrary.Model.Opera;
import DigitaLibrary.Model.Page;

import javax.swing.JList;
import javax.swing.border.BevelBorder;
import javax.swing.Action;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JLabel;
import java.awt.Font;

/*
 * 	GESTIONE UTENZA/PAGINE
 * 	- promuovere,declassare ed eliminare gli utenti registrati.
 * 	- eliminazione delle pagine di un'opera.
 */

public class GestisciUtenzaPagineFrame extends JFrame {

	private static final long serialVersionUID = 969473694097131456L;
	
	private JPanel contentPane;


	public GestisciUtenzaPagineFrame(Biblioteca data, String type, String opera) {
		
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setLocation(465, 120);
		setSize(325, 280);
		setResizable(false);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		contentPane.setBackground(new Color(237, 160, 78));
		
		
		
		if(type.equals("user")){
			
		/*  --  GESTIONE UTENZA  --  */
			
			/* -- Titolo finestra -- */
			JLabel lbUtenti = new JLabel("Gestione Utenti");
			lbUtenti.setFont(new Font("Thaoma", Font.BOLD, 14));
			lbUtenti.setHorizontalAlignment(JLabel.CENTER);
			lbUtenti.setBounds(62, 8, 200, 25);
			contentPane.add(lbUtenti);
			
			/* -- Elenco utenti registrati -- */
			JLabel lbelencoUtenti = new JLabel("Elenco Utenti");
			lbelencoUtenti.setFont(new Font("Tahoma", Font.BOLD, 11));
			lbelencoUtenti.setBounds(19, 33, 80, 14);
			contentPane.add(lbelencoUtenti);
			
			/*  Contenitore lista utenti  */
			JList list = new JList();
			JScrollPane scroll = new JScrollPane(list);
			scroll.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
			scroll.setBounds(18, 54, 285, 239);
			scroll.setSize(140, 180);
			scroll.setBackground(Color.BLACK);
			contentPane.add(scroll);
			
			/* -- Ruolo utente -- */
			JLabel lblStatus = new JLabel("Ruolo");
			lblStatus.setFont(new Font("Tahoma", Font.BOLD, 11));
			lblStatus.setBounds(216, 50, 46, 14);
			contentPane.add(lblStatus);	
			
			JLabel label = new JLabel("-");
			label.setHorizontalAlignment(JLabel.CENTER);
			label.setBounds(172, 54, 120, 40);
			contentPane.add(label);
			
			/* -- Interazione con il controller -- */
			GestioneUser act = new GestioneUser(this, data, list, null);
			act.refresh();
			GestioneMouse mouse = new GestioneMouse(data, "UserFrame", list, label, null, null, null);
			list.addMouseListener(mouse);
		
			/* -- Modifica ruolo utente -- */
			JButton btnPromuovi = new JButton("Promuovi");
			btnPromuovi.setBounds(182, 112, 173, 33);
			btnPromuovi.setSize(100,32);
			btnPromuovi.setFont(new Font("Tahoma", Font.PLAIN, 11));
			btnPromuovi.addActionListener(act);
			contentPane.add(btnPromuovi);
		
			JButton btnDeclassa = new JButton("Declassa");
			btnDeclassa.setBounds(182, 154, 173, 33);
			btnDeclassa.setSize(100,32);
			btnDeclassa.setFont(new Font("Tahoma", Font.PLAIN, 11));
			btnDeclassa.addActionListener(act);
			contentPane.add(btnDeclassa);
		
			JButton btnDeleteUser = new JButton("Elimina Utente");
			btnDeleteUser.setBounds(182, 196, 173, 33);
			btnDeleteUser.setSize(100,32);
			btnDeleteUser.setFont(new Font("Tahoma", Font.PLAIN, 11));
			btnDeleteUser.addActionListener(act);
			contentPane.add(btnDeleteUser);
			
		/* -- FINE Gestione Utenza -- */
			
		} else {	
			
		/*  -- GESTIONE PAGINE --  */
			
			
			/*  Modifica dimensione finestra  */
			setLocation(500, 125);
			setSize(253,245);
			
			/* -- Titolo finestra -- */
			JLabel lbOpera = new JLabel(opera);
			lbOpera.setFont(new Font("Thaoma", Font.BOLD, 13));
			lbOpera.setHorizontalAlignment(JLabel.CENTER);
			lbOpera.setBounds(25, 8, 200, 25);
			contentPane.add(lbOpera);
			
			
			/* -- Stato delle pagine (immagine e trascrizione) -- */
			JLabel lblStatus = new JLabel("Status");
			lblStatus.setFont(new Font("Tahoma", Font.BOLD, 11));
			lblStatus.setBounds(135, 65, 50, 14);
			contentPane.add(lblStatus);
			
			JLabel label = new JLabel("-");
			label.setFont(new Font("Tahoma", Font.PLAIN, 10));
			label.setBounds(85, 67, 140, 40);
			label.setHorizontalAlignment(JLabel.CENTER);
			contentPane.add(label);
			
			/* -- Report -- */
			JLabel lbReport = new JLabel("Last report");
			lbReport.setFont(new Font("Tahoma", Font.BOLD, 11));
			lbReport.setBounds(125, 98, 80, 14);
			contentPane.add(lbReport);
			
			JLabel labelr = new JLabel("-");
			labelr.setFont(new Font("Tahoma", Font.PLAIN, 10));
			labelr.setBounds(85, 101, 140, 40);
			labelr.setHorizontalAlignment(JLabel.CENTER);
			contentPane.add(labelr);
			
			
			/*  -- Elenco pagine -- */
			JLabel lbpagine = new JLabel("Elenco Pagine");
			lbpagine.setFont(new Font("Tahoma", Font.BOLD, 11));
			lbpagine.setBounds(13, 47, 80, 14);
			contentPane.add(lbpagine);	
			
			/* -- Creazione elenco pagine disponibili -- */
			Opera selected = null;
			LinkedList<Opera> operaList = data.getOperaList();
			Iterator<Opera> itrO = operaList.iterator();
			while(itrO.hasNext()){
				Opera next = itrO.next();
				if(next.getTitle().equals(opera)){
					selected = next;
					break;
				}
			}
			
			ArrayList<Integer> listaPagine=new ArrayList<Integer>();
			Iterator<Page> itr = data.getPageList().iterator();
			while(itr.hasNext()){
				Page next = itr.next();
				if(next.getOperaID() == selected.getId()) listaPagine.add(next.getNumber());
			}
			Collections.sort(listaPagine);
			DefaultListModel<Integer> model = new DefaultListModel<Integer>();
			for(int p : listaPagine) model.addElement(p);
					
			
			/* -- Contenitore lista pagine -- */ 
			JList list = new JList(model);
			JScrollPane scroll = new JScrollPane(list);
			scroll.setBounds(13, 68, 285, 240);
			scroll.setSize(55, 135);
			scroll.setBackground(Color.BLACK);			
			contentPane.add(scroll);
		
			
			/* -- Interazione con il controller  -- */
			Action act2 = new GestionePagina(this, data, list, null, null, null);
			GestioneMouse mouse = new GestioneMouse(data, "PageFrame", list, label, labelr, null, null);
			list.addMouseListener(mouse);
			
					
			/*  -- Tasti modifica pagine -- */
			JButton btnDeletePage = new JButton("Elimina Pagina");
			btnDeletePage.setFont(new Font("Tahoma", Font.PLAIN, 11));
			btnDeletePage.setBounds(105, 144, 173, 33);
			btnDeletePage.setSize(100,32);
			btnDeletePage.addActionListener(act2);
			contentPane.add(btnDeletePage);
			
			JButton btnDeleteText = new JButton("Elimina Testo");
			btnDeleteText.setBounds(105, 178, 173, 33);
			btnDeleteText.setFont(new Font("Tahoma", Font.PLAIN, 11));
			btnDeleteText.setSize(100,32);
			btnDeleteText.addActionListener(act2);
			contentPane.add(btnDeleteText);
			
		/* -- FINE Gestione Pagine -- */

		}
	}
}
/* END class GestisciUtenzaPagineFrame */