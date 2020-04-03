package cromosoma;

import java.util.ArrayList;
import java.util.Random;
import gen.GenBooleano;

public class CromosomaF4 extends Cromosoma {

	private double min;
	private double max;

	public CromosomaF4(double precision, int numparams) {
		this.cromosoma = new ArrayList<GenBooleano>(); 

		this.numGenes = numparams;
		this.min = 0.0;
		this.max = Math.PI;
		this.precision = precision;
		calcularLongitudes();
		
		for(int i = 0; i < this.numGenes; i++) {
			GenBooleano g = new GenBooleano(generarGenRandom(longitudes[i]));
			this.cromosoma.add(g);		
			}
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
		double[] fenotipo = new double[numGenes];
		boolean[][] x = new boolean[numGenes][Math.min(longitudes[0],longitudes[1])];
		
		for(int i = 0; i < numGenes; i++) { 
			for(int j = 0; j < longitudes[i]; j++) { 
				
				x[i][j] = this.cromosoma.get(i).getAlelo()[j]; 
				} 
			}
		
		
		
		
		for(int i = 0; i < numGenes; i++) {
			fenotipo[i] = min + bin2dec(x[i]) * (max - min) / (Math.pow(2, longitudes[0]) - 1);
		}

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
