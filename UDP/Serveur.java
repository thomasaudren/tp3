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
			
			String meth = racine.getAttributeValue("nom");
			Object[] params = racine.getChildren("arg").toArray();
			Api a = new Api("Utilisateurs.xml");
			Method m;
			try {
				Class[] classParam = new Class[]{String.class,String.class,String.class };
				m = a.getClass().getMethod(meth,classParam );
				m.invoke(a, "marie", "coucou", "f");
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
			
			////////////////////////////////////////////////////////////
		
	 
			
			u.close();
	    }
	 

}