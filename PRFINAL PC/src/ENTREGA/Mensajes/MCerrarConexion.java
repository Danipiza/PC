package ENTREGA.Mensajes;

public class MCerrarConexion extends Mensaje {

	private static final long serialVersionUID = 1L;

	public MCerrarConexion(String origen, String destino) {
		super("MCerrarConexion", origen, destino);
	}
}
