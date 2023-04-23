package ENTREGA;

import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.ObjectInputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;

import ENTREGA.Concurrencia.Monitor.MonitorConexion;
import ENTREGA.Concurrencia.Monitor.MonitorLock;


// PUEDE SER UN MAIN Y ABRIR VARIAS CONSOLAS 

public class Servidor extends Thread{
	
	
	private ServerSocket servidor;
	private Socket sc;       
	private DataInputStream in;
	private DataOutputStream out;
	
	
	private volatile TablaUsuarios tUSERs;
	private volatile TablaSockets tSOCKETs;

	// Monitor
	private MonitorLock mPidePeli;
	private MonitorConexion mConexion;
	
	// Semaforo
	private HashMap<String, Boolean> mapaClienteActivo;
	
	// PUERTO DEL SERVIDOR
    final int PUERTO = 5000;
    
	
	public Servidor(MonitorLock mPidePeli, MonitorConexion mConexion,
			HashMap<String, Boolean> mapaClienteActivo) {
		this.servidor = null;
		this.sc = null;        
		this.in = null;
		this.out = null;		
		// Tablas 
		this.tUSERs = new TablaUsuarios();
		this.tSOCKETs = new TablaSockets();
		// Monitor ----------------------------------
		this.mPidePeli = mPidePeli;
		this.mConexion = mConexion; 
		// Semaforo ---------------------------------		
		this.mapaClienteActivo = mapaClienteActivo;
	}
	
	@Override 	
	public void run() {                
        
        try {
        	// Se crea el servidor
        	System. out.println ("Servidor iniciado");
        	servidor = new ServerSocket(PUERTO);
            
        	OyenteServidor OS;
        	// Bucle que nunca termina
            while (true) {
            	// Acepta una conexion de un cliente, que se conecte
            	// con el host y el puerto creado por el ServerSocket
            	sc = servidor.accept();                                             
                
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
        	        		
        		        		
        		System.out.println("Cliente conectado al servidor");        		
        		tUSERs.addUsuario(usuarioC);
        		InfoSocket socketCliente = new InfoSocket(sc, in, out, usuarioC.getID());
        		tSOCKETs.addSocket(usuarioC.getID(), socketCliente);
        		
        		
                // Crea el hilo OyenteServidor
                OS = new OyenteServidor(in, out, usuarioC.getID(), 
                		mPidePeli, mConexion, 
                		mapaClienteActivo,
                		tUSERs, tSOCKETs);
                
                OS.start();                
            }
        } catch (Exception e) {
        	Exception excepcion = new Exception("Excepcion en el Servidor");
			excepcion.setStackTrace(e.getStackTrace());
			excepcion.printStackTrace();
		}
	}
}
