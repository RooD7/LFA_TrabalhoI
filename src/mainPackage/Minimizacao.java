package mainPackage;

import java.util.ArrayList;

public class Minimizacao {

	private boolean x;
	private ArrayList<Integer> pendencia = new ArrayList<>();
	
	
	public static int valorCorrespondente(int linha, int coluna) {		
		return (((linha-2) + (linha+1)) / 2) + (coluna-1);
	}
	
	
	
	
	public ArrayList<Integer> getPendencia() {
		return this.pendencia;
	}
	public void setPendencia(ArrayList<Integer> pendencia) {
		this.pendencia = pendencia;
	}
	
	public boolean getX() {
		return this.x;
	}
	public void setX(boolean x) {
		this.x = x;
	}
}
