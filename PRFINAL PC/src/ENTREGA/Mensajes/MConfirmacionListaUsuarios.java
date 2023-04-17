package ENTREGA.Mensajes;

public class MConfirmacionListaUsuarios extends Mensaje {

	private static final long serialVersionUID = 1L;
	
	public MConfirmacionListaUsuarios(String origen, String destino) {
		super("MConfirmacionListaUsuarios", origen, destino);
	}
	
	public void printListaUsuarios() {
		
	}
}
