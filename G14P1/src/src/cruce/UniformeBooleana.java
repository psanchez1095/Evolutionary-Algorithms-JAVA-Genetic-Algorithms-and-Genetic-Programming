package cruce;

import java.util.ArrayList;

import cromosoma.Cromosoma;
import gen.GenBooleano;

public class UniformeBooleana {
	double probCruce, probUnif;
	int tamPoblacion;
	Cromosoma[] poblacion;
	int elit;
	
	public UniformeBooleana(double probCruce, double probUnif, int tamPoblacion, Cromosoma[] pob, int elit) {
		this.probCruce = probCruce;
		this.probUnif = probUnif;
		this.tamPoblacion = tamPoblacion;
		this.poblacion = pob;
		this.elit = elit;
	}
	
	public void cruzar() {
		
		boolean[] progenitores = new boolean[this.tamPoblacion];
		Cromosoma soltero = null;
		//seleccionamos las cromosomas a cruzar
		for(int i = 0; i < this.tamPoblacion; i++) {
			double aleatorio = Math.random();
			if(aleatorio < this.probCruce && i > elit) { progenitores[i] = true;}
			else progenitores[i] = false;
		}
		
		
		//de los seleccionados, va haciendo parejas y las cruza.
		for(int i = 0; i < this.tamPoblacion; i++) {
			if(progenitores[i]) {
				if(soltero != null) {
					cruzarGenesUnif(soltero, this.poblacion[i]);
					soltero = null;
				}
				else soltero =  this.poblacion[i];
			}
		}		
	}
	
	
	/**
	 * Cruza dos genes por 2 puntos
	 */
	private void cruzarGenesUnif(Cromosoma padre, Cromosoma madre) {
		int longCromosoma = this.poblacion[0].getLongitudCrom();
	
		ArrayList<GenBooleano> infoPadre =  padre.getCromosoma();
		ArrayList<GenBooleano> infoMadre = madre.getCromosoma();
		boolean aux;
		
		
		
		for(int i = 0; i < longCromosoma; i++) {
			for(int j = 0 ; j < infoMadre.get(i).getAlelo().length; ++j) {
				double ale = Math.random();
				if(ale < this.probUnif) {
					aux = infoMadre.get(i).getAlelo()[j];
					infoMadre.get(i).setAleloExct(infoPadre.get(i).getAlelo()[j], j);
					infoPadre.get(i).setAleloExct(aux, j);
				}
			}
		}
		padre.setCromosoma(infoPadre);
		madre.setCromosoma(infoMadre);
	}
}
