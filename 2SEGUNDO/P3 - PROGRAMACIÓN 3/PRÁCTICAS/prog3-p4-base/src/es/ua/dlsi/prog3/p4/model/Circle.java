/**
 * @author Jaime Hernández
 * @dni 48776654W
 */
package es.ua.dlsi.prog3.p4.model;

/**
 * Esta clase representa un circulo y hereda de Form2D
 */
public class Circle extends Form2D {
    public double radius; // Radio del círculo.

    /**
	 * Constructor que inicializa el radio a 0 y el centro (posicion) desconocida
	 */
    public Circle() {
        super();
        this.radius = 0;
    }

    /**
	 * Constructor que inicializa el radio a d (pasado por parametro) y el centro a c (pasado por parametro)
	 * 
	 * @param c coordenada que representa el centro
	 * @param radius numero que representa el radio
	 * @throws IllegalArgumentException si los parametros no son validos
	 */
    public Circle(Coordinate c, double radius) {
        super(c);
        if(radius < 0) throw new IllegalArgumentException();
        this.radius = radius;
    }

    /**
	 * Constructor de copia
	 * 
	 * @param c circulo del que copiar
	 */
    public Circle(Circle c) {
        super(c.getPosition());
        this.radius = c.radius;
    }

    /**
	 * Getter que devuelve el radio
	 * 
	 * @return un numero real que representa el radio
	 */
    public double getRadius() {
        return this.radius;
    }
    
    /**
	 * Metodo para clonar un circulo
	 * 
	 * @return un clon del circulo que llama a este metodo
	 */
    @Override
    public Form2D clone() {
        return new Circle(this.getPosition(), this.radius);
    }

    /**
     * Escala el círculo por el factor proporcionado.
     *
     * @param scale Factor de escala. Debe ser un valor positivo.
     * @throws IllegalArgumentException Si el factor de escala es menor o igual a 0.
     */
    @Override
    public void scale(double scale) throws IllegalArgumentException {
        if (scale <= 0) {
            throw new IllegalArgumentException();
        }

        scale = scale / 100;

        this.radius = scale * this.radius;
    }

    /**
     * Devuelve una representación en cadena del círculo.
     *
     * @return Representación en cadena del círculo, incluyendo posición y radio.
     */
    @Override
    public String toString() {
        return "(" + getPosition().getX() + "," + getPosition().getY() + ")" + ",radius=" + this.radius;
    }
    
    /**
	 * Metodo para comparar dos circulos
	 * 
	 * @param obj circulo a comparar
	 * @return devuelve cierto si son iguales, falso en caso contrario
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		Circle other = (Circle) obj;
		return Double.doubleToLongBits(radius) == Double.doubleToLongBits(other.radius);
	}

    
}
