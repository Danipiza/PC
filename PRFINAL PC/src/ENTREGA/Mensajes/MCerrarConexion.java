package ENTREGA.Mensajes;

// Mensaje del Cliente al Servidor para solicitar 
// cerrar conexion con el Servidor 
public class MCerrarConexion extends Mensaje {

	private static final long serialVersionUID = 1L;

	public MCerrarConexion(String origen, String destino) {
		super("MCerrarConexion", origen, destino, null, null, null);
	}
}
