package ENTREGA.Mensajes;

import java.util.HashMap;

// Mensaje del Servidor al Cliente para confirmar lista de peliculas
public class MConfirmacionListaPeliculas extends Mensaje {
	private static final long serialVersionUID = 1L;
	
	public MConfirmacionListaPeliculas(String origen, String destino, HashMap<String, String> pelis) {
		super("MConfirmacionListaPeliculas", origen, destino, null, null, pelis);
	}
}
