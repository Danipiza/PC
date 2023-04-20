package ENTREGA;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Scanner;

import ENTREGA.Mensajes.*;

public class OyenteCliente extends Thread {

	// ID del Cliente
	private String IDCliente;
	
	// Entrada y salida de mensajes
	private DataInputStream in;
	private DataOutputStream out;
	
	// Paso de Mensajes por el Socket
	private Mensaje mensajeEnviado;
	private Mensaje mensajeRecibido;
	private byte[] bytesEnviado; // Arrays de Bytes para convertir las clase
	private byte[] bytesRecibido; // y poder enviarlo gracias a Serializable
	private ObjectOutputStream objectStreamEnviado;
	private ObjectInputStream objectStreamRecibido;
	private ByteArrayOutputStream byteStream;
	
	private boolean conectado;
	private boolean finConexion;
	
	
	// TODO monitor
	
	public OyenteCliente(DataInputStream in, DataOutputStream out, String IDCliente) {
		this.in = in;
		this.out = out;
		this.IDCliente = IDCliente;
		// ------------
		mensajeEnviado = null;
		mensajeRecibido = null;
		bytesEnviado = null;
		bytesRecibido = null;
		objectStreamEnviado = null;
		objectStreamRecibido = null;
		byteStream = null;	
		conectado = false;
		finConexion = false;
	}
	
	// TODO CONCURRENCIA
	public void menu() {
		System.out.println("MENU:");
		// 1 CONCURRENCIA
		System.out.println(" 0. Cerrar conexion");
		// 2 CONCURRENCIA 
		System.out.println(" 1. Lista de usuarios");
		System.out.println(" 2. Peliculas en el servidor");
		
		System.out.println(" 3. Pedir pelicula");	
		// 2 CONCURRENCIA
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
		boolean fin = false;
		
		@SuppressWarnings("resource")
		Scanner sn = new Scanner(System.in);
		int opcion = -1;		
		
		// Termina cuando el Cliente pide el cierre de conexion (opcion = 0)
		while(!fin) {			
			menu();			
			
			// sem
			// Espera en el caso de que otro cliente quiera recibir fichero
			// sem					
			
			/*
			
			if(monitor.getPideFich(IDCliente)){
				recibeMensaje();
				
				mensajeEnviado = new MPrepCS("Cliente","Servidor");
				enviarMensaje();	
				
				// Esperar a que se conecte el cliente
			}	 
			 
			*/
			
			/*while(monitor.getPideFich(IDCliente)) {
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}*/
			
			opcion = sn.nextInt();
			//monitor.pideFichTrue(IDCliente);
			
			try {
				// Manda la opccion elegida al OyenteServidor
				out.writeInt(opcion);
			
				switch (opcion) {
				// CERRAR CONEXION	
				case 0: { 						
						// Mensaje a enviar
						mensajeEnviado = new MCerrarConexion("Cliente", "Servidor");				
						// Enviar el mensaje al Servidor (mediante OyenteServidor)
						enviarMensaje();
						// Termina el bucle para eliminar el hilo OyenteCliente
						fin = true;						
						// Recibe el mensaje de confirmacion del Servidor
						recibeMensaje();						
						
						break;
					}
					// LISTA DE USUARIOS 
					case 1: { 
						// Mensaje a enviar
						mensajeEnviado = new MListaUsuarios("Cliente", "Servidor");
						// Enviar el mensaje al Servidor (mediante OyenteServidor)							
						enviarMensaje();									
						// Recibe el mensaje de confirmacion del Servidor
						recibeMensaje();
						// Imprime la lista de usuarios
						mensajeRecibido.imprimeUsuarios();
						
						break;
					}
					// LISTA DE PELICULAS
					case 2: { 
						// Mensaje a enviar
						mensajeEnviado = new MListaPeliculas("Cliente", "Servidor");
						// Enviar el mensaje al Servidor (mediante OyenteServidor)
						enviarMensaje();	
						// Recibe el mensaje de confirmacion del Servidor
						recibeMensaje();
						// Imprime la lista de peliculas
						mensajeRecibido.imprimePeliculas();
						break;
					}
					// PEDIR FICHERO
					case 3: {
						// Esperar si el cliente 
						
						// TODO
						System.out.print("Nombre de la pelicula deseada: ");
						// Pelicula a añadir
						String p = sn.nextLine();
						p = sn.nextLine();											
						
						mensajeEnviado = new MPedirFichero("Cliente", "Servidor", p);
						enviarMensaje(); // envia mensaje de peticion	
						recibeMensaje(); // recibe menaje de confirmacion
						
						
						if (mensajeRecibido.getPelicula() != null) {
							Socket scAux = new Socket("127.0.0.1", 5001);
							conectado = true;
							// Puente desde el cliente al servidor
							DataInputStream inAux = new DataInputStream(scAux.getInputStream());
							// Puente desde el servidor al cliente
							DataOutputStream outAux = new DataOutputStream(scAux.getOutputStream());
							
													
							// Establecer conexion con un usuario que tenga la pelicula
							OyenteCliente OCAux = new OyenteCliente(in, out, IDCliente);
							OCAux.start();
						}
						break;
					}
					// AÑADIR PELICULA
					case 4: { 
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
						// Establecer conexion con un usuario que tenga la pelicula
						break;
					}
					// ELIMINAR PELICULA
					case 5: { // Pedir pelicula
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
						// Establecer conexion con un usuario que tenga la pelicula
						break;
					}
					case 6: {
						// Termina la conexion con el cliente que tenia la pelicula
						finConexion = true;
						
						conectado = false;
					}
											
					default:	
						System.out.println("Introduce un numero del 0-5 (incluidos)");
						
				}
			} catch (Exception e) {
				System.out.println("Excepcion en el OyenteCliente");
			}
			
		}
		
	}
	
}
