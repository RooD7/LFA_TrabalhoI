package mainPackage;

import java.io.IOException;

import org.jdom2.JDOMException;

public class Main {

	public static void main(String[] args) throws JDOMException, IOException {
		// TODO Auto-generated method stub
		AFD m1 = new AFD();
		AFD m1C = new AFD();
		AFD m2 = new AFD();
		AFD m3 = new AFD();
		AFD mm = new AFD();

		// load
		m1.Load("entrada.jff");
		m2.Load("entrada2.jff");
		// complement
		m1C = m1.complement();
		
		// equivalents
		if (AFD.equivalents(m1, m2)) 
			System.out.println("Equivalentes!");
		else
			System.out.println("Não equivalentes!");
		
		// Apagar
		for (Integer e : m1.getEstado()) {
			System.out.println("Estados: "+e.intValue());
		}
		// addState, addTransition, deleteState, deleteTransition
		//m2.addState(5, false, false);
		//m2.addTransition(2, 2, 'b');
		//m2.deleteState(2);
		//m2.deleteState(3);
		//m2.deleteTransition(0, 1, 'a');
		
		// Accept
		if(m1.accept("abaab"))
			System.out.println("Aceita!");
		else
			System.out.println("Rejeita!");
		
		if(m1.accept("b"))
			System.out.println("Aceita!");
		else
			System.out.println("Rejeita!");
		
		// initial, move, finals
		int estado = m1.initial();
		estado = m1.move(estado, "ab");
		if(m1.finals().contains((Integer)estado))
			System.out.println("Aceita!");
		else
			System.out.println("Rejeita!");
		
		// Apagar
		for (Integer e : m2.getEstado()) {
			System.out.println("Estados: "+e.intValue());
		}
		
		// equivalents
		if (AFD.equivalents(m1, m2)) 
			System.out.println("Equivalentes!");
		else
			System.out.println("Não equivalentes!");
		
		System.out.println("Vai imprimir!");
		m3 = m1.produtoAFD(m1, m2);
		System.out.println("Imprimiu!");
		
		mm = m1.minimum();
		// salve
		m1.Salve("saidaM1.jff");
		m1C.Salve("saidaM1C.jff");
		m2.Salve("saidaM2.jff");
		System.out.println("Vai imprimir!");
		m3.Salve("saidaM3C.jff");
		System.out.println("Imprimiu!");
		
	}

}
