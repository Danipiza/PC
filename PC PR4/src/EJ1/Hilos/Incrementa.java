package EJ1.Hilos;

import EJ1.Monitor;
import EJ1.MonitorLock;

public class Incrementa extends Thread{		
	private int i, mode;
	private Monitor monitor;
	private MonitorLock monitorLock;
	
	public Incrementa(int i, Monitor monitor, int mode, MonitorLock monitorLock) {
		this.i = i;
		this.monitor = monitor;
		this.mode = mode;
		this.monitorLock = monitorLock;
	}
	
	@Override 	
	public void run() {		
		try {
			Thread.sleep(100);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		if(mode == 1) monitor.incrementa();
		else monitorLock.incrementa();
		
		
				
    }
		
		
    
}