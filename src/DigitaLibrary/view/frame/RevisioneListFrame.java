package DigitaLibrary.view.frame;

import java.awt.Color;
import java.awt.Dimension;
import java.util.Iterator;
import java.util.LinkedList;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.Action;
import javax.swing.JButton;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.BorderFactory;
import javax.swing.table.DefaultTableModel;

import DigitaLibrary.model.Biblioteca;
import DigitaLibrary.model.Opera;
import DigitaLibrary.model.Page;
import DigitaLibrary.view.listener.ControllerInterface;

/*	MINI-FINESTRA CON LISTA REVISIONI DISPONIBILI
 * 	tabella riassuntiva delle revisioni delle acquisizioni disponibili.
 */

public class RevisioneListFrame extends JFrame {

	private static final long serialVersionUID = -4690482798943652820L;
	private JPanel contentPane;
	private JTable table_acq;
	private String actioncmd;
	private static Border border_one;

	
	public RevisioneListFrame(Biblioteca data, String type) {
				
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(410, 120, 442, 252);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setBackground(new Color(210, 210, 210));

		
		/* -- Tabella riassuntiva delle revisioni disponibili -- */
		table_acq = new JTable();
		table_acq.setBounds(10, 32, 400, 163);
		DefaultTableModel model = new DefaultTableModel(
			new Object[][] {
			},
			new String[] {
				"Opera", "Autore", "Pagina"
			}
		);
		table_acq.setModel(model);
		table_acq.getColumnModel().getColumn(0).setPreferredWidth(145);
		table_acq.getColumnModel().getColumn(1).setPreferredWidth(175);
		border_one = BorderFactory.createMatteBorder(0, 0, 1, 0, new Color(230, 230, 230));
		table_acq.setBorder(border_one);

		JScrollPane scroll = new JScrollPane(table_acq);
		scroll.setPreferredSize(new Dimension(400, 163));
		scroll.setBorder(new LineBorder(new Color(17, 128, 180)));
		contentPane.add(scroll);
		
        if(type.equals("img")){
			LinkedList<Page> listaPagine = data.getPageList();
			Iterator<Page> itr = listaPagine.iterator();
			while(itr.hasNext()){
				Page next = itr.next();
				if(next.getStatus() == 0){
					Opera toAdd = new Opera(next.getOperaID());
					model.addRow(new Object[]{ toAdd.getTitle(), toAdd.getAuthor(), next.getNumber()});
				}
			}
		} else{
			LinkedList<Page> listaPagine = data.getPageList();
			Iterator<Page> itr = listaPagine.iterator();
			while(itr.hasNext()){
				Page next = itr.next();
				if(next.getStatus() == 2){
					Opera toAdd = new Opera(next.getOperaID());
					model.addRow(new Object[]{ toAdd.getTitle(), toAdd.getAuthor(), next.getNumber()});
				}
			}
		}
		
        if(type.equals("img")) 
        	actioncmd="Revisiona Acquisizione";
        else 
        	actioncmd="Revisiona Trascrizione";
        
        /* -- Tasto: inizia la revisione -- */
		Action act = new ControllerInterface(this, data, null, table_acq, null, model, null);
		JButton btnRevisiona = new JButton(actioncmd);
		btnRevisiona.setBounds(127, 245, 180, 38);
		btnRevisiona.addActionListener(act);
		contentPane.add(btnRevisiona);
	}
}
