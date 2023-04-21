package ENTREGA;

import java.util.HashMap;

public class TablaSockets {
		
	private HashMap<String, InfoSocket> sockets;
	
	
	public TablaSockets() {		
		sockets = new HashMap<String, InfoSocket>();			
	}
	
	
	public HashMap<String, InfoSocket> getSockets(){
		return sockets;
	}
	
	public InfoSocket getSocket(String u) {
		return sockets.get(u);
	}
	
	public void addSocket(String u, InfoSocket s) {
		sockets.put(u, s);
	}
	
	public void eliminaSocket(String u) {
		sockets.remove(u);		
	}
	
	
	
	
	
	public void addUsuario(Usuario u) {		
		/*usuarios.put(u.getID(), u);	
		for (Entry<String, Integer> p : u.getPeliculas().entrySet()) {			
			addPelicula(u.getID(), p.getKey());				
		}*/
	}
	
	
	// private HashMap<String, Integer> pelis;
	public void eliminaUsuario(Usuario u) {		
		/*// Peliculas del usuario a eliminar
		for (Entry<String, Integer> p : u.getPeliculas().entrySet()) {
			Integer aux = pelis.get(p.getKey()) - 1;	
			// Si hay mas usuarios con esa peli, 
			// se reduce el numero de usuarios con esa peli
			if(aux != 0) pelis.put(p.getKey(), aux);
			// En caso contrario se elimina
			else pelis.remove(p.getKey());			
		}
		// Se quita el usuario
		usuarios.remove(u.getID());*/
				
	}
	
	
}
