package DigitaLibrary.view.frame;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;

import javax.swing.Action;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import DigitaLibrary.model.Biblioteca;
import DigitaLibrary.view.listener.ControllerInterface;

/*
 * LOGIN/REGISTRAZIONE
 * Finestra di avvio e di Login/Registrazione
 */

public class LoginFrame extends JFrame {

	private static final long serialVersionUID = -2190644274278007540L;
	private JPanel contentPane;
	private JPasswordField passwordField;
	private JTextField userField;
	Biblioteca data = new Biblioteca();

	
	public static void main(String[] args) {
		
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					LoginFrame frame = new LoginFrame();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public LoginFrame() {
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(500, 170, 290, 290);
		setSize(275, 295);
		setResizable(false);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(2, 2, 2, 2));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		contentPane.setBackground(new Color(67, 168, 195));

		
		/* -- Nome dell'applicazione -- */
		JLabel lbDigitlibrary = new JLabel("DigitaLibrary");
		lbDigitlibrary.setFont(new Font("Thaoma", Font.BOLD, 29));
		lbDigitlibrary.setBounds(42, 13, 210, 56);
		contentPane.add(lbDigitlibrary);
		
		/* LOGIN */
		
		/* -- Login - Username -- */
		JLabel lbUsername = new JLabel("Username");
		lbUsername.setFont(new Font("Thaoma", Font.BOLD, 11));
		lbUsername.setBounds(95, 93, 81, 14);
		lbUsername.setHorizontalAlignment(JLabel.CENTER);
		contentPane.add(lbUsername);
		
		userField = new JTextField();
		userField.setFont(new Font("Thaoma", Font.PLAIN, 11));
		userField.setBounds(56, 109, 160, 20);
		userField.setBorder(new EmptyBorder(2, 2, 2, 2));
		userField.setHorizontalAlignment(JTextField.CENTER);
		contentPane.add(userField);
		userField.setColumns(10);
		
		/* -- Login - Password -- */
		JLabel lblPassword = new JLabel("Password");
		lblPassword.setFont(new Font("Thaoma", Font.BOLD, 11));
		lblPassword.setBounds(95, 135, 81, 14);
		lblPassword.setHorizontalAlignment(JLabel.CENTER);
		contentPane.add(lblPassword);
		
		passwordField = new JPasswordField();
		passwordField.setFont(new Font("Thaoma", Font.PLAIN, 11));
		passwordField.setBounds(56, 150, 160, 20);
		passwordField.setBorder(new EmptyBorder(2, 2, 2, 2));
		passwordField.setHorizontalAlignment(JTextField.CENTER);
		contentPane.add(passwordField);	
		
		
		/* -- Invio dati al Controller -- */
		Action actLog = new ControllerInterface(this, data, null, null, userField, passwordField, null);
		
		/* -- Login - Tasto -- */
		JButton btnLogin = new JButton("Login");
		btnLogin.setFont(new Font("Calibri", Font.BOLD, 11));
		btnLogin.setBounds(154, 191, 98, 29);
		btnLogin.setSize(70,30);
		btnLogin.addActionListener(actLog);
		contentPane.add(btnLogin);
		
		
		/* REGISTRAZIONE */
		
		JLabel lbdesc_1 = new JLabel("Sei un nuovo utente?");
		lbdesc_1.setFont(new Font("Tahoma", Font.PLAIN, 10));
		lbdesc_1.setBounds(56, 178, 210, 20);
		lbdesc_1.setForeground(new Color(50, 50, 60));
		contentPane.add(lbdesc_1);
		
		/* -- Registrazione - Tasto -- */
		Action actReg = new ControllerInterface(this, data, null, null, null, null, null);
		JButton btnRegistrati = new JButton("Registrati");
		btnRegistrati.setFont(new Font("Calibri", Font.BOLD, 11));
		btnRegistrati.setBounds(49, 191, 98, 29);
		btnRegistrati.setSize(82,30);
		btnRegistrati.addActionListener(actReg);
		contentPane.add(btnRegistrati);
		
		
		/* -- Marchio TEAM -- */
		JLabel team = new JLabel("Team@24CinL");
		team.setFont(new Font("Tahoma", Font.PLAIN, 8));
		team.setBounds(110, 253, 210, 20);
		contentPane.add(team);
	}

}
/*  END class LoginFrame  */