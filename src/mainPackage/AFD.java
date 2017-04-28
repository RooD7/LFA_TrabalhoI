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
	        
	        if(state.getChild("initial") != null) {
	        	this.setEstadoInicial(Integer.parseInt(state.getAttributeValue("id")));
	        	System.out.println("Inicial: "+ state.getAttributeValue("id"));
	        }
	        if(state.getChild("final") != null) {
	        	this.estadoFinal.add(Integer.parseInt(state.getAttributeValue("id")));
	        	System.out.println("Final: "+ state.getAttributeValue("id"));
	        }
		}
		
		String s;
		Iterator j = trst.iterator();
		while( j.hasNext() ){
			
			Transicao t = new Transicao();
	        Element trs = (Element) j.next();
	        t.setFrom(Integer.parseInt(trs.getChildText("from")));
	        t.setTo(Integer.parseInt(trs.getChildText("to")));
	        //String to Char
	        s = trs.getChildText("read");
	        t.setValue(s.charAt(0));

	        this.trans.add(t);
	        
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
		Iterator k = trans.iterator();
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