package EJ4;

import java.util.concurrent.Semaphore;

import EJ4.Clases.Entero;
import EJ4.Clases.Producto;
import EJ4.Hilos.Lector;
import EJ4.Hilos.Escritor;




public class EJ4 {	
	
	public EJ4(int N) {
		
		try {
			mainEJ1(N);
		} catch (InterruptedException e) {			
			e.printStackTrace();
		}
	}
	
	final int k = 5;
	
	public volatile Producto productos[] = new Producto[k];
	public volatile Entero ini = new Entero(0), fin = new Entero(0);
	
	//public volatile Semaphore empty = new Semaphore(k), full = new Semaphore(0);
	//public volatile Semaphore mutexP = new Semaphore(1), mutexC = new Semaphore(1);
	
	
	public volatile Semaphore e = new Semaphore(1);
	public volatile Entero  nr = new Entero(0), nw = new Entero(0), 
							dr = new Entero(0), dw = new Entero(0);
	
	public volatile Semaphore r = new Semaphore(0), w = new Semaphore(0);
	
	public void mainEJ1(int N) throws InterruptedException {
		
		
		Escritor[] prod = new Escritor[N];
		Lector[] cons = new Lector[N];

        for (int i = 0; i < N; i++) {          	
        	prod[i] = new Escritor(i, productos, ini, fin, k, e, nr, nw, dr, dw, r, w);
        	cons[i] = new Lector(i, productos, ini, fin, k, e, nr, nw, dr, dw, r, w);

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
