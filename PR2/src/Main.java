public class Main {
	
	/*public static class Decrementa extends Thread{		
		private int N;
		private Entero x;
		RompeEmpates RE;
		
		
		public Decrementa(int N, Entero x, 
				boolean in1, boolean in2, Entero last) {
			this.N = N;
			this.x = x;
			this.RE = new RompeEmpates(in1, in2, last);
		}
		
		@Override
	    public void run() {
			while(N != 0) {
				
				// CSEntry
				RE.CSEntryP1();
				/*in1 = true;
				last.n = 1;
				while(in2 && last.n == 1);/
				
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
	
	
	public static class Incrementa extends Thread {		
		private int N;
		public Entero x;
		private RompeEmpates RE;
		
		public Incrementa(int N, Entero x, 
				boolean in1, boolean in2, Entero last) {
			
			this.N = N;
			this.x = x;
			this.RE = new RompeEmpates(in1, in2, last);
		}
		
		@Override
	    public void run() {
			while(N != 0) {
				// CSEntry
				RE.CSEntryP2();
				/*in2 = true;
				last.n = 0;
				while(in1 && last.n == 0);/
				
				
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
	
	
	// VARIABLES GLOBALES
	//public volatile static boolean in1, in2;
	//public volatile static Entero last = new Entero(0);
		
	public static void main(String[] args) throws InterruptedException {			
		Entero x = new Entero(0);
		int N = 5;
		
		boolean in1 = false, in2 = false;
		Entero last = new Entero(0);
		
		// Crea los hilos
		Incrementa inc = new Incrementa(N,x, in1, in2, last);
		Decrementa dec = new Decrementa(N,x, in1, in2, last);	
		inc.start();
		dec.start();           
        
		// Destruye los hilos
		inc.join();
		dec.join();
        
		
        // SALIDA
        System.out.println("El valor del entero al finalizar la ejecucion es: " + x.n);
		
	}

}
