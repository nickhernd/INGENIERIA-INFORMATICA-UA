package es.ua.dlsi.prog3.p6.graph;

import java.util.HashSet;
import java.util.Set;
import java.util.TreeSet;

class Alumno implements Comparable<Alumno>{
	private String nombre;
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public Alumno(String nombre) {
		super();
		this.nombre = nombre;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((nombre == null) ? 0 : nombre.toLowerCase().hashCode());
		return result;
	}
	
	public String toString() {
		return nombre;
	}
	@Override
	public boolean equals(Object o) {
		boolean iguales = false;
		if(o != null && o.getClass() == getClass()) {
			Alumno a = (Alumno) o;
			iguales = nombre.equalsIgnoreCase(a.nombre);
		}
		return iguales;
	}
	@Override
	public int compareTo(Alumno o) {
		return nombre.toLowerCase().compareTo(o.nombre.toLowerCase());
	}
}


// SOLAMENTE PUEDE HABER UNA CLASE PUBLICA POR FICHERO Y DEBE
// COINCIDIR CON EL NOMBRE DEL FICHERO.
public class mainJFC{
	public static void main(String [] args) {
		// para saber si son iguales elementos
		// 	hashCode (que por defecto devuelve la refernecia del objeto)
		//	equals (que por cierto que por defecto devuelve cierto si son el mismo)
		Set<Alumno> alus = new HashSet<>();
		Alumno alu1 = new Alumno("triana");
		Alumno alu2 = new Alumno("queko");
		Alumno alu3 = new Alumno("aitana");
		Alumno alu4 = new Alumno("aitana");
		Alumno alu5 = new Alumno("Aitana");
		
		alus.add(alu1);
		alus.add(alu1);
		alus.add(alu2);
		alus.add(alu5);
		alus.add(alu4);
		alus.add(alu3);
		
		
		Set<Alumno> alusTree = new TreeSet<Alumno>();
		alusTree.add(alu1);
		alusTree.add(alu2);
		alusTree.add(alu3);
		alusTree.add(alu4);
		System.out.println(alusTree);
		
		
		System.out.println(alus);
	}
}

/*
public class mainJFC {
	public static void main(String [] args) {
		// Conjunto: Coleccion de elementos no ordenados (numerados), sin repetidos.
		//		- NO podemos acceder a los elementos del conjunto por posicion.
		//		- Si queremos recorrer los elementos del conjunto usaremos un  for-each
		//		- Tenemos disponibles operaciones como add, contains...
		
		//	Dos implementaciones en JAVA
		//		- HashSet, implementado con una tabla de dispersion, tiene acceso constante
		//		para las operaciones de borrado e insercion, pero no mantiene una relacion
		// 		de orden entre los elementos del conjunto.
		//		- TreeSet, implementado con un AVL, que guarda una relacion de orden entre los
		//		elementos del conjunto. Coste de insercion/borrado log2(n).
		Set<Integer> c1 = new HashSet<Integer>();
		Set<Integer> c2 = new TreeSet<Integer>();
		
		c1.add(3); c1.add(6);
		c1.add(5); c1.add(4);
		c1.add(12); c1.add(34);
		c1.add(300); c1.add(343);
		int suma = 0;
		for(Integer i : c1) {
			suma = suma + i;
		}
		System.out.println("La suma de los elementos del conjunto es " + suma);
		Scanner sc = new Scanner(System.in);
		System.out.print("Introduce el elemento a buscar: ");
		Integer leido = sc.nextInt();
		if(c1.contains(leido)){
			System.out.println("El elemento esta en el conjunnto");
		}
		else {
			System.out.println("El elemento no esta en el conjunto");
		}
		sc.close();
		c2.add(12);
		c2.add(12);
		c2.add(125);
		c2.add(145);
		c2.add(152);
		System.out.println(c2);
		
		
		
	}
}
*/

