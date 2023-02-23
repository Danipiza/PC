package EJ2.Ticket;

import java.util.concurrent.atomic.AtomicInteger;

import EJ2.Ticket.Clases.Entero;
import EJ2.Ticket.Hilos.Decrementa;
import EJ2.Ticket.Hilos.Incrementa;



public class Ticket {		
	
	public Ticket(int N) {		
		
		try {
			mainEJ2(N);
		} catch (InterruptedException e) {			
			e.printStackTrace();
		}
	}
	
	//public volatile boolean in1 = false, in2 = false;
	//public volatile Entero last = new Entero(0);
	
	
	
	public volatile AtomicInteger ticket = new AtomicInteger(0);
	public volatile Entero next = new Entero(0);
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
        	incrementa[i] = new Incrementa(i, x, ticket, next, turno);
        	decrementa[i] = new Decrementa(i+5, x, ticket, next, turno);

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
