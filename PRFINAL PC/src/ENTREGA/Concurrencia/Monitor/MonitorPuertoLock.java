package ENTREGA.Concurrencia.Monitor;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;


public class MonitorPuertoLock {
	
	private int puerto;
	private int indice;
	private HashMap<String, Boolean> conexiones;
	
	private final String HOST = "127.0.0.1";	
	
	
	public MonitorPuertoLock(int puerto) {
		this.conexiones = new HashMap<String, Boolean>();
		this.indice = 0;
		this.puerto = puerto;		
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
	
	public synchronized int getIndice() {	
		return this.indice;
	}		
	public synchronized void aumentaIndice() {
		this.indice++;
	}
	public synchronized void reduceIndice() {
		this.indice--;
	}
	
	public synchronized HashMap<String, Boolean> getConexiones(){
		return conexiones;
	}
	
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
	
	public synchronized void addConexion(String IDEmisor, String IDDestino) {
		conexiones.put(IDEmisor +"_"+ IDDestino, true);
	}
	
	public synchronized void eliminaConexion(String IDEmisor, String IDDestino) {	
		conexiones.remove(IDEmisor +"_"+ IDDestino);		
	}
	
}
