package mainPackage;

public class Transicao {

	private int from;
	private int to;
	private char value;

	public Transicao() {
		this.from = -1;
		this.to = -1;
		this.value = '.';
	}
	
	//From
	public int getFrom() {
		return this.from;
	}
	public void setFrom(int from) {
		this.from = from;
	}
	
	//To
	public int getTo() {
		return this.to;
	}
	public void setTo(int to) {
		this.to = to;
	}
	
	//Value
	public char getValue() {
		return this.value;
	}
	public void setValue(char value) {
		this.value = value;
	}
}
