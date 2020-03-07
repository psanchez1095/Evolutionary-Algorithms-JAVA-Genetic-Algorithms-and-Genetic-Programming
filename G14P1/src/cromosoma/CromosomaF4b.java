package cromosoma;

import java.util.ArrayList;
import java.util.Random;
import gen.GenReal;

public class CromosomaF4b extends Cromosoma {

	private double min;
	private double max;
	
	
	
	public CromosomaF4b(double precision, int numparams) {
		 cromosomab  = new ArrayList<GenReal>(); 

		this.numGenes = numparams;
		this.min = 0.0;
		this.max = Math.PI;
		this.precision = precision;
		calcularLongitudes();
		
		for(int i = 0; i < this.numGenes; i++) {
			GenReal g = new GenReal(generarGenRandomb(longitudes[i]));
			this.cromosomab.add(g);			}
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
//		boolean[][] x = new boolean[numGenes][longitudes[0]];
//		
//		for(int i = 0; i < longitudes[0]; i++) { 
//			for(int j = 0; j < numGenes; j++) { 
//				x[j][i] = this.cromosomab.get(i+j*longitudes[0]);
//				} 
//			}
//		
//		
//		for(int i = 0; i < numGenes; i++) {
//			fenotipo[i] = min + bin2dec(x[i]) * (max - min) / (Math.pow(2, longitudes[0]) - 1);
//		}
		double acum = 0;
		for (int i = 0; i < numGenes; i++) {
			for (int j = 0; j < longitudes[i]; ++j) {
				acum += this.cromosomab.get(i).getAlelo()[j];
			}
			fenotipo[i] = (acum/longitudes[i]);
		}

		this.fenotipo = fenotipo;	
	}

	
	public double[] generarGenRandomb(int longitud) {
		 Random rand = new Random();
	        double[] alelo = new double[longitud];
	        for(int i = 0; i < longitud; i++) {
	        	alelo[i] = min + (max - min) * rand.nextDouble();
	        	}
	        
	        return alelo;
	}

	
	public Double generarGenRandom(int longitud) {
		// TODO Auto-generated method stub
		return null;
	}

}


