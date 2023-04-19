package ENTREGA.Mensajes;

// Mensaje del Servidor al Cliente para confirmar 
// que ha añadido la pelicula solicitada
public class MNoExitePeliculaServidor extends Mensaje {

	private static final long serialVersionUID = 1L;
	
	public MNoExitePeliculaServidor(String origen, String destino, String p) {
		super("MNoExitePeliculaServidor", origen, destino, null, null, null);
	}
}
