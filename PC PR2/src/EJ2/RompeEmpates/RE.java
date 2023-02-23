package EJ2.RompeEmpates;

import EJ2.RompeEmpates.Clases.Entero;
import EJ2.RompeEmpates.Hilos.Decrementa;
import EJ2.RompeEmpates.Hilos.Incrementa;

public class RE {	
	
	public RE(int N) {
		
		try {
			mainEJ1(N);
		} catch (InterruptedException e) {			
			e.printStackTrace();
		}
	}
	
	
	public volatile Entero[] in, last;
	
	private void init(int N) {
		for(int i = 0; i < N*2; i++) {			
			in[i] = new Entero(-1);
			last[i] = new Entero(-1);
		}
	}
	
	public void mainEJ1(int N) throws InterruptedException {
		Entero x = new Entero(0);		
		
		in = new Entero[N*2];
		last = new Entero[N*2];
		init(N);
		
		Incrementa[] incrementadores = new Incrementa[N];
        Decrementa[] decrementadores = new Decrementa[N];

        for (int i = 0; i < N; i++) {
            incrementadores[i] = new Incrementa(N*2, i*2, x, in, last);
            decrementadores[i] = new Decrementa(N*2, i*2+1, x, in, last);

            incrementadores[i].start();
            decrementadores[i].start();
        }

        for (int i = 0; i < N; i++) {
            incrementadores[i].join();
            decrementadores[i].join();
        }
        
		
        // SALIDA
        System.out.println("El valor del entero al finalizar la ejecucion es: " + x.n);
		
	}
}
