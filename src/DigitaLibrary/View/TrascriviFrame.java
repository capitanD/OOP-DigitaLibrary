package DigitaLibrary.View;

import java.awt.Color;
import java.awt.Font;
import java.awt.image.BufferedImage;

import java.net.URL;

import javax.imageio.ImageIO;
import javax.swing.Action;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;

import DigitaLibrary.Controller.GestionePagina;
import DigitaLibrary.Model.Biblioteca;
import DigitaLibrary.Model.User;

/*  FINESTRA DI PREPARAZIONE ALLA TRASCRIZIONE
 * 	- Anteprima dell'immagine da trascrivere.
 */

public class TrascriviFrame extends JFrame {

	private static final long serialVersionUID = 8591284913835692717L;
	private JPanel contentPane;
	private BufferedImage image = null;
	private static Border border_one;

	
	public TrascriviFrame(Biblioteca data, User user, String opera, int page) throws Exception {
		
		setSize(735, 660);
        setLocation(250, 40);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		contentPane.setBackground(new Color(96, 144, 170));
		

		/* -- Pagina acquisita -- */
		JLabel lblPreview = new JLabel("Acquisizione");
		lblPreview.setBounds(207, 24, 182, 20);
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
		scroll.setBorder(new EmptyBorder(2, 2, 2, 2));
		panel_container.add(scroll);
		
		String operaurl = opera.replaceAll("\\s","%20");
		image = ImageIO.read(new URL("http://frank.altervista.org/"+operaurl+"/"+page+".jpg"));
		GestionePagina act = new GestionePagina(this, data, null, lblImage, opera, page);
		image =  act.scaleImage(480, 540, image);
		act.setImage(image);
		ImageIcon imageIcon = new ImageIcon(image);
		lblImage.setIcon(imageIcon);
		
		/* -- Numero pagina acquisita -- */
		JLabel lbZoom = new JLabel("Zoom");
		lbZoom.setFont(new Font("Tahoma", Font.PLAIN, 11));
		lbZoom.setBounds(20, 602, 100, 27);
		contentPane.add(lbZoom);
		
		JButton btnZoom_In = new JButton(" + ");
		btnZoom_In.setFont(new Font("Tahoma", Font.PLAIN, 11));
		btnZoom_In.setBounds(45, 605, 40, 23);
		btnZoom_In.addActionListener(act);
		contentPane.add(btnZoom_In);
		
		JButton btnZoom_Out = new JButton(" - ");
		btnZoom_Out.setFont(new Font("Tahoma", Font.PLAIN, 11));
		btnZoom_Out.setBounds(73, 605, 40, 23);
		btnZoom_Out.addActionListener(act);
		contentPane.add(btnZoom_Out);
		
		
		/* -- Descrizione opera -- */
		
		/* TITOLO */
		JLabel lblOpera = new JLabel("Opera:");
		lblOpera.setFont(new Font("Tahoma", Font.PLAIN, 11));
		lblOpera.setBounds(520, 65, 46, 14);
		contentPane.add(lblOpera);
		
		JLabel labelTitolo = new JLabel("");
		labelTitolo.setFont(new Font("Tahoma", Font.BOLD, 11));
		labelTitolo.setBounds(580, 65, 124, 14);
		contentPane.add(labelTitolo);
		labelTitolo.setText(opera);

		
		/* N° PAGINA */
		JLabel lblPagina = new JLabel("Pagina:");
		lblPagina.setFont(new Font("Tahoma", Font.PLAIN, 11));
		lblPagina.setBounds(520, 85, 46, 14);
		contentPane.add(lblPagina);
		
		JLabel labeln = new JLabel("");
		labeln.setFont(new Font("Tahoma", Font.BOLD, 11));
		labeln.setBounds(580, 85, 124, 14);
		labeln.setText(Integer.toString(page));
		contentPane.add(labeln);
		
		/* -- Invio dati al controller: caricamento testo TEI -- */
		Action act2 = new GestionePagina(this, data, null, user, opera, page);
		
		
		/*  SEPARATORE_0  */
		JLabel separatore_0 = new JLabel("");
		separatore_0.setBounds(520, 105, 190, 2);
		border_one = BorderFactory.createLineBorder(Color.black, 2);
		separatore_0.setBorder(border_one);
		contentPane.add(separatore_0);
		
		/* -- Carica file JEdit relativo all'immagine -- */
		JLabel desc_operation = new JLabel("<html>Clicca 'Carica Trascrizione' e seleziona il file  "
				+ "appena trascritto con jedit per caricalo sul sistema");
		desc_operation.setFont(new Font("Tahoma", Font.PLAIN, 9));
		desc_operation.setBounds(522, 130, 190, 40);
		contentPane.add(desc_operation);
		
		/* -- Tasto: carica trascrizione -- */
		JButton btnUploadTextFile = new JButton("Carica Trascrizione");
		btnUploadTextFile.setBounds(543, 173, 130, 33);
		btnUploadTextFile.setFont(new Font("Tahoma", Font.PLAIN, 11));
		btnUploadTextFile.addActionListener(act2);
		contentPane.add(btnUploadTextFile);
		
		
		/* -- Marchio TEAM -- */
		JLabel team = new JLabel("DigitaLibrary - Team@24CinL");
		team.setFont(new Font("Tahoma", Font.PLAIN, 8));
		team.setBounds(615, 617, 150, 20);
		contentPane.add(team);
	}
}
/* END Class TrascriviFrame */