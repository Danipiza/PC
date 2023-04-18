package PRUEBA;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class Servidor extends Thread{
	
	private ServerSocket servidor;
	private Socket sc;       
	private DataInputStream in;
	private DataOutputStream out;
	
	// PUERTO DEL SERVIDOR
    final int PUERTO = 5000;
	
	public Servidor() {
		servidor = null;
		sc = null;        
		in = null;
		out = null;
	}
	
	@Override 	
	public void run() {                
        
        try {
        	// Se crea el servidor
            servidor = new ServerSocket(PUERTO);
            System. out.println ("Servidor iniciado");
            
            while (true) {
            	// Se queda esperando a un cliente
                sc = servidor.accept();
                System.out.println("Cliente conectado");
                
                // Puente desde el cliente al servidor
                in = new DataInputStream(sc.getInputStream());
                // Puente desde el servidor al cliente
                out = new DataOutputStream(sc.getOutputStream());
                
                // Mensaje recibido del cliente
                String mensaje = in.readUTF();
                System.out.println(mensaje);
                
                // Mensaje escrito por el servidor hacia el cliente
                out.writeUTF("Hola cliente, soy el servidor");

                // Cierra conexion del cliente con el servidor
                sc.close();
                System.out.println("Cliente desconectado"); 
            }
        } catch (Exception e) {
			System.out.println("Excepcion en el servidor");
		}
	}
}
