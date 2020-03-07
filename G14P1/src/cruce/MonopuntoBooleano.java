package cruce;
import java.util.ArrayList;

import cromosoma.Cromosoma;
import gen.GenBooleano;
import utils.TipoFuncion;

public class MonopuntoBooleano {
	double probCruce;
	int tamPoblacion;
	Cromosoma[] poblacion;
	
	public MonopuntoBooleano(double probCruce, int tamPoblacion, Cromosoma[] pob) {
		this.probCruce = probCruce;
		this.tamPoblacion = tamPoblacion;
		this.poblacion = pob;
	}
	
	public void cruzar(TipoFuncion funcion) {
		
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
		ArrayList<GenBooleano> infoPadre;
		ArrayList<GenBooleano> infoMadre;
		infoPadre =  padre.getCromosoma();
		infoMadre = madre.getCromosoma();

		boolean aux;
		for(int i = 0; i < longCromosoma; i++) {
			for(int j = puntoCruce ; j < infoMadre.get(i).getAlelo().length; ++j) {
				aux = infoMadre.get(i).getAlelo()[j];
				infoMadre.get(i).setAleloExct(infoPadre.get(i).getAlelo()[j], j);
				infoPadre.get(i).setAleloExct(aux, j);
			}
		}
		padre.setCromosoma(infoPadre);
		madre.setCromosoma(infoMadre);
		
	}
}







