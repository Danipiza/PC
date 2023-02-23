package EJ2.Ticket;

import java.util.concurrent.atomic.AtomicInteger;

import EJ2.Ticket.Clases.Entero;

public class TicketAlgoritmo {	
	
	// INC
	public void TakeLock(int i, AtomicInteger ticket, Entero next, Entero[] turno) {		
		turno[i].n = ticket.getAndAdd(1);
		//turno[i].n = ticket.getAndIncrement();

		while(turno[i].n != next.n);
	}
	
	public void ReleaseLock(Entero next) {
		next.n++;
	}
	
	
}
