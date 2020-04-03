package funcion;


public class Funcion2 {
	public static double evalua(double[] x) {
		double p1 = (Math.sin(x[0])*Math.cos(x[1]));
		double xycuadrado = Math.pow(x[0],2) + Math.pow(x[1], 2);
		double raiz = Math.sqrt(xycuadrado);
		double raizEntrePi = raiz / Math.PI;
		double p2= Math.abs(1-raizEntrePi); 
		double finale =-Math.abs(p1*Math.exp(p2));
		
		return finale;
	}
}
