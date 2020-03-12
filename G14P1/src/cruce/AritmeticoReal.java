package cruce;

import java.util.ArrayList;

import cromosoma.Cromosoma;
import gen.GenBooleano;
import gen.GenReal;

public class AritmeticoReal {

	double probCruce;
	int tamPoblacion;
	Cromosoma[] poblacion;
	int elit;
	
	public AritmeticoReal(double probCruce, int tamPoblacion, Cromosoma[] pob, int elit) {
		this.probCruce = probCruce;
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
	
		ArrayList<GenReal> infoPadre =  padre.getCromosomab();
		ArrayList<GenReal> infoMadre = madre.getCromosomab();
		double aux;
		
		
		
		for(int i = 0; i < longCromosoma; i++) {
			for(int j = 0 ; j < infoMadre.get(i).getAlelo().length; ++j) {

					aux = infoMadre.get(i).getAlelo()[j];
					infoMadre.get(i).setAleloExct((infoPadre.get(i).getAlelo()[j]+infoMadre.get(i).getAlelo()[j])/2, j);
					infoPadre.get(i).setAleloExct((aux+infoPadre.get(i).getAlelo()[j])/2, j);
				}
			}
		padre.setCromosomab(infoPadre);
		madre.setCromosomab(infoMadre);
		}
		
}
	

