package ENTREGA.Mensajes;

public class MListaPeliculas extends Mensaje {

	private static final long serialVersionUID = 1L;
	
	public MListaPeliculas(String origen, String destino) {
		super("MListaPeliculas", origen, destino);
	}
}
