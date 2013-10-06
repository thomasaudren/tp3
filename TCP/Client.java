package TCP;

public class Client {
	private String id = null;
	private String psw = null;
	private ApiTcp apiTp = null;
	
	

	public Client(String id, String psw) {
		this.id = id;
		this.psw = psw;
		this.apiTp = new ApiTcp();
	}
	
	public void close(){
		this.apiTp.close();
	}



	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
