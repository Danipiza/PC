package EJ3.Hilos;

import java.util.concurrent.Semaphore;

import EJ3.Almacen;
import EJ3.Clases.Entero;
import EJ3.Clases.Producto;

public class Consumidor extends Thread implements Almacen {		
	
	Producto productos[];
	Entero ini, fin;
	int k;
	
	Semaphore empty, full;	
	Semaphore mutexP, mutexC;	
	
	
	public Consumidor(Producto[] productos, Entero ini, Entero fin, int k,
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
				full.acquire();
				mutexC.acquire();
				
				//buf
				Producto x = extraer();
				//Producto x = productos[ini.n];
				//productos[ini.n] = null;
				System.out.println("Cogido " + x.n);
				//producto.n++;
				ini.n = (ini.n+1)%k;
				mutexC.release();
				empty.release();
							
				
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
					
    }


	@Override
	public void almacenar(Producto p) {				
	}


	
	
	@Override
	public Producto extraer() {
		Producto x = productos[ini.n];
		productos[ini.n] = null;
		return x;
	}
		
		
    
}