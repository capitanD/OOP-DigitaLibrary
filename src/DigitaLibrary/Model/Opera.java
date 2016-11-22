package DigitaLibrary.Model;

import java.util.ArrayList;

import DigitaLibrary.DAO.OperaDAO;

/* MODEL --> Class OPERA
 * - Costruttore vuoto,
 * - Costruttore inizializzato,
 * - Metodi get(),
 * - Metodi set(),
 * - Lista opera: ArrayList().
 */

public class Opera {
	
	private int id;
	private String title;
	private String author;
	private int pages;
	private boolean status;
	
	/* OPERA - Costruttore vuoto */
	public Opera (int id, String t, String a, int p, boolean s){
		this.id     = id;
		this.title  = t;
		this.author = a;
		this.pages  = p;
		this.status = s;
	}
	
	/* OPERA - Costruttore inizializzato dal DAO */
	public Opera(int id){
		OperaDAO DAO           = new OperaDAO();
		ArrayList<String> data = DAO.retrieve(id);
		this.id     = Integer.parseInt(data.get(0));
		this.title  = data.get(1);
		this.author = data.get(2);
		this.pages  = Integer.parseInt(data.get(3));
		if(Integer.parseInt(data.get(4)) == 1) this.status = true;
		else this.status = false;
			
	}
	
	/* OPERA - Metodi get() */
	public int getId(){
		return this.id;
	}
	
	public String getTitle(){
		return this.title;
	}
	
	public String getAuthor(){
		return this.author;
	}
	
	public int getPages(){
		return this.pages;
	}
	
	public boolean getStatus(){
		return this.status;
	}
	
	/* OPERA - Metodi set() */
	public void setTitle(String t){
		this.title=t;
	}
	
	public void setAuthor(String a){
		this.author=a;
	}
	
	public void setPages(int p){
		this.pages=p;
	}
	
	public void setStatus(boolean s){
		this.status=s;
	}
	
	/* OPERA - Lista opera */
	public ArrayList<String> toArray(){
		ArrayList<String> data= new ArrayList<String>();
		data.add(Integer.toString(this.id));
		data.add(this.title);
		data.add(this.author);
		data.add(Integer.toString(this.pages));
		data.add(Boolean.toString(this.status));
		return data;
	}
}
/*  END Class  Opera  */