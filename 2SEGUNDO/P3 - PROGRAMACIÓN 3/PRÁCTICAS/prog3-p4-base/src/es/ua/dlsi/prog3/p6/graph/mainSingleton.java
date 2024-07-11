package es.ua.dlsi.prog3.p6.graph;

public class mainSingleton {
	public static void main(String [] args) {
		//IDGenerator id = new IDGenerator();
		IDGenerator id1 = IDGenerator.getInstance();
		IDGenerator id2 = IDGenerator.getInstance();
		
		// ambas referencias estan apuntando al mismo objeto.
		System.out.println(id1.getNextID());
		System.out.println(id2.getNextID());
		
	}
}
