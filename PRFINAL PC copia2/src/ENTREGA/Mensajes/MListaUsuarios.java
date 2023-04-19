package ENTREGA.Mensajes;

// Mensaje del Cliente al Servidor para solicitar la lista de usuarios
public class MListaUsuarios extends Mensaje {

	private static final long serialVersionUID = 1L;
	
	public MListaUsuarios(String origen, String destino) {
		super("MListaUsuarios", origen, destino, null, null, null);
	}
}
