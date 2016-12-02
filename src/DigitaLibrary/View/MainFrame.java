package DigitaLibrary.View;

import java.awt.Font;
import java.awt.Color;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import DigitaLibrary.Controller.GestioneAction;
import DigitaLibrary.Controller.GestioneOpera;
import DigitaLibrary.Controller.GestioneMouse;
import DigitaLibrary.Model.Biblioteca;
import DigitaLibrary.Model.Opera;
import DigitaLibrary.Model.User;

import javax.swing.border.BevelBorder;
import javax.swing.border.Border;
import javax.swing.BorderFactory;
import javax.swing.JList;
import javax.swing.JTextField;
import javax.swing.Action;
import javax.swing.ButtonGroup;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Iterator;


/*	MAINFRAME
 *	Frame principale in cui parte del contenuto verrà modificato 
 * 	in base al ruolo dell'utente.
 * 	Admin: ruolo 6
 *	Revisore Trascrizioni: ruolo 5
 *	Trascrittore: ruolo 4
 *	Revisore Acquisizioni: ruolo 3
 *	Acquisitore: ruolo 2
 *	Utente Avanzato: ruolo 1
 *	Utente Base: ruolo 0 
 */

public class MainFrame extends JFrame {
	
	private static final long serialVersionUID = 7469885287644889648L;
	private JPanel contentPane;
	private JTextField cercaField;
	private Biblioteca data;
	private static Border border_one;
	private static Border border_two;


	public MainFrame(Biblioteca data, User user) {
		
		int role = user.getRole();
		this.data = data;
		int count = 0;
		DefaultListModel<String> listaOpera = new DefaultListModel<String>();
		
		if(role == 0 || role == 1){
			Iterator<Opera> itr = data.getOperaList().iterator();
			while(itr.hasNext()){
				Opera next = itr.next();
				if(next.getStatus()){
					listaOpera.addElement(next.getTitle());
					count++;
				}
			}
		}else{
			Iterator<Opera> itr = data.getOperaList().iterator();
			while(itr.hasNext()){
				listaOpera.addElement(itr.next().getTitle());
			}
		}
		
		/*  ContentPane: finestra principale  */
		setSize(645, 540);
		setLocation(295, 55);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setResizable(false);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);		
		contentPane.setBackground(new Color(185, 200, 210));
		
		/* -- Copyright TEAM -- */
		JLabel team = new JLabel("Copyright - Team@24CinL");
		team.setFont(new Font("Tahoma", Font.PLAIN, 8));
		team.setBounds(287, 498, 210, 20);
		contentPane.add(team);
		
		/*  -- NOME PROGETTO --  */
		JLabel lbprojectName = new JLabel("DigitaLibrary");
		lbprojectName.setFont(new Font("Tahoma", Font.BOLD, 19));
		lbprojectName.setBounds(241, 10, 180, 27);
		lbprojectName.setHorizontalAlignment(JLabel.CENTER);
		lbprojectName.setOpaque(true);
		contentPane.add(lbprojectName);
		
		
		/*  -- CERCA  --  */	
		
		/*  Textfield di ricerca  */
		cercaField = new JTextField();
		cercaField.setFont(new Font("Tahoma", Font.PLAIN, 11));
		cercaField.setBounds(17, 55, 155, 18);
		cercaField.setBorder(new EmptyBorder(2, 2, 2, 2));
		contentPane.add(cercaField);
		cercaField.setColumns(10);
		
		/*  Cerca per Autore  */
		JRadioButton rdbtnAutore = new JRadioButton("Autore");
		rdbtnAutore.setFont(new Font("Tahoma", Font.PLAIN, 11));
		rdbtnAutore.setBounds(78, 76, 74, 23);
		contentPane.add(rdbtnAutore);
		
		/*  Cerca per Titolo (default)  */
		JRadioButton rdbtnTitolo = new JRadioButton("Titolo", true);
		rdbtnTitolo.setFont(new Font("Tahoma", Font.PLAIN, 11));
		rdbtnTitolo.setBounds(14, 76, 69, 23);
		contentPane.add(rdbtnTitolo);
				
		ButtonGroup group = new ButtonGroup();
		group.add(rdbtnTitolo);
		group.add(rdbtnAutore);
		
		/*  Tasto Cerca  */
		Action act2 = new GestioneOpera( this, data, listaOpera, cercaField, group, null);
		JButton btnCerca = new JButton("Cerca");
		btnCerca.setFont(new Font("Tahoma", Font.PLAIN, 11));
		btnCerca.setBounds(173, 50, 68, 31);
		btnCerca.setSize(65, 30);
		btnCerca.addActionListener(act2);
		contentPane.add(btnCerca);
		
		
		/*  SEPARATORE_0  */
		JLabel separatore_0 = new JLabel("");
		separatore_0.setBounds(242, 52, 2, 25);
		border_two = BorderFactory.createLineBorder(new Color(185, 200, 210), 2);
		separatore_0.setBorder(border_two);
		contentPane.add(separatore_0);
		
		/*  BACKGROUND LINE  */
		JLabel back = new JLabel("");
		back.setBounds(0, 52, 650, 25);
		back.setBackground(new Color(90, 102, 107));
		back.setOpaque(true);
		contentPane.add(back);
			
		
		
		/*  -- ELENCO OPERE  --*/
		
		/*  label elenco opere  */
		JLabel lbelenco = new JLabel("Elenco opere");
		lbelenco.setFont(new Font("Tahoma", Font.BOLD, 11));
		lbelenco.setBounds(18, 108, 95, 17);
		contentPane.add(lbelenco);
		
		/*  Elenco opere  */
		JList<String> list = new JList<String>(listaOpera);	
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		scrollPane.setBounds(18, 123, 233, 360);
		scrollPane.setSize(225, 360);
		scrollPane.setViewportView(list);
		scrollPane.setBackground(Color.BLACK);
		contentPane.add(scrollPane);
		
		
		/*  -- ANTEPRIMA OPERE --  */
		
		JLabel lbAnteprima = new JLabel("Anteprima opera");
		lbAnteprima.setFont(new Font("Tahoma", Font.BOLD, 11));
		lbAnteprima.setBounds(260, 108, 95, 17);
		contentPane.add(lbAnteprima);
		
		/*  Autore  */
		JLabel lblAutore = new JLabel("Autore:");
		lblAutore.setFont(new Font("Tahoma", Font.PLAIN, 11));
		lblAutore.setBounds(260, 151, 46, 14);
		contentPane.add(lblAutore);
		
		JLabel autore = new JLabel("-");
		autore.setFont(new Font("Tahoma", Font.BOLD, 11));
		autore.setBounds(313, 151, 134, 14);
		contentPane.add(autore);
		
		/*  N° Pagine  */
		JLabel lblPagine = new JLabel("Pagine:");
		lblPagine.setFont(new Font("Tahoma", Font.PLAIN, 11));
		lblPagine.setBounds(260, 170, 46, 14);
		contentPane.add(lblPagine);
		
		JLabel pagine = new JLabel("-");
		pagine.setFont(new Font("Tahoma", Font.BOLD, 11));
		pagine.setBounds(313, 170, 134, 14);
		contentPane.add(pagine);

		/*  Tasto Visualizza  */
		Action act5 = new GestioneOpera( this, data, listaOpera, list, user, null);
		JButton btnVisualizza = new JButton("Visualizza");
		btnVisualizza.setEnabled(false);
		btnVisualizza.setFont(new Font("Tahoma", Font.PLAIN, 11));
		btnVisualizza.setBounds(258, 193, 150, 33);
		btnVisualizza.addActionListener(act5);
		if(role != 0) 
			contentPane.add(btnVisualizza);
		
		
		/*  SEPARATORE  */
		JLabel separatore = new JLabel("");
		separatore.setBounds(430, 110, 2, 387);
		border_one = BorderFactory.createLineBorder(Color.black, 2);
		separatore.setBorder(border_one);
		contentPane.add(separatore);
		
		
		
		/*  CONTENT DIVISION
		 *  Divisione del contenuto in base al ruolo dell'utente.  
		 */
		
		
		/*  -- Utente Base -- */
		if(role == 0){
			
			/*  Status  */
			JLabel lblStatus = new JLabel("Status:");
			lblStatus.setFont(new Font("Tahoma", Font.PLAIN, 11));
			lblStatus.setBounds(260, 132, 46, 14);
			contentPane.add(lblStatus);
			
			JLabel status = new JLabel("-");
			status.setFont(new Font("Tahoma", Font.BOLD, 11));
			status.setBounds(313, 132, 134, 14);
			contentPane.add(status);
			
			if(count == 0){
							
				/*  Status  */
				lblStatus.setForeground(new Color(122, 124, 122));					
				status.setText("Access Denied");
				status.setFont(new Font("Tahoma", Font.BOLD+Font.ITALIC, 11));
				status.setForeground(new Color(122, 124, 122));
				
				/*  Autore  */
				lblAutore.setForeground(new Color(122, 124, 122));					
				autore.setText("Access Denied");
				autore.setFont(new Font("Tahoma", Font.BOLD+Font.ITALIC, 11));
				autore.setForeground(new Color(122, 124, 122));
				
				/*  N° Pagine  */
				lblPagine.setForeground(new Color(122, 124, 122));			
				pagine.setText("Access Denied");
				pagine.setFont(new Font("Tahoma", Font.BOLD+Font.ITALIC, 11));
				pagine.setForeground(new Color(122, 124, 122));
				
				JLabel noOperaExist = new JLabel("<html>Digitalizzazione dei manoscritti "
						+ "in corso...</html>");
				noOperaExist.setFont(new Font("Tahoma", Font.PLAIN+Font.ITALIC, 11));
				noOperaExist.setBounds(278, 196, 150, 50);
				contentPane.add(noOperaExist);
			}
			
			
			
			/*  Operazioni concesse all'utente  */
			JLabel lbOperation = new JLabel("Pannello Utente");
			lbOperation.setFont(new Font("Tahoma", Font.BOLD, 11));
			lbOperation.setBounds(450, 108, 150, 20);
			contentPane.add(lbOperation);
			
			JLabel lbDescUserBase = new JLabel("<html>Benvenuto su DigitaLibrary.  "
					+ " Lei è un utente base e, in quanto tale, "
					+ "non ha diritto a visualizzare il contenuto delle opere "
					+ "già presenti sul nostro sistema. "
					+ "Può solo visualizzare l'elenco delle opere pubblicate.</html>");
			lbDescUserBase.setFont(new Font("Tahoma", Font.PLAIN, 10));
			lbDescUserBase.setBounds(450, 128, 175, 85);
			contentPane.add(lbDescUserBase);
			
			/* Nome utente */
			JLabel lblUsername = new JLabel("Nome Utente:");
			lblUsername.setFont(new Font("Tahoma", Font.PLAIN, 11));
			lblUsername.setBounds(450, 220, 100, 14);
			contentPane.add(lblUsername);
			
			JLabel username = new JLabel(user.getUsername());
			username.setBounds(533, 220, 134, 14);
			username.setFont(new Font("Tahoma", Font.BOLD, 11));
			contentPane.add(username);
			
			GestioneMouse act_base = new GestioneMouse( this.data, "MainFrame", list, autore, pagine, btnVisualizza, status);
			list.addMouseListener(act_base);

		}
		
		
		/* -- Utente Avanzato  -- */
		if(role == 1){
			
			/*  Status  */
			JLabel lblStatus = new JLabel("Status:");
			lblStatus.setFont(new Font("Tahoma", Font.PLAIN, 11));
			lblStatus.setBounds(260, 132, 46, 14);
			contentPane.add(lblStatus);
			
			JLabel status = new JLabel("-");
			status.setFont(new Font("Tahoma", Font.BOLD, 11));
			status.setBounds(313, 132, 134, 14);
			contentPane.add(status);
			
			if(count == 0){
							
				/*  Status  */
				lblStatus.setForeground(new Color(122, 124, 122));					
				status.setText("Access Denied");
				status.setFont(new Font("Tahoma", Font.BOLD+Font.ITALIC, 11));
				status.setForeground(new Color(122, 124, 122));
				
				/*  Autore  */
				lblAutore.setForeground(new Color(122, 124, 122));					
				autore.setText("Access Denied");
				autore.setFont(new Font("Tahoma", Font.BOLD+Font.ITALIC, 11));
				autore.setForeground(new Color(122, 124, 122));
				
				/*  N° Pagine  */
				lblPagine.setForeground(new Color(122, 124, 122));			
				pagine.setText("Access Denied");
				pagine.setFont(new Font("Tahoma", Font.BOLD+Font.ITALIC, 11));
				pagine.setForeground(new Color(122, 124, 122));
				
				btnVisualizza.setVisible(false);
				
				JLabel noOperaExist = new JLabel("<html>Digitalizzazione dei manoscritti "
						+ "in corso...</html>");
				noOperaExist.setFont(new Font("Tahoma", Font.PLAIN+Font.ITALIC, 11));
				noOperaExist.setBounds(278, 196, 150, 50);
				contentPane.add(noOperaExist);
			}
			
	
			/*  Operazioni concesse all'utente  */
			JLabel lbOperation = new JLabel("Operazioni Utente");
			lbOperation.setFont(new Font("Tahoma", Font.BOLD, 11));
			lbOperation.setBounds(450, 108, 150, 20);
			contentPane.add(lbOperation);
			
			JLabel lbDescAdvance = new JLabel("<html>Pannello operazioni Utente. "
					+ "  Siamo spiacenti ma un utente con il suo ruolo "
					+ "non è abilitato ad eseguire alcuna operazione sul "
					+ "sistema, può visualizzare le opere pubbliche.</html>");
			lbDescAdvance.setFont(new Font("Tahoma", Font.PLAIN, 10));
			lbDescAdvance.setBounds(450, 128, 165, 65);
			contentPane.add(lbDescAdvance);
			
			/* Nome utente */
			JLabel lblUsername = new JLabel("Nome Utente:");
			lblUsername.setFont(new Font("Tahoma", Font.PLAIN, 11));
			lblUsername.setBounds(450, 203, 100, 14);
			contentPane.add(lblUsername);
			
			JLabel username = new JLabel(user.getUsername());
			username.setBounds(533, 203, 134, 14);
			username.setFont(new Font("Tahoma", Font.BOLD, 11));
			contentPane.add(username);
			
			/* Ruolo utente */
			JLabel lblRole = new JLabel("Ruolo Utente:");
			lblRole.setFont(new Font("Tahoma", Font.PLAIN, 11));
			lblRole.setBounds(450, 223, 100, 14);
			contentPane.add(lblRole);
			
			JLabel UserRole = new JLabel("Utente Avanzato");
			UserRole.setBounds(533, 223, 134, 14);
			UserRole.setFont(new Font("Tahoma", Font.BOLD, 11));
			contentPane.add(UserRole);
			
			GestioneMouse act_advanced = new GestioneMouse( this.data, "MainFrame", list, autore, pagine, btnVisualizza, status);
			list.addMouseListener(act_advanced);
		}
		
		
		/*  -- STATUS OPERA --  */
		if(role > 1){
			JLabel lblStatus = new JLabel("Status:");
			lblStatus.setFont(new Font("Tahoma", Font.PLAIN, 11));
			lblStatus.setBounds(260, 132, 46, 14);
			contentPane.add(lblStatus);
			
			JLabel status = new JLabel("-");
			status.setBounds(313, 132, 134, 14);
			status.setFont(new Font("Tahoma", Font.BOLD, 11));
			contentPane.add(status);
			
			GestioneMouse act = new GestioneMouse( this.data, "MainFrame", list, autore, pagine, btnVisualizza,status);
			list.addMouseListener(act);
		}
		
		
		/*	-- Acquisitore/Trascrittore	--  */
		if(role == 2 || role == 4){
			String actioncmd;
			String ruoloUtente;
	
			if(role == 2){
				actioncmd = "Acquisisci";
				ruoloUtente = "Acquisitore";
			}else{
				actioncmd = "Trascrivi";
				ruoloUtente = "Trascrittore";
			}				
			
			/*  Operazioni concesse all'utente  */
			JLabel lbOperation = new JLabel("Operazioni Utente");
			lbOperation.setFont(new Font("Tahoma", Font.BOLD, 11));
			lbOperation.setBounds(450, 108, 150, 20);
			contentPane.add(lbOperation);
			
			JLabel lbDescAcquisitoreTrascrittore = new JLabel("<html>Pannello operazioni Utente. "
					+ "  Di seguito sono elencate le operazioni permesse "
					+ "all'utente con il suo grado nel sistema.</html>");
			lbDescAcquisitoreTrascrittore.setFont(new Font("Tahoma", Font.PLAIN, 10));
			lbDescAcquisitoreTrascrittore.setBounds(450, 128, 165, 65);
			contentPane.add(lbDescAcquisitoreTrascrittore);
			
			/* Nome utente */
			JLabel lblUsername = new JLabel("Nome Utente:");
			lblUsername.setFont(new Font("Tahoma", Font.PLAIN, 11));
			lblUsername.setBounds(450, 203, 100, 14);
			contentPane.add(lblUsername);
			
			JLabel username = new JLabel(user.getUsername());
			username.setBounds(533, 203, 134, 14);
			username.setFont(new Font("Tahoma", Font.BOLD, 11));
			contentPane.add(username);
			
			/* Ruolo utente */
			JLabel lblRole = new JLabel("Ruolo Utente:");
			lblRole.setFont(new Font("Tahoma", Font.PLAIN, 11));
			lblRole.setBounds(450, 223, 100, 14);
			contentPane.add(lblRole);
			
			JLabel UserRole = new JLabel(ruoloUtente);
			UserRole.setBounds(533, 223, 134, 14);
			UserRole.setFont(new Font("Tahoma", Font.BOLD, 11));
			contentPane.add(UserRole);
			
			Action act3 = new GestioneOpera( this, data, listaOpera, list, user, null);
			
			/*  Tasto di Acquisizione/Trascrizione */
			JButton btnAcquisisci = new JButton(actioncmd);
			btnAcquisisci.setBounds(448, 255, 140, 33);
			btnAcquisisci.setFont(new Font("Tahoma", Font.PLAIN, 11));
			btnAcquisisci.addActionListener(act3);
			contentPane.add(btnAcquisisci);
		}
		
		
		/*	-- Revisori --  */
		if(role == 3 || role == 5){
			String actioncmd;
			String ruoloUtente;
			
			if(role==5){ 
				actioncmd = "Revisiona Trascrizioni";
				ruoloUtente = "Revisore Testi";
			}else{
				actioncmd = "Revisiona Acquisizioni";
				ruoloUtente = "Revisore Immagini";
			}
			
			/*  Operazioni concesse all'utente  */
			JLabel lbOperation = new JLabel("Operazioni Utente");
			lbOperation.setFont(new Font("Tahoma", Font.BOLD, 11));
			lbOperation.setBounds(450, 108, 150, 20);
			contentPane.add(lbOperation);
			
			JLabel lbDescAcquisitoreTrascrittore = new JLabel("<html>Pannello operazioni Utente. "
					+ "  Di seguito sono elencate le operazioni permesse "
					+ "all'utente con il suo grado nel sistema.</html>");
			lbDescAcquisitoreTrascrittore.setFont(new Font("Tahoma", Font.PLAIN, 10));
			lbDescAcquisitoreTrascrittore.setBounds(450, 128, 165, 65);
			contentPane.add(lbDescAcquisitoreTrascrittore);
			
			/* Nome utente */
			JLabel lblUser = new JLabel("Nome Utente:");
			lblUser.setFont(new Font("Tahoma", Font.PLAIN, 11));
			lblUser.setBounds(450, 203, 100, 14);
			contentPane.add(lblUser);
			
			JLabel username = new JLabel(user.getUsername());
			username.setBounds(533, 203, 134, 14);
			username.setFont(new Font("Tahoma", Font.BOLD, 11));
			contentPane.add(username);
			
			/* Ruolo Utente */
			JLabel lblrole = new JLabel("Ruolo Utente:");
			lblrole.setFont(new Font("Tahoma", Font.PLAIN, 11));
			lblrole.setBounds(450, 223, 100, 14);
			contentPane.add(lblrole);
			
			JLabel UserRole = new JLabel(ruoloUtente);
			UserRole.setBounds(533, 223, 134, 14);
			UserRole.setFont(new Font("Tahoma", Font.BOLD, 11));
			contentPane.add(UserRole);
			
			/* Tasto Revisiona */
			JButton btnRevisione = new JButton(actioncmd);
			btnRevisione.setBounds(448, 255, 140, 33);
			btnRevisione.setFont(new Font("Tahoma", Font.PLAIN, 11));
			btnRevisione.addActionListener(act2);
			contentPane.add(btnRevisione);
		}
		
		/*	-- Acquisitore/Trascrittore: Cronologia operazioni effettuate e in sospeso  */
		if(role == 2 || role == 4){
			Action act4 = new GestioneAction(data, user, null);
			JButton btnCronologiaOperazioni = new JButton("Cronologia Operazioni");
			btnCronologiaOperazioni.setBounds(448, 290, 140, 33);
			btnCronologiaOperazioni.setFont(new Font("Tahoma", Font.PLAIN, 11));
			btnCronologiaOperazioni.addActionListener(act4);
			contentPane.add(btnCronologiaOperazioni);
		}
		
		/*	Admin  */
		if(role == 6){
			Action act3 = new GestioneOpera( this, data, listaOpera, list, user, null);
			
			/*  Pannello Amministratore: Operazioni Privilegiate  */
			JLabel lbOperationReserved = new JLabel("Operazioni Privilegiate");
			lbOperationReserved.setFont(new Font("Tahoma", Font.BOLD, 11));
			lbOperationReserved.setBounds(450, 108, 140, 17);
			contentPane.add(lbOperationReserved);
			
			JLabel lbDescriptionReserved = new JLabel("<html>Pannello operazioni privilegiate, "
					+ " permesse esclusivamente all'amministratore di sistema.</html>");
			lbDescriptionReserved.setFont(new Font("Tahoma", Font.PLAIN, 10));
			lbDescriptionReserved.setBounds(450, 125, 165, 65);
			contentPane.add(lbDescriptionReserved);
			
			
			/*  Pannello Amministratore: Gestione Opere  */
			JLabel lbgestioneOpere = new JLabel("Gestione opera");
			lbgestioneOpere.setFont(new Font("Tahoma", Font.BOLD, 11));
			lbgestioneOpere.setBounds(450, 195, 95, 17);
			contentPane.add(lbgestioneOpere);
			
			/*  Rendi opera Pubblica/Privata  */
			JButton btnPubblica = new JButton("Pubblica/Privata");
			btnPubblica.setBounds(448, 215, 140, 33);
			btnPubblica.setFont(new Font("Tahoma", Font.PLAIN, 11));
			btnPubblica.addActionListener(act3);
			contentPane.add(btnPubblica);
			
			JButton btnNuovaOpera = new JButton("Nuova Opera");
			btnNuovaOpera.setBounds(448, 250, 140, 33);
			btnNuovaOpera.setFont(new Font("Tahoma", Font.PLAIN, 11));
			btnNuovaOpera.addActionListener(act3);
			contentPane.add(btnNuovaOpera);
			
			JButton btnElimina = new JButton("Elimina Opera");
			btnElimina.setBounds(448, 285, 140, 33);
			btnElimina.setFont(new Font("Tahoma", Font.PLAIN, 11));
			btnElimina.addActionListener(act3);
			contentPane.add(btnElimina);
			
			JButton btnGestisciPag = new JButton("Gestisci Pagine");
			btnGestisciPag.setBounds(448, 320, 140, 33);
			btnGestisciPag.setFont(new Font("Tahoma", Font.PLAIN, 11));
			btnGestisciPag.addActionListener(act3);
			contentPane.add(btnGestisciPag);	
			
			
			/*  Pannello Amministratore: Pannello Avanzato  */
			JLabel lbadvance = new JLabel("Gestione Sistema/Utenza");
			lbadvance.setFont(new Font("Tahoma", Font.BOLD, 11));
			lbadvance.setBounds(450, 373, 160, 20);
			contentPane.add(lbadvance);
			
			JButton btnUtenti = new JButton("Gestisci Utenti");
			btnUtenti.setBounds(448, 393, 140, 33);
			btnUtenti.setFont(new Font("Tahoma", Font.PLAIN, 11));
			btnUtenti.addActionListener(act3);
			contentPane.add(btnUtenti);
			
			JButton btnNewButton = new JButton("Gestione Database");
			btnNewButton.setBounds(448, 428, 140, 33);
			btnNewButton.setFont(new Font("Tahoma", Font.PLAIN, 11));
			contentPane.add(btnNewButton);
			
			Action act6 = new GestioneAction(data, null, null);
			JButton btnCronologiaOperazioni = new JButton("Storico Operazioni");
			btnCronologiaOperazioni.setBounds(448, 463, 140, 33);
			btnCronologiaOperazioni.setFont(new Font("Tahoma", Font.PLAIN, 11));
			btnCronologiaOperazioni.addActionListener(act6);
			contentPane.add(btnCronologiaOperazioni);
			
			btnNewButton.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					GestisciDBFrame f = new GestisciDBFrame();
					f.setVisible(true);
				}
			});
		}
	}
}
/*  END class MainFrame  */