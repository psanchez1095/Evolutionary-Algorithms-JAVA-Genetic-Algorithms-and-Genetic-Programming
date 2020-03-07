package seleccion;

import cromosoma.Cromosoma;
import cromosoma.CromosomaF1;
import cromosoma.CromosomaF2;
import cromosoma.CromosomaF3;
import cromosoma.CromosomaF4;
import utils.TipoFuncion;

public class EstocasticoUniversal {
	Cromosoma[] pob, nuevaPob;
	private int tamPob;
	
	public EstocasticoUniversal(Cromosoma[] pob, int tamPob) {
		this.pob = pob;
		this.nuevaPob = new Cromosoma[tamPob];
		this.tamPob = tamPob;
	}
	

	public void seleccionEstocastico(TipoFuncion tFunc, double prec, int nGenes) {
        double total = 0.0;
        double[] fitnessIndiv = new double[this.tamPob];
        double[] valores = new double[this.tamPob];
        boolean encontrado = false;
        Cromosoma[] generacionNueva = new Cromosoma[this.tamPob];
        
        
        for (int i = 0; i < this.tamPob; i++) {
            //calculamos el total
        	fitnessIndiv[i] = this.pob[i].getFitness();
            total += fitnessIndiv[i];
        }
        
        for (int i = 0; i < this.tamPob; i++) {
            //calculamos los valores de cada individuo
            valores[i] = fitnessIndiv[i] / total;
   
        }
        
        //calculamos los valores acumulados
        for (int i = 1; i < this.tamPob; i++) {
            valores[i] = valores[i] + valores[i - 1];
        }
        //elegimos los individuos
        for (int i = 0; i < this.tamPob; i++) {
            int j = 0;
            double valor = (1.0 * i) / this.tamPob;
            while (!encontrado && j<this.tamPob) {
                if (valor < valores[j]) {
                    generacionNueva[i] = duplicarCromosoma(this.pob[j], tFunc, prec, nGenes);
                    encontrado = true;
                } else {
                    j++;
                }
            }
            if(j > this.tamPob)
            	generacionNueva[i] = duplicarCromosoma(this.pob[j-1],  tFunc, prec, nGenes);
            encontrado = false;
        }
        this.pob = generacionNueva;

	}
	
	private Cromosoma duplicarCromosoma(Cromosoma c, TipoFuncion tipoFuncion, double precision, int numGenes) {
	    Cromosoma nuevo = null;
	    
	    switch(tipoFuncion) {
	    	case F1:
	    		nuevo = new CromosomaF1(precision);
	    		break;
	    	case F2:
	    		nuevo = new CromosomaF2(precision);
	    		break;
	    	case F3:
	    		nuevo = new CromosomaF3(precision);
	    		break;
	    	case F4:
	    		nuevo = new CromosomaF4(precision, numGenes);
	    		break;
	    	default:
	    		nuevo = new CromosomaF1(precision);
	    		break;
	    	}
	    	
	        nuevo.setFitness(c.getFitness());
	        nuevo.setCromosoma(c.getCromosoma());
	        nuevo.setPuntAcumulada(c.getPuntAcumulada());
	        nuevo.getFenotipo();
	                
	    	return nuevo;
	    }

}
