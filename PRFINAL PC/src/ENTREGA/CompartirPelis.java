package ENTREGA;

import java.util.HashMap;
import java.util.Scanner;
import java.util.concurrent.Semaphore;
import java.util.concurrent.atomic.AtomicInteger;

import ENTREGA.Concurrencia.Clases.Entero;
import ENTREGA.Concurrencia.Monitor.MonitorConexion;
import ENTREGA.Concurrencia.Monitor.MonitorLock;

// Main donde estan las variables de la concurrencia
public class CompartirPelis {
	
	// Lock
	public volatile AtomicInteger ticket;
	public volatile Entero next;
	public volatile Entero[] turno;
	
	// Semaforo
	private Semaphore sem, semServidor;
	private Entero entero, numHilos;
	private HashMap<String, Boolean> mapaClienteActivo;
	// Monitor
	private MonitorLock mPidePeli;
	private MonitorConexion mConexion;
	
	public CompartirPelis() {
		// Lock -------------------------------
		this.ticket = new AtomicInteger(0);
		this.next = new Entero(0);
		// Monitores --------------------------
		this.mPidePeli = new MonitorLock();
		this.mConexion = new MonitorConexion(5001);
		// Semaforo ---------------------------
		this.sem = new Semaphore(1);
		this.entero = new Entero(0);
		this.numHilos = new Entero(0);
		this.semServidor = new Semaphore(1);
		this.mapaClienteActivo = new HashMap<String, Boolean>(); 
		// ------------------------------------
		
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
			Servidor s = new Servidor(mPidePeli, mConexion, mapaClienteActivo);
			s.start();
			turno = new Entero[N];
			init(N);
			
			// Crea los clientes
			Cliente[] clientes = new Cliente[N];
			for(int i = 0; i < N; i++) {
				clientes[i] = new Cliente(i, 
						ticket, next, turno, 
						mPidePeli, 
						sem, entero, numHilos, semServidor, mapaClienteActivo);
				
				clientes[i].start();	
			}		
			
						
			// Finaliza los hilos creados (Servidor y Clientes)
			// Da igual porque hay un bucle infinito
			s.join();
			for(int i = 0; i < N; i++) {
				clientes[i].join();		
			}
			
		} catch (InterruptedException e) {			
			e.printStackTrace();
		}
		
	}
	
}
