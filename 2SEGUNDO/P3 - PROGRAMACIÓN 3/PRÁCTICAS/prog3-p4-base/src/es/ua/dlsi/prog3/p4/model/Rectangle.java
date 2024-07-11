/**
 * @author Jaime Hernández
 * @dni 48776654W
 */
package es.ua.dlsi.prog3.p4.model;
/**
 * Esta clase representa un rectangulo y hereda de AbstractPolygon
 */
public class Rectangle extends AbstractPolygon {
   

    public double length;
   

    public double width;

    /**
     * Constructor por defecto de Rectangle. Inicializa la longitud y la anchura a 0 y el ángulo a 0.
     */
    public Rectangle() {
        super();
        this.length = 0;
        this.width = 0;
    }

    /**
     * Constructor de Rectangle con parámetros para establecer la posición, el ángulo, la longitud y la anchura del rectángulo.
     *
     * @param c Coordenada de la posición del rectángulo.
     * @param angle  Ángulo de rotación del rectángulo.
     * @param length Longitud del rectángulo.
     * @param width  Anchura del rectángulo.
     * @throws IllegalArgumentException Si la longitud o la anchura son negativas.
     */
    public Rectangle(Coordinate c, double angle, double length, double width) {
        super(c, angle);
        if (length < 0 || width < 0) {
            throw new IllegalArgumentException("La longitud y la anchura deben ser no negativas.");
        }
        this.length = length;
        this.width = width;
    }

    /**
     * Constructor de copia de Rectangle. Crea un nuevo rectángulo con la misma posición, ángulo, longitud y anchura que el rectángulo pasado como argumento.
     *
     * @param r Rectángulo que se quiere copiar.
     */
    public Rectangle(Rectangle r) {
        super(r.getPosition(), r.getAngle());
        this.length = r.length;
        this.width = r.width;
    }

    /**
     * Obtiene la longitud del rectángulo.
     *
     * @return Longitud del rectángulo.
     */
    public double getLength() {
        return this.length;
    }

    /**
     * Obtiene la anchura del rectángulo.
     *
     * @return Anchura del rectángulo.
     */
    public double getWidth() {
        return this.width;
    }

    /**
     * Escala el rectángulo multiplicando su longitud y anchura por el factor de escala proporcionado.
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
        this.length = scale * this.length;
        this.width = scale * this.width;
    }

    /**
     * Crea y devuelve un nuevo rectángulo con la misma posición, ángulo, longitud y anchura que el rectángulo actual.
     *
     * @return Nuevo rectángulo clonado.
     */
    @Override
    public Form2D clone() {
        return new Rectangle(this.getPosition(), this.getAngle(), this.length, this.width);
    }

    /**
     * Compara el rectángulo con otro objeto para determinar si son iguales.
     * Dos rectángulos son iguales si tienen la misma posición, ángulo, longitud y anchura.
     *
     * @param obj Objeto a comparar.
     * @return true si los rectángulos son iguales, false en caso contrario.
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!super.equals(obj)) return false;
        if (getClass() != obj.getClass()) return false;
        Rectangle other = (Rectangle) obj;
        if (Double.doubleToLongBits(length) != Double.doubleToLongBits(other.length)) return false;
        return Double.doubleToLongBits(width) == Double.doubleToLongBits(other.width);
    }

    /**
     * Devuelve una representación en forma de cadena del rectángulo, incluyendo su posición, ángulo, longitud y anchura.
     *
     * @return Representación en forma de cadena del rectángulo.
     */
    @Override
    public String toString() {
        return "(" + this.getPosition().getX() + "," + this.getPosition().getY() + ")," +
                "angle=" + this.getAngle() + ",length=" + this.length + ",width=" + this.width;
    }
}
