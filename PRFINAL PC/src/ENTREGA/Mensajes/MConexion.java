package ENTREGA.Mensajes;

public class MConexion extends Mensaje {

	private static final long serialVersionUID = 1L;
	
	public MConexion(String origen, String destino) {
		super("MConexion", origen, destino);
	}
}
