package ENTREGA.Mensajes;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map.Entry;

import ENTREGA.InfoSocket;
import ENTREGA.Usuario;

// Implementa Serializable para poder mandar clases por mensaje
// Consiste en convertir la clase del mensaje a enviar en un
// array de bytes, para luego volver a convertirlo en la clase receptora
public abstract class Mensaje implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	// Tipo de mensaje (Para las Subclases)
	private String tipo;
	// Origen y destino del mensaje.
	// Clientes y Servidor en ambos.
	private String origen, destino;	
	
	// Variable para añadir o eliminar una pelicula de un usuario
	// Solo se usa en MAddPelicula y MEliminaPelicula
	// Se pasa por mensaje desde el Cliente al Servidor, para que este 
	// cambie la base de datos (TablaUsuario)
	private String pelicula;	
	// Variable para mostrar la lista de usuario en el servidor
	// Solo se usa en MConfirmacionListaUsuarios
	// Se pasa por mensaje desde el Servidor al Cliente	
	private HashMap<String, Usuario> usuarios;
	// Variable para mostrar la lista de peliculas en el servidor
	// Solo se usa en MConfirmacionListaPeliculas
	// Se pasa por mensaje desde el Servidor al Cliente	
	private HashMap<String, String> pelis;
	
	
	// Constructora de la clase abstracta Mensaje,
	// Se usa en sus subclases mediante la llamada super(...)
	public Mensaje(String tipo, String origen, String destino,
			String pelicula, HashMap<String, Usuario> usuarios, HashMap<String, String> pelis) {
		this.tipo = tipo;
		this.origen = origen;
		this.destino = destino;
		// Variable para modificar TablaUsuario
		this.pelicula = pelicula;
		// Variables para mostrar listas
		this.usuarios = usuarios; 
		this.pelis = pelis;
	}
	
	// Getter: Tipo del mensaje
	public String getTipo() {
		return tipo;
	}
	// Getter: Origen del mensaje
	public String getOrigen() {
		return origen;
	}
	// Getter: Destino del mensaje
	public String getDestino() {
		return destino;
	}
	// Getter: Pelicula a modificar
	public String getPelicula() {
		return pelicula;
	}
	
	// Imprime: Lista de peliculas
	public void imprimePeliculas() {
		System.out.println("Peliculas compartidas en el servidor:");
		
		for (Entry<String, String> p : pelis.entrySet()) {
			System.out.println(" - "+ p.getKey() + " ["+p.getValue()+"]");
		}
		
		System.out.println();
	}
	
	// Imprime: Lista de usuarios
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
