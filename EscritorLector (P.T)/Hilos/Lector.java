package EscritorLector.Hilos;

import java.util.concurrent.Semaphore;
import EscritorLector.Clases.Entero;

public class Lector extends Thread {

	int i;
	Semaphore e;
	Entero nr,nw;
	Entero dr,dw;
	Semaphore r, w;
	
	public Lector(int i, Semaphore e, Entero nr, Entero nw, Entero dr, Entero dw, 
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
				Thread.sleep(10);
				e.acquire();
				if(nw.n > 0) {
					dr.n++;
					e.release();
					r.acquire(); // P.T
				}
				nr.n++;
				if(dr.n > 0) {
					dr.n--;
					r.release(); // P.T
				} else e.release();
				
				System.out.println("Lector " + i + " lee");
				Thread.sleep(10);
				e.acquire();
				nr.n--;
				if(nr.n == 0 && dw.n > 0) {
					dw.n--;
					w.release();
				} else e.release();
				
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			
			
		}
					
    }
	
	
	
	
	
}
