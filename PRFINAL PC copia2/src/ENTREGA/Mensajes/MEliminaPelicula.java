package ENTREGA.Mensajes;

// Mensaje del Cliente al Servidor para solicitar eliminar pelicula del Cliente
public class MEliminaPelicula extends Mensaje {

	private static final long serialVersionUID = 1L;
	
	public MEliminaPelicula(String origen, String destino, String p) {
		super("MEliminaPelicula", origen, destino, p, null, null);
	}
}
