package TCP;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;

import API.Api;
import UDP.ApiUdp;

public class Serveur  extends Thread{
	private String id = null;
	private String psw = null;
	private ApiTcp apiTcp = null;
	private static Api api = new Api("Utilisateurs.xml");
	

	public Serveur() {
		this.apiTcp = new ApiTcp();
		this.apiTcp.serveurTcp();
	}
	
	public boolean authorize(){
		boolean res = false;
		String connection =  this.receive();
		
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
		
		if(!this.api.isUser(id, psw)){
			/*try {
				this.apiTcp.getSocketClient().close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}*/
			this.close();
			System.out.println(id+" ne peut pas se loger");
		}
		else{
			System.out.println(id+" est loguer");
			res = true;
		}
		return res;
	}
	
	public void send(String message){
		this.apiTcp.send(message);
	}
	
	public String receive(){
		return this.apiTcp.receive();
	}
	
	public void close(){
		this.apiTcp.close();
	}
	
	public ApiTcp getApi(){
		return this.apiTcp;
	}

	/**
	 * @param args
	 */
	/*public static void main(String[] args) {
		Serveur s = new Serveur();
		if(s.authorize()){
			System.out.println(s.receive());
		}*/
		
	  @Override
	  public void run(){
		if(this.authorize()){
			System.out.println(this.receive());
		}
	}


}
