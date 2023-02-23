package EJ2.TicketOrden.Hilos;

import java.util.concurrent.atomic.AtomicInteger;

import EJ2.TicketOrden.TicketAlgoritmo;
import EJ2.TicketOrden.Clases.*;

public class Decrementa extends Thread{		
	private int i;
	private Entero x;
	
	public volatile AtomicInteger ticket;
	public volatile Entero next;
	public volatile Entero[] turno;
	public volatile Booleano inc;
	
	private TicketAlgoritmo TicketClase;
	
	public Decrementa(int i, Entero x,
			AtomicInteger ticket, Entero next, Entero[] turno, Booleano inc) {
		this.i = i;
		this.x = x;
		
		this.ticket = ticket;
		this.next = next;
		this.turno = turno;
		this.inc = inc;
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
		TicketClase.TakeLockP2(i, ticket, next, turno, inc);			
		
		// CS. 		
		
		x.n--;
		System.out.println(i+". Se ha decrementado: " + x.n);		
		
			
		
		// CSExit/ReleaseLock
		TicketClase.ReleaseLockP2(next, inc);			
		
		
				
    }
		
		
    
}