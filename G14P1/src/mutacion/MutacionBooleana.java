package mutacion;

import java.util.ArrayList;

import cromosoma.Cromosoma;
import gen.GenBooleano;

public class MutacionBooleana {
	
	 
	double probMutacion;
	int numGenes;
	int[] longitudes;
	
	public MutacionBooleana(double probMutacion, int numGenes, int[] longitudes) {
		this.probMutacion = probMutacion;
		this.numGenes = numGenes;
		this.longitudes = longitudes;
	}
	
    public void mutar(Cromosoma[] poblacion) { 
       
    	for (int p = 0; p < poblacion.length ; ++p) {
    		for (int i = 0; i < numGenes; i++) {
            	for (int j = 0; j < longitudes[i]-1; ++j) {
            		double valor = Math.random();
            		if (valor < this.probMutacion) {
            			if (poblacion[p].getCromosoma().get(i).getAlelo()[j])
            				poblacion[p].getCromosoma().get(i).setAleloExct(false, j);
            			else
            				poblacion[p].getCromosoma().get(i).setAleloExct(true, j);
                	}
            	}
            }
    	}
    }
}
