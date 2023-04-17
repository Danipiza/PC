package ENTREGA;

import java.util.ArrayList;
import java.util.HashMap;

public class Main {

	private static HashMap<String, Integer> addPeliculasMain(String[] pelis) {
		HashMap<String, Integer> ret = new HashMap<String, Integer>();
		for (String p : pelis) {
			ret.put(p,1);
		}
		
		return ret;
	}
	
	public static void main(String[] args) throws InterruptedException {

		Servidor s = new Servidor();
		String[] p = {"House Party (2023)", "El cabo del miedo (1991) "};
		HashMap<String, Integer> peliculasU1 = addPeliculasMain(p);
		
		
		
		Usuario usuario1 = new Usuario("1", "104.9.226.93", peliculasU1);
		Cliente c = new Cliente(usuario1);
		
		s.start();
		c.start();
		
		s.join();
		c.join();
		

	}

}
