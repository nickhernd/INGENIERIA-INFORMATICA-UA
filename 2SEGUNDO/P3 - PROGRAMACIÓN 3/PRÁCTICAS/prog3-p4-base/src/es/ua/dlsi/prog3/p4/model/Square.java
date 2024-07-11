/**
 * @author Jaime Hernández
 * @dni 48776654W
 */
package es.ua.dlsi.prog3.p4.model;
/**
 * Esta clase representa un poligono sin especificar y hereda de Form2D
 */
public class Square extends AbstractPolygon {
    
    public double side;

    /**
	 * Constructor que inicializa la posicion a no definida y el angulo de rotacion a 0
	 */
    public Square() {
        super();
        this.side = 0;
    }

    /**
     * Constructor de Square con parámetros para establecer la posición, el ángulo y el lado del cuadrado.
     *
     * @param c Coordenada de la posición del cuadrado.
     * @param angle Ángulo de rotación del cuadrado.
     * @param side Lado del cuadrado.
     * @throws IllegalArgumentException Si el lado es negativo.
     */
    public Square(Coordinate c, double angle, double side) {
        super(c, angle);
        if (side < 0) {
            throw new IllegalArgumentException();
        }
        this.side = side;
    }

    /**
     * Constructor de copia de Square. Crea un nuevo cuadrado con la misma posición, ángulo y lado que el cuadrado pasado como argumento.
     *
     * @param s Cuadrado que se quiere copiar.
     */
    public Square(Square s) {
        super(s.getPosition(), s.getAngle());
        this.side = s.side;
    }

    /**
     * Obtiene el lado del cuadrado.
     *
     * @return Lado del cuadrado.
     */
    public double getSide() {
        return this.side;
    }

    /**
     * Escala el cuadrado multiplicando su lado por el factor de escala proporcionado.
     *
     * @param scale Factor de escala.
     * @throws IllegalArgumentException Si el factor de escala es no positivo.
     */
    @Override
    public void scale(double scale) {
        if (scale <= 0) {
            throw new IllegalArgumentException();
        }
        scale = scale / 100;
        this.side = scale * this.side;
    }

    /**
     * Crea y devuelve un nuevo cuadrado con la misma posición, ángulo y lado que el cuadrado actual.
     *
     * @return Nuevo cuadrado clonado.
     */
    @Override
    public Form2D clone() {
        return new Square(this.getPosition(), this.getAngle(), this.side);
    }

    /**
     * Compara el cuadrado con otro objeto para determinar si son iguales.
     * Dos cuadrados son iguales si tienen la misma posición, ángulo y lado.
     *
     * @param obj Objeto a comparar.
     * @return true si los cuadrados son iguales, false en caso contrario.
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!super.equals(obj)) return false;
        if (getClass() != obj.getClass()) return false;
        Square other = (Square) obj;
        return Double.doubleToLongBits(side) == Double.doubleToLongBits(other.side);
    }

    /**
     * Devuelve una representación en forma de cadena del cuadrado, incluyendo su posición, ángulo y lado.
     *
     * @return Representación en forma de cadena del cuadrado.
     */
    @Override
    public String toString() {
        return "(" + this.getPosition().getX() + "," + this.getPosition().getY() + ")," +
                "angle=" + this.getAngle() + ",side=" + this.side;
    }
}
