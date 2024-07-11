package es.ua.dlsi.prog3.p6.graph;

import java.util.HashMap;
import java.util.Map;

public class meinMapa {
	public static void main(String [] args) {
		// Conjunto de pares clave valor donde la clave no se puede repetir
		// y el valor es el valor es valor asociado a dicha clave.
		Map<String, Integer> alus = new HashMap<>();
		
		// Si es un HashMap para ver si las claves son iguales
		// utilizará el equals y el hashCode
		
		// Si es un TreeMap las claves tendran que haber implementado
		// la interfaz Comparable
		
		alus.put("Aitana", 34);
		alus.put("Juan Carlos", 34);
		alus.put("Enrique", 34);
		alus.put("Jorge", 34);
		alus.put("Aitana", 23); // actualiza el valor de la clave
		
		System.out.println(alus);
		System.out.println(alus.get("Aitana"));
		
		System.out.println("Claves: " + alus.keySet());
		System.out.println("Valores: " + alus.values());
		
		System.out.println("el mapa");
		System.out.println("=======");
		for(String alu : alus.keySet()) {
			System.out.println(alu + " => " + alus.get(alu));
		}
		System.out.println(alus.remove("Aitana"));
		if(!alus.containsKey("Aitana")) {
			System.out.println("Aitana ya no esta.... :_)");
		}
	}
}



