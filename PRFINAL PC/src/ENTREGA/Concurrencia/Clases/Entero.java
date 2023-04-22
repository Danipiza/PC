package ENTREGA.Concurrencia.Clases;

public class Entero {
	private int valor;
	
	public Entero(int valor) {
		this.valor = valor;
	}
	
	public int getValor() {
		return this.valor;
	}	
	public void setValor(int valor) {
		this.valor = valor;		
	}
	public void addValor(int x) {
		this.valor += x;
	}
	public void restaValor(int x) {
		this.valor -= x;
	}
}
