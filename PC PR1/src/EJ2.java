public class EJ2 {
	public static class Entero {
	    int n = 0;
	}

	public static class Incrementador extends Thread {
	    Entero entero;
	    int N;

	    public Incrementador(Entero entero, int N) {
	        this.entero = entero;
	        this.N = N;
	    }

	    @Override
	    public void run() {
	        for (int i = 0; i < N; i++) {
	            entero.n++;
	        }
	    }
	}

	public static class Decrementador extends Thread {
	    Entero entero;
	    int N;

	    public Decrementador(Entero entero, int N) {
	        this.entero = entero;
	        this.N = N;
	    }

	    @Override
	    public void run() {
	        for (int i = 0; i < N; i++) {
	            entero.n--;
	        }
	    }
	}

	
    public static void main(String[] args) throws InterruptedException {
        Entero entero = new Entero();
        int M = 5;
        int N = 1000;

        Incrementador[] incrementadores = new Incrementador[M];
        Decrementador[] decrementadores = new Decrementador[M];

        for (int i = 0; i < M; i++) {
            incrementadores[i] = new Incrementador(entero, N);
            decrementadores[i] = new Decrementador(entero, N);

            incrementadores[i].start();
            decrementadores[i].start();
        }

        for (int i = 0; i < M; i++) {
            incrementadores[i].join();
            decrementadores[i].join();
        }

        System.out.println("El valor final es: " + entero.n);
    }
	
}
