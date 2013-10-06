package TCP;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;


public class ApiTcp {
	public static final int numeroPort = 5001;
	private Socket socket = null;
	private ServerSocket serverSocket = null;
	private Socket socketClient = null;
	private String host = null;
	private BufferedReader input =null;
	private PrintWriter output = null;
	
	public void iniSend(String host) {
		host = (host.equals(""))?"localhost":host;
		this.host = host;
		try {
		    this.socket = new Socket(host, this.numeroPort);
		} catch(Exception e) {
		    System.err.println("Creation socket impossible "+e.getMessage());
		    System.exit(-1);
		}
	}
	
	public void iniReceive() {
		try {	
		    this.serverSocket = new ServerSocket(this.numeroPort);
		} catch(Exception e) {
			System.err.println("Creation socket impossible "+e.getMessage());
		    System.exit(-1);
		}
	}
	
	public void attentClient(){
		try {
		    this.socketClient = this.serverSocket.accept();
		} catch(Exception e) {
		    System.err.println("Erreur lors de l'attente d'une connexion");
		    System.exit(-1);
		}
	}
	
	public void assocFlux(){
		Socket socketUtil = (this.socket != null)?this.socket:this.socketClient;
		try {
		    this.input = new BufferedReader(new InputStreamReader(socketUtil.getInputStream()));
		    this.output = new PrintWriter(new BufferedWriter(new OutputStreamWriter(socketUtil.getOutputStream())), true);
		} catch(Exception e) {
		    System.err.println("Association des flux impossible");
		    System.exit(-1);
		}
	}
	
	public void send(String message){
		System.out.println("Envoi: " + message);
		try {
		    this.output.println(message);
		} catch(Exception e) {
		    System.err.println("Erreur lors de l'envoi");
		    System.exit(-1);
		}
	}
	
	public String receive(){
		String message = null;
		try {
		    message = input.readLine();
		} catch(Exception e) {
		    System.err.println("Erreur lors de la lecture");
		    System.exit(-1);
		}
		return message;
	}
	
	public void close(){
		try {
		    this.input.close();
		    this.output.close();
		    if(this.socket!=null){
		    	this.socket.close();
		    }
		    else{
		    	this.socketClient.close();
			    this.serverSocket.close();
		    }
		} catch(Exception e) {
		    System.err.println("Erreur lors de la fermeture des flux et sockets");
		    System.exit(-1);
		}
	}
	public void serveurTcp(){
		this.iniReceive();
		this.attentClient();
		this.assocFlux();
	}
	public void clientTcp(String host){
		this.iniSend(host);
		this.assocFlux();
	}
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		ApiTcp u = new ApiTcp();
		//test recoi
//		u.serveurTcp();
//		u.receive();
		//test envoi
		u.clientTcp("");
		u.send("coucou");
		u.close();

	}

}
