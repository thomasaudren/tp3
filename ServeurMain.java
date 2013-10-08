import UDP.Serveur;


public class ServeurMain {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		UDP.Serveur udp = new UDP.Serveur();
		udp.start();
		
		TCP.Serveur[] tcp = {new TCP.Serveur() };
		tcp[0].start();
		/*while(true){
			if(tcp[tcp.length-1].getApi().getSocketClient() != null){
				tcp[tcp.length] = new TCP.Serveur();
				tcp[tcp.length].start();
			}
		}*/

	}

}
