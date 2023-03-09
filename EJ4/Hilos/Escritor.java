package EJ4.Hilos;

import java.util.concurrent.Semaphore;

import EJ4.Almacen;
import EJ4.Clases.*;

public class Escritor extends Thread implements Almacen {		
	
	int id;
	Producto productos[];
	Entero ini, fin;
	int k;
	
	Semaphore e;
	Entero nr, nw, dr, dw;
	
	Semaphore r, w;
	
	public Escritor(int id, Producto[] productos, Entero ini, Entero fin, int k,
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
				e.acquire(); // P(e)
				if(nr.n > 0 || nw.n >0) {
					dw.n = dw.n+1;
					e.release(); // V(e)
					
					// PASO DE TESTIGO
					w.acquire(); // P(w)
					
				}
				nw.n = nw.n+1;
				e.release(); // V(e)

				// write
				System.out.println("Escritor " + id + " escribe");
				
				e.acquire(); // P(e)
				nw.n = nw.n -1;
				if(dw.n > 0) {
					dw.n = dw.n -1;
					w.release(); // V(w)
				}
				else {
					if(dr.n > 0) {
						dr.n = dr.n -1;
						r.release(); // V(r)
					}
					else e.release(); // V(e)
				}
				
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
					
    }


	@Override
	public void escribir(Producto p, int pos) {
		productos[pos] = p;
		
	}
	/*@Override
	public void almacenar(Producto p) {
		productos[fin.n] = p;		
	}
	*/

	
	@Override
	public Producto leer(int pos) {		
		return null;
	}


		
    
}