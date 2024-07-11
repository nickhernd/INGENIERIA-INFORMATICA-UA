package es.ua.dlsi.prog3.p4.model;

import java.util.Objects;
/**
 * Esta clase representa la forma basica de todas las formas que crearemos
 */
public abstract class Form2D {
	
	private Coordinate position;
	/**
	 * Constructor que inicializa las coordenadas a no validas
	 */
	protected Form2D() {
		this.position = new Coordinate();
	}
	/**
	 * Constructor que inicializa las coordenadas a las que nos pasan como parametro
	 * 
	 * @param c la coordenada a utilizar
	 */
	protected Form2D(Coordinate c) {
		this.position = new Coordinate(c);
	}
	/**
	 * Constructor de copia
	 * 
	 * @param f forma de la que copiar
	 */
	protected Form2D(Form2D f) {
		this.position = new Coordinate(f.position);
	}
	/**
	 * Getter de posicion
	 * 
	 * @return la coordenada de la forma
	 */
	public Coordinate getPosition() {
		return this.position;
	}
	/**
	 * Para mover la figura
	 * 
	 * @param c coordenada a la que desplazar la figura
	 * @return la coordenada resultado de mover la figura
	 */
	public Coordinate move(Coordinate c) {
		Coordinate res = new Coordinate(this.position);
		if(c != null) this.position = new Coordinate(c);
		return res;
	}
	/**
	 * Para comparar las posiciones de dos figuras
	 * 
	 * @param obj forma a comparar
	 * @return verdadero si las posiciones son iguales, falso en caso contrario
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Form2D other = (Form2D) obj;
		return Objects.equals(position, other.position);
	}
	/**
	 * Para escribir las posiciones
	 * 
	 * @return un string con la posicion de la figura
	 */
	@Override
	public String toString() {
		return "(" + position + ")";
	}
	/**
	 * Para clonar una forma
	 * 
	 * @param c posicion en la que clonamos la figura
	 * @return una forma resultado de la clonacion
	 */
	public Form2D clone(Coordinate c) {
		Form2D res = this.clone();
		res.position = c;
		return res;
	}
	/**
	 * Metodo abstracto para clonar formas
	 * 
	 * @return un clon de la forma
	 */
	public abstract Form2D clone();
	/**
	 * Metodo abstracto para escalar formas
	 * 
	 * @param d porcentaje de escalado
	 */
	public abstract void scale(double d);
	
}