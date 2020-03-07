package gen;

public class GenReal {

private double[] alelo;
	
	public GenReal() {
		this.alelo = null;
	}
	
	public GenReal(double[] alelo) {
		this.setAlelo(alelo);
	}

	public double[] getAlelo() {
		return alelo;
	}

	public void setAlelo(double[] alelo) {
		this.alelo = alelo;
	}
	

	public void setAleloExct(double alelo, int pos) {
		this.alelo[pos] = alelo;
	}
}
