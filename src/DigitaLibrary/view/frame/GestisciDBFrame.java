package DigitaLibrary.view.frame;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JButton;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/*
 * GESTIONE DEL SISTEMA
 * Frame per il cambio Database: creazione e aggiornamento tabelle.
 */
public class GestisciDBFrame extends JFrame {

	private static final long serialVersionUID = 319603074783599395L;
	
	private JPanel contentPane;
	private JTextField textField;
	private JTextField textField_1;
	private JPasswordField passwordField;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					GestisciDBFrame frame = new GestisciDBFrame();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public GestisciDBFrame() {
		
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setLocation(465, 115);
		setSize(316, 315);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		contentPane.setBackground(new Color(218, 220, 225));
		
		
		/*  -- Titolo frame -- */
		JLabel lbgestisciDB = new JLabel("Gestione Sistema");
		lbgestisciDB.setFont(new Font("Tahoma", Font.BOLD, 15));
		lbgestisciDB.setBounds(92, 20, 162, 24);
		contentPane.add(lbgestisciDB);
		
		JLabel lbdesc_1 = new JLabel("DigitaLibrary permette il cambio di Database.");
		JLabel lbdesc_2 = new JLabel("Riempire i campi con i nuovi parametri");
		lbdesc_1.setFont(new Font("Tahoma", Font.PLAIN, 10));
		lbdesc_2.setFont(new Font("Tahoma", Font.PLAIN, 10));
		lbdesc_2.setHorizontalAlignment(JLabel.CENTER);
		lbdesc_1.setHorizontalAlignment(JLabel.CENTER);
		lbdesc_1.setBounds(60, 35, 198, 24);
		lbdesc_2.setBounds(60, 46, 198, 24);
		contentPane.add(lbdesc_1);
		contentPane.add(lbdesc_2);
		
		
		/*	-- Server -- */
		JLabel lblServer = new JLabel("Server Name");
		lblServer.setFont(new Font("Thaoma", Font.BOLD, 11));
		lblServer.setBounds(113, 80, 80, 14);
		lblServer.setHorizontalAlignment(JLabel.CENTER);
		contentPane.add(lblServer);
		
		textField = new JTextField();
		textField.setBounds(25, 95, 260, 20);
		textField.setHorizontalAlignment(JTextField.CENTER);
		textField.setBorder(new EmptyBorder(2, 2, 2, 2));
		contentPane.add(textField);
		textField.setColumns(10);
		
		/* -- Username -- */
		JLabel lblUser = new JLabel("Username");
		lblUser.setFont(new Font("Thaoma", Font.BOLD, 11));
		lblUser.setBounds(121, 130, 65, 14);
		lblUser.setHorizontalAlignment(JLabel.CENTER);
		contentPane.add(lblUser);
		
		textField_1 = new JTextField();
		textField_1.setBounds(65, 145, 175, 20);
		textField_1.setBorder(new EmptyBorder(2, 2, 2, 2));
		textField_1.setHorizontalAlignment(JTextField.CENTER);
		contentPane.add(textField_1);
		textField_1.setColumns(10);
		
		/* -- Password -- */
		JLabel lblPass = new JLabel("Password");
		lblPass.setFont(new Font("Thaoma", Font.BOLD, 11));
		lblPass.setBounds(125, 170, 65, 14);
		contentPane.add(lblPass);
		
		passwordField = new JPasswordField();
		passwordField.setBounds(65, 185, 175, 20);
		passwordField.setBorder(new EmptyBorder(2, 2, 2, 2));
		passwordField.setHorizontalAlignment(JPasswordField.CENTER);
		contentPane.add(passwordField);
		passwordField.setColumns(10);

		
		/* -- Invio nuove informazioni -- */
		JButton btnModifica = new JButton("Aggiorna");
		btnModifica.addActionListener(new ActionListener() {
			@SuppressWarnings("deprecation")
			public void actionPerformed(ActionEvent arg0) {
				try {
					FileWriter writer = new FileWriter("db.txt");
					writer.write(textField.getText());
		            writer.write("\r\n");   
		            writer.write(textField_1.getText());
		            writer.write("\r\n");   
		            writer.write(passwordField.getText());
		            writer.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		});
		btnModifica.setBounds(34, 230, 111, 32);
		contentPane.add(btnModifica);
		
		
		/* -- Crea nuove tabelle nel database -- */
		JButton btnCreaTabelle = new JButton("Crea Tabelle");
		btnCreaTabelle.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				try {
					FileReader reader = new FileReader("dump.sql");
		            BufferedReader bufferedReader = new BufferedReader(reader);
		            String line;
		            String dump = "";
		            while ((line = bufferedReader.readLine()) != null) {
		            	dump+=line;
		            }
		            reader.close();
					@SuppressWarnings("deprecation")
					Connection con=DriverManager.getConnection(textField.getText(), textField_1.getText() , passwordField.getText());
					PreparedStatement pst=con.prepareStatement(dump);
					pst.executeUpdate();
				} catch (IOException | SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});
		btnCreaTabelle.setBounds(164, 230, 111, 32);
		contentPane.add(btnCreaTabelle);
	}
}
/*  END class GestisciDBFrame  */