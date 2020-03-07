package cromosoma;

import java.util.ArrayList;
import java.util.Random;

import gen.GenBooleano;

public class CromosomaF2 extends Cromosoma{

    double x1_min;
    double x1_max;
    double x2_min;
    double x2_max;
    public CromosomaF2(double precision) {
    	this.cromosoma = new ArrayList<GenBooleano>(); 
    	this.x1_min = -10;
		this.x1_max = 10;
		this.x2_min = -10;
		this.x2_max = 10;
        super.numGenes = 2;
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
		this.longitudes[0] = (int) (Math.log(1 + ((x1_max - x1_min) / precision)) / Math.log(2));
		this.longitudes[1] = (int) (Math.log(1 + ((x2_max - x2_min) / precision)) / Math.log(2));	
	}

	@Override
	public void calcularFenotipo() {
		double[] fenotipo = new double[2];
		fenotipo[0] = x1_min + bin2dec(this.cromosoma, 0) * (x1_max - x1_min) / (Math.pow(2, longitudes[0]) - 1);
		fenotipo[1] = x2_min + bin2dec(this.cromosoma, 1) * (x2_max - x2_min) / (Math.pow(2, longitudes[1]) - 1);
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


	 public double bin2dec(ArrayList<GenBooleano> genotipo, int k) { // Cambia una cadena de booleanos de binario a decimal
	        double total = 0;
	        int j = 0;
	        int bool;
	        for (int i = longitudes[k] - 1; i >= 0; i--) {
	            if (genotipo.get(k).getAlelo()[i]) 
	                bool = 1;
	            else 
	                bool = 0;
	            total += bool * Math.pow(2, j);
	            j++;
	        }
	        return total;
	    }

}
