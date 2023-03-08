package EJ2.Hilos;

import java.util.concurrent.Semaphore;

import EJ2.Almacen;
import EJ2.Clases.Producto;

public class Consumidor extends Thread implements Almacen {		
	
	Semaphore empty, full;		
	Producto producto;
	
	public Consumidor(Producto producto, Semaphore empty, Semaphore full) {		
		this.producto = producto;		
		this.empty = empty;
		this.full = full;
	}
	
	
	@Override 	
	public void run() {	
		//while(true) {
		try {
			full.acquire();
			
			//producto = extraer();
			System.out.print("Valor antes " + producto.n);
			producto.n--;
			System.out.println(" Se ha extraido el producto: " + producto.n);					
			
			empty.release();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//}
		
					
    }


	@Override
	public void almacenar(Producto p) {				
	}


	
	
	@Override
	public Producto extraer() {
		Producto aux = producto;
		producto = null;
		return aux;
	}
		
		
    
}