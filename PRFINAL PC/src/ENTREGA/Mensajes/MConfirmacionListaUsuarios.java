package ENTREGA.Mensajes;

import java.util.HashMap;

import ENTREGA.Usuario;

// Mensaje del Servidor al Cliente para confirmar lista de usuarios 
public class MConfirmacionListaUsuarios extends Mensaje {

	private static final long serialVersionUID = 1L;
	
	public MConfirmacionListaUsuarios(String origen, String destino, HashMap<String, Usuario> usuarios) {
		super("MConfirmacionListaUsuarios", origen, destino, null, usuarios, null);
	}
}
