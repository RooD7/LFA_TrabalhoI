package mainPackage;

import java.util.ArrayList;

public class Minimizacao {

	private boolean boolX;
	private ArrayList<Transicao> pendencia = new ArrayList<>();
	
	
	public static int valorCorrespondente(int linha, int coluna) {
		linha -= 2;
		coluna -= 1;
		
		return funcao(linha) + coluna;
	}	
	
	public static int funcao(int n) {
		return (n*(n+1))/2;
	}
	
	public ArrayList<Transicao> getPendencia() {
		return this.pendencia;
	}
	public void setPendencia(ArrayList<Transicao> pendencia) {
		this.pendencia = pendencia;
	}
	
	public boolean getBoolX() {
		return this.boolX;
	}
	public void setBoolX(boolean x) {
		this.boolX = x;
	}
}
