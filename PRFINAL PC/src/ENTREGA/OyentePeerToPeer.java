package ENTREGA;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import ENTREGA.Concurrencia.Monitor.MonitorConexion;

@SuppressWarnings("unused")
public class OyentePeerToPeer extends Thread {

	private Socket sc1;	
	private ServerSocket servidor2;
	
	private DataInputStream inSC1;
	private DataOutputStream outSC1;
	
	private String conexion;	
	private MonitorConexion mConexion;
	
	
	public OyentePeerToPeer(Socket sc1, ServerSocket servidor2, MonitorConexion mConexion, 
			String conexion) {
		this.sc1 = sc1;
		this.servidor2 = servidor2;
		this.mConexion = mConexion;
		this.conexion = conexion;
	}
	
	@Override
	public void run() {
		try {
			
			sc1 = new Socket(mConexion.getHost(), mConexion.getPuerto());			
			mConexion.aumentaPuerto();
			
			inSC1 = new DataInputStream(sc1.getInputStream());			
			outSC1 = new DataOutputStream(sc1.getOutputStream());
			
			// Mientras que haya conexion se mantiene en el bucle
			while(mConexion.getConexiones().get(conexion)) {
				// Para que este durmiendo un rato y no este constantemente
				// accediendo al monitor 
				try {					
					Thread.sleep(5000);
				} catch (InterruptedException e) {	
					System.out.println("Excepcion en OyentePeerToPeer");
				}
			}
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	
}
