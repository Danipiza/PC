package ENTREGA.Mensajes;

// Mensaje del Servidor al Cliente para confirmar 
// que ha añadido la pelicula solicitada
public class MFalloEliminarPelicula extends Mensaje {

	private static final long serialVersionUID = 1L;
	
	public MFalloEliminarPelicula(String origen, String destino) {
		super("MFalloEliminarPelicula", origen, destino, null, null, null);
	}
}
