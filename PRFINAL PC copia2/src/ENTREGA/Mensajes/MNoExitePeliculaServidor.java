package ENTREGA.Mensajes;

// Mensaje del Servidor al Cliente para confirmar 
// que ha aņadido la pelicula solicitada
public class MNoExitePeliculaServidor extends Mensaje {

	private static final long serialVersionUID = 1L;
	
	public MNoExitePeliculaServidor(String origen, String destino, String p) {
		super("MAņadirPelicula", origen, destino, null, null, null);
	}
}
