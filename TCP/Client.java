package TCP;

import org.jdom2.Attribute;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.output.XMLOutputter;

public class Client {
	private String id = null;
	private String psw = null;
	private ApiTcp apiTcp = null;
	
	
	

	public Client(String id, String psw, String host) {
		this.id = id;
		this.psw = psw;
		this.apiTcp = new ApiTcp();
		this.apiTcp.clientTcp(host);
	}
	
	public void send(String message){
			this.apiTcp.send(message);
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
		return this.apiTcp.receive();
	}
	
	public void close(){
		this.apiTcp.close();
	}



	/**
	 * @param args
	 */
	public static void main(String[] args) {
		Client c = new Client("ouiiii44", "coucou", "");
		c.connection();
		if(c.receive().equals("-1")){
			c.close();
			System.out.println("mauvaise connection");
			System.exit(-1);
		}
		c.send("coucou c est moi\n");

	}

}
