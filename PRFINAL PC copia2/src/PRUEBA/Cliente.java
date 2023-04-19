package PRUEBA;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.Socket;

public class Cliente extends Thread{

	private final String HOST = "127.0.0.1";
	private final int PUERTO = 5000;
	
	private DataInputStream in;
	private DataOutputStream out;
	
	private Socket sc;
	
	public Cliente() {
		in = null;
		out = null;
		sc = null;
	}
	
	
	
	@Override
	public void run() {			

		try {
			// Conectar el cliente al servidor
			sc = new Socket (HOST, PUERTO);
			System.out.println("Cliente conectado al servidor");
			
			// Puente desde el cliente al servidor
			in = new DataInputStream(sc.getInputStream());
			// Puente desde el servidor al cliente
			out = new DataOutputStream(sc.getOutputStream());
			
			// Mensaje enviado del cliente al servidor
			out.writeUTF("Hola servidor, soy el cliente");
			
			// Mensaje recibido del servidor
			String mensaje = in.readUTF();
			System.out.println(mensaje);
			
			// Cerrar conexion
			sc.close();
			
		} catch (Exception e) {
			System.out.println("Excepcion en el cliente");
		}
		

		
	}
}
