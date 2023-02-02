public class EJ1 {
	
	public static class MultiThread extends Thread{
		
		public long id;
		
		MultiThread(long id){
			this.id = id;
		}	
		
		public void run() {
			System.out.println("El proceso " + id + " se esta ejecutando");		
			
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {				
				e.printStackTrace();
			}
			
			System.out.println("El proceso " + id + " ha terminado");
		}
	}
	
	
	public static void main(String[] args) throws InterruptedException {
		int N = 5;
		MultiThread p[] = new MultiThread[N];
		//int T[] = {100, 200, 50, 80, 10};
		
		for(int i = 0; i < N; i++) {
			p[i] = new MultiThread(N*5 + i);		
			p[i].start(); // Se puede poner en otro for
		}	
		
		// Espera a que los hilos terminen 
		for(int i = 0; i < N; i++) {			
			p[i].join();
												
		}
		
		System.out.println("Los " + N + " procesos han terminado");

	}

}
