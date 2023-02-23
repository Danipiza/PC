package EJ2.Ticket.Hilos;

import java.util.concurrent.atomic.AtomicInteger;

import EJ2.Ticket.TicketAlgoritmo;
import EJ2.Ticket.Clases.Entero;

public class Incrementa extends Thread{		
	private int i;
	private Entero x;
	
	public volatile AtomicInteger ticket;
	public volatile Entero next;
	public volatile Entero[] turno;
	
	private TicketAlgoritmo TicketClase;
	
	public Incrementa(int i, Entero x,
			AtomicInteger ticket, Entero next, Entero[] turno) {
		this.i = i;
		this.x = x;
		
		this.ticket = ticket;
		this.next = next;
		this.turno = turno;
		
		TicketClase = new TicketAlgoritmo();
	}
	
	@Override 	
	public void run() {
		// SIN ESTO CASI SIEMPRE VA POR ORDEN
		
		try {
			Thread.sleep(100);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		// CSEntry/TakeLock
		TicketClase.TakeLock(i, ticket, next, turno);			
		
		// CS. 		
		x.n++;
		System.out.println(i+". Se ha incrementado: " + x.n);		
			
		
		// CSExit/ReleaseLock
		TicketClase.ReleaseLock(next);			
		
		
				
    }
		
		
    
}