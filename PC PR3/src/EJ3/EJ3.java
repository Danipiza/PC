package EJ3;

import java.util.concurrent.Semaphore;

import EJ3.Clases.Entero;
import EJ3.Clases.Producto;
import EJ3.Hilos.Consumidor;
import EJ3.Hilos.Productor;




public class EJ3 {	
	
	public EJ3(int N) {
		
		try {
			mainEJ1(N);
		} catch (InterruptedException e) {			
			e.printStackTrace();
		}
	}
	
	final int k = 5;
	
	public volatile Producto productos[] = new Producto[k];
	public volatile Entero ini = new Entero(0), fin = new Entero(0);
	
	public volatile Semaphore empty = new Semaphore(k), full = new Semaphore(0);
	public volatile Semaphore mutexP = new Semaphore(1), mutexC = new Semaphore(1);
	
	public void mainEJ1(int N) throws InterruptedException {
		
		
		Productor[] prod = new Productor[N];
		Consumidor[] cons = new Consumidor[N];

        for (int i = 0; i < N; i++) {            
        	prod[i] = new Productor(productos, ini, fin, k, empty ,full, mutexP, mutexC);
        	cons[i] = new Consumidor(productos, ini, fin, k, empty ,full, mutexP, mutexC);

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
