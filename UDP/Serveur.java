package UDP;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;

import API.Api;

public class Serveur {

	public static void main(String[] args) {
		ApiUdp u = new ApiUdp();
		u.iniReceive();
	 
		String texte = u.receive();
		   /////////////////////////////////////
			SAXBuilder builder = new SAXBuilder();
			Document anotherDocument = null;
			Element racine = new Element("vide");
			try {
				InputStream stream = new ByteArrayInputStream(texte.getBytes("UTF-8"));
				anotherDocument = builder.build(stream);
				racine = anotherDocument.getRootElement();
			} catch (JDOMException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			Api a = new Api("Utilisateurs.xml");
			
			//authentification
			String id = racine.getAttributeValue("id");
			String psw = racine.getAttributeValue("psw");

			if(a.isAdmin(id,psw)){
				System.out.println("est un admin");
				String meth = racine.getAttributeValue("nom");
				Object[] params = racine.getChildren("arg").toArray();
				
				Method m;
				try {
					Class[] classParam = new Class[]{String.class };
					m = a.getClass().getMethod(meth,classParam );
					System.out.println(m.invoke(a, ""));
				} catch (NoSuchMethodException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (SecurityException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (IllegalAccessException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IllegalArgumentException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (InvocationTargetException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			////////////////////////////////////////////////////////////
		
	 
			
			u.close();
	    }
	 

}
