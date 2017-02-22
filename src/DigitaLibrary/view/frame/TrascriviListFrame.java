package DigitaLibrary.view.frame;

import javax.swing.Action;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.JScrollPane;

import DigitaLibrary.model.Biblioteca;
import DigitaLibrary.model.Opera;
import DigitaLibrary.model.Page;
import DigitaLibrary.model.User;
import DigitaLibrary.view.listener.ControllerInterface;

import java.awt.Color;
import java.awt.Dimension;


/*	MINI-FINESTRA CON LISTA TRASCRIZIONI DISPONIBILI
 * 	tabella riassuntiva delle trascrizioni disponibili ancora da effettuare.
 */

public class TrascriviListFrame extends JFrame {

	private static final long serialVersionUID = 6729311541480240059L;
	private JPanel contentPane;
	private JTable table_1;

	public TrascriviListFrame(Biblioteca data, User user) {
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(415, 120, 442, 252);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setBackground(new Color(210, 210, 210));

	
		/* -- Tabella riassuntiva delle trascrizioni disponibili -- */
		table_1 = new JTable();
		table_1.setBounds(10, 32, 400, 163);
		DefaultTableModel model = new DefaultTableModel(
				new Object[][] {
					
				},
				new String[] {
					"Opera", "Pagina"
				}
			);
		table_1.setModel(model);
		table_1.getColumnModel().getColumn(0).setPreferredWidth(151);
		table_1.getColumnModel().getColumn(1).setPreferredWidth(50);
		
		for(Page p : data.getPageList()){
			if(p.getStatus() == 1){
			Opera next = new Opera(p.getOperaID());
			model.addRow(new Object[]{ next.getTitle(), p.getNumber() });
			}
		}
		
		JScrollPane scroll = new JScrollPane(table_1);
		scroll.setPreferredSize(new Dimension(400,163));
		scroll.setBorder(new LineBorder(new Color(17, 128, 180)));
		contentPane.add(scroll);
		
        /* -- Tasto: inizia la trascrizione -- */
		Action act = new ControllerInterface(this, data, null, table_1, null, model, user);
		JButton btnTrascrivi = new JButton("Esegui Trascrizione");
		btnTrascrivi.setBounds(127, 250, 180, 38);
		btnTrascrivi.addActionListener(act);
		contentPane.add(btnTrascrivi);

	}
}
/* END Class TrascriviListFrame */