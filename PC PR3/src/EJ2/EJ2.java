package EJ2;

import java.util.concurrent.Semaphore;

import EJ2.Clases.Producto;
import EJ2.Hilos.*;


public class EJ2 {	
	
	public EJ2(int N) {
		
		try {
			mainEJ1(N);
		} catch (InterruptedException e) {			
			e.printStackTrace();
		}
	}
	
	public volatile Producto x = new Producto(0);
	public volatile Semaphore empty = new Semaphore(1), full = new Semaphore(0);
	
	public void mainEJ1(int N) throws InterruptedException {
		
		
		Productor[] prod = new Productor[N];
		Consumidor[] cons = new Consumidor[N];

        for (int i = 0; i < N; i++) {            
        	prod[i] = new Productor(x, empty ,full);
        	cons[i] = new Consumidor(x, empty ,full);

            prod[i].start();
            cons[i].start();
        }

        for (int i = 0; i < N; i++) {
        	prod[i].join();
        	cons[i].join();
        }
        
		
        // SALIDA
        //System.out.println("El valor del entero al finalizar la ejecucion es: " + x.n);
		
	}
}
