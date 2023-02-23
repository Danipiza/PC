package EJ1.Hilos;

import EJ1.RompeEmpates;
import EJ1.Clases.Entero;

public class Incrementa extends Thread{		
	private int N;
	private Entero x;
	
	private boolean in1, in2;
	private Entero last;
	private RompeEmpates RE;	
	
	public Incrementa(int N, Entero x,
		boolean in1, boolean in2, Entero last) {
		this.N = N;
		this.x = x;
		
		this.in1 = in1;
		this.in2 = in2;
		this.last = last;
		RE = new RompeEmpates();
	}
	
	
	@Override 	
	public void run() {
		while(N != 0) {			
			// CSEntry/TakeLock
			RE.TakeLockP2(in1,in2,last);			
			
			// CS. Incrementa		
			x.n++;
			System.out.println("Se ha incrementado: " + x.n);			
			
			// CSExit/ReleaseLock
			RE.ReleaseLockP2(in1, last);			
			
			N--;
		}			
    }
		
		
    
}