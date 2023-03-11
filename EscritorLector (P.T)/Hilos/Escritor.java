package EscritorLector.Hilos;

import java.util.concurrent.Semaphore;

import EscritorLector.Clases.Entero;

public class Escritor extends Thread {

	int i;
	Semaphore e;
	Entero nr,nw;
	Entero dr,dw;
	Semaphore r, w;
	
	public Escritor(int i, Semaphore e, Entero nr, Entero nw, Entero dr, Entero dw, 
			Semaphore r, Semaphore w) {
		this.i = i;
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
				//Thread.sleep(100);
				e.acquire();
				if(nr.n > 0 || nw.n > 0) { // Esperar
					dw.n++;
					e.release();
					w.acquire(); // P.T
				}
				nw.n++;
				e.release();
				
				System.out.println("Escritor " + i + " escribe");
				Thread.sleep(10);
				e.acquire();
				nw.n--;
				if(dw.n > 0) {
					dw.n--;
					w.release(); // P.T
				}
				else {
					if(dr.n > 0) {
						dr.n--;
						r.release();
					} else e.release();
					
				}
				
				Thread.sleep(100);
				
				
				
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			
			
		}
					
    }
	
}
