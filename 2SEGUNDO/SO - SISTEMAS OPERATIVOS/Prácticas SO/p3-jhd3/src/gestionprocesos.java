import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;

import javax.swing.JFrame;
import javax.swing.JPanel;

class ElementoFichero{
	private int proceso;
	private int instanteLlegada;
	private int tamanyo;
	private int tiempoEjecucion;
	public int getProceso() {
		return proceso;
	}
	public void setProceso(int proceso) {
		this.proceso = proceso;
	}
	public int getInstanteLlegada() {
		return instanteLlegada;
	}
	public void setInstanteLlegada(int instanteLlegada) {
		this.instanteLlegada = instanteLlegada;
	}
	public int getTamanyo() {
		return tamanyo;
	}
	public void setTamanyo(int tamanyo) {
		this.tamanyo = tamanyo;
	}
	public int getTiempoEjecucion() {
		return tiempoEjecucion;
	}
	public void setTiempoEjecucion(int tiempoEjecucion) {
		this.tiempoEjecucion = tiempoEjecucion;
	}
	public ElementoFichero(int proceso, int instanteLlegada, int tamanyo,
			int tiempoEjecucion) {
		super();
		this.proceso = proceso;
		this.instanteLlegada = instanteLlegada;
		this.tamanyo = tamanyo;
		this.tiempoEjecucion = tiempoEjecucion;
	}
	public ElementoFichero(String linea){
		String [] campos = null;
		
		campos = linea.split(" ");
		proceso = Integer.parseInt(campos[0]);
		instanteLlegada = Integer.parseInt(campos[1]);
		tamanyo = Integer.parseInt(campos[2]);
		tiempoEjecucion = Integer.parseInt(campos[3]);
	}
}

public class gestionprocesos{
	public static ArrayList<ElementoFichero> listaFichero = new ArrayList<ElementoFichero>(); 
	public static int opcion; 
	public static Memoria memoria = new Memoria(); 
	
	
	public static void leerFichero(String nombreFichero) throws FileNotFoundException{
		FileReader fr = null;
		BufferedReader br = null;
		String linea = null;
		
		fr = new FileReader(nombreFichero);
		br = new BufferedReader(fr);
		try{
			linea = br.readLine();
			
			while(linea != null && !linea.equals("")){
			
				listaFichero.add(new ElementoFichero(linea)); 
				linea = br.readLine();
			}
		}
		catch(IOException e){ }
		finally{
			try{
				fr.close();
				br.close();
			}catch(IOException e){ }
		}
	}
	public static void menu(){
		System.out.println("1. Primer hueco");
		System.out.println("2. Mejor hueco");
		System.out.print("Opcion: ");
	}
	
	public static void simular(){
		int instante;
		boolean insertado = false;
		File file = null;
		
		instante = 1;
		while(!listaFichero.isEmpty() || !memoria.isEmpty()){
		
			for(int i = 0; i < listaFichero.size(); i++){
				if(listaFichero.get(i).getInstanteLlegada() <= instante){
					if(opcion == 1){
					
						insertado = memoria.siguienteHueco(listaFichero.get(i));
					}
					else{
						
						insertado = memoria.peorHueco(listaFichero.get(i));
					}
					
					if(insertado){
						listaFichero.remove(i);
						i--;
					}
				}
			}
			
			System.out.print(instante + " "  + memoria.toString());
			
			instante++; 
			
			memoria.decrementar();
			
		}
		System.out.print(instante + " "  + memoria.toString());
		
	}

	public static void main(String [] args){
		Scanner sc = null;
		
		sc = new Scanner(System.in); 
		do{
			menu();
			opcion = sc.nextInt(); 
			if(opcion < 1 || opcion > 2)
				System.out.println("Error en la opcion.");
		}while(opcion < 1 || opcion > 2);
		if(args.length == 1){ 
			try{
				leerFichero(args[0]); 
				simular();

			}
			catch(FileNotFoundException e){
				System.out.println("Error al abrir el fichero");
			}
		}
	}
}
