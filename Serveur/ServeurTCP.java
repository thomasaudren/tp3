package Serveur;

import java.net.*;
import java.io.*;

import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;

import API.Api;

public class ServeurTCP extends Thread {

  
	 private Socket socket;
	/**/ private Api apiXML = new Api("Utilisateurs.xml");

  public static void main(String[] args) {
    
  }

  public ServeurTCP(Socket socket) {
    this.socket = socket;
  }

  public void run() {
    traitements();
  }
  
  public String receive(BufferedReader input){
		String message = "";
		try {
			boolean test = true;
			while (test) {
				String ligne = input.readLine();
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
  
  public boolean authorize(String connection){
	  System.out.println("debut");
		boolean res = false;
		
		SAXBuilder builder = new SAXBuilder();
		Document anotherDocument = null;
		Element racine = new Element("vide");
		try {
			InputStream stream = new ByteArrayInputStream(connection.getBytes("UTF-8"));
			anotherDocument = builder.build(stream);
			racine = anotherDocument.getRootElement();
		} catch (JDOMException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		String id = racine.getAttributeValue("id");
		String psw = racine.getAttributeValue("psw");
		
		if(!this.apiXML.isUser(id, psw)){
			System.out.println(id+" ne peut pas se loger");
		}
		else{
			System.out.println(id+" est loguer");
			res = true;
		}
		 System.out.println("fin");
		return res;
	}

  public void traitements() {
    try {
      String message = "";

      System.out.println("Connexion avec le client : " + socket.getInetAddress()+" "+
    		  socket.getLocalPort()+" "+socket.getPort());

      BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
      PrintWriter out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(socket.getOutputStream())), true);
      message = this.receive(in);
      Boolean res = authorize(message);
      if(res){
    	  out.println("<- Vous este login\n");
      }
      else{
    	  System.out.println("false");
    	  out.println("-1\n");
      }
     while(res){
    	 String messageClient = receive(in);
    	 if(messageClient.equals("logout")){
    		 res = false;
    		 System.out.println("");
    	 }
    	 else{
    		 System.out.println("execution"+ getId());
    	 }
     }
     in.close();
     out.close();
     socket.close();
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
}