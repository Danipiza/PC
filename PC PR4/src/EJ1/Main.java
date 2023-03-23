package EJ1;

import java.util.Scanner;

public class Main {

	public static void main(String[] args) {
		
		System.out.println("Elige un modo: " + '\n' + 
				"1: Monitor usando synchronized" + '\n' +
				"2: Monitor usando lock");
		//Scanner x = new Scanner();
		IncDec main = new IncDec(5,2);
		//IncDec main = new IncDec(5,x);
	}

}
