package ENTREGA.Mensajes;

public class MConfirmacionListaPeliculas extends Mensaje {

	private static final long serialVersionUID = 1L;
	
	public MConfirmacionListaPeliculas(String origen, String destino) {
		super("MConfirmacionListaPeliculas", origen, destino);
	}
}
