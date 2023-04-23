package ENTREGA.Mensajes;

// Mensaje del Servidor al Cliente para confirmar 
// que ha a�adido la pelicula solicitada
public class MConfirmacionAddPelicula extends Mensaje {

	private static final long serialVersionUID = 1L;
	
	public MConfirmacionAddPelicula(String origen, String destino) {
		super("MConfirmacionA�adirPelicula", origen, destino, null, null, null);
	}
}
