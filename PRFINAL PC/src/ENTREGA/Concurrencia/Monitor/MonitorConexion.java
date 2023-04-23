package ENTREGA.Concurrencia.Monitor;

import java.util.HashMap;
import java.util.Map.Entry;

/* Este monitor se usa para compartir la variable HOST, que aumenta
 		cuando un cliente se conecta a otro
   Y para saber los clientes que estan conectados entre si, y asi 
 		finalizar su conexion cuando el cliente emisor quiera.
Implementado usando synchronized.
*/
public class MonitorConexion {	
	
	// Puerto que va aumentadose cada vez que se conectan 
	// 2 clientes para compartir pelicula
	private int puerto;
	// Host donde se conectan
	private final String HOST = "127.0.0.1";	
	
	// Para saber los clientes conectados entre si
	private HashMap<String, Boolean> conexiones;
	
	public MonitorConexion(int puerto) {
		this.puerto = puerto;		
		this.conexiones = new HashMap<String, Boolean>();			
	}
	
	public synchronized String getHost() {
		return HOST;
	}
	
	public synchronized int getPuerto() {	
		return this.puerto;
	}		
	public synchronized void aumentaPuerto() {
		this.puerto++;
	}	
	
	public synchronized HashMap<String, Boolean> getConexiones(){
		return conexiones;
	}

	public synchronized void addConexion(String IDEmisor, String IDDestino) {
		conexiones.put(IDEmisor +"_"+ IDDestino, true);
	}	
	public synchronized void eliminaConexion(String IDEmisor, String IDDestino) {	
		conexiones.remove(IDEmisor +"_"+ IDDestino);		
	}
	
	// Finaliza una conexion Peer to Peer entre dos clientes
	// Introduce el cliente que pidio la pelicula y un numero,
	// que es el numero de conexion por si esta conectado a mas de un cliente
	public synchronized void conexionFalse(String IDEmisor, int num) {
		int i = 0;
		for (Entry<String, Boolean> c : conexiones.entrySet()) {			
			if(num == i) {
				String[] aux = c.getKey().split("_");
				conexiones.put(IDEmisor+"_"+aux[1], false);
			}
			i++;
		}
	}
	
	
	
}
