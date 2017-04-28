package mainPackage;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;



public class AFD {
	
	private ArrayList<Integer> estad;	//Estados
	private ArrayList<Character> alfa;	//Afabeto
	private ArrayList<Transicao> trans;	//Transicoes
	private int Inicial;	//Estado Inicial
	private ArrayList<Integer> EstadoFinal;	//Estados Finais
	
	public AFD() {
		this.estad = new ArrayList<>();
		this.alfa = new ArrayList<>();
		this.trans = new ArrayList<>();
		this.Inicial = -1;
		this.EstadoFinal = new ArrayList<>();
	}

	public void Load(String nomeArq) throws JDOMException, IOException {
		File f = new File(nomeArq);
        if (!f.exists())
        	System.out.println("FAIL!");
        
		SAXBuilder builder = new SAXBuilder();
		     
		Document doc = builder.build(f);
		             
		// Pega Automaton
		Element root = (Element) doc.getRootElement();
		
		System.out.println("...: "+root.getChildTextNormalize("type"));
		// Pega Lista de States
		List states = root.getChildren("automaton");
		             
		
		Iterator i = states.iterator();
		             
		while( i.hasNext() ){
		        Element state = (Element) i.next();
		        System.out.println("Estado 1: " + state.getChildText("type"));
		        System.out.println("Estado 1: " + state.getChildTextNormalize("name"));
		        System.out.println("ID: " + state.getChildText("id"));
		        System.out.println("ID: " + state.getChildTextNormalize("id"));
		        System.out.println("ID: " + state.getChildText("from"));
		        System.out.println("ID: " + state.getChildText("to"));
		        System.out.println("X: " + state.getChildText("x"));
		        System.out.println("Y: " + state.getChildTextNormalize("y"));
		        System.out.println("Y: " + state.getAttributeValue("transition"));
		        System.out.println();
		}
	}
	
	public void Salve(String nomeArq) {
		
	}
	
	//Estados
	public ArrayList<Integer> getEstad() {
		return estad;
	}
	public void setEstad(ArrayList<Integer> estad) {
		this.estad = estad;
	}

	//Alfabeto
	public ArrayList<Character> getAlfa() {
		return alfa;
	}
	public void setAlfa(ArrayList<Character> alfa) {
		this.alfa = alfa;
	}

	//Transicoes
	public ArrayList<Transicao> getTrans() {
		return trans;
	}
	public void setTrans(ArrayList<Transicao> trans) {
		this.trans = trans;
	}

	//Estado Inicial
	public int getInicial() {
		return Inicial;
	}
	public void setInicial(int inicial) {
		Inicial = inicial;
	}

	//Estado Final
	public ArrayList<Integer> getEstadoFinal() {
		return EstadoFinal;
	}
	public void setEstadoFinal(ArrayList<Integer> estadoFinal) {
		EstadoFinal = estadoFinal;
	}
	
}