package EJ2.RompeEmpates;

import EJ2.RompeEmpates.Clases.Entero;

public class LockRompeEmpate {
	
	// INC
	public void TakeLock(int N, int i, Entero[] in, Entero[] last) {		
		for(int j = 0; j < N; j++) {
			in[i].n = j;
			last[j].n = i;
			for(int k = 0; j < N && k!=i; k++) {
				while(in[k].n >= in[i].n && last[j].n == i);
			}
			
		}	
		
		
	}
	
	public void ReleaseLock(int i, Entero[] in) {
		in[i].n = -1;	
	}
	
	
	
}
