package ENTREGA.Mensajes;

// Mensaje del Cliente al Servidor para solicitar la lista de peliculas
public class MListaPeliculas extends Mensaje {

	private static final long serialVersionUID = 1L;
	
	public MListaPeliculas(String origen, String destino) {
		super("MListaPeliculas", origen, destino, null, null, null);
	}
}
