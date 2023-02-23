package EJ2.RompeEmpates.Hilos;

import EJ2.RompeEmpates.LockRompeEmpate;
import EJ2.RompeEmpates.Clases.Entero;

public class Decrementa extends Thread{		
	private int N, i;
	private Entero x;
	
	private Entero[] in, last;
	private LockRompeEmpate REClase;
	
	
	
	public Decrementa(int N, int i, Entero x,
			Entero[] in, Entero[] last) {
		this.N = N;
		this.i = i;
		this.x = x;
		
		this.in = in;
		this.last = last;
		REClase = new LockRompeEmpate();
	}
	
	@Override
    public void run() {		
		
		
		
		// CSEntry/TakeLock
		REClase.TakeLock(N, i, in, last);		
		
		// CS. Decrementa		
		x.n--;
		System.out.println(i+". Se ha decrementado: " + x.n);			
		
		// CSExit/ReleaseLock
		REClase.ReleaseLock(i, in);			
		
				
    }
}