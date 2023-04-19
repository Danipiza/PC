package ENTREGA.Mensajes;

// Mensaje del Servidor al Cliente para confirmar la eliminacion de 
// la pelicula solicitada por el Cliente
public class MConfirmacionEliminarPelicula extends Mensaje {

	private static final long serialVersionUID = 1L;
	
	public MConfirmacionEliminarPelicula(String origen, String destino) {
		super("MConfirmacionEliminarPelicula", origen, destino, null, null, null);
	}
}
