package ENTREGA;

import java.util.HashMap;
import java.util.Map.Entry;

// Tabla de Usuarios que el servidor apunta
public class TablaUsuarios {
		
	private HashMap<String, Usuario> usuarios;
	private HashMap<String, String> pelis;
	
	public TablaUsuarios() {		
		usuarios = new HashMap<String, Usuario>();		
		pelis = new HashMap<String, String>();
	}
	
	
	public Usuario getUsuario(String ID) {
		return usuarios.get(ID);
	}
	
	public HashMap<String, Usuario> getUsuarios(){
		return usuarios;
	}	
	
	
	public HashMap<String, String> getPelis(){
		return pelis;
	}
	
	
	
	public void addUsuario(Usuario u) {		
		usuarios.put(u.getID(), u);	
		for (Entry<String, String> p : u.getPeliculas().entrySet()) {			
			addPelicula(u.getID(), p.getKey());				
		}
	}
	
	
	// private HashMap<String, Integer> pelis;
	public void eliminaUsuario(Usuario u) {		
		// Peliculas del usuario a eliminar
		for (Entry<String, String> p : u.getPeliculas().entrySet()) {			
			String[] usuariosID = pelis.get(p.getKey()).split(" ");
			//Integer aux = pelis.get(p.getKey()) - 1;	
			// Si hay mas usuarios con esa peli, 
			
			// Si no hay mas usuarios aparte del que 
			// Se elimina, se quita la peli del servidor
			if(usuariosID.length == 1) {
				pelis.remove(p.getKey());				
			}
			// Se reduce el numero de usuarios con esa peli
			else {
				String aux = "";
				for (int i = 0; i < usuariosID.length; i++) {					
					if(!usuariosID[i].equals(u.getID())) {
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
	
	public void addPelicula(String u, String p) {			
		// La pelicula ya esta en el servidor
		if(pelis.containsKey(p)) {
			// Peliculas del usuario con ID = u
			HashMap<String, String> aux = usuarios.get(u).getPeliculas();
			// Recorre las peliculas
			boolean esta = false;
			for (Entry<String, String> pelicula : aux.entrySet()) {
				if(pelicula.getValue().equals(p)) esta = true;
			}
			// Si el usuario ya tiene la pelicula a añadir,
			// No la añade
			if(!esta) pelis.put(p, pelis.get(p) + " " + u);				
		}
		// La pelicula no esta en el servidor
		else pelis.put(p, u);	
		
		// Añade la pelicula al usuario
		// Si ya la tiene no hace nada (ya que la sobreescribe)
		usuarios.get(u).addPeliculaUsuario(p);		
	}
	
	public boolean eliminaPelicula(String u, String p) {		
		boolean ret = true;
		// El usuario con ID = u tiene la pelicula "p"
		if(usuarios.get(u).getPeliculas().containsKey(p)) {
			// IDs de los usuarios con la pelicula "p"
			String[] usuariosID = pelis.get(p).split(" ");
			// Si la pelicula solo tiene un usuario se elimina la pelicula del servidor
			if (usuariosID.length == 1) pelis.remove(p);
			else {
				String aux = "";
				// Recorre todos los usuarios
				for (int i = 0; i < usuariosID.length; i++) {
					// Si el usuario no coincide con el usuario que elimina la pelicula
					// añade ID del usuario a aux
					if(!usuariosID[i].equals(u)) {
						// Si el usuario es el 1er usuario a añadir no hay que poner espacio
						if(aux.equals("")) {
							aux += usuariosID[i];
						} else {
							aux += " " + usuariosID[i];
						}						
					}
				}
				pelis.put(p,aux);
			}
			
			// Elimina la pelicula del usuario
			usuarios.get(u).eliminaPeliculaUsuario(p);
		} else {
			System.out.println("El usuario con ID: "+ u + " no tiene la pelicula: " + p);
			ret = false;
		}
		return ret;
	}	
	
}
