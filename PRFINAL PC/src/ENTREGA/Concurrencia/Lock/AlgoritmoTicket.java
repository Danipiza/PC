package ENTREGA.Concurrencia.Lock;

import java.util.concurrent.atomic.AtomicInteger;
import ENTREGA.Concurrencia.Clases.Entero;

public class AlgoritmoTicket {
		
		/* VARIABLES A USAR PARA EL LOCK
		public volatile AtomicInteger ticket;
		public volatile Entero next;
		public volatile Entero[] turno;
	
		private TicketAlgoritmo TicketClase;*/
	
		public void TakeLock(int i, AtomicInteger ticket, Entero next, Entero[] turno) {		
			turno[i].n = ticket.getAndAdd(1);

			while(turno[i].n != next.n);
		}
		
		public void ReleaseLock(Entero next) {
			next.n++;
		}
}
