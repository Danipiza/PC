package ENTREGA;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Scanner;

import ENTREGA.Mensajes.*;

public class OyenteCliente extends Thread{

	private DataInputStream in;
	private DataOutputStream out;
	private String IDCliente;
	
	// TODO VARIABLE EN LA CLASE?
	private Mensaje mensajeEnviado;
	private Mensaje mensajeRecibido;
	private byte[] bytesEnviado;
	private byte[] bytesRecibido;
	private ObjectOutputStream objectStreamEnviado;
	private ObjectInputStream objectStreamRecibido;
	private ByteArrayOutputStream byteStream;
	
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
	}
	
	public void menu() {
		System.out.println("MENU:");
		System.out.println(" 0. Cerrar conexion");
		System.out.println(" 1. Establecer conexion");
		System.out.println(" 2. Lista de usuarios");
		System.out.println(" 3. Peliculas en el servidor");
		System.out.println(" 4. Pedir pelicula");	
		System.out.println(" 5. Añadir pelicula al usuario con ID: " + IDCliente);	
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
		boolean fin = false;
		
		while(!fin) {
			// TODO QUITAR CUANDO PONGA n CLIENTES
			menu();
					
			
			/*Mensaje mensajeEnviado = null;
			Mensaje mensajeRecibido = null;
			byte[] bytesEnviado = null;
			byte[] bytesRecibido = null;
			ObjectOutputStream objectStreamEnviado = null;
			ObjectInputStream objectStreamRecibido = null;
			ByteArrayOutputStream byteStream = null;*/
			
			opcion = sn.nextInt();
			try {
				out.writeInt(opcion);
			
				switch (opcion) {
				// CERRAR CONEXION	
				case 0: { 						
						// El Cliente manda un mensaje al Servidor -------------------------------------
						// -----------------------------------------------------------------------------
						mensajeEnviado = new MCerrarConexion("origen", "destino");				
						enviarMensaje();
						fin = true;
						/*
						// Convierte la instancia de la clase del mensaje a enviar, en un array de bytes
						byteStream = new ByteArrayOutputStream();
						objectStreamEnviado = new ObjectOutputStream(byteStream);
						objectStreamEnviado.writeObject(mensajeEnviado);						
						bytesEnviado = byteStream.toByteArray();	
						
						// Envia la longitud del array y el array (clase mensaje)
						out.writeInt(bytesEnviado.length);
						out.write(bytesEnviado);
						System.out.println("El Cliente ha enviado -> " + mensajeEnviado.getTipo());*/
						
						// -----------------------------------------------------------------------------
						// El Cliente recibe el mensaje del Servidor -----------------------------------
						
						
						recibeMensaje();
						
						
						// Recibe la longitud del array
						/*int length = in.readInt();
						bytesRecibido = new byte[length];
						// Recibe el array de bytes
						in.readFully(bytesRecibido, 0, length);
						
						// Convierte el array de bytes en la clase mensaje
						objectStreamRecibido = new ObjectInputStream(new ByteArrayInputStream(bytesRecibido));
						mensajeRecibido = (Mensaje) objectStreamRecibido.readObject();
						System.out.println("El Cliente ha recibido -> " + mensajeRecibido.getTipo());*/
						
						break;
					}
					case 1: { // Establecer conexion
						// TODO DUDA CON CONECTARSE	
						//mensajeEnviado = new MConexion("origen", "destino");
						break;
					}
					case 2: { // Lista de usuarios
						mensajeEnviado = new MListaUsuarios("origen", "destino");
						enviarMensaje();
						
						recibeMensaje();
						// recibeTablaUsuarios
						
						// Lee la lista de usuarios
						
						break;
					}
					case 3: { // Lista de peliculas
						mensajeEnviado = new MListaPeliculas("origen", "destino");
						
						enviarMensaje();
						
						recibeMensaje();
						break;
					}
					case 4: { // Pedir pelicula
						mensajeEnviado = new MPedirFichero("origen", "destino");
						
						// Establecer conexion con un usuario que tenga la pelicula
						break;
					}
					case 5: { // Pedir pelicula
						mensajeEnviado = new MAddPelicula("origen", "destino");
						enviarMensaje();
						
						recibeMensaje();
						// Establecer conexion con un usuario que tenga la pelicula
						break;
					}
					default:	
						System.out.println("Introduce un numero del 0-5 (incluidos)");
						
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
		
	}
	
}
