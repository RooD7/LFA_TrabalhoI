package mainPackage;

import java.io.IOException;

import org.jdom2.JDOMException;

public class Main {

	public static void main(String[] args) throws JDOMException, IOException {
		// TODO Auto-generated method stub
		AFD m = new AFD();

		m.Load("entrada.jff");
		m.addState(5, false, false);
		m.addTransition(2, 2, 'b');
		m.deleteState(3);
		m.Salve("saida.jff");
		
		/*
		m. addState ( id =10 , initial = false , final =true ) ;
		m. addTransition ( source =1 , target =2 , consume= "b" ) ;
		m. deleteState ( 3 ) ;
		m. deleteTransition( source =1 , target =4 , consume= "a" ) ;
		 */
	}

}
