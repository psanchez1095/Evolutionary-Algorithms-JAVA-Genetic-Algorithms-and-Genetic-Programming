package cromosoma;

import java.util.ArrayList;
import java.util.Random;
import gen.GenBooleano;

public class CromosomaF3 extends Cromosoma{
	double min;
	double max;
	
	public CromosomaF3(double precision) {
		this.cromosoma = new ArrayList<GenBooleano>(); 
		this.numGenes = 2;
		this.min = -10.0;
		this.max = 10.0;
		this.precision = precision;
		calcularLongitudes();
		for(int i = 0; i < this.numGenes; i++) {
			GenBooleano g = new GenBooleano(generarGenRandom(longitudes[i]));
			this.cromosoma.add(g);		}
		calcularFenotipo();
	}

	@Override
	public void calcularLongitudes() {
		for(int i = 0; i < numGenes; i++) {
			this.longitudes[i] = (int) (Math.log(1 + ((max - min) / precision)) / Math.log(2));
		}
	}

	@Override
	public void calcularFenotipo() {
		double[] fenotipo = new double[2];
		fenotipo[0] = min + bin2dec(this.cromosoma.get(0).getAlelo()) * (max - min) / (Math.pow(2, longitudes[0]) - 1);
		fenotipo[1] = min + bin2dec(this.cromosoma.get(1).getAlelo()) * (max - min) / (Math.pow(2, longitudes[1]) - 1);
		this.fenotipo = fenotipo;
	}

	
	public boolean[] generarGenRandom(int longitud) {
	        Random rand = new Random();
	        boolean[] alelo = new boolean[longitud];
	        for(int i = 0; i < longitud; i++) {
	        	alelo[i] = rand.nextBoolean();
	        }
	        
	        return alelo;
	}

}
