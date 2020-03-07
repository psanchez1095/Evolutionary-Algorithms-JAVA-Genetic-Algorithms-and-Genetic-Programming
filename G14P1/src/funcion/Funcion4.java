package funcion;

public class Funcion4 {
	
	public static double evalua(double[] x) {
		double suma = 0.0;
		
		for(int i = 0 ; i < x.length; i++) {
			suma += Math.sin(x[i]) * Math.pow(Math.sin(((i + 1) * Math.pow(x[i], 2))/Math.PI), 20);
		}
		return -suma;
	}
}
