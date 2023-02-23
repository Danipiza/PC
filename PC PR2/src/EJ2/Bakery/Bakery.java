package EJ2.Bakery;

import EJ2.Bakery.Clases.*;
import EJ2.Bakery.Hilos.*;

public class Bakery {		
	
	public Bakery(int N) {		
		
		try {
			mainEJ2(N);
		} catch (InterruptedException e) {			
			e.printStackTrace();
		}
	}
		
	
	
	public volatile Entero[] turno;
	
	private void init(int N) {
		for(int i = 0; i < N*2; i++) {
			turno[i] = new Entero(-1);
		}
	}
	
	public void mainEJ2(int N) throws InterruptedException {
		Entero x = new Entero(0);		
		
		//ticket.getAndAdd(1);
		
		turno = new Entero[N*2];
		init(N);
		
		Incrementa[] incrementa = new Incrementa[N];
		Decrementa[] decrementa = new Decrementa[N];

        for (int i = 0; i < N; i++) {            
        	incrementa[i] = new Incrementa(N*2, i*2, x, turno);
            decrementa[i] = new Decrementa(N*2, i*2+1, x, turno);

            incrementa[i].start();
            decrementa[i].start();
        }

        for (int i = 0; i < N; i++) {
            incrementa[i].join();
            decrementa[i].join();
        }
        
		
        // SALIDA
        System.out.println("El valor del entero al finalizar la ejecucion es: " + x.n);
		
	}
}
