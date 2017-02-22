package DigitaLibrary.view.frame;

import java.awt.Color;
import java.awt.Font;

import javafx.application.Platform;
import javafx.embed.swing.JFXPanel;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.text.Text;
import javafx.scene.web.WebView;

import java.awt.image.BufferedImage;
import java.net.URL;

import javax.imageio.ImageIO;
import javax.swing.Action;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.SwingUtilities;
import javax.swing.border.EmptyBorder;

import DigitaLibrary.controller.GestioneOpera;
import DigitaLibrary.controller.GestionePagina;
import DigitaLibrary.model.Biblioteca;
import DigitaLibrary.view.listener.ControllerInterface;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.BorderFactory;
import javax.swing.border.*;

/*  VISUALIZZA OPERA
 *  Frame di visualizzazione dell'opera, pagina per pagina.
 */

@SuppressWarnings({ "restriction", "unused" })
public class VisualizzaFrame {
	private static BufferedImage image = null;
	private static JTextField pageField;
	private static Biblioteca data;
	private static String opera;
	private static int page;
	private static String html;
	private static int oid;
	private static Border border_one;
	private static Border border_two;

	
	@SuppressWarnings("static-access")
	public VisualizzaFrame(Biblioteca data, String opera, int page, String html, int oid) throws Exception{
		this.data = data;
		this.opera = opera;
		this.page = page;
		this.html = html;
		this.oid = oid;
		main(new String[0]);
	}
	

    private static void initAndShowGUI() throws Exception {
        // Metodo invocato sul thread EDT
        JFrame frame = new JFrame("Pagina "+page);
        final JFXPanel fxPanel = new JFXPanel();

        frame.getContentPane().setLayout(null);
        frame.setSize(1085, 660);
        frame.setLocation(90, 40);
        frame.setVisible(true);
        frame.getContentPane().setBackground(new Color(96, 144, 170));
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        
        
        /* -- Titolo  opera -- */
		JLabel lblTitoloOpera = new JLabel(opera);
		lblTitoloOpera.setFont(new Font("Tahoma", Font.BOLD, 21));
		lblTitoloOpera.setBounds(420, 10, 240, 27);
		lblTitoloOpera.setHorizontalAlignment(JLabel.CENTER);
		lblTitoloOpera.setOpaque(true);
		lblTitoloOpera.setBackground(new Color(175, 207, 170));
		frame.getContentPane().add(lblTitoloOpera);
		
		
        /* -- Contenitore Trascrizione -- */
        fxPanel.setBounds(580, 57, 555, 605);
        fxPanel.setSize(480, 546);
		border_one = BorderFactory.createLineBorder(Color.black, 2);
		fxPanel.setBorder(border_one);
		frame.getContentPane().add(fxPanel);
		
		/* -- Numero pagina trascritta -- */
		JLabel lbpageTxt = new JLabel("Pagina "+page);
		lbpageTxt.setFont(new Font("Tahoma", Font.PLAIN, 11));
		lbpageTxt.setBounds(800, 602, 100, 27);
		frame.getContentPane().add(lbpageTxt);
            	
		
        /* -- Contenitore Immagine -- */
        JPanel panel_container = new JPanel();
        panel_container.setBounds(15, 52, 526, 610);
        panel_container.setSize(490, 550);
        panel_container.setBorder(null);
        panel_container.setBackground(new Color(96, 144, 170));
		frame.getContentPane().add(panel_container);
		
		JLabel lblImage = new JLabel("");
		JScrollPane scroll = new JScrollPane(lblImage);
		panel_container.add(scroll);
		scroll.setBorder(new EmptyBorder(2, 2, 2, 2));
		
		String operaurl = opera.replaceAll("\\s","%20");
		image = ImageIO.read(new URL("http://frank.altervista.org/"+operaurl+"/"+page+".jpg"));
		ControllerInterface act = new ControllerInterface(frame, null, null, lblImage, null, null, null);
		
		GestionePagina imageAdapter = new GestionePagina();
		image =  imageAdapter.scaleImage(480, 540, image);
		imageAdapter.setImage(image);
		ImageIcon imageIcon = new ImageIcon(image);
		lblImage.setIcon(imageIcon);
		
		/* -- Numero pagina acquisita -- */
		JLabel lbpageImg = new JLabel("Pagina "+page);
		lbpageImg.setFont(new Font("Tahoma", Font.PLAIN, 11));
		lbpageImg.setBounds(240, 602, 100, 27);
		frame.getContentPane().add(lbpageImg);


		
		/* -- Selezione pagine -- */
		JLabel lblPag = new JLabel("Vai a ");
		lblPag.setFont(new Font("Arial", Font.BOLD, 12));
		lblPag.setBounds(528, 271, 95, 14);
		frame.getContentPane().add(lblPag);
		
		pageField = new JTextField();
		pageField.setBounds(524, 285, 67, 20);
		pageField.setSize(37, 20);
		pageField.setHorizontalAlignment(JTextField.CENTER);
		border_two = BorderFactory.createLineBorder(new Color(17, 128, 180));
		pageField.setBorder(border_two);
		frame.getContentPane().add(pageField);
		pageField.setColumns(10);
		
		/* -- Invio dati al controller: naviga tra le pagine -- */
		Action actgo = new ControllerInterface( frame, data, null, pageField, opera, oid, null);
		
		/* -- Tasto: Vai a.. -- */
		JButton btnGo = new JButton("Go");
		btnGo.setFont(new Font("Tahoma", Font.PLAIN, 11));
		btnGo.setSize(20, 15);
		btnGo.setBounds(529, 307, 28, 20);
		btnGo.addActionListener(actgo);
		frame.getContentPane().add(btnGo);
		
		
		/*  Marchio TEAM  */
		JLabel team = new JLabel("DigitaLibrary - Team@24CinL");
		team.setFont(new Font("Tahoma", Font.PLAIN, 8));
		team.setBounds(490, 35, 210, 20);
		frame.getContentPane().add(team);
		
		
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                initFX(fxPanel);
            }
       });
    }

    private static void initFX(JFXPanel fxPanel) {
        // This method is invoked on the JavaFX thread
        Scene scene = createScene();
        fxPanel.setScene(scene);
    }

    private static Scene createScene() {
     
        WebView web = new WebView();
        if(html.length() > 0) 
        	web.getEngine().loadContent(html);
        else
        	web.getEngine().loadContent("<h3> Pagina non ancora trascritta </h3>");
        
        Scene scene = new Scene(web);
        return (scene);
    }
    
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                try {
                	Platform.setImplicitExit(false);
					initAndShowGUI();
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
            }
        });
    }
}
/*  END class VISUALIZZAFRAME  */