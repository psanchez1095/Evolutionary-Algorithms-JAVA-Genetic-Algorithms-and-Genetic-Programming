package funcion;

public class Funcion1 {
	public static double evalua(double[] x) {
		return 21.5 + x[0] * (Math.sin(4 * Math.PI * x[0])) + x[1] * (Math.sin(20 * Math.PI * x[1]));
	}
}
