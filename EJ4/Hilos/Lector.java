package EJ4.Hilos;

import java.util.concurrent.Semaphore;

import EJ4.Almacen;
import EJ4.Clases.*;


public class Lector extends Thread implements Almacen {		
	
	int id;
	Producto productos[];
	Entero ini, fin;
	int k;
	
	Semaphore e;
	Entero nr, nw, dr, dw;
	
	Semaphore r, w;
	
	public Lector(int id, Producto[] productos, Entero ini, Entero fin, int k,
			Semaphore e, Entero nr, Entero nw, Entero dr, Entero dw, Semaphore r, Semaphore w) {		
		this.id = id;
		this.productos = productos;		
		this.ini = ini;
		this.fin = fin;
		this.k = k;
		
		this.e = e;
		this.nr = nr;
		this.nw = nw;
		this.dr = dr;
		this.dw = dw;
		this.r = r;
		this.w = w;		
	}
	
	
	@Override 	
	public void run() {		
		while(true) {
			try {
				// TODO LO QUE FALTABA ERA QUE DORMIRLOS PARA QUE NO SIEMPRE LO COJA UN HILO
				Thread.sleep(10);
				e.acquire(); // P(e)
				if(nw.n > 0) {
					dr.n = dr.n +1;
					e.release(); // V(e)
					// PASO DE TESTIGO
					r.acquire(); // P(r)
				}
				
				nr.n = nr.n +1;
				if(dr.n > 0) {
					dr.n = dr.n -1;
					r.release(); // V(r)
				}
				else e.release(); // V(e)

				// Read
				System.out.println("Lector " + id + " lee");
				Thread.sleep(10);
				e.acquire(); // P(e)
				nr.n = nr.n -1;
				if(nr.n == 0 && dw.n > 0) {
					dw.n = dw.n -1;
					w.release(); // V(w)
				} else e.release(); // V(e)
							
				
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
					
    }


	


	@Override
	// SE PUEDEN LEER POR VARIOS LECTORES POR LO QUE NO HACE 
	// FALTA ELIMINAR EL PRODUCTO YA LEIDO
	public Producto leer(int pos) {		
		return productos[pos];
	}
	/*@Override
	public Producto extraer() {
		Producto x = productos[ini.n];
		productos[ini.n] = null;
		return x;
	}*/

	@Override
	public void escribir(Producto producto, int pos) {			
	}

	

	
	
	
		
		
    
}
