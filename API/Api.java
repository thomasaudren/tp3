package API;

import java.io.File;
import java.io.FileOutputStream;
import java.util.Iterator;
import java.util.List;

import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.input.SAXBuilder;
import org.jdom2.output.Format;
import org.jdom2.output.XMLOutputter;

public class Api {
	private Document document = null;
	private Element racine = null;
	private File file = null;
	
	public Api(String document) {
		//On crée une instance de SAXBuilder
	      SAXBuilder sxb = new SAXBuilder();
	      try
	      {
	         //On crée un nouveau document JDOM avec en argument le fichier XML
	         //Le parsing est terminé ;)
	    	 this.file = new File(document);
	         this.document = sxb.build(file);
	         
	      }
	      catch(Exception e){System.out.println(e.getMessage());}
	      //On initialise un nouvel élément racine avec l'élément racine du document.
	      this.racine = this.document.getRootElement();
	      
	}

	public Document getDocument() {
		return new Document(this.racine);
	}


	private void setDocument(Document document) {
		this.document = document;
	}


	public Element getRacine() {
		return racine.clone();
	}


	private void setRacine(Element racine) {
		this.racine = racine;
	}

	

	@Override
	public String toString() {
		return "Api [document=" + document + ", racine=" + racine + "]";
	}

	public String listUtilisateurs(String type) {
		String res = "";
		List listUsers = this.racine.getChildren("Utilisateur");
		Iterator i = listUsers.iterator();
		while(i.hasNext()){
			 Element courant = (Element)i.next();
				if(type.equals("admin") && courant.getAttributeValue("type").equals("admin")){
					res += courant.getChild("pseudo").getText()+" - "+courant.getAttributeValue("sexe")+"\n"; 
				}
				else if(type.equals("") && courant.getAttributeValue("type").equals("user")){
					res += courant.getChild("pseudo").getText()+" - "+courant.getAttributeValue("sexe")+"\n";
				}
				else if(type.equals("all")){
					res += courant.getChild("pseudo").getText()+" - "+courant.getAttributeValue("sexe")+"\n";
				}
			}
		return res;
	}
	
	public void ajoutUtilisateur(String pseudo, String password, String sexe)
	{	sexe = sexe.toUpperCase();
		List listUsers = this.racine.getChildren("Utilisateur");
		Iterator i = listUsers.iterator();
		Boolean res = true;
		while(res && i.hasNext()){
			Element courant = (Element)i.next();
			System.out.println();
			if(pseudo.equals(courant.getChildText("pseudo"))){
				res = false;
			}
		}
		if(res){
			Element child = new Element("Utilisateur");
			Element childPseudo = new Element("pseudo");
			Element childPassword = new Element("password");
			child.setAttribute("type", "user");
			child.setAttribute("sexe", sexe);
			childPseudo.addContent(pseudo);
			childPassword.addContent(password);
			child.addContent(childPseudo);
			child.addContent(childPassword);
			this.racine.addContent(child);
		}
		else{
			System.out.println("Pseudo deja prit.");
		}
		this.save();
	}
	
	public void suprimeUtilisateur(String pseudo){
		List listUsers = this.racine.getChildren("Utilisateur");
		Iterator i = listUsers.iterator();
		Boolean res = true;
		while(res && i.hasNext()){
			Element courant = (Element)i.next();
			if(pseudo.equals(courant.getChildText("pseudo"))){
				courant.detach();
				res = false;
			}
		}
		this.save();
	}
	public void save(){
		 try
	      {
	         XMLOutputter sortie = new XMLOutputter(Format.getPrettyFormat());
	         sortie.output(document, new FileOutputStream(this.file.getAbsolutePath()));
	      }
	      catch (java.io.IOException e){}
	}
	
	public boolean isAdmin(String id, String psw) {
		Boolean res = false;
		List listUsers = this.racine.getChildren("Utilisateur");
		Iterator i = listUsers.iterator();
		while(!res && i.hasNext()){
			Element courant = (Element)i.next();
			if(id.equals(courant.getChildText("pseudo"))){
				if(psw.equals(courant.getChildText("password"))){
					res = true;
				}
			}
		}
		return res;
	}
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
//		Api a = new Api("Utilisateurs.xml");
//		System.out.println(a.listUtilisateurs(""));
//		a.ajoutUtilisateur("ouiiii44", "coucou", "m");
//		System.out.println(a.listUtilisateurs(""));
//		a.suprimeUtilisateur("ouiiii");
//		System.out.println(a.listUtilisateurs(""));
//		a.save();
	}

	

}
