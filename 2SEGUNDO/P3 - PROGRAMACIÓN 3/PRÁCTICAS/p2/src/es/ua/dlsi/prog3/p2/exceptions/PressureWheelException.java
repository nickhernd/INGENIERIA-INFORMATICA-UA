/**
 * @author Jaime Hernández
 * 
 * @dni 48776654W
 */

package es.ua.dlsi.prog3.p2.exceptions;

/**
 * La clase PressureWheelException representa una excepción lanzada cuando la presión de una rueda está fuera de los límites permitidos.
 */
public class PressureWheelException extends Exception {
    private double pressure;

    /**
     * Constructor que inicializa la excepción con la presión que causó la excepción.
     *
     * @param pressure Presión que causó la excepción.
     */
    public PressureWheelException(double pressure) {
        this.pressure = pressure;
    }

    /**
     * Obtiene un mensaje descriptivo de la excepción, indicando la presión que causó el problema.
     *
     * @return Mensaje descriptivo de la excepción.
     */
    public String getMessage() {
        return "Presión de " + pressure + " BAR"; 
    }
}

