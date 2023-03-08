package EJ3.Hilos;

import java.util.concurrent.Semaphore;

import EJ3.Almacen;
import EJ3.Clases.*;

public class Productor extends Thread implements Almacen {		
	
	Producto productos[];
	Entero ini, fin;
	int k;
	
	Semaphore empty, full;	
	Semaphore mutexP, mutexC;	
	
	
	public Productor(Producto[] productos, Entero ini, Entero fin, int k,
			Semaphore empty, Semaphore full, Semaphore mutexP, Semaphore mutexC) {		
		this.productos = productos;		
		this.ini = ini;
		this.fin = fin;
		this.k = k;
		this.empty = empty;
		this.full = full;
		this.mutexP = mutexP;
		this.mutexC = mutexC;
	}
	
	
	@Override 	
	public void run() {	
		while(true) {
			try {
				empty.acquire();
				mutexP.acquire();
				
				//buf
				almacenar(new Producto(1));
				//productos[fin.n] = new Producto(1);			
				fin.n = (fin.n+1)%k;
				mutexP.release();
				full.release();
							
				
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
					
    }


	@Override
	public void almacenar(Producto p) {
		productos[fin.n] = p;		
	}


	
	
	@Override
	public Producto extraer() {
		return null;
	}
		
		
    
}