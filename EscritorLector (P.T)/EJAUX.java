package EscritorLector;

import java.util.concurrent.Semaphore;

import EscritorLector.Clases.*;
import EscritorLector.Hilos.*;




public class EJAUX {	
	
	public EJAUX(int N) {
		
		try {
			mainEJ1(N);
		} catch (InterruptedException e) {			
			e.printStackTrace();
		}
	}
	
	final int k = 5;
	
	//public volatile Producto productos[] = new Producto[k];
	public volatile Entero ini = new Entero(0), fin = new Entero(0);
	
	public volatile Semaphore empty = new Semaphore(k), full = new Semaphore(0);
	public volatile Semaphore mutexP = new Semaphore(1), mutexC = new Semaphore(1);
	
	
	public volatile Semaphore e = new Semaphore(1);
	public volatile Entero nr = new Entero(0), nw = new Entero(0);
	public volatile Entero dr = new Entero(0), dw = new Entero(0);
	
	public volatile Semaphore r = new Semaphore(0), w = new Semaphore(0);
	
	public void mainEJ1(int N) throws InterruptedException {
		
		
		Lector[] lectores = new Lector[N];
		Escritor[] escritores = new Escritor[N];

        for (int i = 0; i < N; i++) {            
        	lectores[i] = new Lector(i, e, nr, nw, dr, dw, r, w);
        	escritores[i] = new Escritor(i, e, nr, nw, dr, dw, r, w);

        	lectores[i].start();
        	escritores[i].start();
        }

        for (int i = 0; i < N; i++) {
        	lectores[i].join();
        	escritores[i].join();
        }
        
		
        // SALIDA
        //System.out.println("El valor del entero al finalizar la ejecucion es: " + x.n);
		
	}
}
