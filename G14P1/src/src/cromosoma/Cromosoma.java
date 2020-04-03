package cromosoma;

import java.util.ArrayList;
import java.util.Random;

import gen.GenBooleano;
import gen.GenReal;

public abstract class Cromosoma implements Comparable<Cromosoma>{
	
	ArrayList<GenBooleano> cromosoma; 
	ArrayList<GenReal> cromosomab;
    public ArrayList<GenReal> getCromosomab() {
		return cromosomab;
	}

	public void setCromosomab(ArrayList<GenReal> cromosomab) {
		this.cromosomab = cromosomab;
	}

	int[] longitudes; //array de longitudes de cada gen del cromosoma
    double[] fenotipo;
    double fitness;
    double puntuacion;
    double punt_Acumulada;
    int numGenes;
    double precision;
	

    public Cromosoma() {
    	this.longitudes = new int[7];
    	this.fitness = 0.0;
    	this.puntuacion = 0.0;
    	this.punt_Acumulada = 0.0;
    }
    
    public Cromosoma(ArrayList<GenBooleano> cromosoma, double[] fenotipo, int[] longitudes, int numgenes) {
    	this.cromosoma = cromosoma;
    	this.fenotipo = fenotipo;
    	this.numGenes = numgenes;
    	this.longitudes = longitudes;
    	this.fitness = 0.0;
    	this.puntuacion = 0.0;
    	this.punt_Acumulada = 0.0;
    }
    
    
    
    
    public ArrayList<GenBooleano> getCromosoma(){
    	return this.cromosoma;
    } // Intentar hacer uno general para cromosomas
     
    public void setCromosoma(ArrayList<GenBooleano> cromosoma) {
    	this.cromosoma = cromosoma;
    }

    public double[] getFenotipo() {
        return this.fenotipo;
    }
    
    public void setFenotipo(double[] fenotipo) {
    	this.fenotipo = fenotipo;
    }
    
    public double getFitness() {
        return this.fitness;
    }
    
    public void setFitness(double fitness) {
        this.fitness = fitness;
    }

    public double getPuntuacion() {
        return this.puntuacion;
    }
    
    public void setPuntuacion(double puntuacion) {
    	this.puntuacion = puntuacion;
    }
    
    public double getPuntAcumulada() {
        return this.punt_Acumulada;
    }
    
    public void setPuntAcumulada(double punt_Acumulada) {
        this.punt_Acumulada = punt_Acumulada;
    }
    
    public int[] getLongitudes() {
        return this.longitudes;
    }
    
    public void setLongitudes(int[] longitudes) {
    	this.longitudes = longitudes;
    }
    
    public int getNumGenes() {
        return this.numGenes;
    }
    
    public void setNumGenes(int numGenes) {
    	this.numGenes = numGenes;
    }

    public double getTolerancia() {
    	return this.precision;
    }
    
    public void setTolerancia(double precision) {
    	this.precision = precision;
    }
    
    public int getLongitudCrom() {
    	return this.numGenes;
    }

    public double bin2dec(boolean[] genotipo) { // Cambia una cadena de booleanos de binario a decimal
        double total = 0;
        int j = 0;
        int bool;
        for (int i = genotipo.length - 1; i >= 0; i--) {
            if (genotipo[i]) 
                bool = 1;
            else 
                bool = 0;
            total += bool * Math.pow(2, j);
            j++;
        }
        return total;
    }
    
    
    
    //creo que esta mal   ?????????
	public double bin2float(boolean[] genotipo) { // // Cambia una cadena de booleanos de binario a decimal
		String geno = "";
		for(int i = 0; i < genotipo.length; i++) 
			geno += genotipo[i];
		
		int intBits = Integer.parseInt(geno, 2);
		float myFloat = Float.intBitsToFloat(intBits);
		return myFloat;
		//Integer.toBinaryString(i)
	}

    public int compareTo(Cromosoma cromosoma) {
    	return (int) (this.getFitness() - cromosoma.getFitness());
    }

    public abstract void calcularLongitudes();
    public abstract void calcularFenotipo();

	
	

}

