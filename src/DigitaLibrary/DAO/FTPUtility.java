package DigitaLibrary.DAO;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
 
import org.apache.commons.net.ftp.*;
 
/* DAO --> Class FTPUTILITY
 * - No costruttore,
 * - connect()	-> Implementa la connessione al server,
 * - uploadFile(String, String, String)	 -> Implementa il caricamento del file,
 * - deleteFile(String)	 -> Dato un percorso, elimina il file,
 * - createDir(String)	 -> Dato un nome, crea la cartella con quel nome,
 * - deleteDir(String)	 -> Dato un nome, cancella la cartella con quel nome,
 * - writeFileBytes(byte[], int, int)	-> Scrivi tot byte nel file.
 * - finish()	-> Ferma e chiudi la scrittura del file. 
 * - disconnect()	-> Chiudi la comunicazione con il server.
 */

public class FTPUtility {
 
    private String host = "ftp.frank.altervista.org";
    private int port = 21;
    private String username = "frank";
    private String password = "fregno";
 
    private FTPClient ftpClient = new FTPClient();
    private int replyCode; 
    private OutputStream outputStream;
     
    
    /*  CONNECT()
     * 	Connessione al server FTP per lo scambio file.
     */
    public void connect() throws Exception {
        try {
            ftpClient.connect(host, port);
            replyCode = ftpClient.getReplyCode();
            if (!FTPReply.isPositiveCompletion(replyCode)) {
                throw new Exception("FTP serve refused connection.");
            }
 
            boolean logged = ftpClient.login(username, password);
            if (!logged) {
                // failed to login
                ftpClient.disconnect();
                throw new Exception("Could not login to the server.");
            }
 
            ftpClient.enterLocalPassiveMode();
 
        } catch (IOException ex) {
            throw new Exception("I/O error: " + ex.getMessage());
        }
    }
 
    
    /*  UPLOADFILE(String, String, String)
     *  Caricamento del file sul server.
     */
    public void uploadFile(String path, String destDir, String fileName) throws Exception {
        try {
            boolean success = ftpClient.changeWorkingDirectory(destDir);
            if (!success) {
                throw new Exception("Could not change working directory to "
                        + destDir + ". The directory may not exist.");
            }
             
            success = ftpClient.setFileType(FTP.BINARY_FILE_TYPE);         
            if (!success) {
                throw new Exception("Could not set binary file type.");
            }
            InputStream is = new FileInputStream(path);
            ftpClient.storeFile(fileName , is);
             
        } catch (IOException ex) {
            throw new Exception("Error uploading file: " + ex.getMessage());
        }
    }
    
    
    /*  DELETEFILE(String)
     *  Cancella il file.
     */
    public void deleteFile(String path){
    	try {
			ftpClient.deleteFile(path);
		} catch (IOException e) {
			e.printStackTrace();
		}
    }
    
    
    /*  CREATEDIR(String)
     *  Crea una cartella.
     */
    public void createDir(String dir) throws IOException{
    	ftpClient.makeDirectory(dir);
    }
    
    
    /*  REMOVEDIR(String)
     *  Cancella una cartella.
     */
    public void removeDir(String dir) throws IOException{
    	ftpClient.removeDirectory(dir);
    }
 
    
    /*  WRITEFILEBYTES(byte[], int, length)
     *  Scrivi tot byte nel file selezionato.
     */
    public void writeFileBytes(byte[] bytes, int offset, int length) throws IOException {
        outputStream.write(bytes, offset, length);
    }

    
    /*  FINISH()
     *  Ferma e chidi la scrittura del file.
     */
    public void finish() throws IOException {
        outputStream.close();
        ftpClient.completePendingCommand();
    }
     
    
    /*  DISCONNECT()
     *  Esegui la disconnessione con il server.
     */
    public void disconnect() throws Exception {
        if (ftpClient.isConnected()) {
            try {
                if (!ftpClient.logout()) {
                    throw new Exception("Could not log out from the server");
                }
                ftpClient.disconnect();
            } catch (IOException ex) {
                throw new Exception("Error disconnect from the server: "
                        + ex.getMessage());
            }
        }
    }
}
/*  END Class  FTPUtility  */