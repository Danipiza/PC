package ENTREGA;

import java.util.Scanner;
import java.util.concurrent.atomic.AtomicInteger;

import ENTREGA.Concurrencia.Clases.Entero;

// Main donde estan las variables de la concurrencia
public class CompartirPelis {
	
	// Lock
	public volatile AtomicInteger ticket = new AtomicInteger(0);
	public volatile Entero next = new Entero(0);
	public volatile Entero[] turno;
	
	// TODO
	// Semaforo
	
	// Monitor
	
	
	public CompartirPelis() {
		mainPRFinal();
	}
	
	// Inicializa el vector de turnos del Lock
	private void init(int N) {
		for(int i = 0; i < N; i++) {			
			turno[i] = new Entero(-1);
		}
	}
	
	private void mainPRFinal() {
		@SuppressWarnings("resource")
		Scanner sn = new Scanner(System.in);
		int N = 1; // Numero de Clientes que va haber conectados
				
		try {			
			
			System.out.print("Cuantos clientes quieres conectar: ");
			N = sn.nextInt();
			
			// Crea el servidor
			Servidor s = new Servidor();
			s.start();
			turno = new Entero[N];
			init(N);
			
			// Crea los clientes
			Cliente[] clientes = new Cliente[N];
			for(int i = 0; i < N; i++) {
				clientes[i] = new Cliente(i, ticket, next, turno);
				clientes[i].start();	
			}		
			
						
			// Finaliza los hilos creados (Servidor y Clientes)
			s.join();
			for(int i = 0; i < N; i++) {
				clientes[i].join();		
			}
			
		} catch (InterruptedException e) {			
			e.printStackTrace();
		}
		
	}
	
}
