package ENTREGA.Mensajes;

public class MEmitirFichero extends Mensaje {

	private static final long serialVersionUID = 1L;
	
	public MEmitirFichero(String origen, String destino) {
		super("MEmitirFichero", origen, destino, null, null);
	}
}
