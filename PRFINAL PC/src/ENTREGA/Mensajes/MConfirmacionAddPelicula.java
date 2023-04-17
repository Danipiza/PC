package ENTREGA.Mensajes;

public class MConfirmacionAddPelicula extends Mensaje {

	private static final long serialVersionUID = 1L;
	
	public MConfirmacionAddPelicula(String origen, String destino) {
		super("MAñadirPelicula", origen, destino);
	}
}
