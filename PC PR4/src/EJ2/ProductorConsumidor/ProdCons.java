package EJ2.ProductorConsumidor;

import EJ2.ProductorConsumidor.Hilos.Consumidor;
import EJ2.ProductorConsumidor.Hilos.Productor;

public class ProdCons {
	
	public ProdCons(int N) {
		
		try {
			mainEJ1(N);
		} catch (InterruptedException e) {			
			e.printStackTrace();
		}
	}
	
	
	
	public void mainEJ1(int N) throws InterruptedException {
		
		Monitor monitor = new Monitor();
		
		Productor prod = new Productor(monitor);
		Consumidor[] cons = new Consumidor[N];

		prod.start();
        for (int i = 0; i < N; i++) {                  	
        	cons[i] = new Consumidor(monitor);           
            cons[i].start();
        }

        prod.join();
        for (int i = 0; i < N; i++) {        	
        	cons[i].join();
        }
        
		
        // SALIDA
        //System.out.println("El valor del entero al finalizar la ejecucion es: " + x.n);
		
	}
}
