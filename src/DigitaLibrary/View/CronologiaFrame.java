package DigitaLibrary.View;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.table.DefaultTableModel;
import javax.swing.BorderFactory;
import javax.swing.border.LineBorder;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;

import DigitaLibrary.Controller.GestioneAction;
import DigitaLibrary.Model.Biblioteca;
import DigitaLibrary.Model.User;

import java.awt.Color;
import java.awt.Font;

/*  CRONOLOGIA OPERAZIONI
 * 	Finestra contente la cronologia delle operazioni effettuate e quelle ancora da effettuare.
 */

public class CronologiaFrame extends JFrame {

	private static final long serialVersionUID = 6531347665039617886L;
	private JPanel contentPane;
	private JTable table_1;
	private static Border border_one;
	private static Border border_two;


	public CronologiaFrame(Biblioteca data, User user) {
		
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
		JLabel lbTitolo = new JLabel("Cronologia Operazioni");
		lbTitolo.setFont(new Font("Arial", Font.BOLD, 16));
		lbTitolo.setBounds(245, 0, 190, 45);
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
		
		/*  -- Tabella riassuntiva -- */
		table_1 = new JTable();
		table_1.setBounds(10, 32, 590, 190);
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
		border_one = BorderFactory.createMatteBorder(0, 0, 1, 0, new Color(220, 220, 220));
		table_1.setBorder(border_one);

		/* -- Contenitore informazioni -- */
		JScrollPane scroll = new JScrollPane(table_1);
		scroll.setBounds(20, 45, 606, 220);		
		scroll.setBorder(new EmptyBorder(1, 1, 1, 1));
		scroll.setBorder(new LineBorder(new Color(17, 128, 180)));
		contentPane.add(scroll);
		
		GestioneAction act = new GestioneAction(data, user, model);
		act.retrieve();
	}
}
/*  END class CronologiaFrame  */