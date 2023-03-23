package EJ1.Hilos;

import EJ1.Monitor;
import EJ1.MonitorLock;

public class Decrementa extends Thread{		
	private int i, mode;
	private Monitor monitor;
	private MonitorLock monitorLock;
	/*private Entero x;
	
	public volatile AtomicInteger ticket;
	public volatile Entero next;
	public volatile Entero[] turno;
	
	private TicketAlgoritmo TicketClase;*/
	
	public Decrementa(int i, Monitor monitor, int mode, MonitorLock monitorLock) {
		this.i = i;
		this.monitor = monitor;
		this.mode = mode;
		this.monitorLock = monitorLock;
	}
	
	@Override 	
	public void run() {
		// SIN ESTO CASI SIEMPRE VA POR ORDEN
		
		try {
			Thread.sleep(100);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		if(mode == 1) monitor.decrementa();
		else monitorLock.decrementa();
		
		
				
    }
		
		
    
}