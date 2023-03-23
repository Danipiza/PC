package EJ2.ProductorConsumidor;

public class Main {

	public static void main(String[] args) {
		
		
		System.out.println("Elige un modo: " + '\n' + 
				"1: Monitor usando synchronized" + '\n' +
				"2: Monitor usando lock");
		//Scanner x = new Scanner();
		ProdCons mainEJ = new ProdCons(5,2);
		//IncDec main = new IncDec(5,x);
	}

}
