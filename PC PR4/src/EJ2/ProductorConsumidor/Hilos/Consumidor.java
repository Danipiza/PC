package EJ2.ProductorConsumidor.Hilos;

import EJ2.ProductorConsumidor.Monitor;
import EJ2.ProductorConsumidor.MonitorLock;

public class Consumidor extends Thread {		
			
	private Monitor monitor;
	private int mode;
	private MonitorLock monitorLock;
	
	public Consumidor(Monitor monitor, int mode, MonitorLock monitorLock) {		
		this.monitor = monitor;
		this.mode = mode;
		this.monitorLock = monitorLock;		
	}
	
	
	@Override 	
	public void run() {	
		while(true) {
			try {		
				if(mode == 1) monitor.consumir();
				else monitorLock.consumir();
		
		
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
					
    }


	
		
		
    
}