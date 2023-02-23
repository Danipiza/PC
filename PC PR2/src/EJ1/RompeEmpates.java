package EJ1;

import EJ1.Clases.Entero;

public class RompeEmpates {	
	
	public void TakeLockP1(boolean in1, boolean in2, Entero last) {
		in1 = true;
		last.n = 1;
		while(in2 || last.n == 1);
	}
	
	public void ReleaseLockP1(boolean in1, Entero last) {
		in1 = false;
		last.n = 1;		
	}
	
	public void TakeLockP2(boolean in1, boolean in2, Entero last) {
		in2 = true;
		last.n = 0;
		while(in1 || last.n == 0);
	}
	
	public void ReleaseLockP2(boolean in2, Entero last) {
		in2 = false;	
		last.n = 0;	
	}
	
}

