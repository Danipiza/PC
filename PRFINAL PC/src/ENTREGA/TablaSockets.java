package ENTREGA;

import java.util.HashMap;

// Toda la informacion de los canales de comunicacion
public class TablaSockets {
		
	private HashMap<String, InfoSocket> sockets;
	
	
	public TablaSockets() {		
		this.sockets = new HashMap<String, InfoSocket>();			
	}
	
	
	public HashMap<String, InfoSocket> getSockets(){
		return this.sockets;
	}
	
	public InfoSocket getSocket(String u) {
		return this.sockets.get(u);
	}
	
	public void addSocket(String u, InfoSocket s) {
		this.sockets.put(u, s);
	}
	
	public void eliminaSocket(String u) {
		this.sockets.remove(u);		
	}
	
		
	
}
