package TCP;

import java.io.*;
import java.net.*;
 
/**
 * Classe correspondant à un serveur TCP
 * @author Cyril Rabat
 * @version 2012/08/30
 */
public class ServeurTCP {
 
    public static final int numeroPort = 5001;
 
    public static void main(String[] args) {
	// Creation du socket serveur
	ServerSocket socketServeur = null;
	try {	
	    socketServeur = new ServerSocket(numeroPort);
	} catch(Exception e) {
	    System.err.println("Creation socket impossible");
	    System.exit(-1);
	}
 
	// Attente d'une connexion d'un client
	Socket socketClient = null;
	try {
	    socketClient = socketServeur.accept();
	} catch(Exception e) {
	    System.err.println("Erreur lors de l'attente d'une connexion");
	    System.exit(-1);
	}
 
	// Association d'un flux d'entree et de sortie
	BufferedReader input = null;
	PrintWriter output = null;
	try {
	    input = new BufferedReader(new InputStreamReader(socketClient.getInputStream()));
	    output = new PrintWriter(new BufferedWriter(new OutputStreamWriter(socketClient.getOutputStream())), true);
	} catch(Exception e) {
	    System.err.println("Association des flux impossible");
	    System.exit(-1);
	}
 
	// Lecture de 'Bonjour'
	String message = "";
	try {
	    message = input.readLine();
	} catch(Exception e) {
	    System.err.println("Erreur lors de la lecture");
	    System.exit(-1);
	}
	System.out.println("Lu: " + message);
 
	// Envoi de 'Bonjour'
	message = "Bonjour";
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
 
	// Envoi de 'Au revoir'
	message = "Au revoir";
	System.out.println("Envoi: " + message);
	try {
	    output.println(message);
	} catch(Exception e) {
	    System.err.println("Erreur lors de l'envoi");
	    System.exit(-1);
	}
 
	// Fermeture des flux et des sockets
	try {
	    input.close();
	    output.close();
	    socketClient.close();
	    socketServeur.close();
	} catch(Exception e) {
	    System.err.println("Erreur lors de la fermeture des flux et sockets");
	    System.exit(-1);
	}
    }
 
}