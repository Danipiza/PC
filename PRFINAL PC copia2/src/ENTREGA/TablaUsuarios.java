package ENTREGA;

import java.util.HashMap;
import java.util.Map.Entry;

public class TablaUsuarios {
		
	private HashMap<String, Usuario> usuarios;
	private HashMap<String, String> pelis;
	
	public TablaUsuarios() {		
		usuarios = new HashMap<String, Usuario>();		
		pelis = new HashMap<String, String>();
	}
	
	
	public synchronized Usuario getUsuario(String ID) {
		return usuarios.get(ID);
	}
	
	public synchronized HashMap<String, Usuario> getUsuarios(){
		return usuarios;
	}	
	
	
	public synchronized HashMap<String, String> getPelis(){
		return pelis;
	}
	
	
	
	public synchronized void addUsuario(Usuario u) {		
		usuarios.put(u.getID(), u);	
		for (Entry<String, String> p : u.getPeliculas().entrySet()) {			
			addPelicula(u.getID(), p.getKey());				
		}
	}
	
	
	// private HashMap<String, Integer> pelis;
	public synchronized void eliminaUsuario(Usuario u) {		
		// Peliculas del usuario a eliminar
		for (Entry<String, String> p : u.getPeliculas().entrySet()) {
			String[] usuariosID = pelis.get(p).split(" ");
			//Integer aux = pelis.get(p.getKey()) - 1;	
			// Si hay mas usuarios con esa peli, 
			// Si no hay mas usuarios aparte del que 
			// Se elimina se quita la peli del servidor
			if(usuariosID.length == 1) {
				pelis.remove(p.getKey());				
			}
			// Se reduce el numero de usuarios con esa peli
			else {
				String aux = "";
				for (int i = 0; i < usuariosID.length-1; i++) {
					if(usuariosID[i] != u.getID()) {
						if(aux.equals("")) {
							aux += usuariosID[i];
						} else {
							aux += " " + usuariosID[i];
						}						
					}
				}
				pelis.put(p.getKey(),aux);
			}
		}
		// Se quita el usuario
		usuarios.remove(u.getID());
				
	}
	/*public void eliminaUsuario(Usuario u) {		
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
				
	}*/
	
	public synchronized void addPelicula(String u, String p) {			
		
		if(pelis.containsKey(p)) {
			pelis.put(p, pelis.get(p) + " " + u);				
		}
		else pelis.put(p, u);	
		//else pelis.put(p, 1);
		
		usuarios.get(u).addPeliculaUsuario(p);
		
	}
	
	public synchronized boolean eliminaPelicula(String u, String p) {		
		boolean ret = true;
		if(usuarios.get(u).getPeliculas().containsKey(p)) {
			String[] usuariosID = pelis.get(p).split(" ");			
			if (usuariosID.length == 1) pelis.remove(p);
			else {
				String aux = "";
				for (int i = 0; i < usuariosID.length-1; i++) {
					if(usuariosID[i] != u) {
						if(aux.equals("")) {
							aux += usuariosID[i];
						} else {
							aux += " " + usuariosID[i];
						}						
					}
				}
				pelis.put(p,aux);
			}
			
			usuarios.get(u).eliminaPeliculaUsuario(p);
		} else {
			System.out.println("El usuario con ID: "+ u + " no tiene la pelicula: " + p);
			ret = false;
		}
		return ret;
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
	
	
}
