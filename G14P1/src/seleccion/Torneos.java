package seleccion;

import cromosoma.Cromosoma;
import cromosoma.CromosomaF1;
import cromosoma.CromosomaF2;
import cromosoma.CromosomaF3;
import cromosoma.CromosomaF4;
import utils.TipoFuncion;

public class Torneos {
	private Cromosoma[] pob, nuevaPob;
	private int tamPob;
	
	public Torneos (Cromosoma[] pob, int tamPob) {
		this.pob = pob;
		this.nuevaPob = new Cromosoma[tamPob];
		this.tamPob = tamPob;
	}
	

	public void seleccionTorneos(TipoFuncion tFunc, double prec, int nGenes) {
        int posicionElegido = -1;
        double [] fitnessIndiv = new double[tamPob];
        for (int i = 0; i < this.tamPob; i++) {
            //calculamos el total
        	fitnessIndiv[i] = this.pob[i].getFitness();
        }
        
        for (int i = 0; i < this.tamPob; i++) {
        	int aleatorio = (int) (Math.random() * this.tamPob);
        	double mejor = fitnessIndiv[aleatorio];
        	posicionElegido = aleatorio;
                
            for (int j = 1; j < tamPob; j++) {
                aleatorio = (int) (Math.random() * this.tamPob);
                if (compararFitness(fitnessIndiv[aleatorio], mejor, tFunc) != mejor) {
                    mejor = fitnessIndiv[aleatorio];
                    posicionElegido = aleatorio;
                }
            }
            nuevaPob[i] = duplicarCromosoma(this.pob[posicionElegido], tFunc, prec, nGenes);
        }
        this.pob = nuevaPob;

	}
	
	
	
	private double compararFitness(double uno, double otro, TipoFuncion tFunc) {
		if(tFunc == TipoFuncion.F1) {
			if(uno > otro) return uno;
			else return otro;
		} else if(uno < otro) return uno;
			   else return otro;
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
