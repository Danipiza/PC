package ENTREGA.Mensajes;

import java.util.HashMap;

public class MConfirmacionListaPeliculas extends Mensaje {
	private static final long serialVersionUID = 1L;
	
	public MConfirmacionListaPeliculas(String origen, String destino, HashMap<String, Integer> pelis) {
		super("MConfirmacionListaPeliculas", origen, destino, null, pelis);
	}
}
