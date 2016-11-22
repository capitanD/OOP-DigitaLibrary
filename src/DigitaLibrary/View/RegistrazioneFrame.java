package DigitaLibrary.View;

import java.awt.Color;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;


import DigitaLibrary.Controller.GestioneUser;
import DigitaLibrary.Model.Biblioteca;

import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.Action;
import javax.swing.JButton;
import java.awt.Font;

/*
 * 	REGISTRAZIONE UTENTI
 * 	Finestra di registrazione di un nuovo utente.
 */

public class RegistrazioneFrame extends JFrame {

	private static final long serialVersionUID = 9177906409615893370L;
	private JPanel contentPane;
	private JTextField userField;
	private JPasswordField passField;
	private JPasswordField passField_repeat;
	private JTextField emailField;
	 
	public RegistrazioneFrame(Biblioteca data) {
		
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(500, 170, 265, 314);
		setSize(275, 310);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		contentPane.setBackground(new Color(250, 250, 250));
		
		/* -- Titolo finestra -- */
		JLabel lblRegistrazione = new JLabel("Registrazione");
		lblRegistrazione.setFont(new Font("Tahoma", Font.BOLD, 16));
		lblRegistrazione.setBounds(78, 8, 129, 50);
		contentPane.add(lblRegistrazione);
		
		JLabel lbdesc = new JLabel("Inserisci qui i tuo dati personali.");
		lbdesc.setFont(new Font("Tahoma", Font.PLAIN, 10));
		lbdesc.setHorizontalAlignment(JLabel.CENTER);
		lbdesc.setBounds(60, 24, 150, 50);
		contentPane.add(lbdesc);
		
		/* -- Username -- */
		JLabel lblUsername = new JLabel("Username");
		lblUsername.setFont(new Font("Calibri", Font.BOLD, 11));
		lblUsername.setBounds(47, 75, 73, 14);
		contentPane.add(lblUsername);
		
		userField = new JTextField();
		userField.setBounds(46, 90, 181, 22);
		userField.setFont(new Font("Thaoma", Font.PLAIN, 11));
		userField.setBorder(new EmptyBorder(1, 1, 1, 1));
		userField.setBorder(new LineBorder(new Color(17, 128, 180)));
		contentPane.add(userField);
		userField.setColumns(10);
		
		/* -- Password -- */
		JLabel lblPassword = new JLabel("Password");
		lblPassword.setFont(new Font("Calibri", Font.BOLD, 11));
		lblPassword.setBounds(47, 115, 70, 14);
		contentPane.add(lblPassword);
		
		passField = new JPasswordField();
		passField.setBounds(46, 130, 181, 21);
		passField.setFont(new Font("Thaoma", Font.PLAIN, 11));
		passField.setBorder(new EmptyBorder(1, 1, 1, 1));
		passField.setBorder(new LineBorder(new Color(17, 128, 180)));
		contentPane.add(passField);
		passField.setColumns(10);
		
		JLabel lbrepeatPwd = new JLabel("Repeat password");
		lbrepeatPwd.setFont(new Font("Calibri", Font.BOLD, 11));
		lbrepeatPwd.setBounds(47, 155, 100, 14);
		contentPane.add(lbrepeatPwd);
		
		passField_repeat = new JPasswordField();
		passField_repeat.setBounds(46, 170, 181, 21);
		passField_repeat.setFont(new Font("Thaoma", Font.PLAIN, 11));
		passField_repeat.setBorder(new EmptyBorder(1, 1, 1, 1));
		passField_repeat.setBorder(new LineBorder(new Color(17, 128, 180)));
		contentPane.add(passField_repeat);
		passField_repeat.setColumns(10);
		
		
		/* -- Email -- */
		JLabel lblEmail = new JLabel("Email");
		lblEmail.setFont(new Font("Calibri", Font.BOLD, 11));
		lblEmail.setBounds(47, 195, 46, 14);
		contentPane.add(lblEmail);
		
		emailField = new JTextField();
		emailField.setBounds(46, 210, 181, 21);
		emailField.setFont(new Font("Thaoma", Font.PLAIN, 11));
		emailField.setBorder(new EmptyBorder(1, 1, 1, 1));
		emailField.setBorder(new LineBorder(new Color(17, 128, 180)));
		contentPane.add(emailField);
		emailField.setColumns(10);
		
		/* -- Invio dati -- */
		Action act = new GestioneUser(this, data, userField, passField, passField_repeat, emailField);
		
		JButton btnConferma = new JButton("Conferma");
		btnConferma.setFont(new Font("Tahoma", Font.PLAIN, 11));
		btnConferma.setBounds(95, 240, 105, 31);
		btnConferma.setSize(78, 33);
		btnConferma.addActionListener(act);
		contentPane.add(btnConferma);					
	}
}
/* END Class RegistrazioneFrame */