package DigitaLibrary.view.frame;

import java.awt.Color;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import DigitaLibrary.model.Biblioteca;
import DigitaLibrary.view.listener.ControllerInterface;

import javax.swing.JTextField;
import javax.swing.JLabel;
import java.awt.Font;

import javax.swing.Action;
import javax.swing.DefaultListModel;
import javax.swing.JButton;

/*	AGGIUNGI NUOVA OPERA
 * 	Aggiunta di una nuova opera nel database.
 * 	Operazione consentita solo all'amministratore.
 */

public class NuovaOperaFrame extends JFrame {

	private static final long serialVersionUID = 1L;
	
	private JPanel contentPane;
	private JTextField titoloField;
	private JTextField autoreField;
	private JTextField npagineField;


	public NuovaOperaFrame(Biblioteca data, DefaultListModel<String> list) {
		
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setLocation(493, 120);
		setSize(258, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		contentPane.setBackground(new Color(231, 214, 182));

		/* -- Titolo finestra -- */
		JLabel lblAggiungiOpera = new JLabel("Nuova Opera");
		lblAggiungiOpera.setFont(new Font("Tahoma", Font.BOLD, 16));
		lblAggiungiOpera.setBounds(73, 21, 152, 24);
		contentPane.add(lblAggiungiOpera);
		
		JLabel lbdesc_1 = new JLabel("Aggiungi una nuova opera su DigitaLibrary.");
		JLabel lbdesc_2 = new JLabel("(Attenzione, tutti i campi sono obligatori)");
		lbdesc_1.setFont(new Font("Tahoma", Font.PLAIN, 10));
		lbdesc_2.setFont(new Font("Tahoma", Font.PLAIN, 10));
		lbdesc_1.setBounds(34, 42, 210, 20);
		lbdesc_2.setBounds(40, 56, 210, 20);
		contentPane.add(lbdesc_1);
		contentPane.add(lbdesc_2);

		
		/* -- Titolo dell'opera -- */
		JLabel lblTitolo = new JLabel("Titolo");
		lblTitolo.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblTitolo.setBounds(110, 90, 46, 14);
		contentPane.add(lblTitolo);
		
		titoloField = new JTextField();
		titoloField.setFont(new Font("Tahoma", Font.PLAIN, 11));
		titoloField.setBounds(45, 105, 162, 20);
		titoloField.setBorder(new EmptyBorder(2, 2, 2, 2));
		titoloField.setHorizontalAlignment(JTextField.CENTER);
		contentPane.add(titoloField);
		titoloField.setColumns(10);
		
		/* -- Autore dell'opera -- */
		JLabel lblAutore = new JLabel("Autore");
		lblAutore.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblAutore.setBounds(108, 130, 46, 14);
		contentPane.add(lblAutore);
		
		autoreField = new JTextField();
		autoreField.setFont(new Font("Tahoma", Font.PLAIN, 11));
		autoreField.setBounds(45, 145, 162, 20);
		autoreField.setBorder(new EmptyBorder(2, 2, 2, 2));
		autoreField.setHorizontalAlignment(JTextField.CENTER);
		contentPane.add(autoreField);
		autoreField.setColumns(10);	
		
		/* -- Numero di pagine -- */
		JLabel lblN = new JLabel("n\u00B0 Pagine");
		lblN.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblN.setBounds(102, 166, 57, 34);
		contentPane.add(lblN);
		
		npagineField = new JTextField();
		npagineField.setFont(new Font("Tahoma", Font.PLAIN, 11));
		npagineField.setBounds(86, 191, 86, 20);
		npagineField.setBorder(new EmptyBorder(2, 2, 2, 2));
		npagineField.setHorizontalAlignment(JTextField.CENTER);
		contentPane.add(npagineField);
		npagineField.setColumns(10);
		
		
		/* -- Tasto Crea opera -- */
		JButton btnCreaOpera = new JButton("Crea");
		btnCreaOpera.setFont(new Font("Tahoma", Font.PLAIN, 11));
		btnCreaOpera.setBounds(95, 230, 97, 35);
		btnCreaOpera.setSize(70,35);
		contentPane.add(btnCreaOpera);
		Action control = new ControllerInterface(this, data, list, titoloField, autoreField, npagineField, null);
		btnCreaOpera.addActionListener(control);

		
	}
}
/* END Class NuovaOperaFrame */