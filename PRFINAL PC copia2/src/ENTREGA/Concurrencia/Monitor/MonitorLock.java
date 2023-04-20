package ENTREGA.Concurrencia.Monitor;

import java.util.HashMap;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class MonitorLock {

	private HashMap<String, Boolean> pideFich;
	private final Lock l;
	
	public MonitorLock() {
		this.pideFich = new HashMap<String, Boolean>();
		l = new ReentrantLock(true);
	}
	
	public boolean getPideFich(String u) {
		return pideFich.get(u);
	}
	
	public void pideFichTrue(String u) {
		l.lock();
		pideFich.put(u, true);
		l.unlock();
	}
	
	public void pideFichFalse(String u) {
		l.lock();
		pideFich.put(u,false);
		l.unlock();		
	}
	
	public void eliminaUsuario(String u) {
		l.lock();
		pideFich.remove(u);
		l.unlock();
	}
	
}
