package EJ2.TicketOrden;

import java.util.concurrent.atomic.AtomicInteger;

import EJ2.TicketOrden.Clases.*;

public class TicketAlgoritmo {	
	
	// INC
	public void TakeLockP1(int i, AtomicInteger ticket, Entero next, Entero[] turno, 
			Booleano inc) {		
		turno[i].n = ticket.getAndAdd(1);
		//turno[i].n = ticket.getAndIncrement();

		while(turno[i].n != next.n);
		while(!inc.n);
	}
	
	public void TakeLockP2(int i, AtomicInteger ticket, Entero next, Entero[] turno, 
			Booleano inc) {		
		turno[i].n = ticket.getAndAdd(1);

		while(turno[i].n != next.n);
		while(inc.n);
	}
	
	public void ReleaseLockP1(Entero next, Booleano inc) {
		next.n++;
		inc.n = false;
	}
	
	public void ReleaseLockP2(Entero next, Booleano inc) {
		next.n++;
		inc.n = true;
	}
	
}
