package EJ1;

import java.util.concurrent.Semaphore;

import EJ1.Clases.Entero;
import EJ1.Hilos.*;


public class EJ1 {	
	
	public EJ1(int N) {
		
		try {
			mainEJ1(N);
		} catch (InterruptedException e) {			
			e.printStackTrace();
		}
	}
	
	public volatile Entero x = new Entero(0);
	private volatile Semaphore sem = new Semaphore(1);;
	
	public void mainEJ1(int N) throws InterruptedException {
		
		
		Incrementa[] incrementa = new Incrementa[N];
		Decrementa[] decrementa = new Decrementa[N];

        for (int i = 0; i < N; i++) {            
        	incrementa[i] = new Incrementa(x, sem);
            decrementa[i] = new Decrementa(x, sem);

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
