package cruce;

import java.util.ArrayList;

import cromosoma.Cromosoma;
import gen.GenBooleano;
import gen.GenReal;
import utils.TipoFuncion;

public class MonopuntoReal {
	
	double probCruce;
	int tamPoblacion;
	Cromosoma[] poblacion;
	int elit;
	
	public MonopuntoReal(double probCruce, int tamPoblacion, Cromosoma[] pob, int elit) {
		this.probCruce = probCruce;
		this.tamPoblacion = tamPoblacion;
		this.poblacion = pob;
		this.elit = elit;
	}
	
	public void cruzar(TipoFuncion funcion) {
		
		boolean[] progenitores = new boolean[this.tamPoblacion];
		Cromosoma soltero = null;
		//seleccionamos los cromosomas a cruzar.
		for(int i = 0; i < this.tamPoblacion; i++) {
			double aleatorio = Math.random();
			if(aleatorio < this.probCruce && i > elit) { progenitores[i] = true;}
			else progenitores[i] = false;
		}
		
		//de los seleccionados, va haciendo parejas y las cruza.
		for(int i = 0; i < this.tamPoblacion; i++) {
			if(progenitores[i]) {
				if(soltero != null) {
					cruzarGenesMonopunto(soltero, this.poblacion[i], funcion);
					soltero = null;
				}
				else soltero =  this.poblacion[i];
			}
		}
	}

	/**
	 * Cruza dos genes por un punto.
	 */
	private void cruzarGenesMonopunto(Cromosoma padre, Cromosoma madre, TipoFuncion funcion) {
		//obtenemos el punto de cruce
		int longCromosoma = this.poblacion[0].getLongitudCrom();
		int puntoCruce = (int) (Math.random() * longCromosoma) + 1;
		ArrayList<GenReal> infoPadre;
		ArrayList<GenReal> infoMadre;
		infoPadre =  padre.getCromosomab();
		infoMadre = madre.getCromosomab();

		double aux;
		for(int i = 0; i < longCromosoma; i++) {
			for(int j = puntoCruce ; j < infoMadre.get(i).getAlelo().length; ++j) {
				aux = infoMadre.get(i).getAlelo()[j];
				infoMadre.get(i).setAleloExct(infoPadre.get(i).getAlelo()[j], j);
				infoPadre.get(i).setAleloExct(aux, j);
			}
		}
		padre.setCromosomab(infoPadre);
		madre.setCromosomab(infoMadre);
		
	}

}
