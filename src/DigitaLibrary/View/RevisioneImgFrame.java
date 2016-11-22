package DigitaLibrary.View;

import java.awt.Font;
import java.awt.Color;
import java.awt.image.BufferedImage;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.border.Border;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;

import java.net.URL;

import DigitaLibrary.Controller.GestionePagina;
import DigitaLibrary.Model.Biblioteca;


/*  FINESTRA DI PREPARAZIONE ALLA REVISIONE
 * 	Anteprima dell'immagine acquisita da revisionare e accettare/rifiutare.
 */

public class RevisioneImgFrame extends JFrame {


	private static final long serialVersionUID = -1623881826062829714L;
	
	private JPanel contentPane;
	private BufferedImage image = null;
	private static Border border_one;
	private static Border border_two;
	
	public RevisioneImgFrame(Biblioteca data, String opera, int page) throws Exception {
		
		setSize(735, 660);
        setLocation(250, 40);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		contentPane.setBackground(new Color(96, 144, 170));
		
		
		/* -- Titolo finestra -- */
		JLabel lblPreview = new JLabel("Acquisizione");
		lblPreview.setBounds(205, 24, 182, 20);
		lblPreview.setFont(new Font("Tahoma", Font.BOLD, 15));
		contentPane.add(lblPreview);
		
		/* -- Numero pagina acquisita -- */
		JLabel lbpageImg = new JLabel("Pagina "+page);
		lbpageImg.setFont(new Font("Tahoma", Font.PLAIN, 11));
		lbpageImg.setBounds(240, 602, 100, 27);
		contentPane.add(lbpageImg);
		
		/* -- Contenitore immagine -- */
		JPanel panel_container = new JPanel();
		panel_container.setBounds(15, 52, 526, 610);
		panel_container.setSize(490, 550);
		panel_container.setBorder(null);
		panel_container.setBackground(new Color(96, 144, 170));	
		contentPane.add(panel_container);
		
		JLabel lblImage = new JLabel("");
		JScrollPane scroll = new JScrollPane(lblImage);
		border_two = BorderFactory.createMatteBorder(2, 2, 2, 2, new Color(240, 240, 240));
		scroll.setBorder(border_two);
		panel_container.add(scroll);
		
		String operaurl = opera.replaceAll("\\s","%20");
		image = ImageIO.read(new URL("http://frank.altervista.org/"+operaurl+"/"+page+".jpg"));
		
		/* -- Invio dati al controller -- */
		GestionePagina act = new GestionePagina(this, data, null, lblImage, opera, page);
		image =  act.scaleImage(480, 540, image);
		act.setImage(image);
		ImageIcon imageIcon = new ImageIcon(image);
		lblImage.setIcon(imageIcon);
		
		/* -- Zoom -- */
		JLabel lbZoom = new JLabel("Zoom");
		lbZoom.setFont(new Font("Tahoma", Font.PLAIN, 11));
		lbZoom.setBounds(20, 602, 100, 27);
		contentPane.add(lbZoom);
		
		JButton btnZoom = new JButton(" + ");
		btnZoom.setFont(new Font("Tahoma", Font.PLAIN, 11));
		btnZoom.setBounds(46, 605, 40, 23);
		btnZoom.addActionListener(act);
		contentPane.add(btnZoom);
		
		JButton btnZoom_1 = new JButton(" - ");
		btnZoom_1.setFont(new Font("Tahoma", Font.PLAIN, 11));
		btnZoom_1.setBounds(74, 605, 40, 23);
		btnZoom_1.addActionListener(act);
		contentPane.add(btnZoom_1);
		
		
		/* -- Descrizione opera -- */
		
		/* -- TITOLO -- */
		JLabel lblOpera = new JLabel("Opera:");
		lblOpera.setFont(new Font("Tahoma", Font.PLAIN, 11));
		lblOpera.setBounds(520, 65, 46, 14);
		contentPane.add(lblOpera);
		
		JLabel labelTitolo = new JLabel("");
		labelTitolo.setFont(new Font("Tahoma", Font.BOLD, 11));
		labelTitolo.setBounds(580, 65, 124, 14);
		contentPane.add(labelTitolo);
		labelTitolo.setText(opera);

		
		/* -- N° PAGINA -- */
		JLabel lblPagina = new JLabel("Pagina:");
		lblPagina.setFont(new Font("Tahoma", Font.PLAIN, 11));
		lblPagina.setBounds(520, 85, 46, 14);
		contentPane.add(lblPagina);
		
		JLabel labeln = new JLabel("");
		labeln.setFont(new Font("Tahoma", Font.BOLD, 11));
		labeln.setBounds(580, 85, 124, 14);
		labeln.setText(Integer.toString(page));
		contentPane.add(labeln);
		
		
		/*  SEPARATORE  */
		JLabel separate = new JLabel("");
		separate.setBounds(520, 105, 190, 2);
		border_one = BorderFactory.createLineBorder(Color.black, 2);
		separate.setBorder(border_one);
		contentPane.add(separate);
		
		/* -- Descrizione dell'operazione -- */
		JLabel desc_operation = new JLabel("<html>Controlla se l'immagine è stata acquisita correttamente, "
				+ "e con una buona definizione per essere poi trascritta."
				+ "<br>Clicca 'Approva' o 'Rifiuta' per confermare o annullare l'acquisizione.</html>");
		desc_operation.setFont(new Font("Tahoma", Font.PLAIN, 9));
		desc_operation.setBounds(526, 124, 190, 70);
		contentPane.add(desc_operation);
		
		/* -- Conferma/Rifiuta acquisizione -- */
		JButton btnApprova = new JButton("Approva");
		btnApprova.setFont(new Font("Tahoma", Font.PLAIN, 11));
		btnApprova.setBounds(530, 195, 80, 23);
		btnApprova.addActionListener(act);
		contentPane.add(btnApprova);
		
		JButton btnRifiuta = new JButton("Rifiuta");
		btnRifiuta.setFont(new Font("Tahoma", Font.PLAIN, 11));
		btnRifiuta.setBounds(621, 195, 80, 23);
		btnRifiuta.addActionListener(act);
		contentPane.add(btnRifiuta);
		
		
		/* -- Marchio TEAM -- */
		JLabel team = new JLabel("DigitaLibrary - Team@24CinL");
		team.setFont(new Font("Tahoma", Font.PLAIN, 8));
		team.setBounds(615, 617, 150, 20);
		contentPane.add(team);
	}
}
/* END Class RevisioneImgFrame */
