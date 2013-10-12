package Serveur;
import java.net.ServerSocket;
import java.net.Socket;



public class ServeurMain {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		Serveur.ServeurUDP udp = new Serveur.ServeurUDP();
		udp.start();
		
		int portTcp = 5001;
		try {
		      ServerSocket socketServeur = new ServerSocket(portTcp);
		      System.out.println("Lancement du serveur TCP");
		      while (true) {
		        Socket socketClient = socketServeur.accept();
		        Serveur.ServeurTCP serveurTcp = new Serveur.ServeurTCP(socketClient);
		        serveurTcp.start();
		      }
		    } catch (Exception e) {
		      e.printStackTrace();
		    }
		

	}

}
