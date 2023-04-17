package ENTREGA.Mensajes;

public class MListaUsuarios extends Mensaje {

	private static final long serialVersionUID = 1L;
	
	public MListaUsuarios(String origen, String destino) {
		super("MListaUsuarios", origen, destino);
	}
}
