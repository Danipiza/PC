package ENTREGA;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Scanner;

import ENTREGA.Concurrencia.Monitor.MonitorLock;
import ENTREGA.Concurrencia.Monitor.MonitorPuertoLock;
import ENTREGA.Mensajes.*;

public class OyenteServidor extends Thread{

	private DataInputStream in;
	private DataOutputStream out;
	private String IDCliente;
	
	private volatile TablaUsuarios tUSERs;
	private volatile TablaSockets tSOCKETs;
	
	private Mensaje mensajeEnviado;
	private Mensaje mensajeRecibido;
	private byte[] bytesEnviado;
	private byte[] bytesRecibido;
	private ObjectOutputStream objectStreamEnviado;
	private ObjectInputStream objectStreamRecibido;
	private ByteArrayOutputStream byteStream;
	
	
	// Monitor
	private MonitorLock mPidePeli;
	private MonitorPuertoLock mPuerto;
	// Semaforo
	private HashMap<String, Boolean> mapaClienteActivo;
	
	public OyenteServidor(DataInputStream in, DataOutputStream out, String IDCliente,
			MonitorLock mPidePeli, MonitorPuertoLock mPuerto,
			HashMap<String, Boolean> mapaClienteActivo,
			TablaUsuarios tUSERs, TablaSockets tSOCKETs){
		this.in = in;
		this.out = out;
		this.IDCliente = IDCliente;
		this.tUSERs = tUSERs;
		this.tSOCKETs = tSOCKETs;
		// ------------
		this.mensajeEnviado = null;
		this.mensajeRecibido = null;
		this.bytesEnviado = null;
		this.bytesRecibido = null;
		this.objectStreamEnviado = null;
		this.objectStreamRecibido = null;
		this.byteStream = null;	
		// Monitor --------------------------------
		this.mPidePeli = mPidePeli;
		this.mPuerto = mPuerto;
		// Semaforo -------------------------------
		this.mapaClienteActivo = mapaClienteActivo;
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
							// Todos los usuarios con la pelicula "p"
							String[] usuarios = tUSERs.getPelis().get(p).split(" ");
							// Usuario al que se va a conectar el cliente que pidio la pelicula
							String usuario = usuarios[0];
							// Usuarios conectados al 1er usuario
							int aux = tUSERs.getUsuario(usuarios[0]).getConectados();
							// Recorre los usuarios para escoger el usuario con menos clientes conectados
							for (int i = 1; i < usuarios.length; i++) {
								if(aux > tUSERs.getUsuario(usuarios[i]).getConectados()) {
									usuario = usuarios[i];
								}								
							}
							
							 	
							// Si el cliente ha mandado un mensaje al servidor
							// Se espera a que este termine para pedirle la pelicula
							while(mapaClienteActivo.get(usuario)) { }										
							
							
							mPidePeli.pideFichTrue(usuario);
							InfoSocket scCliente = tSOCKETs.getSocket(IDCliente);
							InfoSocket socketAux = tSOCKETs.getSocket(usuario);
							// TODO POSIBLE ERROR POR SER UN ARRAY LA CLASE QUE ENVIA
											
							mensajeEnviado = new MEmitirFichero("Servidor", "Cliente");
							socketAux.enviaMensaje(); // Envia el mensaje al cliente con la peli
							socketAux.recibeMensaje(); // Recibe el mensaje del cliente con la peli
							mensajeEnviado = new MPreparadoSC("Servidor", "Cliente");	
							
							conexionPeerToPeer(scCliente, socketAux);
							
						} else { // Pelicula no esta en el Servidor
							mensajeEnviado = new MNoExitePeliculaServidor("Servidor", "Cliente", null);
							
						}
						enviarMensaje();
						
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
					case 6: {						
						recibeMensaje();
						// TODO hacer que pueda elegir que conexion finalizar
						mPuerto.conexionFalse(IDCliente, 0);
						
						// Termina la conexion con el cliente que tenia la pelicula
						// Mensaje a enviar
						mensajeEnviado = new MConfirmacionFinalizarConexion("Servidor", "Cliente");
						// Enviar el mensaje al Servidor (mediante OyenteServidor)
						enviarMensaje();
						// Recibe el mensaje de confirmacion del Servidor
						
						
					}
					default:
						// AQUI NO ENTRA NUNCA
				}
				
				
			} catch (Exception e) {
				Exception excepcion = new Exception("Excepcion en OyenteServidor");
				excepcion.setStackTrace(e.getStackTrace());
				excepcion.printStackTrace();
			}
		}
		
	}
	
	private void conexionPeerToPeer(InfoSocket cliente, InfoSocket destino) {
		ServerSocket scAux;
		try {
			
			scAux = new ServerSocket(mPuerto.getPuerto());
			Socket sc2 = null;
			DataInputStream inAux;
			DataOutputStream outAux;
		
			// Aumenta el puerto para la siguiente posible conexion
			String conexion = cliente.getIDCliente()+"_"+destino.getIDCliente();
			
			mPuerto.addConexion(cliente.getIDCliente(), destino.getIDCliente());
			
			new OyentePeerToPeer(cliente.getSocket(), scAux, mPuerto,conexion).start();
			sc2 = scAux.accept();
			 // Puente desde el cliente al servidor
			inAux = new DataInputStream(sc2.getInputStream());
            // Puente desde el servidor al cliente
            outAux = new DataOutputStream(sc2.getOutputStream());
			//System.out.println("Clientes conectados");

            // Bucle que termina cuando se pasa por el menu
            /*while(true) {
				
			}*/
			
		} catch (Exception e) {
			Exception excepcion = new Exception("Excepcion en conexionPeerToPeer");
			excepcion.setStackTrace(e.getStackTrace());
			excepcion.printStackTrace();
		}
	}
}
