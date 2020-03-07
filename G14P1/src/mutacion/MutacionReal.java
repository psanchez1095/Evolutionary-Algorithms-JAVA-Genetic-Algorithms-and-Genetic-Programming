package mutacion;

import java.util.ArrayList;

import gen.GenBooleano;
import gen.GenReal;

public class MutacionReal {
	ArrayList<GenReal> cromosoma;
	double probMutacion;
	
	public MutacionReal(double probMutacion, ArrayList<GenReal> gens) {
		this.probMutacion = probMutacion;
		this.cromosoma = gens;
	}
	
    public void mutar() { 
       
        for (int i = 0; i < this.cromosoma.size(); i++) {
        	for (int j = 0; j < this.cromosoma.get(i).getAlelo().length-1; ++j) {
        		double valor = Math.random();
        		if (valor <= this.probMutacion) {
        			this.cromosoma.get(i).setAleloExct(
        					this.cromosoma.get(i).getAlelo()[j+1], j);
            	}
        	}
        }
    }
}
