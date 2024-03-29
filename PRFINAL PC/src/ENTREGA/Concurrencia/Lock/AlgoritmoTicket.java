package ENTREGA.Concurrencia.Lock;

import java.util.concurrent.atomic.AtomicInteger;
import ENTREGA.Concurrencia.Clases.Entero;

public class AlgoritmoTicket {
			
		public void TakeLock(int i, AtomicInteger ticket, Entero next, Entero[] turno) {		
			//turno[i].n = ticket.getAndAdd(1);
			turno[i].setValor(ticket.getAndAdd(1));

			//while(turno[i].n != next.n);
			while(turno[i].getValor() != next.getValor());
		}
		
		public void ReleaseLock(Entero next) {
			//next.n++;
			next.addValor(1);
		}
}
