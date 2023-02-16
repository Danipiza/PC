package prueba2;

public class RompeEmpates {
	//public volatile boolean in1, in2;
	//public volatile Entero last;
	
	
	
	
	public void CSEntryP1(boolean in1, boolean in2, Entero last) {
		in1 = true;
		last.n = 1;
		while(in2 || last.n == 1);
	}
	
	public void CSExitP1(boolean in1, Entero last) {
		in1 = false;
		last.n = 1;		
	}
	
	public void CSEntryP2(boolean in1, boolean in2, Entero last) {
		in2 = true;
		last.n = 0;
		while(in1 || last.n == 0);
	}
	
	public void CSExitP2(boolean in2, Entero last) {
		in2 = false;	
		last.n = 0;	
	}
	
}

