package UDP;
import java.io.*;
import java.net.*;
 
/**
 * Classe correspondant à un client UDP
 * @author Cyril Rabat
 * @version 27/09/2013
 */
public class ClientUDP {
 
    public static void main(String[] args) {
	String message = "Bonjour";
	byte[] tampon = message.getBytes();
	DatagramSocket socket = null;
 
	// Creation du socket
	try {
	    socket = new DatagramSocket();
	} catch(Exception e) {
	    System.err.println("Erreur lors de la creation du socket");
	    System.exit(-1);
	}
 
	// Creation du message
	DatagramPacket msg = null;
	try {
	    InetAddress adresse = InetAddress.getByName(null);
	    msg = new DatagramPacket(tampon,
				     tampon.length,
				     adresse,
				     1025);
 
	} catch(Exception e) {
	    System.err.println("Erreur lors de la creation du message");
	    System.exit(-1);
	}
 
	// Envoi du message
	try {
	    socket.send(msg);
	} catch(Exception e) {
	    System.err.println("Erreur lors de l'envoi du message");
	    System.exit(-1);
	}
 
	// Fermeture du socket
	try {
	    socket.close();
	} catch(Exception e) {
	    System.err.println("Erreur lors de la fermeture du socket");
	    System.exit(-1);
	}
    }
 
}