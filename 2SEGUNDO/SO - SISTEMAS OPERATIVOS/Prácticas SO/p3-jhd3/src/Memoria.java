import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.Ellipse2D;
import java.util.ArrayList;

import javax.swing.JPanel;

class ElementoMemoria{
	private int pro; // numero de proceso, si es 0 sera un hueco
	private int dir;
	private int tam;
	private int tr; // tiempo restante
	public int getPro() {
		return pro;
	}
	public void setPro(int pro) {
		this.pro = pro;
	}
	public int getDir() {
		return dir;
	}
	public void setDir(int dir) {
		this.dir = dir;
	}
	public int getTam() {
		return tam;
	}
	public void setTam(int tam) {
		this.tam = tam;
	}
	public int getTr() {
		return tr;
	}
	public void setTr(int tr) {
		this.tr = tr;
	}
	public ElementoMemoria(int pro, int dir, int tam, int tr) {
		super();
		this.pro = pro;
		this.dir = dir;
		this.tam = tam;
		this.tr = tr;
	}
	public void decrementar(){
		tr--;	// decremento el timpo restante
		if(tr == 0) // si no le queda tiempo
			pro = 0; // lo transformo a hueco poniendo pro a 0.
	}
	public ElementoMemoria(ElementoFichero f){
		pro = f.getProceso();
		tam = f.getTamanyo();
		tr = f.getTiempoEjecucion();
		dir = 0; // la direccion de comienzo en la memoria
		// aun no la se
	}
	public String toString(){
		String cadena = "";
		cadena += "[";
		cadena += dir;
		if(pro != 0)
			cadena += " P" + pro + " ";
		else
			cadena += " hueco ";
		cadena += tam + "]";
		return cadena;
	}
}

public class Memoria{
	
	private ArrayList<ElementoMemoria> lista;
	private static final int tam = 2000;
	private static final int unidad = 10;
	// para el algoritmo de siguiente hueco.
	private int dirSiguienteHueco = 0;
	
	public ArrayList<ElementoMemoria> getMemoria(){
		return lista;
	}
	
	
	public boolean isEmpty(){
		return lista.size() == 1 && lista.get(0).getPro() == 0;
	}
	
	public String toMapa(){
		String mapa = "";
		int i;
		
		for(i = 0; i < lista.size(); i++){
			if(lista.get(i).getPro() == 0){
				for(int j = 0; j < lista.get(i).getTam() / unidad; j++){
					mapa += lista.get(i).getPro() ;
				}
			}
			else{
				for(int j = 0; j < lista.get(i).getTam() / unidad; j++){
					mapa += lista.get(i).getPro() ;
				}
			}
		}	
		return mapa;
	}

	public Memoria(){
		lista = new ArrayList<ElementoMemoria>();
		lista.add(new ElementoMemoria(0, 0, tam, 0));
	}
	
	public void decrementar(){
		int k;
		
		// LE QUITO UNA UNIDAD A TODOS LOS PROCESOS QUE HAY EN LA MEMORIA
		// AL CONTADOR DE TIEMPO RESTANTE.
		for(int i = 0; i < lista.size(); i++){
			// si es un proceso decremento su tiempo restante.
			// getPro devuelve 0 si un hueco.
			if(lista.get(i).getPro() != 0){
				lista.get(i).decrementar();
				// SI EL TIEMPO ES IGUAL A 0 LO TRANFORMO EN HUECO.
			}
		}
		// LA VUELVO A RECORRER Y LOS HUECOS QUE HAYA SEGUIDOS LOS JUNTO
		// EN UNO SOLO.
		for(int i = 0; i < lista.size(); i++){
			if(lista.get(i).getPro() == 0){
				k = i + 1; // kaaaaah es el siguiente
				while(k < lista.size() && lista.get(k).getPro() == 0){
					// SI K (EL SIGUIENTE) ES OTRO HUECO LE SUMO SU TAMAÑO AL HUECO I
					// Y LO QUITO.
					lista.get(i).setTam(lista.get(i).getTam() + lista.get(k).getTam());
					lista.remove(k);
				}
			}
		}
	}
	
	public boolean peorHueco(ElementoFichero nuevo){
		int i, posPeor;
		int diferenciaActual, diferenciaPeor;
		boolean encontrado;
		
		encontrado = false;
		posPeor = -1;
		// busco el peor hueco para meter al nuevo.
		// el que mas se ajuste a su tamaño es el mejor hueco.
		for(i = 0; i < lista.size(); i++){
			if(lista.get(i).getPro() == 0 && lista.get(i).getTam() >= nuevo.getTamanyo()){
				// aun no he encontrado ninguna mejor
				if(posPeor == -1){
					posPeor = i; // el primero que encuentro no lo comparo.
				}
				else{
					diferenciaPeor = lista.get(posPeor).getTam() - nuevo.getTamanyo();
					diferenciaActual = lista.get(i).getTam() - nuevo.getTamanyo();
					// para el mejor hueco cambiar esto por <
					if(diferenciaActual > diferenciaPeor)
						posPeor = i; // el que estoy es peor hueco :)
				}
			}
		}
		// si hemos encontrado un hueco
		if(posPeor != -1){
			ElementoMemoria nuevoMemoria = new ElementoMemoria(nuevo);
			
			i = posPeor; /// juas juas!!
			
			encontrado = true;
			
			// al hueco le quito el tama�o del proceso.
			lista.get(i).setTam(lista.get(i).getTam() - nuevo.getTamanyo());
	
			// el nuevo elemento empieza donde empieza el hueco
			nuevoMemoria.setDir(lista.get(i).getDir());

			// el hueco empieza despues del proceso.
			lista.get(i).setDir(lista.get(i).getDir() + nuevo.getTamanyo());
			
			if(lista.get(i).getTam() == 0)
				lista.remove(i);
			
			lista.add(i, nuevoMemoria);
			
		}
		return encontrado;
	}
	/*
	public boolean mejorHueco(ElementoFichero nuevo){
		int i, posPeor;
		int diferenciaActual, diferenciaPeor;
		boolean encontrado;
		
		encontrado = false;
		posPeor = -1;
		// busco el peor hueco para meter al nuevo.
		// el que mas se ajuste a su tamaño es el mejor hueco.
		for(i = 0; i < lista.size(); i++){
			if(lista.get(i).getPro() == 0 && lista.get(i).getTam() >= nuevo.getTamanyo()){
				// aun no he encontrado ninguna mejor
				if(posPeor == -1){
					posPeor = i; // el primero que encuentro no lo comparo.
				}
				else{
					diferenciaPeor = lista.get(posPeor).getTam() - nuevo.getTamanyo();
					diferenciaActual = lista.get(i).getTam() - nuevo.getTamanyo();
					// para el mejor hueco cambiar esto por <
					if(diferenciaActual < diferenciaPeor)
						posPeor = i; // el que estoy es peor hueco :)
				}
			}
		}
		// si hemos encontrado un hueco
		if(posPeor != -1){
			ElementoMemoria nuevoMemoria = new ElementoMemoria(nuevo);
			
			i = posPeor; /// juas juas!!
			
			encontrado = true;
			
			// al hueco le quito el tama�o del proceso.
			lista.get(i).setTam(lista.get(i).getTam() - nuevo.getTamanyo());
	
			// el nuevo elemento empieza donde empieza el hueco
			nuevoMemoria.setDir(lista.get(i).getDir());

			// el hueco empieza despues del proceso.
			lista.get(i).setDir(lista.get(i).getDir() + nuevo.getTamanyo());
			
			if(lista.get(i).getTam() == 0)
				lista.remove(i);
			
			lista.add(i, nuevoMemoria);
			
		}
		return encontrado;
	}
	
	*/
	@Override
	public String toString(){
		String resultado = "";
		int i;
		
		for(i = 0; i < lista.size(); i++){
			resultado += lista.get(i).toString();
			if(i != lista.size() - 1){
				resultado += " ";
			}
		}
		resultado += "\n";
		
		return resultado;
	}

	public boolean siguienteHueco(ElementoFichero nuevo){
		int i;
		boolean encontrado;
		
		encontrado = false;
		// busco un hueco en el que quepa el nuevo elemento y cuya
		// direccion sea mayor que la del ultimo hueco usado (siguiente hueco
		// al ultimo usado).
		i = 0;
		while(i < lista.size() && !encontrado){
			if(lista.get(i).getPro() == 0 && lista.get(i).getTam() >= nuevo.getTamanyo()
					&& lista.get(i).getDir() >= dirSiguienteHueco){
				encontrado = true;
			}
			else{
				i++;
			}
		}
		// si no lo he encontrado lo busco desde el principio.
		if(!encontrado){
			i = 0;
			while(i < lista.size() && !encontrado){
				if(lista.get(i).getPro() == 0 && lista.get(i).getTam() >= nuevo.getTamanyo()){
					encontrado = true;
				}
				else{
					i++;
				}
			}	
		}
		
		if(encontrado){
			// actualizo la direccion del ultimo hueco usado.
			dirSiguienteHueco = lista.get(i).getDir();
			
			
			ElementoMemoria nuevoMemoria = new ElementoMemoria(nuevo);
			
			// al hueco le quito el tama�o del proceso.
			lista.get(i).setTam(lista.get(i).getTam() - nuevo.getTamanyo());
	
			// el nuevo elemento empieza donde empieza el hueco
			nuevoMemoria.setDir(lista.get(i).getDir());

			// el hueco empieza despues del proceso.
			lista.get(i).setDir(lista.get(i).getDir() + nuevo.getTamanyo());
			
			if(lista.get(i).getTam() == 0)
				lista.remove(i);
			
			lista.add(i, nuevoMemoria);
		}
		return encontrado;
	}
/*
	public boolean primerHueco(ElementoFichero nuevo){
		int 
		i;
		boolean encontrado;
		
		encontrado = false;
		i = 0;
		while(i < lista.size() && !encontrado){
			if(lista.get(i).getPro() == 0 && lista.get(i).getTam() >= nuevo.getTamanyo()){
				encontrado = true;
			}
			else{
				i++;
			}
		}
		if(encontrado){
			ElementoMemoria nuevoMemoria = new ElementoMemoria(nuevo);
			
			// al hueco le quito el tamaño del proceso.
			// el nuevo hueco, tiene como tamaño lo que tenia menos lo que le quito del nuevo.
			lista.get(i).setTam(lista.get(i).getTam() - nuevo.getTamanyo());
	
			// el nuevo elemento empieza donde empieza el hueco
			nuevoMemoria.setDir(lista.get(i).getDir());

			// el hueco empieza despues del proceso.
			lista.get(i).setDir(lista.get(i).getDir() + nuevo.getTamanyo());
			
			if(lista.get(i).getTam() == 0)
				lista.remove(i);
			
			lista.add(i, nuevoMemoria);
		}
		return encontrado;
	}
	*/
}


