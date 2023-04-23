package ENTREGA;

import java.io.Serializable;
import java.util.HashMap;

// Datos de cada cliente
public class Usuario implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private String id;
	private String ip;
	private HashMap<String, String> peliculas;
	private int conectados;
	
	public Usuario(String id, String ip, HashMap<String, String> peliculas) {
		this.id = id;
		this.ip = ip;
		this.peliculas = peliculas;
		this.conectados = 0;
	}
	
	public String getID() {
		return this.id;
	}
	
	public String getIP() {
		return this.ip;
	}
	
	public int getConectados() {
		return this.conectados;
	}
	
	public void addConectado() {
		this.conectados++;
	}
	
	public void eliminaConectado() {
		this.conectados--;
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
