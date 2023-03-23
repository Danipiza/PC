package EJ2.ProductorConsumidor;

import EJ2.ProductorConsumidor.Hilos.Consumidor;
import EJ2.ProductorConsumidor.Hilos.Productor;

public class ProdCons {
	
	public ProdCons(int N, int mode) {
		
		try {
			mainEJ1(N, mode);
		} catch (InterruptedException e) {			
			e.printStackTrace();
		}
	}
	
	
	
	public void mainEJ1(int N, int mode) throws InterruptedException {
		
		Monitor monitor = new Monitor();
		MonitorLock monitorLock = new MonitorLock();
		
		Productor prod = new Productor(monitor, mode, monitorLock);
		Consumidor[] cons = new Consumidor[N];

		prod.start();
        for (int i = 0; i < N; i++) {                  	
        	cons[i] = new Consumidor(monitor, mode, monitorLock);           
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
