package ENTREGA.Mensajes;

// Mensaje del Cliente al Servidor para solicitar pelicula de 
// TablaUsuario
public class MPedirFichero extends Mensaje {

	private static final long serialVersionUID = 1L;
	
	public MPedirFichero(String origen, String destino, String p) {
		super("MPedirFichero", origen, destino, p, null, null);
	}
}
