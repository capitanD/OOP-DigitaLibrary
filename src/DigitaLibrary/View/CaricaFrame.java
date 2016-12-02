package DigitaLibrary.View;

import java.awt.Font;
import java.awt.Color;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.border.Border;
import javax.swing.JLabel;

import javax.swing.JTextField;
import javax.imageio.ImageIO;
import javax.swing.Action;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;

import java.awt.image.BufferedImage;
import java.net.URL;
import javax.swing.JScrollPane;

import DigitaLibrary.Controller.GestionePagina;
import DigitaLibrary.Model.Biblioteca;
import DigitaLibrary.Model.Opera;
import DigitaLibrary.Model.User;

/*  CARICA UNA NUOVA IMMAGINE
 * 	Frame di caricamento di una nuova immagine nel sistema da parte dell'acquisitore.
 * 	Si può scegliere il file attraverso un apposito pulsante per scegliere il percorso di appartenenza del file.
 * 	Una volta selezionata l'immagine, verrà messa a disposizione una sua anteprima prima di caricarlo sul sistema.
 */

public class CaricaFrame extends JFrame {

	private static final long serialVersionUID = -1623881826062829714L;
	private JPanel contentPane;
	private JTextField pathField;
	private JTextField npaginaField;
	private BufferedImage image = null;
	private static Border border_one;
	private static Border border_two;

	
	public CaricaFrame(Biblioteca data, Opera opera, User user) throws Exception {
		
		setSize(735, 660);
        setLocation(250, 40);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		contentPane.setBackground(new Color(96, 144, 170));		

		/* --  Preview  -- */
		JLabel lblPreview = new JLabel("Preview");
		lblPreview.setBounds(218, 28, 182, 20);
		lblPreview.setFont(new Font("Tahoma", Font.BOLD, 15));
		contentPane.add(lblPreview);
		
		/* --  Contenitore immagine  -- */
		JPanel panel_container = new JPanel();
		panel_container.setBounds(15, 52, 526, 610);
		panel_container.setSize(480, 560);
		panel_container.setLayout(null);
		border_two = BorderFactory.createMatteBorder(2, 2, 2, 2, new Color(240, 240, 240));
		panel_container.setBorder(border_two);		
		panel_container.setBackground(new Color(96, 144, 170));
		contentPane.add(panel_container);
		
		JLabel lblImage = new JLabel("");
		JScrollPane scroll = new JScrollPane(lblImage);
		scroll.setBorder(border_two);	
		scroll.setSize(480, 560);
		panel_container.add(scroll);
		image = ImageIO.read(new URL("http://frank.altervista.org/nopreview.jpg"));
		ImageIcon imageIcon = new ImageIcon(image);
		lblImage.setIcon(imageIcon);
		

		
		/* --  Seleziona percorso file immagine  -- */
		JLabel lblPath = new JLabel("Percorso");
		lblPath.setBounds(520, 64, 70, 14);
		lblPath.setFont(new Font("Tahoma", Font.BOLD, 11));
		contentPane.add(lblPath);
		
		pathField = new JTextField();
		pathField.setBounds(521, 80, 190, 20);
		pathField.setBorder(new EmptyBorder(2, 2, 2, 2));
		pathField.setFont(new Font("Thaoma", Font.PLAIN, 10));
		contentPane.add(pathField);
		pathField.setColumns(10);
		
		/* -- Invio dati al controller: mostra anteprima immagine --  */
		Action act = new GestionePagina(this, data, pathField, lblImage, null, null);  		
		
		JButton btnScegliFile = new JButton("Scegli File");
		btnScegliFile.setBounds(514, 103, 103, 24);
		btnScegliFile.setFont(new Font("Tahoma", Font.PLAIN, 11));
		btnScegliFile.addActionListener(act);   
		contentPane.add(btnScegliFile);
		
		
		/*  --  Opera di riferimento  -- */
		JLabel lblOpera = new JLabel("Opera:");
		lblOpera.setBounds(520, 155, 46, 14);
		lblOpera.setFont(new Font("Tahoma", Font.PLAIN, 11));
		contentPane.add(lblOpera);
		
		JLabel operaField = new JLabel(opera.getTitle());
		operaField.setBounds(575, 155, 180, 15);
		operaField.setFont(new Font("Thaoma", Font.BOLD, 11));
		contentPane.add(operaField);
		
		/*  --  Autore opera  --  */
		JLabel lbAutore = new JLabel("Autore:");
		lbAutore.setBounds(520, 175, 50, 15);
		lbAutore.setFont(new Font("Thaoma", Font.PLAIN, 11));
		contentPane.add(lbAutore);
		
		JLabel authorField = new JLabel(opera.getAuthor());
		authorField.setBounds(575, 175, 180, 15);
		authorField.setFont(new Font("Thaoma", Font.BOLD, 11));
		contentPane.add(authorField);

		
		/*  --  ID opera  -- */
		JLabel lblOperaId = new JLabel("ID Opera:");
		lblOperaId.setFont(new Font("Tahoma", Font.PLAIN, 11));
		lblOperaId.setBounds(520, 195, 70, 14);
		contentPane.add(lblOperaId);
		
		JLabel lbOperaID = new JLabel(Integer.toString(opera.getId()));
		lbOperaID.setBounds(575, 195, 50, 15);
		lbOperaID.setFont(new Font("Thaoma", Font.BOLD, 11));
		contentPane.add(lbOperaID);

		
		/* -- Imposta numero pagina da acquisire -- */
		JLabel lblNPagina = new JLabel("n\u00B0 Pagina:");
		lblNPagina.setBounds(520, 220, 80, 14);
		lblNPagina.setFont(new Font("Tahoma", Font.PLAIN, 11));
		contentPane.add(lblNPagina);
		
		npaginaField = new JTextField();
		npaginaField.setFont(new Font("Tahoma", Font.PLAIN, 11));
		npaginaField.setBounds(576, 218, 60, 20);
		npaginaField.setBorder(new EmptyBorder(2, 2, 2, 2));
		contentPane.add(npaginaField);
		npaginaField.setColumns(10);
		
		/*  -- SEPARATORE -- */
		JLabel separate = new JLabel("");
		separate.setBounds(520, 260, 190, 2);
		border_one = BorderFactory.createLineBorder(Color.black, 2);
		separate.setBorder(border_one);
		contentPane.add(separate);
		
		/* -- Descrizione Operazioni -- */
		JLabel desc_operation = new JLabel("<html>Seleziona il percorso del file immagine da acquisire,"
				+ "imposta il numero di pagina e clicca sul pulsante 'Carica'.");
		desc_operation.setFont(new Font("Tahoma", Font.PLAIN, 9));
		desc_operation.setBounds(525, 270, 190, 30);
		contentPane.add(desc_operation);
		
		/* -- Invio dati al controller: Salvataggio nel database -- */
		Action act2 = new GestionePagina(this, data,  pathField , npaginaField, user, opera); // View -> Controller
		
		JButton btnUpload = new JButton("Carica");
		btnUpload.setBounds(570, 312, 85, 33);
		btnUpload.setFont(new Font("Tahoma", Font.PLAIN, 11));
		btnUpload.addActionListener(act2);
		contentPane.add(btnUpload);
		
			
		
		/*  -- Marchio TEAM -- */
		JLabel team = new JLabel("DigitaLibrary - Team@24CinL");
		team.setFont(new Font("Tahoma", Font.PLAIN, 8));
		team.setBounds(615, 617, 150, 20);
		contentPane.add(team);
		
	}
}
/*  END class CaricaFrame  */