package ENTREGA.Mensajes;

// Mensaje del Cliente al Servidor para solicitar a�adir una pelicula
public class MAddPelicula extends Mensaje {

	private static final long serialVersionUID = 1L;
	
	public MAddPelicula(String origen, String destino, String p) {
		super("MA�adirPelicula", origen, destino, p, null, null);
	}
}
