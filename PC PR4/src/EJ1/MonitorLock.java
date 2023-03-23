package EJ1;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class MonitorLock {

	private int x;
	private final Lock l;
	
	public MonitorLock(int x) {
		this.x = x;
		l = new ReentrantLock(true);
	}
	
	
	public void incrementa() {
		l.lock();
		x++;
		System.out.println("(+) Se ha incrementado, x = " + x);
		l.unlock();
	}
	
	public void decrementa() {
		l.lock();
		x--;
		System.out.println("(-) Se ha decrementado, x = " + x);
		l.unlock();		
	}
	
}
