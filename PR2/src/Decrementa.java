public class Decrementa extends Thread{		
	private int N;
	private Entero x;
	private volatile boolean in1, in2;
	private volatile Entero last;
	private RompeEmpates RE;
	
	public Decrementa(int N, Entero x,
			boolean in1, boolean in2, Entero last) {
		this.N = N;
		this.x = x;
		this.in1 = in1;
		this.in2 = in2;
		this.last = last;
		this.RE = new RompeEmpates(in1, in2, last);
	}
	
	@Override
    public void run() {
		while(N != 0) {
			// CSEntry
			RE.CSEntryP1();
			/*in1 = true;
			last.n = 1;
			while(in2 && last.n == 1);*/
			
			// CS					
			// 1. decrementa
			x.n--;
			System.out.println("Se ha decrementado: " + x.n);
			
			// CSExit
			RE.CSExitP1();
			//in1 = false;
			N--;
		}
		
		
    }
}