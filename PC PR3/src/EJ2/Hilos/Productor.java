package EJ2.Hilos;

import java.util.concurrent.Semaphore;

import EJ2.Almacen;
import EJ2.Clases.Producto;

public class Productor extends Thread implements Almacen {		
	
	Semaphore empty, full;		
	Producto producto;
	
	public Productor(Producto producto, Semaphore empty, Semaphore full) {		
		this.producto = producto;		
		this.empty = empty;
		this.full = full;
	}
	
	
	@Override 	
	public void run() {	
		//while(true) {
		try {
			empty.acquire();
			
			//almacenar(new Producto(1));
			//producto = new Producto(1);
			System.out.print("Valor antes " + producto.n);
			producto.n++;
			System.out.println(" Se ha almacenado un elemento ("+producto.n + ")");					
			
			full.release();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//}
		
					
    }


	@Override
	public void almacenar(Producto p) {
		producto = p;			
	}


	
	
	@Override
	public Producto extraer() {
		return null;
	}
		
		
    
}