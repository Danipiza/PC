package ENTREGA.Mensajes;

import java.util.HashMap;

import ENTREGA.Usuario;

public class MConfirmacionListaUsuarios extends Mensaje {

	private static final long serialVersionUID = 1L;
	
	public MConfirmacionListaUsuarios(String origen, String destino, HashMap<String, Usuario> usuarios) {
		super("MConfirmacionListaUsuarios", origen, destino, usuarios, null);
	}
	
	public void printListaUsuarios() {
		
	}
}
