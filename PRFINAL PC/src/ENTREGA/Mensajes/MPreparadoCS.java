package ENTREGA.Mensajes;

// Mensaje del Cliente (que tiene la pelicula solicitada por el 
// Cliente) al Servidor 
public class MPreparadoCS extends Mensaje {

	private static final long serialVersionUID = 1L;
	
	public MPreparadoCS(String origen, String destino) {
		super("MPreparadoCS", origen, destino, null, null, null);
	}
	
}
