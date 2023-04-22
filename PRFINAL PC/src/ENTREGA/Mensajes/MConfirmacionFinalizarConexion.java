package ENTREGA.Mensajes;

// Mensaje del Cliente al Servidor para solicitar añadir una pelicula
public class MConfirmacionFinalizarConexion extends Mensaje {

	private static final long serialVersionUID = 1L;
	
	public MConfirmacionFinalizarConexion(String origen, String destino) {
		super("MConfirmacionFinalizarConexion", origen, destino, null, null, null);
	}
}
