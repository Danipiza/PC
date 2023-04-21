package PRUEBA;

public class Main {

	public static void main(String[] args) throws InterruptedException {

		Servidor s = new Servidor();
		Cliente c = new Cliente();
		
		s.start();
		c.start();
		
		s.join();
		c.join();
		

	}

}
