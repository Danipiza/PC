package prueba2;

public class EJ1 {	
	
	public EJ1(int N) {
		
		try {
			mainEJ1(N);
		} catch (InterruptedException e) {			
			e.printStackTrace();
		}
	}
	
	public volatile boolean in1 = false, in2 = false;
	public volatile Entero last = new Entero(0);
	
	
	public void mainEJ1(int N) throws InterruptedException {
		Entero x = new Entero(0);
		
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
