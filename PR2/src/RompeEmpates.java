public class RompeEmpates {
	public volatile boolean in1, in2;
	public volatile Entero last, x;
	
	/*public class Decrementa extends Thread{		
		private int N;
		private Entero x;
		RompeEmpates RE;
		
		
		public Decrementa(int N, Entero x) { 
				//boolean in1, boolean in2, Entero last) {
			this.x = x;
			this.N = N;
			this.RE = new RompeEmpates(in1, in2, last);
			
		}
		
		@Override
	    public void run() {
			while(N != 0) {
				
				// CSEntry
				RE.CSEntryP1();
				/*in1 = true;
				last = 1;
				while(in2 && last == 1);/
				
				// CS					
				// 1. decrementa
				x.n--;
				System.out.println("Se ha decrementado: " + x.n);
				
				// CSExit
				//in1 = false;
				RE.CSExitP1();
				
				N--;
			}
			
			
	    }
	}
	
	public class Incrementa extends Thread {		
		private int N;
		public Entero x;
		private RompeEmpates RE;
		
		public Incrementa(int N, Entero x) {
				//boolean in1, boolean in2, Entero last) {
			this.x = x;
			this.N = N;			
			this.RE = new RompeEmpates(in1, in2, last);
		}
		
		@Override
	    public void run() {
			while(N != 0) {
				// CSEntry
				RE.CSEntryP2();
				/*in2 = true;
				last = 0;
				while(in1 && last == 0);/
				
				
				// CS				
				// 2. Incrementa
				x.n++;
				System.out.println("Se ha incrementado: " + x.n);
				
				// CSExit
				//in2 = false;	
				RE.CSExitP2();
				
				N--;
			}
			
			
	    }
	}*/
	
	public RompeEmpates(boolean in1, boolean in2, Entero last) {
		this.in1 = in1;
		this.in2 = in2;
		this.last = last;
		//this.x = new Entero(0);
	}
	
	public void CSEntryP1() {
		in1 = true;
		last.n = 1;
		while(in2 && last.n == 1);
	}
	
	public void CSExitP1() {
		in1 = false;		
	}
	
	public void CSEntryP2() {
		in2 = true;
		last.n = 0;
		while(in1 && last.n == 0);
	}
	
	public void CSExitP2() {
		in2 = false;	
	}
	
}
