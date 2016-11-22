package DigitaLibrary.View;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.JLabel;
import javax.swing.table.DefaultTableModel;
import javax.swing.border.LineBorder;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import javax.swing.BorderFactory;


import DigitaLibrary.Controller.GestioneAction;
import DigitaLibrary.Model.Biblioteca;

import java.awt.Color;
import java.awt.Font;

import javax.swing.JScrollPane;

/* 	HISTORY FRAME
 * 	Frame contente la cronologia delle operazioni effettuate dagli utenti. 
 */

public class HistoryFrame extends JFrame {

	private static final long serialVersionUID = -8651570519321543808L;
	private JPanel contentPane;
	private JTable table_1;
	private static Border border_one;
	private static Border border_two;


	public HistoryFrame(Biblioteca data) {
		
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setSize(646, 307);
		setLocation(295, 96);
		setResizable(false);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setBackground(new Color(220, 220, 220));
		contentPane.setLayout(null);
		
		
		/* -- Titolo finestra -- */
		JLabel lbTitolo = new JLabel("Storico Operazioni");
		lbTitolo.setFont(new Font("Arial", Font.BOLD, 16));
		lbTitolo.setBounds(260, 0, 150, 45);
		contentPane.add(lbTitolo);
		
		/*  SEPARATORE_0  */
		JLabel separatore_0 = new JLabel("");
		separatore_0.setBounds(20, 6, 2, 30);
		border_two = BorderFactory.createLineBorder(new Color(220, 220, 220), 2);
		separatore_0.setBorder(border_two);
		contentPane.add(separatore_0);
		
		/*  SEPARATORE_1  */
		JLabel separatore_1 = new JLabel("");
		separatore_1.setBounds(624, 6, 2, 30);
		border_two = BorderFactory.createLineBorder(new Color(220, 220, 220), 2);
		separatore_1.setBorder(border_two);
		contentPane.add(separatore_1);
		
		/*  BACKGROUND LINE  */
		JLabel back = new JLabel("");
		back.setBounds(0, 11, 650, 25);
		back.setBackground(new Color(185, 200, 210));
		back.setOpaque(true);
		back.setLayout(null);
		contentPane.add(back);

		
		/* -- Tabella riassuntiva -- */
		table_1 = new JTable();
		table_1.setBounds(15, 30, 620, 210);
		table_1.setEnabled(false);
		DefaultTableModel model = new DefaultTableModel(
				new Object[][] {
					
				},
				new String[] {
					"User", "Opera", "Pagina", "Tipo", "Status", "Report"
				}
		);
		table_1.setModel(model);
		table_1.getColumnModel().getColumn(0).setPreferredWidth(110);
		table_1.getColumnModel().getColumn(1).setPreferredWidth(160);
		table_1.getColumnModel().getColumn(2).setPreferredWidth(57);
		table_1.getColumnModel().getColumn(3).setPreferredWidth(110);
		table_1.getColumnModel().getColumn(4).setPreferredWidth(120);
		table_1.getColumnModel().getColumn(5).setPreferredWidth(250);
		border_one = BorderFactory.createMatteBorder(0, 0, 1, 0, new Color(230, 230, 230));
		table_1.setBorder(border_one);
		


		/* -- Contenitore informazioni -- */
		JScrollPane scroll = new JScrollPane(table_1);
		scroll.setBounds(20, 45, 606, 220);
		scroll.setBorder(new EmptyBorder(1, 1, 1, 1));
		scroll.setBorder(new LineBorder(new Color(17, 128, 180)));
		contentPane.add(scroll);
		
		GestioneAction act = new GestioneAction(data, null, model);
		act.retrieveAll();		
	}
}
/* END Class HistoryFrame */