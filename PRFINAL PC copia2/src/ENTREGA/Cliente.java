package ENTREGA;

import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.HashMap;
import java.util.Scanner;
import java.util.concurrent.atomic.AtomicInteger;

import ENTREGA.Concurrencia.Clases.Entero;
import ENTREGA.Concurrencia.Lock.AlgoritmoTicket;

import java.util.Random;



public class Cliente extends Thread {

	// Numero en orden de llegada de cada cliente
	private int numCliente;
	// Clase usuario con las peliculas
	private Usuario usuario;
	
	// Host local para conectar el socket del Servidor
	private final String HOST = "127.0.0.1"; 
	private final int PUERTO = 5000; // Puerto guardado
	
	// Entrada y salida de mensajes
	private DataInputStream in;
	private DataOutputStream out;
	// Socket que conecta con el servidor
	private Socket sc;
	
	// Variables para el Lock.
	// Se usa para que los clientes se conecten correctamente
	private volatile AtomicInteger ticket;
	private Entero next;
	private Entero[] turno;
	private AlgoritmoTicket TicketClase;
	
	
	public Cliente(int numCliente, AtomicInteger ticket, Entero next, Entero[] turno) {
		in = null;
		out = null;
		sc = null;
		
		this.numCliente = numCliente;
		this.ticket = ticket;
		this.next = next;
		this.turno = turno;
		TicketClase = new AlgoritmoTicket();
	}
	
	// Funcion para crear usuario
	private Usuario crearUsuario(String IP) {
		@SuppressWarnings("resource") // Para suprimir el warning
		Scanner sn = new Scanner(System.in);
		String aux = null;
		
		System.out.print("ID de usuario (unico): ");			
		String ID = sn.nextLine();	
		
		System.out.print("Numero de peliculas a compartir: ");
		int numP = sn.nextInt();
		
		HashMap<String, String> peliculas = new HashMap<String, String>();
		aux = sn.nextLine(); // Salto de linea del entero anterior
		System.out.print("Nombre de las peliculas: ");
		for(int i = 0; i < numP; i++) {
			aux = sn.nextLine();
			peliculas.put(aux,ID);
		}
		return new Usuario(ID, IP, peliculas);
	}
	
	// Generador de IPs de los usuarios
	private String generadorIP() {
		String ret = "";
		Random rand = new Random();
		int num = 0;
		for(int i = 0; i < 4; i++) {
			num = rand.nextInt(255) + 1;
			if(i == 3) ret +=num;
			else ret +=num+".";
		}
		return ret;
	}
	
	
	@Override
	public void run() {			
		
		try {			
			String IP = generadorIP();
			
			
			// CSEntry/TakeLock
			TicketClase.TakeLock(numCliente, ticket, next, turno);	
			// CS. 			
			usuario = crearUsuario(IP);	
			// CSExit/ReleaseLock
			//TicketClase.ReleaseLock(next);	
			
			
			sc = new Socket (HOST, PUERTO);
			System.out.println("Cliente conectandose...");
			
			// Puente desde el cliente al servidor
			in = new DataInputStream(sc.getInputStream());
			// Puente desde el servidor al cliente
			out = new DataOutputStream(sc.getOutputStream());
			
			// Manda usuario
			// La clase tiene que implementar la interfaz Serializable,
			// para poder convertir la clase en un array de bytes								
			ByteArrayOutputStream byteStream = new ByteArrayOutputStream();
			ObjectOutputStream objectStreamEnviado = new ObjectOutputStream(byteStream);
			objectStreamEnviado.writeObject(usuario);						
			byte[] bytesEnviado = byteStream.toByteArray();	
						
			
			// Envia la longitud del array y el array (clase mensaje)
			out.writeInt(bytesEnviado.length);
			out.write(bytesEnviado);			
			
			// TODO SEMAFORO PA QUE NO HAYA PROBLEMAS?
			// Manda Socket
			// La clase tiene que implementar la interfaz Serializable,
			// para poder convertir la clase en un array de bytes								
			/*byteStream = new ByteArrayOutputStream();
			objectStreamEnviado = new ObjectOutputStream(byteStream);
			objectStreamEnviado.writeObject(sc);						
			bytesEnviado = byteStream.toByteArray();							
			
			// Envia la longitud del array y el array (clase mensaje)
			out.writeInt(bytesEnviado.length);
			out.write(bytesEnviado);	*/
			
			// Crea el hilo OyenteCliente
            OyenteCliente OC = new OyenteCliente(in, out, usuario.getID());
            OC.start();
            // CSExit/ReleaseLock
            TicketClase.ReleaseLock(next);	
            // TODO
            //OC.join();
			
		} catch (Exception e) {
			System.out.println("Excepcion en el cliente");
		}
	}
}
