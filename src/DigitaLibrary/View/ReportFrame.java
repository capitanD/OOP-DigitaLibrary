package DigitaLibrary.View;

import java.awt.Font;
import java.awt.Color;

import javax.swing.Action;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JTextField;
import javax.swing.JLabel;

import DigitaLibrary.Controller.GestionePagina;
import DigitaLibrary.Model.Biblioteca;


/*  MINI-FINESTRA PER I REPORT
 *  Finestra contenente un piccolo text-field in cui immettere il commento relativo all'operazione effettuata.
 */

public class ReportFrame extends JFrame {
	
	private static final long serialVersionUID = 8030496311663964480L;	
	private JPanel contentPane;
	private JTextField reportField;
	
	public ReportFrame(Biblioteca data, String opera, int pagine, int operation) throws Exception {
		
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setSize(285, 135);
		setLocation(480, 205);
		
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setBackground(new Color(243, 185, 0));
		contentPane.setLayout(null);
		
		/* -- Titolo finestra  -- */
		JLabel lbReport = new JLabel("Report");
		lbReport.setFont(new Font("Thaoma", Font.BOLD, 17));
		lbReport.setBounds(110, 12, 60, 23);
		lbReport.setHorizontalAlignment(JLabel.CENTER);
		contentPane.add(lbReport);
		
		JLabel lbdesc = new JLabel("Giustifica la scelta e conferma l'operazione.");
		lbdesc.setFont(new Font("Thaoma", Font.PLAIN, 10));
		lbdesc.setBounds(12, 28, 260, 30);
		lbdesc.setHorizontalAlignment(JLabel.CENTER);
		contentPane.add(lbdesc);
		
		reportField = new JTextField();
		reportField.setFont(new Font("Thaoma", Font.PLAIN, 11));
		reportField.setBounds(45, 58, 190, 19);
		reportField.setBorder(new EmptyBorder(2, 2, 2, 2));
		contentPane.add(reportField);
		
		/* -- Pulsante di operazione confermata -- */
		Action act = new GestionePagina(this, data, reportField, opera, pagine, operation);
		JButton btnConfirm = new JButton("Conferma");
		btnConfirm.setSize(95, 30);
		btnConfirm.setLocation(92, 80);
		btnConfirm.addActionListener(act);
		contentPane.add(btnConfirm);
		
	}	
}
/* END class REPORTFRAME */