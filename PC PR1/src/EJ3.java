import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import java.io.BufferedReader;

public class EJ3 {

	public static class Matriz {	
		public int m[][];
		
		public int n;
		
		public Matriz(int n) {
			m = new int[n][n];
			this.n = n;
		}
		
		public void inicializar(String archivo) throws FileNotFoundException {			
			BufferedReader br;			
		        try {
		        	br = new BufferedReader(new FileReader(new File(archivo + ".txt")));
		        	
		        	String linea;
		            int col = 0;
		            while ((linea = br.readLine()) != null) {
		                String[] val = linea.split(" ");
		                if (m == null) {
		                    m = new int[val.length][val.length];		                    
		                }
		                for (int j = 0; j < val.length; j++) {
		                    m[col][j] = Integer.parseInt(val[j]);
		                }
		                col++;
		            }
		        } catch (IOException e) {
		            System.out.println("Error, fichero inexistente: " + e.getMessage());
		        }
			
			
		}
		
		public void imprimirMatriz() {
			for(int i = 0; i < 3; i++) {
				for(int j = 0; j < 3; j++) {
					System.out.print(m[i][j] + " ");
				}
				System.out.println();
			}
		}
		
	}
	
	public static class Multi extends Thread{
		private int linea;
		private int N;
		Matriz m1,m2;
		
		public Multi(int linea, int N, Matriz m1, Matriz m2) {
			this.linea = linea;
			this.N = N;
			this.m1 = m1;
			this.m2 = m2;
		}
		
		@Override
	    public void run() {
			for(int j = 0; j < N; j++) {
				for(int k = 0; k < N; k++) {
					mRet.m[linea][j] = mRet.m[linea][j] + m1.m[linea][k]*m2.m[k][j];
				}
			}
			
	    }
	}
	
	
	public static Matriz mRet;
	
	public static void main(String[] args) throws FileNotFoundException, InterruptedException {
		Matriz m1 = new Matriz(3);
		Matriz m2 = new Matriz(3);
		mRet = new Matriz(3);
		m1.inicializar("matrix1");
		m2.inicializar("matrix2");
		m1.imprimirMatriz();
		System.out.println();
		m2.imprimirMatriz();
		
		
		int N = m1.n;
		
		Multi[] procesos = new Multi[N];
		for (int i = 0; i < N; i++) {
			procesos[i] = new Multi(i,N,m1,m2);
			procesos[i].start();
           
        }

        for (int i = 0; i < N; i++) {
        	procesos[i].join();            
        }
		
        
        System.out.println("La matriz resultante es: ");
		mRet.imprimirMatriz();
	}

}
