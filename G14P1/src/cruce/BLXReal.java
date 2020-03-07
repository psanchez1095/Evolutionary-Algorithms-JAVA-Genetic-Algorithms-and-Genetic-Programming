package cruce;

import java.util.ArrayList;
import java.util.Random;

import cromosoma.Cromosoma;
import gen.GenReal;
import utils.TipoFuncion;

public class BLXReal {

	double probCruce;
	int tamPoblacion;
	Cromosoma[] poblacion;
	
	public BLXReal(double probCruce, int tamPoblacion, Cromosoma[] pob) {
		this.probCruce = probCruce;
		this.tamPoblacion = tamPoblacion;
		this.poblacion = pob;
	}
	
	public void cruzar() {
		
		boolean[] progenitores = new boolean[this.tamPoblacion];
		Cromosoma soltero = null;
		//seleccionamos los cromosomas a cruzar.
		for(int i = 0; i < this.tamPoblacion; i++) {
			double aleatorio = Math.random();
			if(aleatorio < this.probCruce) { progenitores[i] = true;}
			else progenitores[i] = false;
		}
		
		//de los seleccionados, va haciendo parejas y las cruza.
		for(int i = 0; i < this.tamPoblacion; i++) {
			if(progenitores[i]) {
				if(soltero != null) {
					cruzarGenesBLX(soltero, this.poblacion[i]);
					soltero = null;
				}
				else soltero =  this.poblacion[i];
			}
		}
	}

	/**
	 * Cruza dos genes por un punto.
	 */
	private void cruzarGenesBLX(Cromosoma padre, Cromosoma madre) {
		//obtenemos el punto de cruce
		int longCromosoma = this.poblacion[0].getLongitudCrom();
		int puntoCruce = (int) (Math.random() * longCromosoma) + 1;
		double cmax, cmin, x ,maxrange, minrange, rng1, rng2;
		ArrayList<GenReal> infoPadre;
		ArrayList<GenReal> infoMadre;
		infoPadre =  padre.getCromosomab();
		infoMadre = madre.getCromosomab();
		Random randomGenerator = new Random();
		double aux;
		for(int i = 0; i < longCromosoma; i++) {
			for(int j = 0 ; j < infoMadre.get(i).getAlelo().length; ++j) {
				
				cmax = Math.max(infoMadre.get(i).getAlelo()[j], infoPadre.get(i).getAlelo()[j]);
				cmin = Math.min(infoMadre.get(i).getAlelo()[j], infoPadre.get(i).getAlelo()[j]);
				x = cmax-cmin;
				if(probCruce > 1) probCruce = 1;
				minrange = cmin-x*probCruce;
				maxrange = cmin+x*probCruce;
				rng1 = minrange + (maxrange - minrange) * randomGenerator.nextDouble();				
				rng2 = minrange + (maxrange - minrange) * randomGenerator.nextDouble();
				infoMadre.get(i).getAlelo()[j] = rng1;
				infoPadre.get(i).getAlelo()[j] = rng2;
			}
		}
		padre.setCromosomab(infoPadre);
		madre.setCromosomab(infoMadre);
		
	}

	
}
