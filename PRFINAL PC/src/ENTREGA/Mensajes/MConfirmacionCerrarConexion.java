package ENTREGA.Mensajes;

// Mensaje del Servidor al Cliente para confirmar que ha cerrado conexion
public class MConfirmacionCerrarConexion extends Mensaje {

	private static final long serialVersionUID = 1L;
	
	public MConfirmacionCerrarConexion(String origen, String destino) {
		super("MConfirmacionCerrarConexion", origen, destino, null, null, null);
	}
}
