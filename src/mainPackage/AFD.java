package mainPackage;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.jdom2.Attribute;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;
import org.jdom2.output.XMLOutputter;



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

	//Carrega arquivo de entrada
	public void Load(String nomeArq) throws JDOMException, IOException {
		
		File f = new File(nomeArq);
        if (!f.exists()){
        	System.out.println("Arquivo "+nomeArq+" não existe!");
        	System.exit(0);
        }
        
		SAXBuilder builder = new SAXBuilder();
		Document doc = builder.build(f);
		             
		// Pega Structure
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
	        // Adiciona estado na lista de estados
	        this.getEstado().add(Integer.parseInt(state.getAttributeValue("id")));
	        
	        // Adiciona simbolo na lista do alfabeto
	        if(this.getAlfa().contains(state.getAttributeValue("id")))
	        	this.getAlfa().add(state.getAttributeValue("id").charAt(0));
	        
	        /*
	        System.out.println("State id: " + state.getAttributeValue("id"));
	        System.out.println("State x: " + state.getChildText("x"));
	        System.out.println("State y: " + state.getChildText("y"));
	        System.out.println();
	        */
	        
	        if(state.getChild("initial") != null) {
	        	this.setEstadoInicial(Integer.parseInt(state.getAttributeValue("id")));
	        }
	        if(state.getChild("final") != null) {
	        	this.getEstadoFinal().add(Integer.parseInt(state.getAttributeValue("id")));	        
	        }
		}
		
		String s;
		Iterator<?> j = trst.iterator();
		while( j.hasNext() ){
			
			Transicao t = new Transicao();
	        Element trs = (Element) j.next();
	        t.setFrom(Integer.parseInt(trs.getChildText("from")));
	        t.setTo(Integer.parseInt(trs.getChildText("to")));
	        //String to Char
	        s = trs.getChildText("read");
	        t.setValue(s.charAt(0));

	        this.getTrans().add(t);
	        
	        /*System.out.println("From: " + t.getFrom());
	        System.out.println("To: " + t.getTo());
	        System.out.println("Read: " + t.getValue());
	        System.out.println();
	        System.out.println("OK!Trans--------");
	        */
		}
	}
	
	// Carrega arquivo de saida
	public void Salve(String nomeArq) throws IOException {
		Document doc = new Document();
        
		Element root = new Element("structure");
		Element auto = new Element("automaton");
		Element type = new Element("type");
		type.setText("fa");
		root.addContent(type);
		root.addContent(auto);
		         
		double xl = 175.0;
		double yl = 150.0;
		for(Integer e : estado) {
			Element state = new Element("state");
			Attribute id = new Attribute("id",e.toString());
			Attribute name = new Attribute("name","q"+e.toString());
			state.setAttribute(id);
			state.setAttribute(name);

			Element x = new Element("x");
			Element y = new Element("y");
			x.setText(Double.toString(xl));
			y.setText(Double.toString(yl));
			state.addContent(x);
			state.addContent(y);
			
			if(this.getEstadoInicial() == e) {
				Element init = new Element("initial");
				state.addContent(init);
	        }
	        if(this.estadoFinal.contains(e)) {
	        	Element fini = new Element("final");
				state.addContent(fini);
	        }

			auto.addContent(state);
			xl += 75;
			yl += 50;
		}

		//for(Transicao t : trans) {
		Iterator<Transicao> k = trans.iterator();
		while( k.hasNext() ) {
			Transicao t = (Transicao) k.next();
			Element trs = new Element("transition");
			Element from = new Element("from");
			Element to = new Element("to");
			Element read = new Element("read");
			
			/*System.out.println("From: " + t.getFrom());
	        System.out.println("To: " + t.getTo());
	        System.out.println("Read: " + t.getValue());
	        System.out.println();
	        System.out.println("OK!Trans--------");*/
			from.setText(t.getFrom().toString());
			to.setText(t.getTo().toString());
			read.setText(Character.toString(t.getValue()));
			trs.addContent(from);
			trs.addContent(to);
			trs.addContent(read);

			auto.addContent(trs);
		}
         
		doc.setRootElement(root);
		         
		XMLOutputter xout = new XMLOutputter();
		OutputStream out = new FileOutputStream( new File(nomeArq));
		xout.output(doc , out);
	}

	// Adicionar estado
	public void addState(int id, boolean init, boolean fim) {
		this.getEstado().add(id);
		if(init)
			this.setEstadoInicial(id);
		if(fim)
			this.getEstadoFinal().add(id);
	}
	
	// Adicionar transicao
	public void addTransition(int from, int to, char c) {
		Transicao t = new Transicao();
		t.setFrom(from);
		t.setTo(to);
		t.setValue(c);
		this.getTrans().add(t);
	}
	
	// Deletar estado
	public void deleteState(Integer n) {
		
		if(this.getEstado().contains(n)) {
			
			this.getEstado().remove(n);
			
			Transicao tAux = new Transicao();
			Iterator<Transicao> t = this.getTrans().iterator();
			while(t.hasNext()) {
				tAux = (Transicao) t.next();
				if((tAux.getFrom().equals(n)) || (tAux.getTo().equals(n))) {
					System.out.println("From: "+tAux.getFrom()+"\tTo: "+tAux.getTo()+"\tDeletar = "+this.getTrans().indexOf(tAux));
					try {
						t.remove();
					} catch (Exception e){
						System.out.println("Erro:: "+ e.toString());
					}
					//this.getTrans().remove(tAux);
					//tAux = new Transicao();
				}
//				System.out.println("Pooraa");
			}
		}
		else
			System.out.println("Estado Inválido!");
	}
	
	// Deletar Transicao
	public void deleteTransition(int from, int to, char c) {
		
		Transicao tAux = new Transicao();
		Iterator<Transicao> t = getTrans().iterator();
		while(t.hasNext()) {
			tAux = (Transicao) t.next();
			if((tAux.getFrom().equals(from)) && (tAux.getTo().equals(to) && (tAux.getValue() == c))) {
				t.remove();
			}
		}
	}
	
	// equivalencia de automatos -- FALTA
	public static boolean equivalents(AFD m1, AFD m2) {
		
		return true;
	}
	
	// equivalencia de estados -- FALTA
	public ArrayList<Integer> equivalents() {
		ArrayList<Integer> eqv = new ArrayList<>();
		return eqv;
	}
	
	// Minimizacao -- FALTA
	public AFD minimum() {
		AFD mm = new AFD();
		
		System.out.println("Valor corres: "+ Minimizacao.valorCorrespondente(1, 1));
		System.out.println("Valor corres: "+ Minimizacao.valorCorrespondente(2, 2));
		System.out.println("Valor corres: "+ Minimizacao.valorCorrespondente(3, 3));
		
		return mm;
	}
		
	// Complemento de um automato
	public AFD complement() {
		AFD aux = new AFD();
		
		aux.setAlfa(this.getAlfa());
		aux.setEstado(this.getEstado());
		aux.setEstadoInicial(this.getEstadoInicial());
		aux.setTrans(this.getTrans());
		
		for (Integer i : getEstado()) {
			if(!this.getEstadoFinal().contains(i.intValue())) {
				aux.estadoFinal.add(i.intValue());
			}
		}
		return aux;
	}
	
	// Uniao de Automatos	-- FALTA
	public AFD union(AFD m) {
		/*
		 * _ Criar estados do tamanho m1 x m2
		 * _ Salvar num vetor de Transicoes todas as transicoes possiveis
		 * 	 analisando m1 e m2 paralelamente. 
		 * */
		
		
		//if(tamM1 > tamM2)
		AFD aux = new AFD();
		return (aux);
	}
	
	// Produto de Automatos -- FALTA
	public AFD produtoAFD(AFD m1, AFD m2) {
		
		
		AFD auxM = new AFD();
		int tamM1 = m1.getEstado().size();
		int tamM2 = m2.getEstado().size();
		int iniM1 = m1.initial();
		int iniM2 = m2.initial();
		int[][] mapa = new int[tamM1][tamM2];
		
		int k = 0;
		for (int i = 0; i < tamM1; i++) {
			for (int j = 0; j < tamM2; j++) {
				//estado inicial
				if((i == iniM1) && (j == iniM2))
					auxM.addState(k, true, false);
				else
					auxM.addState(k, false, false);
				
				mapa[i][j] = k;
				k++;
			}
		}		
		
		int stateTo;
		Integer toM2, fromM1, toM1, fromM2;
		char simM1, simM2;

		ArrayList<Transicao> t1 = new ArrayList<>();
		for (Integer e1 : m1.getEstado()) {
			for (Integer e2 : m2.getEstado()) {
				// Lista de transicao com o FROM igual a state e1
				
				t1.clear();
				t1 = listaFromState(m1, t1, e1);
				
				// Percorre todas as transicoes
				for (Transicao t : t1) {
					toM1 = t.getTo();
					simM1 = t.getValue();
					toM2 = getToTrans(m2, e2, simM1);
					
					// existe TO de M2, add transicao
					if(toM2 != null) {
						auxM.addTransition(mapa[e1][e2], mapa[toM1][toM2], simM1); 
					}
				}
				
			}
		}
		auxM = RemoveEstadosVazioNulos(auxM);
		
		return auxM;
	}
	
	public ArrayList<Transicao> listaFromState(AFD m, ArrayList<Transicao> t, int state) {
		
		Transicao tAux = new Transicao();
		Iterator<Transicao> t1 = m.getTrans().iterator();
		while(t1.hasNext()) {
			tAux = (Transicao) t1.next();
			if(tAux.getFrom().equals((Integer)state)) {
				t.add(tAux);
			}
		}
		return t;
	}
	
	public AFD RemoveEstadosVazioNulos(AFD m) {
		
		boolean flagNulo = true;
		ArrayList<Transicao> t = m.getTrans();
		Transicao tAux = new Transicao();
		Iterator<Transicao> t1;
		
		// Percorre todas os estados
		for (Integer e1 = 0; e1 < m.getEstado().size(); e1++) {
			// Nao eh o estado Inicial
			if(!e1.equals((Integer)m.getEstadoInicial())) {
				flagNulo = true;
				// Procura as tansicoes TO e1
				t1 = m.getTrans().iterator();
				while(t1.hasNext()) {
					tAux = (Transicao) t1.next();
					// Estado nao eh nulo ou inacessivel
					if(tAux.getTo().equals(e1)) {
						flagNulo = false;
						break;
					}
				}
				// Remover estados nulos e inacessiveis
				if(flagNulo) {
					System.out.println("Estado deletado: "+e1);
					m.deleteState(e1);
				}
			
			}
		}
		return m;
	}
	
	public Integer getToTrans(AFD m1, Integer from, char value) {
		
		Transicao tAux = new Transicao();
		Iterator<Transicao> t = m1.getTrans().iterator();
		while(t.hasNext()) {
			tAux = (Transicao) t.next();
			if(tAux.getFrom().equals((Integer)from) && tAux.getValue() == value) {
				return tAux.getTo();
			}
		}
		return null;
	}
	
	public int getStateTo( AFD m1, int atual, Iterator<Transicao> t) {
		
		Transicao tAux = new Transicao();
		tAux = (Transicao) t.next();
		if(tAux.getFrom().equals((Integer)atual)) {
			System.out.println("Estado To: "+tAux.getTo());
			return tAux.getTo();
		}
		
		return -1;
	}
	
	public char getSimbolo( AFD m1, int from, int to) {
		
		Transicao tAux = new Transicao();
		Iterator<Transicao> t = m1.getTrans().iterator();
		while(t.hasNext()) {
			tAux = (Transicao) t.next();
			if(tAux.getFrom().equals((Integer)from) && tAux.getTo().equals((Integer)to)) {
				System.out.println("Estado Char: "+tAux.getValue());
				return tAux.getValue();
			}
		}
		return ' ';
	}
	
	// Percorre 1 estado do Automato
	public int percorreUmEstado(int atual, char c) {
		
		// Percorrer todas as transicoes
		Transicao tAux = new Transicao();
		Iterator<Transicao> t = getTrans().iterator();
		while(t.hasNext()) {
			tAux = (Transicao) t.next();
			if((tAux.getFrom().equals(atual)) && (tAux.getValue() == c)) {
				return tAux.getTo();
			}
		}
		return -1;
	}
	
	// Palavra percorre o Automato
	public boolean accept(String palavra) {
		
		char c;
		int estadoAtual = this.getEstadoInicial();
		
		for (int i=0; i< palavra.length(); i++) { 
			c = palavra.charAt(i);
			estadoAtual = percorreUmEstado(estadoAtual, c);
			if(estadoAtual == -1)
				return false;
		}
		if(this.getEstadoFinal().contains((Integer)estadoAtual))
			return true;

		return false;
	}
	
	// Retorna o Estado Inicial
	public int initial() {
		return getEstadoInicial();
	}
	
	// Retorna os Estados Finais
	public ArrayList<Integer> finals() {
		return getEstadoFinal();
	}
	
	// Testa a pertenca de uma palavra na linguagem
	public int move(int estado, String palavra) {
		
		char c;
		int estadoAtual = estado;
		
		for (int i=0; i< palavra.length(); i++) { 
			c = palavra.charAt(i);
			estadoAtual = percorreUmEstado(estadoAtual, c);
			if(estadoAtual == -1)
				return -1;
		}
		return estadoAtual;
	}
		
	// ---------------- GET's e SET's ------------------ //
	//Estados
	public ArrayList<Integer> getEstado() {
		return this.estado;
	}
	public void setEstado(ArrayList<Integer> estado) {
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