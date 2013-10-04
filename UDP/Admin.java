package UDP;

import org.jdom2.Attribute;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.output.Format;
import org.jdom2.output.XMLOutputter;

import com.sun.xml.internal.bind.v2.runtime.unmarshaller.XsiNilLoader.Array;

public class Admin {
	
	private Element racine = null;
	private Document document = null;
	
	public Admin(String nomMathode,String[] args){
		this.racine = new Element("methode");
		Attribute nom = new Attribute("nom",nomMathode);
		this.racine.setAttribute(nom);
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
		
		String[] params = new String[2];
		params[0] = "coucou";
		params[1] = "hello";
		Admin a = new Admin("setfdg", params);
		a.affiche();
		

	}

}
