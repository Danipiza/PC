package EJ1.Hilos;

import java.util.concurrent.Semaphore;

import EJ1.Clases.Entero;

public class Decrementa extends Thread{		
	
	private Entero x;	
	private Semaphore sem;
	
	public Decrementa(Entero x, Semaphore sem) {		
		this.x = x;		
		this.sem = sem;
	}
	
	
	@Override 	
	public void run() {				
		try {
			sem.acquire();
			x.n--;
			System.out.println("Se ha decrementado: " + x.n);					
			sem.release();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
					
    }
		
		
    
}