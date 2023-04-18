package ENTREGA;

import java.util.HashMap;
import java.util.Map.Entry;

public class TablaUsuarios {
		
	private HashMap<String, Usuario> usuarios;
	private HashMap<String, Integer> pelis;
	
	public TablaUsuarios() {		
		usuarios = new HashMap<String, Usuario>();		
		pelis = new HashMap<String, Integer>();
	}
	
	
	
	public HashMap<String, Usuario> getUsuarios(){
		return usuarios;
	}
	
	
	
	public HashMap<String, Integer> getPelis(){
		return pelis;
	}
	
	
	
	public void addUsuario(Usuario u) {		
		usuarios.put(u.getID(), u);	
		for (Entry<String, Integer> p : u.getPeliculas().entrySet()) {			
			addPelicula(u.getID(), p.getKey());				
		}
	}
	
	
	// private HashMap<String, Integer> pelis;
	public void eliminaUsuario(Usuario u) {		
		// Peliculas del usuario a eliminar
		for (Entry<String, Integer> p : u.getPeliculas().entrySet()) {
			Integer aux = pelis.get(p.getKey()) - 1;	
			// Si hay mas usuarios con esa peli, 
			// se reduce el numero de usuarios con esa peli
			if(aux != 0) pelis.put(p.getKey(), aux);
			// En caso contrario se elimina
			else pelis.remove(p.getKey());			
		}
		// Se quita el usuario
		usuarios.remove(u.getID());
				
	}
	
	public void addPelicula(String u, String p) {		
		if(pelis.containsKey(p)) {
			Integer aux = pelis.get(p) + 1;				
			pelis.put(p,aux);				
		} else { 
			pelis.put(p, 1);				
		}
		usuarios.get(u).addPeliculaUsuario(p);
	}
	
	/*public void imprimeUsuarios() {
		int aux = 1;
		System.out.println("Usuarios conectados al servidor:");
		for (Entry<String, Usuario> u : usuarios.entrySet()) {
			System.out.println(aux + ": "+ u.getKey());
			aux++;
		}
		System.out.println();
	}*/
	
	/*public void imprimePeliculas() {
		System.out.println("Peliculas compartidas en el servidor:");
		
		for (Entry<String, Integer> p : pelis.entrySet()) {
			System.out.println(" - "+ p.getKey() + " ["+p.getValue()+"]");
		}
		
		System.out.println();
	}*/
	
	/*TODO MAS CONCURRENCIA 
	 * Elimina pelicula*/
	
}
