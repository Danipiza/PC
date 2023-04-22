package ENTREGA;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import ENTREGA.Concurrencia.Monitor.MonitorPuertoLock;

public class OyentePeerToPeer extends Thread {

	private Socket sc1;
	private ServerSocket servidor2;
	
	private DataInputStream inSC1;
	private DataOutputStream outSC1;
	
	private String conexion;
	
	private MonitorPuertoLock mPuerto;
	
	
	
	public OyentePeerToPeer(Socket sc1, ServerSocket servidor2, MonitorPuertoLock mPuerto, 
			String conexion) {
		this.sc1 = sc1;
		this.servidor2 = servidor2;
		this.mPuerto = mPuerto;
		this.conexion = conexion;
	}
	
	@Override
	public void run() {
		try {
			
			sc1 = new Socket(mPuerto.getHost(), mPuerto.getPuerto());			
			mPuerto.aumentaPuerto();
			mPuerto.aumentaIndice();
			
			inSC1 = new DataInputStream(sc1.getInputStream());			
			outSC1 = new DataOutputStream(sc1.getOutputStream());
			
			// Mientras que haya conexion se mantiene en el bucle
			while(mPuerto.getConexiones().get(conexion)) {
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
