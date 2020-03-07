package reemplazo;


import java.util.ArrayList;

import cromosoma.Cromosoma;

public class Aleatorio {

	private Cromosoma[] poblacion;
	private ArrayList<Cromosoma> reemplazos;

	public Aleatorio(Cromosoma[] poblacion, ArrayList<Cromosoma> reemplazos){
		this.poblacion = poblacion;
		this.reemplazos = reemplazos;
	}
	
	public void reemplazar() {
		int rand;
		for(int i = this.reemplazos.size()-1; i >= 0 ; i--) {
			rand = (int) (Math.random() * this.poblacion.length);
			
			this.poblacion[rand].setCromosoma(this.reemplazos.get(i).getCromosoma());
		}
	}
}
