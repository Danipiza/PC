package ENTREGA.Mensajes;

import ENTREGA.InfoSocket;

// Mensaje del Sevidor al Cliente para confirmar la conexion 
// con el Cliente que tenga la pelicula solicitada
public class MPreparadoSC extends Mensaje {
	
	private static final long serialVersionUID = 1L;

	public MPreparadoSC(String origen, String destino) {
		super("MPreparadoSC", origen, destino, null, null, null);
	}
}
