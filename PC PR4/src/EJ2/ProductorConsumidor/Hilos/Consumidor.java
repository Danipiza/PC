package EJ2.ProductorConsumidor.Hilos;

import EJ2.ProductorConsumidor.Monitor;

public class Consumidor extends Thread {		
			
	Monitor monitor;
	
	public Consumidor(Monitor monitor) {		
		
		this.monitor = monitor;
	}
	
	
	@Override 	
	public void run() {	
		while(true) {
			try {		
				monitor.consumir();			
		
		
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
					
    }


	
		
		
    
}