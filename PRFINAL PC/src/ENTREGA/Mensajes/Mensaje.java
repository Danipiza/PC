package ENTREGA.Mensajes;

import java.io.Serializable;

public abstract class Mensaje implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	// TODO CAMBIAR?
	private String tipo;
	private String origen, destino;	
	
	public Mensaje(String tipo, String origen, String destino) {
		this.tipo = tipo;
		this.origen = origen;
		this.destino = destino;
	}
	
	public String getTipo() {
		return tipo;
	}
	public String getOrigen() {
		return origen;
	}
	public String getDestino() {
		return destino;
	}
}
