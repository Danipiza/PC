package ENTREGA;

import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.ObjectInputStream;
import java.net.ServerSocket;
import java.net.Socket;


// PUEDE SER UN MAIN Y ABRIR VARIAS CONSOLAS 

public class Servidor extends Thread{
	
	
	private ServerSocket servidor;
	private Socket sc;       
	private DataInputStream in;
	private DataOutputStream out;
	
	// TODO MONITOR
	private volatile TablaUsuarios tUSERs;
	private volatile TablaSockets tSOCKETs;
	
	// PUERTO DEL SERVIDOR
    final int PUERTO = 5000;
	
	public Servidor() {
		servidor = null;
		sc = null;        
		in = null;
		out = null;
		
		tUSERs = new TablaUsuarios();
		tSOCKETs = new TablaSockets();
	}
	
	@Override 	
	public void run() {                
        
        try {
        	// Se crea el servidor
        	System. out.println ("Servidor iniciado");
        	servidor = new ServerSocket(PUERTO);
            
        	OyenteServidor OS;
            while (true) {
            	// Se queda esperando a un cliente
                //for(int i = 0; i < N; i++) {
                	sc = servidor.accept();
                    System.out.println("Cliente conectado al servidor");
                // }
                
                
                // Puente desde el cliente al servidor
                in = new DataInputStream(sc.getInputStream());
                // Puente desde el servidor al cliente
                out = new DataOutputStream(sc.getOutputStream());
                
                // Recibe Usuario                
                int length = in.readInt();
        		byte[] bytesRecibido = new byte[length];
        		// Recibe el array de bytes
        		in.readFully(bytesRecibido, 0, length);
        		
        		// Convierte el array de bytes en la clase mensaje
        		ObjectInputStream objectStreamRecibido = new ObjectInputStream(new ByteArrayInputStream(bytesRecibido));
        		Usuario usuarioC = (Usuario) objectStreamRecibido.readObject();                                        		
        		// SEMAFORO
        		tUSERs.addUsuario(usuarioC);        		        		
                // SEMAFORO
        		/*tSOCKETs.addSocket(usuarioC.getID()+"_S", sc);
        		
        		// Recibe Usuario                
                length = in.readInt();
        		bytesRecibido = new byte[length];
        		// Recibe el array de bytes
        		in.readFully(bytesRecibido, 0, length);
        		
        		// Convierte el array de bytes en la clase mensaje
        		objectStreamRecibido = new ObjectInputStream(new ByteArrayInputStream(bytesRecibido));
        		Socket scC = (Socket) objectStreamRecibido.readObject();  
        		tSOCKETs.addSocket(usuarioC.getID()+"_C", scC);*/
        		
                // Crea el hilo OyenteServidor
                OS = new OyenteServidor(in, out, usuarioC.getID(), tUSERs);
                
                OS.start();                
            }
        } catch (Exception e) {
			System.out.println("Excepcion en el servidor");
		}
	}
}
