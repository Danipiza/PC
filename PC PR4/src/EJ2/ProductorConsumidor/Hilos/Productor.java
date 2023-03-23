package EJ2.ProductorConsumidor.Hilos;

import EJ2.ProductorConsumidor.Monitor;

public class Productor extends Thread {		
		
	//Producto producto;
	Monitor monitor;
	
	public Productor(Monitor monitor) {		
		this.monitor = monitor;
	}
	
	
	@Override 	
	public void run() {	
		while(true) {				
			try {
				monitor.producir();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}		
					
    }

		
    
}