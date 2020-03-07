package gen;

public class GenBooleano {
private boolean[] alelo;
	
	public GenBooleano() {
		this.alelo = null;
	}
	
	public GenBooleano(boolean[] alelo) {
		this.setAlelo(alelo);
	}

	public boolean[] getAlelo() {
		return alelo;
	}

	public void setAlelo(boolean[] alelo) {
		this.alelo = alelo;
	}

	
	public void setAleloExct(boolean alelo, int pos) {
		this.alelo[pos] = alelo;
	}
}
