package EJ2.ProductorConsumidor;

public class Monitor {
	private int x;
	 
	public Monitor() {
		x = 0;
	}
	
	
	public synchronized void consumir() throws InterruptedException {
		while(x==0) wait();
		x--;
		System.out.println("Consume, x = " + x);
		if(x== 0) notifyAll();
	}
	
	public synchronized void producir() throws InterruptedException {
		while(x != 0) wait();
		x = 10;
		System.out.println("Rellena");
		notifyAll();
	}
	
	
}
