package ENTREGA.Mensajes;

public class MAddPelicula extends Mensaje {

	private static final long serialVersionUID = 1L;
	
	public MAddPelicula(String origen, String destino) {
		super("MAñadirPelicula", origen, destino, null, null);
	}
}
