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
						
			try {
				opcion = in.readInt();
				
				switch (opcion) {
					case 0: { // Cerrar conexion
						recibeMensaje();
						// Poner a null las conexiones con el cliente que abandona el servidor
						fin = true;
						tUSERs.eliminaUsuario(tUSERs.getUsuario(IDCliente));
						// Actualizar la tabla de usuarios
						
						
											
						mensajeEnviado = new MConfirmacionCerrarConexion("Servidor", "Cliente");
						enviarMensaje();
						
												
						
						break;
					}
					case 1: { // Lista de usuarios
						recibeMensaje();
						HashMap<String, Usuario> u = tUSERs.getUsuarios();
						mensajeEnviado = new MConfirmacionListaUsuarios("Servidor", "Cliente", u);
						enviarMensaje();
						break;
					}
					case 2: { // Lista de peliculas
						recibeMensaje();
						
						HashMap<String, String> p = tUSERs.getPelis();
						mensajeEnviado = new MConfirmacionListaPeliculas("Servidor", "Cliente", p);						
						enviarMensaje();
						
						// 2 Semaforos uno que esta bloqueando esta funcion
						// y se abre cuando le llega el mensaje al cliente.						
						//tUSERs.imprimePeliculas();
						
						break;
					}
					case 3: { // Pedir pelicula
						recibeMensaje();
						String p = mensajeRecibido.getPelicula();
						// Pelicula en el Servidor
						if(tUSERs.getPelis().containsKey(p)) {
							tUSERs.getPelis();
						} else { // Pelicula no esta en el Servidor
							mensajeEnviado = new MNoExitePeliculaServidor("Servidor", "Cliente", null);
						}
						
						
						// Establece conexion con un cliente que tenga la pelicula
						break;
					}
					case 4: { // AÑADIR PELICULA
						recibeMensaje();					
						
						mensajeEnviado = new MConfirmacionAddPelicula("Servidor", "Cliente");
						if(!tUSERs.getUsuario(IDCliente).getPeliculas().
								containsKey(mensajeRecibido.getPelicula())) {
							tUSERs.addPelicula(IDCliente, mensajeRecibido.getPelicula());
						}						
						enviarMensaje();		
						
						break;
					}
					case 5: { // ELIMINAR PELICULA
						recibeMensaje();					
						
												
						
						boolean aux = tUSERs.eliminaPelicula(IDCliente, mensajeRecibido.getPelicula());
						if (aux) {
							mensajeEnviado = new MConfirmacionEliminarPelicula("Servidor", "Cliente");
						}
						else {
							mensajeEnviado = new MFalloEliminarPelicula("Servidor", "Cliente");
						}
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
