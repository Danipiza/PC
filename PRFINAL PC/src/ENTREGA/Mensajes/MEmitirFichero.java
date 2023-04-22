package ENTREGA.Mensajes;

// Mensaje del Servidor al Cliente que tiene la pelicula solicitada 
// por el Cliente 
public class MEmitirFichero extends Mensaje {

	private static final long serialVersionUID = 1L;
	
	public MEmitirFichero(String origen, String destino) {
		super("MEmitirFichero", origen, destino, null, null, null);
	}
}
