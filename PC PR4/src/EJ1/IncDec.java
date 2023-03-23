package EJ1;


//import EJ1.Clases.Entero;
import EJ1.Hilos.Decrementa;
import EJ1.Hilos.Incrementa;



public class IncDec {		
	
	public IncDec(int N, int mode) {		
		
		try {
			mainEJ2(N, mode);
		} catch (InterruptedException e) {			
			e.printStackTrace();
		}
	}
	
	
	
	
	
	public void mainEJ2(int N, int mode) throws InterruptedException {
			
		
		//ticket.getAndAdd(1);
		
		Monitor monitor = new Monitor(0);
		MonitorLock monitorLock = new MonitorLock(0);
		
		Incrementa[] incrementa = new Incrementa[N];
		Decrementa[] decrementa = new Decrementa[N];
		

        for (int i = 0; i < N; i++) {
        	incrementa[i] = new Incrementa(i, monitor, mode, monitorLock);
        	decrementa[i] = new Decrementa(i+5,monitor, mode, monitorLock);

            incrementa[i].start();
            decrementa[i].start();
        }

        for (int i = 0; i < N; i++) {
        	incrementa[i].join();
        	decrementa[i].join();
        }
        
		
		
	}
}
