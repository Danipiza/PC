package ENTREGA.Mensajes;

// Mensaje del Servidor al Cliente para confirmar 
// que ha añadido la pelicula solicitada
public class MConfirmacionAddPelicula extends Mensaje {

	private static final long serialVersionUID = 1L;
	
	public MConfirmacionAddPelicula(String origen, String destino) {
		super("MConfirmacionAñadirPelicula", origen, destino, null, null, null);
	}
}
