package EJ2.RompeEmpates.Hilos;

import EJ2.RompeEmpates.LockRompeEmpate;
import EJ2.RompeEmpates.Clases.Entero;

public class Incrementa extends Thread{		
	private int N, i;
	private Entero x;
	
	private Entero[] in, last;
	
	private LockRompeEmpate REClase;
	
	public Incrementa(int N, int i, Entero x,
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
		
		// CS. Incrementa		
		x.n++;
		System.out.println(i+". Se ha incrementado: " + x.n);			
		
		// CSExit/ReleaseLock
		REClase.ReleaseLock(i, in);			
		
		
				
    }
		
		
    
}