package EJ2.Bakery;

import EJ2.Bakery.Clases.*;

public class BakeryAlgoritmo {
	
	// INC
	public void TakeLock(int N, int i, Entero[] turno) {		
		turno[i].n = maximo(turno) + 1;
		for(int j = 0; j < N && j != i; j++){
			Tupla x = new Tupla(turno[i],i);
			Tupla y = new Tupla(turno[j],j);
			while(turno[j].n != 0 && x.compara(y));
		} 		
		
	}
	
	public int maximo(Entero[] turno) {
		int ret = 0;
		for(int i = 0; i < turno.length; i++) {
			if(ret < turno[i].n) ret = turno[i].n;
		}
		
		return ret;
	}
	
	public void ReleaseLock(int i, Entero[] turno) {
		 turno[i].n = 0;
	}

	
	
}
