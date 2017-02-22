package DigitaLibrary.view.frame;

import java.awt.Color;
import java.awt.Font;

import javafx.application.Platform;
import javafx.embed.swing.JFXPanel;
import javafx.scene.Scene;
import javafx.scene.web.WebView;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.SwingUtilities;
import javax.swing.border.Border;

import DigitaLibrary.model.Biblioteca;
import DigitaLibrary.view.listener.ControllerInterface;

import javax.swing.JButton;


/*  PREPARAZIONE ALLA REVISIONE
 * 	Finestra contenente il testo in formato TEI da revisionare e convalidare.
 */

@SuppressWarnings("restriction")
public class RevisioneTxtFrame {
	
	private static Biblioteca data;
	private static String opera;
	private static int page;
	private static String html;
	private static Border border_one;
	
	@SuppressWarnings("static-access")
	public RevisioneTxtFrame(Biblioteca data, String opera, int page, String html) throws Exception{
		this.data = data;
		this.opera = opera;
		this.page = page;
		this.html = html;
		main(new String[0]);
	}
	
	
    private static void initAndShowGUI() throws Exception {
        // Metodo invocato sul thread EDT
        JFrame frame = new JFrame("Pagina "+page);
        final JFXPanel fxPanel = new JFXPanel();
        
        
        frame.getContentPane().setLayout(null);
        frame.setSize(730, 660);
        frame.setLocation(250, 40);
        frame.setVisible(true);
        frame.getContentPane().setBackground(new Color(96, 144, 170));
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        
        
        /* -- Titolo finestra -- */
        JLabel lbTxtPreview = new JLabel("Trascrizione");
        lbTxtPreview.setBounds(205, 28, 182, 20);
        lbTxtPreview.setFont(new Font("Tahoma", Font.BOLD, 15));
		frame.getContentPane().add(lbTxtPreview);
		
		
		/* -- Contenitore Trascrizione -- */
		fxPanel.setBounds(15, 55, 555, 605);
        fxPanel.setSize(480, 546);
        border_one = BorderFactory.createLineBorder(Color.black, 2);
		fxPanel.setBorder(border_one);
		frame.getContentPane().add(fxPanel);
		
		/* -- Invio dati al controller -- */
		ControllerInterface act = new ControllerInterface(frame, data, null, null, opera, page, null);
		
		
		/* -- Descrizione opera -- */
		
		/* TITOLO */
		JLabel lblOpera = new JLabel("Opera:");
		lblOpera.setFont(new Font("Tahoma", Font.PLAIN, 11));
		lblOpera.setBounds(510, 65, 46, 14);
		frame.getContentPane().add(lblOpera);
		
		JLabel labelTitolo = new JLabel(" "+opera);
		labelTitolo.setFont(new Font("Tahoma", Font.BOLD, 11));
		labelTitolo.setBounds(570, 65, 124, 14);
		frame.getContentPane().add(labelTitolo);

		/* N° PAGINA Img  */
		JLabel lblPaginaImg = new JLabel("Pagina Img:");
		lblPaginaImg.setFont(new Font("Tahoma", Font.PLAIN, 11));
		lblPaginaImg.setBounds(510, 85, 60, 14);
		frame.getContentPane().add(lblPaginaImg);
		
		JLabel labePg = new JLabel(" "+Integer.toString(page));
		labePg.setFont(new Font("Tahoma", Font.BOLD, 11));
		labePg.setBounds(570, 85, 124, 14);
		frame.getContentPane().add(labePg);
		
		/* N° PAGINA Txt  */
		JLabel lblPaginaTxt = new JLabel("Pagina Txt:");
		lblPaginaTxt.setFont(new Font("Tahoma", Font.PLAIN, 11));
		lblPaginaTxt.setBounds(510, 105, 60, 14);
		frame.getContentPane().add(lblPaginaTxt);
		
		JLabel labePgT = new JLabel(" "+Integer.toString(page));
		labePgT.setFont(new Font("Tahoma", Font.BOLD, 11));
		labePgT.setBounds(570, 105, 124, 14);
		frame.getContentPane().add(labePgT);
		
		
		/*  separatore  */
		JLabel separate = new JLabel("");
		separate.setBounds(510, 125, 190, 2);
		border_one = BorderFactory.createLineBorder(Color.black, 2);
		separate.setBorder(border_one);
		frame.getContentPane().add(separate);
		
		/*  Carica file JEdit della trascrizione  */
		JLabel desc_operation = new JLabel("<html>Esegui un'ultima revisione all'opera."
				+ "<br>Controlla che la trascrizione sia stata effettuata "
				+ "correttamente e in formato TEI, "
				+ "poi approva oppure rifiuta il testo.</html>");
		desc_operation.setFont(new Font("Tahoma", Font.PLAIN, 9));
		desc_operation.setBounds(515, 135, 180, 70);
		frame.getContentPane().add(desc_operation);
		
		JButton btnRifiuta = new JButton("Rifiuta TEI");
		btnRifiuta.setFont(new java.awt.Font("Tahoma", java.awt.Font.PLAIN, 11));
		btnRifiuta.setBounds(515, 210, 85, 23);
		btnRifiuta.addActionListener(act);
		frame.getContentPane().add(btnRifiuta);
		
		JButton btnAccetta = new JButton("Approva TEI");
		btnAccetta.setFont(new java.awt.Font("Tahoma", java.awt.Font.PLAIN, 11));
		btnAccetta.setBounds(600, 210, 85, 23);
		frame.getContentPane().add(btnAccetta);
        btnAccetta.addActionListener(act);
		
        
        /* -- Marchio TEAM -- */
		JLabel team = new JLabel("DigitaLibrary - Team@24CinL");
		team.setFont(new Font("Tahoma", Font.PLAIN, 8));
		team.setBounds(615, 617, 150, 20);
		frame.getContentPane().add(team);
		
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                initFX(fxPanel);
            }
       });
    }

    private static void initFX(JFXPanel fxPanel) {
        Scene scene = createScene();
        fxPanel.setScene(scene);
    }

    private static Scene createScene() {
        WebView web = new WebView();
        if(html.length() > 0) web.getEngine().loadContent(html);
        else web.getEngine().loadContent("<h3> Non ancora trascritta </h3>");
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
					e.printStackTrace();
				}
            }
        });
    }
}
/* END Class RevisioneTxtFrame */