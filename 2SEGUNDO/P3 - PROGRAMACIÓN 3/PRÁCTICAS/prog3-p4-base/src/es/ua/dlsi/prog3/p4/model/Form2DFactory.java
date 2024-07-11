/**
 * @author Jaime Hernández
 * @dni 48776654W
 */
package es.ua.dlsi.prog3.p4.model;

/**
 * La clase Form2DFactory proporciona un método estático para crear instancias de formas en dos dimensiones.
 */
public class Form2DFactory {

    /**
     * Crea y devuelve una instancia de la forma en dos dimensiones especificada por el argumento de clase proporcionado.
     *
     * @param clase Nombre de la clase de la forma en dos dimensiones ("Circle", "Square" o "Rectangle").
     * @return Instancia de la forma en dos dimensiones creada, o null si la clase proporcionada no es válida
     * @throws IllegalArgumentException Si se proporciona un nombre de clase inválido.
     */
    public static Form2D createForm2D(String clase) {
        if (clase.equals("Circle")) {
            return new Circle();
        } else if (clase.equals("Square")) {
            return new Square();
        } else if (clase.equals("Rectangle")) {
            return new Rectangle();
        } else {
            throw new IllegalArgumentException();
        }
    }
}
