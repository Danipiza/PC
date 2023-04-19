package ENTREGA;

import java.io.Serializable;
import java.util.HashMap;

public class Usuario implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private String id;
	private String ip;
	private HashMap<String, String> peliculas;
	
	public Usuario(String id, String ip, HashMap<String, String> peliculas) {
		this.id = id;
		this.ip = ip;
		this.peliculas = peliculas;
	}
	
	public String getID() {
		return this.id;
	}
	
	public String getIP() {
		return this.ip;
	}
	
	public void addPeliculaUsuario(String p) {
		peliculas.put(p, id);
	}
	
	public void eliminaPeliculaUsuario(String p) {
		peliculas.remove(p);
	}
	
	public HashMap<String, String> getPeliculas(){
		return peliculas;
	}
	
}
