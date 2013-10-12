package Admin;


import API.ApiUdp;

import org.jdom2.Attribute;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.output.Format;
import org.jdom2.output.XMLOutputter;


public class Admin {
	
	private Element racine = null;
	private Document document = null;
	
	
	public Admin(String nomMathode,String[] args,String id, String psw){
		this.racine = new Element("methode");
		Attribute nom = new Attribute("nom",nomMathode);
		this.racine.setAttribute(nom);
		Attribute pseudo = new Attribute("id",id);
		this.racine.setAttribute(pseudo);
		Attribute password = new Attribute("psw",psw);
		this.racine.setAttribute(password);
		for (int i = 0; i < args.length; i++) {
			Element arg = new Element("arg");
			String val = args[i];
			arg.addContent(val);
			this.racine.addContent(arg);
		}
		this.document = new Document(racine);
		
	}
	public void affiche()
	   {
	      try
	      {
	         //On utilise ici un affichage classique avec getPrettyFormat()
	         XMLOutputter sortie = new XMLOutputter(Format.getPrettyFormat());
	         sortie.output(document, System.out);
	      }
	      catch (java.io.IOException e){}
	   }
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		String[] params = new String[3];
		params[0] = "marie";
		params[1] = "COUCOU";
		params[2] = "f";
//		String[] params = new String[1];
//		params[0] = "";
		Admin a = new Admin("ajoutUtilisateur", params,"admin","admin");
		//a.affiche();
		String message = new XMLOutputter().outputString(a.document);
		System.out.println(message);

		ApiUdp u = new ApiUdp();
		u.iniSend(message, "");
		u.write();
		u.send();
		u.close();
	    }

	}

