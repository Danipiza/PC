package EJ2.ProductorConsumidor.Hilos;

import EJ2.ProductorConsumidor.Monitor;
import EJ2.ProductorConsumidor.MonitorLock;

public class Productor extends Thread {		
		
	private int mode;
	//Producto producto;
	private Monitor monitor;
	private MonitorLock monitorLock;
	
	public Productor(Monitor monitor, int mode, MonitorLock monitorLock) {		
		this.monitor = monitor;
		this.mode = mode;
		this.monitorLock = monitorLock;
	}
	
	
	@Override 	
	public void run() {	
		while(true) {				
			try {
				if(mode == 1) monitor.producir();
				else monitorLock.producir();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}		
					
    }

		
    
}