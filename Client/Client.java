package Client;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;

import org.jdom2.Attribute;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.output.XMLOutputter;

public class Client {
	public static final int numeroPort = 5001;
	private String id = null;
	private String psw = null;
	private String host = null;
	private Socket socket = null;
	private BufferedReader in;
	private PrintWriter out;
	
	
	

	public Client(String id, String psw, String host) {
		this.id = id;
		this.psw = psw;
		host = (host.equals(""))?"localhost":host;
		this.host = host;
		try {
		    this.socket  = new Socket(host, numeroPort);
		} catch(Exception e) {
		    System.err.println("Creation socket impossible "+e.getMessage());
		    System.exit(-1);
		}
		
		try {
		    this.in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
		    this.out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(socket.getOutputStream())), true);
		} catch(Exception e) {
		    System.err.println("Association des flux impossible");
		    System.exit(-1);
		}
	}
	
	public void send(String message){
		System.out.print("Send: "+message);
		try {
		    this.out.println(message);
		    this.out.flush();
		} catch(Exception e) {
		    System.err.println("Erreur lors de l'envoi "+e.getMessage());
		    System.exit(-1);
		}
	}
	
	public void connection(){
		Element racine = null;
		Document document = null;
		String connectionXML = null;
		racine = new Element("Connection");
		Attribute pseudo = new Attribute("id",this.id);
		racine.setAttribute(pseudo);
		Attribute password = new Attribute("psw",this.psw);
		racine.setAttribute(password);
		document = new Document(racine);
		connectionXML = new XMLOutputter().outputString(document);
		this.send(connectionXML);
	}
	
	public String receive(){
		String message = "";
		try {
			boolean test = true;
			while (test) {
				String ligne = this.in.readLine();
				if(!ligne.equals("")){
					message += ligne;
				}
				else{
					test = false;
				}
			}
		    
		} catch(Exception e) {
		    return "-1";
		  }
		return message;
	}
	
	public void close(){
		 try {
			this.in.close();
			this.out.close();
			 this.socket.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}



	/**
	 * @param args
	 */
	public static void main(String[] args) {
		Client c = new Client("ouiiii44", "coucou", "");
		//Client c2 = new Client("admin", "admin", "");
		/*c2.connection();*/
		c.connection();
		//c.send("coucou c est moi\n");
		String message = c.receive();
		/*System.out.println(message);
		 message = c2.receive();*/
		if(message.equals("-1")){
			c.close();
			System.out.println("logout");
			System.exit(-1);
		}
		
		c.send("logout\n");
		c.close();
	}

}
