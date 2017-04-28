package mainPackage;

import java.io.IOException;

import org.jdom2.JDOMException;

public class Main {

	public static void main(String[] args) throws JDOMException, IOException {
		// TODO Auto-generated method stub
		System.out.println("OK!");
		AFD m = new AFD();
		m.Load("entrada.jff");
	}

}
