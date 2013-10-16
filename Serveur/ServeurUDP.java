package Serveur;


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
import API.ApiUdp;

public class ServeurUDP extends Thread{
	private static ApiUdp u = new ApiUdp();
	private static Api a = new Api("Utilisateurs.xml");

	/**/
	public void invoke(String meth, Object[] params){
		Method m;
		int nbParam = params.length;
		Class[] classParam = new Class[nbParam];
		String[] valParam = new String[nbParam];
		/*for (int i = 0; i < params.length; i++) {
			classParam[i]= params[i].getClass();
			System.out.println(classParam[i].toString());
		}*/
		int i = 0;
		for (Object param : params) {
			classParam[i]= ((Element)(params[i])).getText().getClass();
			valParam[i]= ((Element)(params[i])).getText();
			i++;
		}
		try {
			m = a.getClass().getMethod(meth,classParam );
			switch (nbParam) {
			case 1:
				m.invoke(a, valParam[0]);
				break;
			case 2:
				m.invoke(a, valParam[0],valParam[1] );
				break;
			case 3:
				m.invoke(a, valParam[0],valParam[1],valParam[2] );
				break;
			default:
				break;
			}
		} catch (NoSuchMethodException | SecurityException | IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
			// TODO Auto-generated catch block
			//e.printStackTrace();
		}
		
	}
	/**/

	public void  UdpOn() {
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
			
			//authentification
			String id = racine.getAttributeValue("id");
			String psw = racine.getAttributeValue("psw");

			if(a.isAdmin(id,psw)){
				String meth = racine.getAttributeValue("nom");
				Object[] params =  racine.getChildren("arg").toArray();
				this.invoke(meth, params);
				/*Method m;
				try {
					Class[] classParam = new Class[]{String.class,String.class,String.class };
					m = a.getClass().getMethod(meth,classParam );
					m.invoke(a, "marie","coucou","f");
					
					
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
				}*/
			}
			////////////////////////////////////////////////////////////
		
	 
			
			u.close();
	    }
	
	@Override
	  public void run() {
		System.out.println("Lancement du serveur UDP");
	    this.UdpOn();
	  }
	 

}
