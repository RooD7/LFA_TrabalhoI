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
	
	private ArrayList<Integer> estado;	//Estados
	private ArrayList<Character> alfa;	//Afabeto
	private ArrayList<Transicao> trans;	//Transicoes
	private int estadoInicial;	//Estado Inicial
	private ArrayList<Integer> estadoFinal;	//Estados Finais
	
	public AFD() {
		this.estado = new ArrayList<>();
		this.alfa = new ArrayList<>();
		this.trans = new ArrayList<>();
		this.estadoInicial = -1;
		this.estadoFinal = new ArrayList<>();
	}

	public void Load(String nomeArq) throws JDOMException, IOException {
		File f = new File(nomeArq);
        if (!f.exists()){
        	System.out.println("Arquivo "+nomeArq+" não existe!");
        }
        
		SAXBuilder builder = new SAXBuilder();
		     
		Document doc = builder.build(f);
		             
		// Pega Automaton
		Element root = (Element) doc.getRootElement();
		
		//Automato nao eh AFD
		if(!root.getChildTextNormalize("type").equals("fa")) {
			System.out.println("Automato não é do tipo FA");
			System.exit(0);
		}
		
		// Pega Lista de States
		List states = root.getChild("automaton").getChildren("state");
		//Pega Lista de Transition
		List trst   = root.getChild("automaton").getChildren("transition");
		             
		Iterator i = states.iterator();
		while( i.hasNext() ){
			
	        Element state = (Element) i.next();
	        this.estado.add(Integer.parseInt(state.getAttributeValue("id")));
	        System.out.println("State id: " + state.getAttributeValue("id"));
	        System.out.println("State x: " + state.getChildText("x"));
	        System.out.println("State y: " + state.getChildText("y"));
	        System.out.println();
		}
		
		String s;
		Transicao t = new Transicao();
		Iterator j = trst.iterator();
		while( j.hasNext() ){
			
	        Element trs = (Element) j.next();
	        t.setFrom(Integer.parseInt(trs.getChildText("from")));
	        t.setTo(Integer.parseInt(trs.getChildText("to")));
	        //String to Char
	        s = (trs.getChildText("read"));
	        t.setValue(s.charAt(0));

	        System.out.println("To: " + trs.getChildText("to"));
	        System.out.println("From: " + trs.getChildText("from"));
	        System.out.println("Read: " + trs.getChildText("read"));
	        System.out.println();

	        trans.add(t);
		}
	}
	
	public void Salve(String nomeArq) {
		
	}
	
	//Estados
	public ArrayList<Integer> getEstad() {
		return estado;
	}
	public void setEstad(ArrayList<Integer> estado) {
		this.estado = estado;
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
	public int getEstadoInicial() {
		return estadoInicial;
	}
	public void setEstadoInicial(int estadoInicial) {
		this.estadoInicial = estadoInicial;
	}

	//Estado Final
	public ArrayList<Integer> getEstadoFinal() {
		return estadoFinal;
	}
	public void setEstadoFinal(ArrayList<Integer> estadoFinal) {
		this.estadoFinal = estadoFinal;
	}
	
}