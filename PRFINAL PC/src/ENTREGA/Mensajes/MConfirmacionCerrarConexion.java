package ENTREGA.Mensajes;

public class MConfirmacionCerrarConexion extends Mensaje {

	private static final long serialVersionUID = 1L;
	
	public MConfirmacionCerrarConexion(String origen, String destino) {
		super("MConfirmacionCerrarConexion", origen, destino, null, null);
	}
}
