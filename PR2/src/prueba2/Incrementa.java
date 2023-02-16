package prueba2;

public class Incrementa extends Thread{		
	private int N;
	private Entero x;
	
	private volatile boolean in1, in2;
	private volatile Entero last;
	private volatile RompeEmpates RE;
	
	public Incrementa(int N, Entero x,
		boolean in1, boolean in2, Entero last) {
		this.N = N;
		this.x = x;
		this.in1 = in1;
		this.in2 = in2;
		this.last = last;
		RE = new RompeEmpates();
		//RE = new RompeEmpates(in1, in2, last);
	}
	
	@Override
    public void run() {
		while(N != 0) {
			// CSEntry
			RE.CSEntryP2(in1,in2,last);
			
			/*in2 = true;
			last.n = 0;
			while(in1 || last.n == 0);*/
			
			
			// CS	1. Incrementa		
			x.n++;
			System.out.println("Se ha incrementado: " + x.n);
			
			
			// CSExit
			RE.CSExitP2(in2,last);
			/*in2 = false;	
			last.n = 0;*/
			
			
			N--;
		}
		
		
    }
}