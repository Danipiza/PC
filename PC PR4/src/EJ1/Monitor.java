package EJ1;

public class Monitor {
	private int x;
	
	public Monitor(int x) {
		this.x =x;
	}
	
	public synchronized void incrementa() {
		x++;
		System.out.println("(+) Se ha incrementado, x = " + x);
	}
	
	public synchronized void decrementa() {
		x--;
		System.out.println("(-) Se ha decrementado, x = " + x);
	}
	
	
	
	
}
