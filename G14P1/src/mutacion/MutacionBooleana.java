package mutacion;

import java.util.ArrayList;

import gen.GenBooleano;

public class MutacionBooleana {
	
	ArrayList<GenBooleano> cromosoma;
	double probMutacion;
	
	public MutacionBooleana(double probMutacion, ArrayList<GenBooleano> gens) {
		this.probMutacion = probMutacion;
		this.cromosoma = gens;
	}
	
    public void mutar() { 
       
        for (int i = 0; i < this.cromosoma.size(); i++) {
        	for (int j = 0; j < this.cromosoma.get(i).getAlelo().length; ++j) {
        		double valor = Math.random();
        		if (valor <= this.probMutacion) {
        			if (this.cromosoma.get(i).getAlelo()[j]) 
        				this.cromosoma.get(i).setAleloExct(false, j);
        			else
        				this.cromosoma.get(i).setAleloExct(true, j);
            	}
        	}
        }
    }
}
