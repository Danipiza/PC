package ENTREGA.Mensajes;

// Mensaje del Cliente al Servidor para solicitar añadir una pelicula
public class MFinalizarConexion extends Mensaje {

	private static final long serialVersionUID = 1L;
	
	public MFinalizarConexion(String origen, String destino) {
		super("MFinalizarConexion", origen, destino, null, null, null);
	}
}
