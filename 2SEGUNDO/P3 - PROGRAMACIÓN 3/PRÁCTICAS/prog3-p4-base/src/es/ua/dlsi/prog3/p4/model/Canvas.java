/**
 * @author Jaime Hernández
 * @dni 48776654W
 */

package es.ua.dlsi.prog3.p4.model;

import java.util.ArrayList;

/**
 * Esta clase representa el lienzo donde se representaran las formas
 */
public class Canvas{
	/**
	 * variable de clase que representa las dimensiones por defecto del lienzo
	 */
	
	public final static float DEFAULT_SIZE = 1000;
	
	private String title;
	private double width;
	private double height;
	private ArrayList<Form2D> forms;
	
	/**
	 * Constructor que inicializa el titulo a "default canvas", dimensiones por defecto y sin figuras
	 */
	public Canvas() {
		this.title = "default canvas";
		this.height = DEFAULT_SIZE;
		this.width = DEFAULT_SIZE;
		this.forms = new ArrayList<>();
	}
	
	/**
	 * Constructor que inicializa los atributos a los valores pasados por parametro
	 * 
	 * @param tittle titulo del lienzo
	 * @param widht anchura del lienzo
	 * @param height altura del lienzo
	 * @throws IllegalArgumentException si los parametros no son validos
	 */
	public Canvas(String title, double width, double height) {
		if(width < 0 || height < 0) {
			throw new IllegalArgumentException();
		}
		
		this.height = height;
		this.width = width;
		this.title = title;
		this.forms = new ArrayList<>();
	}
	
	/**
	 * Constructor de copia que utiliza los atributos del Canvas pasado por parametro
	 * 
	 * @param c lienzo del que copiar
	 */
	public Canvas(Canvas c) {
		this.title = c.title;
		this.height = c.height;
		this.width = c.width;
		for(int i = 0; i < c.forms.size(); i++) {
			this.forms.add(c.forms.get(i));
		}
		
	}
	
	/**
	 * Metodo para añadir una forma al lienzo
	 * 
	 * @param f forma a añadir
	 */
	public void addForm(Form2D f) {
		this.forms.add(f);
	}
	
	/**
	 * Metodo para clonar un lienzo
	 * 
	 * @return un clon del lienzo copiado
	 *
     */
	public Canvas clone() {
		return new Canvas(this.title, this.width, this.height);
	}
	
	/**
	 * Metodo para devolver una forma segun la posicion que ocupe en el arraylist
	 * 
	 * @param n posicion dentro del arraylist de Form2D
	 * @return la forma solicitada
	 * @throws IndexOutOfBoundsException si esta fuera del ArrayList de forms
	 */
	public Form2D getForm(int n) {
		if(n < 1 || n > this.forms.size()) 
			throw new IndexOutOfBoundsException();
		
		return this.forms.get(n-1);
	}
	
	/**
	 * Metodo getter para obtener la anchura del lienzo
	 * 
	 * @return un numero real que representa la anchura del lienzo
	 */
	public double getWidth() {
		return this.width;
	}
	
	/**
	 * Metodo getter para obtener la altura del lienzo 
	 * 
	 * @return un numero real que representa la altura del lienzo
	 */
	public double getHeight() {
		return this.height;
	}
	
	/**
	 * Metodo para obtener la cantidad de figuras en el lienzo
	 * 
	 * @return un numero entero que representa la cantidad de figuras en el lienzo
	 */
	public double getNumForms() {
		return this.forms.size();
	}
	
	/**
	 * Metodo para borrar una forma del lienzo segun la posicion del arraylist
	 * 
	 * @param n representa la posicion en el arraylist
	 * @throws IndexOutOfBoundsException si esta fuera del ArrayList de forms
	 */
	public void removeForm(int n) {
		if(n < 1 || n > this.forms.size()) 
			throw new IndexOutOfBoundsException();
		
		this.forms.remove(n-1);
	}

	/**
	 * Metodo para imprimir la informacion del lienzo
	 * 
	 * @return un string con la informacion del lienzo
	 */
	@Override
	public String toString() {
		return this.title + " (" + this.width + "," + this.height + ") with " + this.forms.size() + " forms";
	}
	
}
