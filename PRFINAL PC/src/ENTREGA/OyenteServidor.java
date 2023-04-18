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

import ENTREGA.Mensajes.*;

public class OyenteServidor extends Thread{

	private DataInputStream in;
	private DataOutputStream out;
	private String IDCliente;
	//private int idCliente;
	
	private volatile TablaUsuarios tUSERs;
	
	private Mensaje mensajeEnviado;
	private Mensaje mensajeRecibido;
	private byte[] bytesEnviado;
	private byte[] bytesRecibido;
	private ObjectOutputStream objectStreamEnviado;
	private ObjectInputStream objectStreamRecibido;
	private ByteArrayOutputStream byteStream;
	
	public OyenteServidor(DataInputStream in, DataOutputStream out, String IDCliente,
			TablaUsuarios tUSERs){
		this.in = in;
		this.out = out;
		this.IDCliente = IDCliente;
		this.tUSERs = tUSERs;
		// ------------
		mensajeEnviado = null;
		mensajeRecibido = null;
		bytesEnviado = null;
		bytesRecibido = null;
		objectStreamEnviado = null;
		objectStreamRecibido = null;
		byteStream = null;		
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
		System.out.println("El Servidor ha enviado -> " + mensajeEnviado.getTipo());
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
		System.out.println("El Servidor ha recibido -> " + mensajeRecibido.getTipo());
	}
	
	@Override
	public void run() {
		int opcion = -1;
		
		boolean fin = false;
		
		while(!fin) {		
			
			/*Mensaje mensajeEnviado = null;
			Mensaje mensajeRecibido = null;
			byte[] bytesEnviado = null;
			byte[] bytesRecibido = null;
			ObjectOutputStream objectStreamEnviado = null;
			ObjectInputStream objectStreamRecibido = null;
			ByteArrayOutputStream byteStream = null;*/
			
			try {
				opcion = in.readInt();
				
				switch (opcion) {
					case 0: { // Cerrar conexion
						
						// El Servidor recibe un mensaje del Cliente -----------------------------------
						// -----------------------------------------------------------------------------
						
						recibeMensaje();
						// Poner a null las conexiones con el cliente que abandona el servidor
						fin = true;
						
						// Actualizar la tabla de usuarios
						
						
						/*// Recibe la longitud
						int length = in.readInt();
						bytesRecibido = new byte[length];
						// Recibe el array de bytes
						in.readFully(bytesRecibido, 0, length);
						
						// Convierte el array de bytes en la clase mensaje
						objectStreamRecibido = new ObjectInputStream(new ByteArrayInputStream(bytesRecibido));
						mensajeRecibido = (Mensaje) objectStreamRecibido.readObject();
						System.out.println("El Servidor ha recibido -> " + mensajeRecibido.getTipo());*/
						
						// -----------------------------------------------------------------------------
						// El Servidor manda un mensaje al Cliente -------------------------------------
						
						mensajeEnviado = new MConfirmacionCerrarConexion("Servidor", "Cliente");
						enviarMensaje();
						
						/*//MiClase obj = new MiClase();
						// Convierte la instancia de la clase del mensaje a enviar, en un array de bytes
						byteStream = new ByteArrayOutputStream();
						objectStreamEnviado = new ObjectOutputStream(byteStream);
						objectStreamEnviado.writeObject(mensajeEnviado);						
						bytesEnviado = byteStream.toByteArray();
						
						//DataOutputStream dataStream = new DataOutputStream(socket.getOutputStream());
						// Envia la longitud del array y el array (clase mensaje)
						out.writeInt(bytesEnviado.length);
						out.write(bytesEnviado);
						System.out.println("El Servidor ha enviado -> " + mensajeEnviado.getTipo());
						*/
						
						
						break;
					}
					case 1: { // Establecer conexion
						
						//mensajeEnviado = new MConfirmacionConexion("Servidor", "Cliente");
						break;
					}
					case 2: { // Lista de usuarios
						recibeMensaje();
						HashMap<String, Usuario> u = tUSERs.getUsuarios();
						mensajeEnviado = new MConfirmacionListaUsuarios("Servidor", "Cliente", u);
						enviarMensaje();
						break;
					}
					case 3: { // Lista de peliculas
						recibeMensaje();
						
						HashMap<String, Integer> p = tUSERs.getPelis();
						mensajeEnviado = new MConfirmacionListaPeliculas("Servidor", "Cliente", p);						
						enviarMensaje();
						
						// 2 Semaforos uno que esta bloqueando esta funcion
						// y se abre cuando le llega el mensaje al cliente.						
						//tUSERs.imprimePeliculas();
						
						break;
					}
					case 4: { // Pedir pelicula
						
						// Establece conexion con un cliente que tenga la pelicula
						break;
					}
					case 5: {
						recibeMensaje();					
						
						mensajeEnviado = new MConfirmacionAddPelicula("Servidor", "Cliente");						
						Scanner sn = new Scanner(System.in);
						String p = sn.nextLine();
						tUSERs.addPelicula(IDCliente, p);
						enviarMensaje();		
						
						break;
					}
					default:
						// AQUI NO ENTRA NUNCA
				}
				
				
			} catch (IOException e) {
				e.printStackTrace();
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}
		}
		
	}
}
