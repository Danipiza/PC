package ENTREGA;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.HashMap;
import java.util.Scanner;
import java.util.concurrent.Semaphore;

import ENTREGA.Concurrencia.Clases.Entero;
import ENTREGA.Concurrencia.Monitor.MonitorLock;
import ENTREGA.Mensajes.*;

// Se encarga de las peticiones de cada cliente
public class OyenteCliente extends Thread {

	// ID del Cliente
	private String IDCliente;
	
	// Entrada y salida de mensajes
	private DataInputStream in;
	private DataOutputStream out;
	
	// Paso de Mensajes por el Socket (y las variables para serializar)
	private Mensaje mensajeEnviado;
	private Mensaje mensajeRecibido;
	private byte[] bytesEnviado; // Arrays de Bytes para convertir las clase
	private byte[] bytesRecibido; // y poder enviarlo gracias a Serializable
	private ObjectOutputStream objectStreamEnviado;
	private ObjectInputStream objectStreamRecibido;
	private ByteArrayOutputStream byteStream;
	
	private boolean conectado;
	
	private boolean fin;
	
	// Semaforo
	private Semaphore sem, semServidor;
	private Entero entero, numHilos;
	private HashMap<String, Boolean> mapaClienteActivo;
	
	// Monitor
	private MonitorLock mPidePeli;
	
	public OyenteCliente(DataInputStream in, DataOutputStream out, String IDCliente, 
			MonitorLock mPidePeli,
			Semaphore sem, Entero entero, Entero numHilos,
			Semaphore semServidor, HashMap<String, Boolean> mapaClienteActivo) {
		this.in = in;
		this.out = out;
		this.IDCliente = IDCliente;
		this.fin = false;
		// ------------
		this.mensajeEnviado = null;
		this.mensajeRecibido = null;
		this.bytesEnviado = null;
		this.bytesRecibido = null;
		this.objectStreamEnviado = null;
		this.objectStreamRecibido = null;
		this.byteStream = null;	
		this.conectado = false;
		//finConexion = false;
		// Monitor -----------------------------------
		this.mPidePeli = mPidePeli;
		// Semaforo ----------------------------------
		this.sem = sem;
		this.entero = entero;
		this.numHilos = numHilos;
		this.semServidor = semServidor;
		this.mapaClienteActivo = mapaClienteActivo;
	}
	
	public void menu() {
		System.out.println("MENU:");
		System.out.println(" 0. Cerrar conexion");
		System.out.println(" 1. Lista de usuarios");
		System.out.println(" 2. Peliculas en el servidor");
		System.out.println(" 3. Pedir pelicula");	
		System.out.println(" 4. Añadir pelicula al usuario con ID: " + IDCliente);	
		System.out.println(" 5. Eliminar pelicula del usuario con ID: " + IDCliente);	
		if(conectado) System.out.println(" 6. Finalizar conexion");
	}
	
	public void enviarMensaje() throws IOException {
		// Convierte la instancia de la clase del mensaje a enviar, en un array de bytes
		byteStream = new ByteArrayOutputStream();
		objectStreamEnviado = new ObjectOutputStream(byteStream);
		objectStreamEnviado.writeObject(mensajeEnviado);						
		bytesEnviado = byteStream.toByteArray();	
		
		// Envia la longitud del array y el array (clase mensaje)
		out.writeInt(bytesEnviado.length);
		out.write(bytesEnviado);
		
		System.out.println("El Cliente ha enviado -> " + mensajeEnviado.getTipo());
	}
	
	public void recibeMensaje() throws IOException, ClassNotFoundException {
		// Recibe la longitud del array
		int length = in.readInt();
		bytesRecibido = new byte[length];
		// Recibe el array de bytes
		in.readFully(bytesRecibido, 0, length);
		
		// Convierte el array de bytes en la clase mensaje
		objectStreamRecibido = new ObjectInputStream(new ByteArrayInputStream(bytesRecibido));
		mensajeRecibido = (Mensaje) objectStreamRecibido.readObject();
		
		System.out.println("El Cliente ha recibido -> " + mensajeRecibido.getTipo());
	}
	
	@Override
	public void run() {	
		
		
		@SuppressWarnings("resource")
		Scanner sn = new Scanner(System.in);
		int opcion = -1;		
		
		// Termina cuando el Cliente pide el cierre de conexion (opcion = 0)
		while(!fin) {				
			
			// Si un cliente ha pedido una pelicula suya
			if(mPidePeli.getPideFich(IDCliente)){
				try {
					recibeMensaje();						
					mensajeEnviado = new MPreparadoCS("Cliente","Servidor");
					enviarMensaje();
					// Ya no le piden mas ficheros
					mPidePeli.pideFichFalse(IDCliente);
				} catch (Exception e) {
					Exception excepcion = new Exception("Excepcion en OyenteCliente, al pedir pelicula");
					excepcion.setStackTrace(e.getStackTrace());
					excepcion.printStackTrace();
				}
			}	 
			
			try {
				// El Cliente esta en pidiendo algo al servidor,
				// Si otro cliente quiere pedir pelicula a este cliente
				// Se espera a que termine
				semServidor.acquire();
				mapaClienteActivo.put(IDCliente, true);			
				semServidor.release();
				
				menu();	
				opcion = sn.nextInt();		
				 			
				// Manda la opccion elegida al OyenteServidor
				out.writeInt(opcion);
			
				boolean aux = false;
				switch (opcion) {
				// CERRAR CONEXION	
				case 0: { 		
						 
						// Modifican la tabla de usuarios, entero.valor = 1
						// Solo pueden contactar con el servidor aquellos clientes
						// que quieran modificar la tabla de usuario
						sem.acquire();
						if(entero.getValor() != 0 && entero.getValor() != 1){
							sem.release();
							while(entero.getValor() != 0 && entero.getValor() != 1){ }
							sem.acquire();					
						} 						
						entero.setValor(1);	
						numHilos.addValor(1);
						sem.release();											
						
						
						// Mensaje a enviar
						mensajeEnviado = new MCerrarConexion("Cliente", "Servidor");				
						// Enviar el mensaje al Servidor (mediante OyenteServidor)
						enviarMensaje();
						// Termina el bucle para eliminar el hilo OyenteCliente
						fin = true;						
						// Recibe el mensaje de confirmacion del Servidor
						recibeMensaje();

						sem.acquire();
						numHilos.restaValor(1);
						sem.release();						
						
						break;
					}
					// LISTA DE USUARIOS 
					case 1: { 
						
						// Acceden a la tabla de usuarios, entero.valor = 2
						sem.acquire();
						if(entero.getValor() != 0 && entero.getValor() != 2){
							sem.release();
							while(entero.getValor() != 0 && entero.getValor() != 2){ }
							sem.acquire();					
						} 						
						entero.setValor(2);
						numHilos.addValor(1);
						sem.release();
						
						// Mensaje a enviar
						mensajeEnviado = new MListaUsuarios("Cliente", "Servidor");
						// Enviar el mensaje al Servidor (mediante OyenteServidor)							
						enviarMensaje();									
						// Recibe el mensaje de confirmacion del Servidor
						recibeMensaje();
						// Imprime la lista de usuarios
						mensajeRecibido.imprimeUsuarios();	
						
						sem.acquire();
						numHilos.restaValor(1);
						sem.release();
						
						break;
					}
					// LISTA DE PELICULAS
					case 2: { 
												 
						// Acceden a la tabla de usuarios, entero.valor = 2
						sem.acquire();
						if(entero.getValor() != 0 && entero.getValor() != 2){
							sem.release();
							while(entero.getValor() != 0 && entero.getValor() != 2){ }
							sem.acquire();					
						} 						
						entero.setValor(2);	
						numHilos.addValor(1);
						sem.release();											
												
						// Mensaje a enviar
						mensajeEnviado = new MListaPeliculas("Cliente", "Servidor");
						// Enviar el mensaje al Servidor (mediante OyenteServidor)
						enviarMensaje();	
						// Recibe el mensaje de confirmacion del Servidor
						recibeMensaje();
						// Imprime la lista de peliculas
						mensajeRecibido.imprimePeliculas();
						
						sem.acquire();
						numHilos.restaValor(1);
						sem.release();
						
						break;
					}
					// PEDIR FICHERO
					case 3: {	
						
						aux = true;															
						
						System.out.print("Nombre de la pelicula deseada: ");
						// Pelicula a añadir
						String p = sn.nextLine();
						p = sn.nextLine();											
						
						mensajeEnviado = new MPedirFichero("Cliente", "Servidor", p);
						enviarMensaje(); // envia mensaje de peticion	
						recibeMensaje(); // recibe menaje de confirmacion		
						conectado = true;
						break;
					}
					// AÑADIR PELICULA
					case 4: { 
						 
						// Modifican la tabla de usuarios, entero.valor = 1
						sem.acquire();
						if(entero.getValor() != 0 && entero.getValor() != 1){
							sem.release();
							while(entero.getValor() != 0 && entero.getValor() != 1){ }
							sem.acquire();					
						} 						
						entero.setValor(1);	
						numHilos.addValor(1);
						sem.release();											
						
						System.out.print("Nombre de la pelicula a añadir: ");
						// Pelicula a añadir
						String p = sn.nextLine();
						p = sn.nextLine();
						
						// Mensaje a enviar
						mensajeEnviado = new MAddPelicula("Cliente", "Servidor", p);
						// Enviar el mensaje al Servidor (mediante OyenteServidor)
						enviarMensaje();
						// Recibe el mensaje de confirmacion del Servidor
						recibeMensaje();
						
						sem.acquire();
						numHilos.restaValor(1);
						sem.release();
						
						break;
					}
					// ELIMINAR PELICULA
					case 5: { 
						 
						// Modifican la tabla de usuarios, entero.valor = 1
						sem.acquire();
						if(entero.getValor() != 0 && entero.getValor() != 1){
							sem.release();
							while(entero.getValor() != 0 && entero.getValor() != 1){ }
							sem.acquire();					
						} 						
						entero.setValor(1);
						numHilos.addValor(1);
						sem.release();											
						
						
						System.out.print("Nombre de la pelicula a eliminar: ");
						// Pelicula a eliminar
						String p = sn.nextLine();
						p = sn.nextLine();
						
						// Mensaje a enviar
						mensajeEnviado = new MEliminaPelicula("Cliente", "Servidor", p);
						// Enviar el mensaje al Servidor (mediante OyenteServidor)
						enviarMensaje();
						// Recibe el mensaje de confirmacion del Servidor
						recibeMensaje();
						
						sem.acquire();
						numHilos.restaValor(1);
						sem.release();
						break;
					}
					case 6: {
						aux = true;
						// Termina la conexion con el cliente que tenia la pelicula
						// Mensaje a enviar
						mensajeEnviado = new MFinalizarConexion("Cliente", "Servidor");
						// Enviar el mensaje al Servidor (mediante OyenteServidor)
						enviarMensaje();
						// Recibe el mensaje de confirmacion del Servidor
						recibeMensaje();							
						
						conectado = false;
						
					}											
					default:	
						System.out.println("Introduce un numero del 0-5 (incluidos)");
						
				}
				
				
				// Pone a 0 el valor del entero para controlar la entrada de 
				// opcciones de los diferentes clientes conectados al servidor
				if(!aux) {
					sem.acquire();		
					 if(numHilos.getValor() == 0) {
						entero.setValor(0);				
					}
					sem.release();
				}																		
				
				// El cliente termina de pedir al servidor
				// Ya puede recibir peticiones de otros clientes
				// que quieran sus peliculas
				semServidor.acquire();
				mapaClienteActivo.put(IDCliente, false);			
				semServidor.release();			
								
				
				
			} catch (Exception e) {
				Exception excepcion = new Exception("Excepcion en OyenteCliente");
				excepcion.setStackTrace(e.getStackTrace());
				excepcion.printStackTrace();
			}
			
		}
		
	}
	
	public boolean getFin() {
		return this.fin;
	}
	
}
