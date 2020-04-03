package algoritmoGenetico;

import cruce.*;
import funcion.*;
import gen.GenBooleano;
import mutacion.MutacionBooleana;
import mutacion.MutacionReal;
import cromosoma.Cromosoma;
import reemplazo.Aleatorio;

import java.util.ArrayList;
import java.util.Arrays;

import cromosoma.*;
import seleccion.*;
import utils.*;

public class AlgoritmoGenetico {
	
	Cromosoma[] poblacion; //Poblacion total
	ArrayList<Cromosoma> elite; //Elite seleccionada
	
	//Parametros del algoritmo.
	TipoFuncion funcion;
	public TipoFuncion getFuncion() {
		return funcion;
	}

	TipoSeleccion tipo_seleccion;
	TipoCruce tipo_cruce;
	int tamPoblacion, numGeneraciones, numGenes, generacionActual;
	double probabilidadCruce, probabilidadMutacion, probabilidadUniforme, precision, elitismo;
	boolean booleanElite;
	//Valores para escribir la grafica.
	double[] mediasGeneracion;
	double[] mejoresGeneracion;
	double[] mejoresAbsolutos;

	double mediaGeneracion;
	double mejorGeneracion;
	double mejorAbsoluto;
	
	private Cromosoma elMejor; //Mejor cromosoma encontrado hasta ahora.
	int pos_mejorGeneracion;   //Posicion del mejor cromosoma de la generacion
	
	public AlgoritmoGenetico(TipoFuncion tFun, TipoSeleccion tSel, TipoCruce tCrux,
							 int tPob, int nGeneracs, int nGenes, 
							 double pCruce, double pMutacion, double pUnif, double prec,
							 double elit) {

		this.generacionActual = 0;
		this.funcion = tFun;
		this.tipo_seleccion = tSel;
		this.tipo_cruce = tCrux;
		this.numGeneraciones = nGeneracs;
		this.numGenes = nGenes;
		this.tamPoblacion = tPob;
		this.probabilidadCruce = pCruce;
		this.probabilidadMutacion = pMutacion;
		this.probabilidadUniforme = pUnif;
		this.precision = prec;
		this.elitismo = elit;
		
		this.poblacion = new Cromosoma[tPob];
		this.elite = new ArrayList<Cromosoma>();
		
		this.booleanElite = this.elitismo == 0.0 ? false : true; // false en caso de que sea 0.0 y true en caso contrario
		
		this.mediasGeneracion = new double[nGeneracs];
		this.mejoresGeneracion = new double[nGeneracs];
		this.mejoresAbsolutos = new double[nGeneracs];
		
		
	}
	
	public void inicializaPoblacion() {
		
		switch (funcion) {
		case F1:
			for(int i = 0; i < tamPoblacion; i++) {
				this.poblacion[i] = new CromosomaF1(this.precision);
			} break;
		case F2:
			for(int i = 0; i < tamPoblacion; i++) {
				this.poblacion[i] = new CromosomaF2(this.precision);
			} break;
		case F3:
			for(int i = 0; i < tamPoblacion; i++) {
				this.poblacion[i] = new CromosomaF3(this.precision);
			} break;
		case F4:
			for(int i = 0; i < tamPoblacion; i++) {
				this.poblacion[i] = new CromosomaF4(this.precision, this.numGenes);
			} break;
		case F4b:
			for(int i = 0; i < tamPoblacion; i++) {
				this.poblacion[i] = new CromosomaF4b(this.precision, this.numGenes);
			} break;
		}
		
        this.elMejor = this.duplicarCromosoma(this.poblacion[0]);
        fitness(elMejor);
        this.generacionActual = 0;

	}
	
	public double fitness(Cromosoma cromosoma) {
		double valor = 0.0;
		cromosoma.calcularFenotipo();
		double[] x = cromosoma.getFenotipo();
		switch(this.funcion) {
			case F1: 
				valor = Funcion1.evalua(x);
				break;
			case F2: 
				valor = Funcion2.evalua(x);
				break;
			case F3: 
				valor = Funcion3.evalua(x);
				break;
			case F4:
				valor = Funcion4.evalua(x);
				break;
			case F4b :
				valor = Funcion4.evalua(x);
				break;
			default:
				valor = Funcion1.evalua(x);
				break;
			}
		cromosoma.setFitness(valor);
		return valor;
	}

	public void evaluaPoblacion() {
		double fitness, fitness_best, sum_fitness = 0;
		int pos_fitness_best = 0;
		if (this.funcion == TipoFuncion.F1 ) {
			fitness_best = 0;
		}else {
			fitness_best = 100;
		}
		
		
		for(int i = 0; i < this.poblacion.length-1; i++) {
			fitness = fitness(this.poblacion[i]);

			if((this.funcion == TipoFuncion.F1 && fitness >= fitness_best) || 
			   (this.funcion != TipoFuncion.F1 && fitness < fitness_best))  
			{
				fitness_best = fitness; pos_fitness_best = i; }
			sum_fitness += fitness;
		}
		double puntuacion = 0, puntuacion_acu = 0;
		
		for(int i = 0; i < this.tamPoblacion; i++) {
			puntuacion = fitness(this.poblacion[i]) / sum_fitness;
			puntuacion_acu += puntuacion;
			this.poblacion[i].setPuntuacion(puntuacion);
			this.poblacion[i].setPuntAcumulada(puntuacion_acu);
		}
		
		
		if(this.funcion == TipoFuncion.F1) {
			if(elMejor.getFitness() < this.poblacion[pos_fitness_best].getFitness()) {
			elMejor = this.duplicarCromosoma(this.poblacion[pos_fitness_best]); 
			} 
		} 
		else if (elMejor.getFitness() > this.poblacion[pos_fitness_best].getFitness()) {
			elMejor = this.duplicarCromosoma(this.poblacion[pos_fitness_best]); 
		}
		
		
		this.mediasGeneracion[this.generacionActual] = sum_fitness / this.tamPoblacion;
		this.mejoresGeneracion[this.generacionActual] = fitness_best;
		this.mejoresAbsolutos[this.generacionActual] = this.elMejor.getFitness();
		
	}
	
	public void seleccionaPoblacion() {
		
		switch(tipo_seleccion) {
		case ESTOCASTICO: 
			EstocasticoUniversal estocastUniv = new EstocasticoUniversal(this.poblacion, this.tamPoblacion);
			estocastUniv.seleccionEstocastico(this.funcion, this.precision, this.numGenes);
			break;
		case RULETA: 
			Ruleta ruleta = new Ruleta(this.poblacion, this.tamPoblacion);
			ruleta.seleccionRuleta();
			break;
		case TORNEO:
			Torneos torneos = new Torneos(this.poblacion, this.tamPoblacion);
			torneos.seleccionTorneos(this.funcion, this.precision, this.numGenes);
			break;	
		default:
			Ruleta r = new Ruleta(this.poblacion, this.tamPoblacion);
			r.seleccionRuleta();
			break;
		}
	}
	
	/**
	 * Cruza y muta la poblacion en funcion de probabilidadCruce y probabilidadMutacion.
	 */
	public void reproducePoblacion() {
		
		
		
		switch(tipo_cruce) {
		case MONOPUNTO: 
			if(funcion != TipoFuncion.F4b) {
				MonopuntoBooleano mp = new MonopuntoBooleano(this.probabilidadCruce, this.tamPoblacion, this.poblacion, (int)this.elitismo*this.tamPoblacion);
				mp.cruzar(funcion);
			}else {
				MonopuntoReal mp = new MonopuntoReal(this.probabilidadCruce, this.tamPoblacion, this.poblacion, (int)this.elitismo*this.tamPoblacion);
				mp.cruzar(funcion);
			}
			break;
		case UNIFORME:
			if(funcion != TipoFuncion.F4b) {
				UniformeBooleana uni = new UniformeBooleana(this.probabilidadCruce, this.probabilidadUniforme, this.tamPoblacion, this.poblacion, (int)this.elitismo*this.tamPoblacion);
				uni.cruzar();
			}else {
				UniformeReal uni = new UniformeReal(this.probabilidadCruce, this.probabilidadUniforme, this.tamPoblacion, this.poblacion, (int)this.elitismo*this.tamPoblacion);
				uni.cruzar();
			}
			break;
		case ARITMETICO:
			if(funcion == TipoFuncion.F4b) {
				AritmeticoReal arit = new AritmeticoReal(this.probabilidadCruce, this.tamPoblacion,
						this.poblacion, (int)this.elitismo*this.tamPoblacion);
				arit.cruzar();
			}else {
				
			}
			break;
		case BLX:
			if(funcion == TipoFuncion.F4b) {
				BLXReal blx = new BLXReal(this.probabilidadCruce, this.tamPoblacion, this.poblacion, (int)this.elitismo*this.tamPoblacion);
				blx.cruzar();
			}else {
				
			}
			break;
		default:
			MonopuntoBooleano mono = new MonopuntoBooleano(this.probabilidadCruce, this.tamPoblacion, this.poblacion, (int)this.elitismo*this.tamPoblacion);
			mono.cruzar(funcion);
			break;
		}
		
		
		//for(int i = (int)(this.elitismo*this.tamPoblacion); i < this.tamPoblacion; i++ ) {
			if(funcion == TipoFuncion.F4b) {
				for (int p = 0; p < poblacion.length ; ++p) {
		    		for (int i = 0; i < numGenes; i++) {
		            	for (int j = 0; j < this.poblacion[i].getCromosomab().get(i).getAlelo().length-1; ++j) {
		            		double valor = Math.random();
		            		if (valor <= this.probabilidadMutacion) {
		            			poblacion[p].getCromosomab().get(i).setAleloExct(
		            					poblacion[p].getCromosomab().get(i).getAlelo()[j+1], j);
		                	}
		            	}
		            }
		    	}
			}else {
				for (int p = 0; p < poblacion.length ; ++p) {
		    		for (int i = 0; i < numGenes; i++) {
		            	for (int j = 0; j < this.poblacion[i].getLongitudes()[i]-1; ++j) {
		            		double valor = Math.random();
		            		if (valor <= this.probabilidadMutacion) {
		            			if (poblacion[p].getCromosoma().get(i).getAlelo()[j])
		            				poblacion[p].getCromosoma().get(i).setAleloExct(false, j);
		            			else
		            				poblacion[p].getCromosoma().get(i).setAleloExct(true, j);
		                	}
		            	}
		            }
		    	}
				}
		//}		
	}
	
	public void seleccionaElite() {
    	int num_elites = (int) (this.tamPoblacion * this.elitismo);
    	if (num_elites > this.tamPoblacion) num_elites = this.tamPoblacion;
    	this.ordenarPoblacion(0, this.tamPoblacion-1);
    	this.elite.clear();

        if (this.funcion == TipoFuncion.F1) {
            for (int i = 1 ; i <= num_elites; i++)
                    this.elite.add(duplicarCromosoma(this.poblacion[this.tamPoblacion-i]));
        }

        else {
            for (int i = 0 ; i < num_elites; i++)
                    this.elite.add(duplicarCromosoma(this.poblacion[i]));
        }    	
    	
    	
	}
	
	public void incluyeElite() {
		for(int i = 0; i < this.elite.size() ; i++) {		
			this.poblacion[i] = elite.get(i);
		}
	}
	public int getNumElites() {
		
			return (int) Math.ceil(this.getTamPoblacion() * this.elitismo / 100);
		
	}
	
	/**
	 * Devuelve true si se cumple la condicion de
	 * terminacion del bucle; false en c/c.
	 * @param generacion_actual
	 * @return
	 */
	boolean terminado(int generacion_actual) {
		return generacion_actual >= this.numGeneraciones;
	}
	
	
    /**
     * Ordena de menor a mayor fitness
     * @param poblacion
     * @param izq
     * @param der
     */
    private void ordenarPoblacion(int izq, int der) {
    	 
        int i = izq;
        int j = der;
        
        Cromosoma pivote = this.poblacion[(i+j)/2];
        
        do {
            while (this.poblacion[i].getFitness() < pivote.getFitness()){
                i++;
            }
            while (this.poblacion[j].getFitness() > pivote.getFitness()){
                j--;
            }
            if (i<=j){
                Cromosoma aux = duplicarCromosoma(this.poblacion[i]);
                this.poblacion[i] = duplicarCromosoma(this.poblacion[j]);
                this.poblacion[j] = duplicarCromosoma(aux);
                i++;
                j--;
            }
        }while(i<=j);
        if (izq<j){
            ordenarPoblacion(izq, j);
        }
        if (i<der){
            ordenarPoblacion(i, der);
        }
    }

	
    public boolean getBooleanElite() {
    	return this.booleanElite;
    }
    
    public int getTamPoblacion() {
    	return this.tamPoblacion;
    }
    
    public int getNumGeneraciones() {
    	return this.numGeneraciones;
    }
    
    public double getProbabilidadCruce() {
    	return this.probabilidadCruce;
    }
    
    public double getProbabilidadMutacion() {
    	return this.probabilidadMutacion;
    }
    
    public double getPrecision() {
    	return this.precision;
    }

	public double[] getMejoresAbsolutos() {
		return this.mejoresAbsolutos;
	}

	public double[] getMejoresGeneracion() {
		return this.mejoresGeneracion;
	}

	public double[] getMedias() {
		return this.mediasGeneracion;
	}

	public Cromosoma getMejor() {
		return duplicarCromosoma(this.elMejor);
	}

	public void aumentaGeneracion() {
		this.generacionActual++;
	}
	
	private Cromosoma duplicarCromosoma(Cromosoma c) {
	    Cromosoma nuevo = null ;
	    
	    switch(this.funcion) {
	    	case F1:
	    		nuevo = new CromosomaF1(this.precision);
	    		break;
	    	case F2:
	    		nuevo = new CromosomaF2(this.precision);
	    		break;
	    	case F3:
	    		nuevo = new CromosomaF3(this.precision);
	    		break;
	    	case F4:
	    		nuevo = new CromosomaF4(this.precision, this.numGenes);
	    		break;
	    	case F4b:
	    		nuevo = new CromosomaF4b(this.precision, this.numGenes);
	    		break;
	    			
	    	default:
	    		nuevo = new CromosomaF1(this.precision);
	    		break;
	    	}
	    	
	        nuevo.setFitness(c.getFitness());
	        nuevo.setCromosoma(c.getCromosoma());
	        nuevo.setPuntAcumulada(c.getPuntAcumulada());
	        nuevo.getFenotipo();
	                
	    	return nuevo;
	    }
	
		
		public void incluyeElite2(Cromosoma[] elite) {
			
			// Buscamos los peores
			double[] aptitudes = new double[this.tamPoblacion];
			
			// Actualizamos el array de aptitudes con el fitness de cada individuo;
			for (int i = 0; i < aptitudes.length; i++) {
				aptitudes[i] = this.poblacion[i].getFitness();
			}
			
			// Ordenador de mayor a menor
			Arrays.sort(aptitudes);

			// Para las funciones donde se tenga que maximizar el valor
			if (this.getFuncion() == TipoFuncion.F1){
				for (int i = 0; i < this.getNumElites(); i++) {
					boolean encontrado = false;
					for (int j = 0; j < this.getTamPoblacion(); j++) {
						if (!encontrado && aptitudes[i] == this.poblacion[j].getFitness()) {
							this.poblacion[j] = elite[i];
							encontrado = true;
						}
					}
				}
			}
			// Para las funciones donde se tenga que minimizar el valor
			else {
				for (int i = 0; i < this.getNumElites(); i++) {
					boolean encontrado = false;
					for (int j = 0; j < this.getTamPoblacion(); j++) {
						if (!encontrado && aptitudes[aptitudes.length - 1 - i] == this.poblacion[j].getFitness()) {
							this.poblacion[j] = elite[i];
							encontrado = true;
						}
					}
				}
			}
			
			
		}



}
