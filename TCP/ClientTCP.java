package TCP;

import java.io.*;
import java.net.*;
 
/**
 * Classe correspondant à un client TCP
 * @author Cyril Rabat
 * @version 27/09/2013
 */
public class ClientTCP {
 
    public static final int numeroPort = 5001;
 
    public static void main(String[] args) {
	// Creation de la socket
	Socket socket = null;
	try {
	    socket = new Socket("localhost", numeroPort);
	} catch(Exception e) {
	    System.err.println("Creation socket impossible "+e.getMessage());
	    System.exit(-1);
	}
 
	// Association d'un flux d'entree et de sortie
	BufferedReader input = null;
	PrintWriter output = null;
	try {
	    input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
	    output = new PrintWriter(new BufferedWriter(new OutputStreamWriter(socket.getOutputStream())), true);
	} catch(Exception e) {
	    System.err.println("Association des flux impossible");
	    System.exit(-1);
	}
 
	// Envoi de 'Bonjour'
	String message = "Bonjour";
	System.out.println("Envoi: " + message);
	try {
	    output.println(message);
	} catch(Exception e) {
	    System.err.println("Erreur lors de l'envoi");
	    System.exit(-1);
	}
 
	// Lecture de 'Bonjour'
	try {
	    message = input.readLine();
	} catch(Exception e) {
	    System.err.println("Erreur lors de la lecture");
	    System.exit(-1);
	}
	System.out.println("Lu: " + message);
 
	// Envoi de 'Au revoir'
	message = "Au revoir";
	System.out.println("Envoi: " + message);
	try {
	    output.println(message);
	} catch(Exception e) {
	    System.err.println("Erreur lors de l'envoi");
	    System.exit(-1);
	}
 
	// Lecture de 'Au revoir'
	try {
	    message = input.readLine();
	} catch(Exception e) {
	    System.err.println("Erreur lors de la lecture");
	    System.exit(-1);
	}
	System.out.println("Lu: " + message);
 
	// Fermeture des flux et de la socket
	try {
	    input.close();
	    output.close();
	    socket.close();
	} catch(Exception e) {
	    System.err.println("Erreur lors de la fermeture des flux et de la socket");
	    System.exit(-1);
	}
    }
 
}

