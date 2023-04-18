package ENTREGA.Mensajes;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map.Entry;

import ENTREGA.Usuario;

public abstract class Mensaje implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private String tipo;
	private String origen, destino;	
	
	private HashMap<String, Usuario> usuarios;
	private HashMap<String, Integer> pelis;
	
	public Mensaje(String tipo, String origen, String destino,
			HashMap<String, Usuario> usuarios, HashMap<String, Integer> pelis) {
		this.tipo = tipo;
		this.origen = origen;
		this.destino = destino;
		this.usuarios = usuarios;
		this.pelis = pelis;
	}
	
	public String getTipo() {
		return tipo;
	}
	public String getOrigen() {
		return origen;
	}
	public String getDestino() {
		return destino;
	}
	
	public void imprimePeliculas() {
		System.out.println("Peliculas compartidas en el servidor:");
		
		for (Entry<String, Integer> p : pelis.entrySet()) {
			System.out.println(" - "+ p.getKey() + " ["+p.getValue()+"]");
		}
		
		System.out.println();
	}
	
	public void imprimeUsuarios() {
		int aux = 1;
		System.out.println("Usuarios conectados al servidor:");
		for (Entry<String, Usuario> u : usuarios.entrySet()) {
			System.out.println(aux + ": "+ u.getKey());
			aux++;
		}
		System.out.println();
	}
	
}
