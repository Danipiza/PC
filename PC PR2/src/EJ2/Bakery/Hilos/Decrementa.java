package EJ2.Bakery.Hilos;

import EJ2.Bakery.BakeryAlgoritmo;
import EJ2.Bakery.Clases.*;

public class Decrementa extends Thread{		
	private int N, i;
	private Entero x;

	public Entero[] turno;

	private BakeryAlgoritmo BakeryClase;
	
	public Decrementa(int N, int i, Entero x, Entero[] turno) {
		this.N = N;
		this.i = i;
		this.x = x;
		
		this.turno = turno;
		BakeryClase = new BakeryAlgoritmo();
	}
	
	@Override 	
	public void run() {
		turno[i].n = 0;
		// SIN ESTO CASI SIEMPRE VA POR ORDEN
		/*try {
			Thread.sleep(100);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/
		
		// CSEntry/TakeLock
		BakeryClase.TakeLock(N, i, turno);				
		
		// CS. 		
		
		x.n--;
		System.out.println(i+". Se ha decrementado: " + x.n);		
		
			
		
		// CSExit/ReleaseLock
		BakeryClase.ReleaseLock(i, turno);			
		
		
				
    }
		
		
    
}