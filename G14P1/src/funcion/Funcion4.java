package funcion;

public class Funcion4 {
	
	public static double evalua(double[] x) {
		
		/*double suma = 0.0;
		
		for(int i = 0 ; i < x.length; i++) {
			suma += Math.sin(x[i]) * Math.pow(Math.sin(((i + 2) * Math.pow(x[i], 2))/Math.PI), 20);
		}
		return -suma;*/
		
		double resultado=0;
		double sen1, sen2, valor, fit;
		
		for(int i=0; i < x.length; i++) {
			
			sen1=Math.sin((x[i]));
			valor= ((i+1)*Math.pow((x[i]), 2))/Math.PI;
			sen2=Math.pow(Math.sin(valor), 20);
			resultado+=sen1*sen2;
			
		}
		
		return -resultado;
		
	}
	
}
