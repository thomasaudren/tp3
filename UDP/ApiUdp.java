package UDP;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class ApiUdp {

	private String message = null;
	private byte[] tampon = null;
	private DatagramSocket socket = null;
	private String adresse = null;
	private DatagramPacket msg = null;

	public void iniSend(String message, String adresse) {
		this.message = message;
		this.tampon = message.getBytes();
		this.adresse = (adresse.equals("")) ? null : adresse;

		try {
			this.socket = new DatagramSocket();
		} catch (Exception e) {
			System.err.println("Erreur lors de la creation du socket");
			System.exit(-1);
		}
	}
	
	public void iniReceive() {
		this.tampon = new byte[1024];
	 
		try {	    
		    this.socket = new DatagramSocket(1025);
		} catch(Exception e) {
		    System.err.println("Erreur lors de la creation du socket "+e.getMessage());
		    System.exit(-1);
		}
	 
		this.msg = new DatagramPacket(this.tampon, this.tampon.length);
	}

	public byte[] getTampon() {
		return this.tampon;
	}

	public DatagramPacket getMsg() {
		return this.msg;
	}

	public void write() {
		try {
			InetAddress adresse = InetAddress.getByName(null);
			this.msg = new DatagramPacket(this.tampon, this.tampon.length,
					adresse, 1025);

		} catch (Exception e) {
			System.err.println("Erreur lors de la creation du message");
			System.exit(-1);
		}
	}

	public void send() {
		// Envoi du message
		try {
			this.socket.send(this.msg);
		} catch (Exception e) {
			System.err.println("Erreur lors de l'envoi du message");
			System.exit(-1);
		}
	}
	
	public String receive() {
		String texte ="";
		try {
		    this.socket.receive(this.msg);
		    texte = new String(tampon, 0, msg.getLength());
	
		} catch(Exception e) {
		    System.err.println("Erreur lors de la reception du message");
		    System.exit(-1);
		}
		return texte;
	}

	public void close() {
		try {
			this.socket.close();
		} catch (Exception e) {
			System.err.println("Erreur lors de la fermeture du socket");
			System.exit(-1);
		}
	}
	
	public void setMessage(String msg){
		this.message = msg;
		this.tampon = msg.getBytes();
		
		this.write();
	}

	public static void main(String[] args) {
		ApiUdp a = new ApiUdp();
		
		//test send
//		a.iniSend("thomas","");
//		a.write();
//		a.setMessage("marie");
//		a.send();
		//test receive
		
		a.iniReceive();
		a.receive();
		
		a.close();
		
		

	}

}
