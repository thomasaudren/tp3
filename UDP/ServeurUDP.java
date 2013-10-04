package UDP;
import java.io.*;
import java.net.*;
 
/**
 * Classe correspondant � un serveur UDP
 * @author Cyril Rabat
 * @version 27/09/2013
 */
public class ServeurUDP {
 
    public static void main(String[] args) {
	byte[] tampon = new byte[1024];
	DatagramSocket socket = null;
 
	// Creation du socket
	try {	    
	    socket = new DatagramSocket(1025);
	} catch(Exception e) {
	    System.err.println("Erreur lors de la creation du socket "+e.getMessage());
	    System.exit(-1);
	}
 
	// Creation du message
	DatagramPacket msg = new DatagramPacket(tampon, tampon.length);
 
	// Lecture du message du client
	try {
	    socket.receive(msg);
	    String texte = new String(tampon, 0, msg.getLength());
	    System.out.println("Lu: " + texte);
	} catch(Exception e) {
	    System.err.println("Erreur lors de la reception du message");
	    System.exit(-1);
	}
 
	// Fermeture du socket
	try {
	    socket.close();
	} catch(Exception e) {
	    System.err.println("Erreur de la fermeture du socket");
	}
    }
 
}

