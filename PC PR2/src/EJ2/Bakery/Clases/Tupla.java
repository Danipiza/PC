package EJ2.Bakery.Clases;

public class Tupla {
	public Entero first;
	public int second;

	public Tupla(Entero first, int second) {
		this.first = first;
		this.second = second;
	}

	public Entero getFirst() { return this.first; }

	public int getSecond() { return this.second; }
	
	public boolean compara(Tupla x) {				
		// (a,b) >> (c,d) si a > c or (a == c and b > d)
		return first.n > x.first.n || (first.n == x.first.n && second > x.second);
	}
		
	
}
