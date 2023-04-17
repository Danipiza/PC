package ENTREGA.Mensajes;

public class MConfirmacionConexion extends Mensaje{

	private static final long serialVersionUID = 1L;
	
	public MConfirmacionConexion(String origen, String destino) {
		super("MConfirmacionConexion", origen, destino);
	}
}
