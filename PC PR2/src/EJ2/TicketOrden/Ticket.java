package EJ2.TicketOrden;

import java.util.concurrent.atomic.AtomicInteger;

import EJ2.TicketOrden.Clases.*;
import EJ2.TicketOrden.Hilos.Decrementa;
import EJ2.TicketOrden.Hilos.Incrementa;



public class Ticket {		
	
	public Ticket(int N) {		
		
		try {
			mainEJ2(N);
		} catch (InterruptedException e) {			
			e.printStackTrace();
		}
	}	
	
	
	
	public volatile AtomicInteger ticketInc = new AtomicInteger(0), ticketDec = new AtomicInteger(0);
	public volatile Entero nextInc = new Entero(0), nextDec = new Entero(0);
	public volatile Entero[] turnoInc, turnoDec;
	
	public volatile Booleano inc = new Booleano(true);	
	
	private void init(int N) {
		for(int i = 0; i < N; i++) {
			turnoInc[i] = new Entero(-1);
			turnoDec[i] = new Entero(-1);
		}
	}
	
	public void mainEJ2(int N) throws InterruptedException {
		Entero x = new Entero(0);		
		
		//ticket.getAndAdd(1);
		
		turnoInc = new Entero[N];
		turnoDec = new Entero[N];
		init(N);
		
		Incrementa[] incrementa = new Incrementa[N];
		Decrementa[] decrementa = new Decrementa[N];
		

        for (int i = 0; i < N; i++) {
        	incrementa[i] = new Incrementa(i, x, ticketInc, nextInc, turnoInc, inc);
        	decrementa[i] = new Decrementa(i, x, ticketDec, nextDec, turnoDec, inc);

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
