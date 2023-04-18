package ENTREGA;

import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;


// PUEDE SER UN MAIN PARA ASI ABRIR VARIAS CONSOLAS

public class Cliente extends Thread {

	private Usuario usuario;
	
	private final String HOST = "127.0.0.1"; // Host local
	private final int PUERTO = 5000;
	
	private DataInputStream in;
	private DataOutputStream out;
	
	private Socket sc;
	
	public Cliente(Usuario usuario) {
		in = null;
		out = null;
		sc = null;
		this.usuario = usuario;
	}
	
	// TODO
	private void crearUsuario() {
		
	}
	
	// TODO
	private void generadorIP() {
		
	}
	
	
	@Override
	public void run() {			
		
		try {
			// MONITOR, QUE ES EL MAS "DIFICIL"
			// TODO CONCURRENCIA PARA QUE NO SE CONECTEN A LA VEZ
			// Conectar el cliente al servidor
			Usuario usuario1 = new Usuario("1", "104.9.226.93", null);
			System.out.print("Nombre de usuario (unico)");
			//scanner
			System.out.println();
			System.out.print("Numero de peliculas a compartir: ");
			//scanner
			System.out.println();
			System.out.println("Nombre de las peliculas: ");
			
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
			
			// Crea el hilo OyenteCliente
            OyenteCliente OC = new OyenteCliente(in, out, usuario.getID());
            OC.start();
            OC.join();
			
		} catch (Exception e) {
			System.out.println("Excepcion en el cliente");
		}
		

		
	}
}
