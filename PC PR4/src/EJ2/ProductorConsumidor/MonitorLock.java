package EJ2.ProductorConsumidor;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class MonitorLock {
	private int x;
	private final Lock l;
	private final Condition condx;
	 
	public MonitorLock() {
		x = 0;
		l = new ReentrantLock();
		condx = l.newCondition();
	}
	
	// TODO NO HAY QUE USAR notifyAll HAY QUE PONER LA COLA DE ESPERA
	
	public void consumir() throws InterruptedException {
		l.lock();
		while(x==0) wait();
		x--;
		System.out.println("Consume, x = " + x);
		if(x== 0) notifyAll();
		l.unlock();
	}
	
	public void producir() throws InterruptedException {
		l.lock();
		while(x != 0) wait();
		x = 10;
		System.out.println("Rellena");
		notifyAll();
		l.unlock();
	}
	
	
}
