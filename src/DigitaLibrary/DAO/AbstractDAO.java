package DigitaLibrary.DAO;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.LinkedList;

/* DAO --> Class ABSTRACTDAO
 * - No costruttore,
 * - connection()	-> Implementa la connessione al database,
 * - retrieve(int) 	-> Metodo astratto,
 * - retrieveAll()	-> Metodo astratto,
 * - add(ArrayList<String>)			-> Metodo astratto,
 * - edit(ArrayList<String>)		-> Metodo astratto,
 * - delete(int)	-> Metodo astratto.
 */


public abstract class AbstractDAO {

	static String serverdata= "";
	static String DBuser= "";
	static String DBpass= "";
	
	public static Connection connection() throws SQLException, IOException, ClassNotFoundException{
		Class.forName("com.mysql.jdbc.Driver");
		if(serverdata.length() == 0){
		FileReader reader = new FileReader("database_config.txt");
        BufferedReader bufferedReader = new BufferedReader(reader);
        serverdata = bufferedReader.readLine();
        DBuser = bufferedReader.readLine();
        DBpass = bufferedReader.readLine();
        reader.close();
		}
		Connection con=DriverManager.getConnection(serverdata, DBuser , DBpass);
		return con;
	}
	
	public abstract ArrayList<String> retrieve(int id);
	public abstract LinkedList<Integer> retrieveAll();
	public abstract void add(ArrayList<String> data);
	public abstract void edit(ArrayList<String> data);
	public abstract void delete(int id);
}
/*  END Class  AbstractDAO  */