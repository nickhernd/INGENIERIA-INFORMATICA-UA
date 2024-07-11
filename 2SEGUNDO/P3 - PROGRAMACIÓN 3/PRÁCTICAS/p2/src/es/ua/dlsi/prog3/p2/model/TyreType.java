/**
 * @author Jaime Hernández
 * 
 * @dni 48776654W
 */

package es.ua.dlsi.prog3.p2.model;
import java.util.Objects;

/**
 * La clase TyreType representa un tipo de neumático con una descripción, presión mínima y presión máxima permitida.
 */
public class TyreType {
    private String description;
    private double min_pressure;
    private double max_pressure;
    
    /**
     * Constructor que inicializa un objeto TyreType con la descripción, presión mínima y presión máxima especificadas.
     *
     * @param description Descripción del tipo de neumático.
     * 
     * @param minPressure Presión mínima permitida para el neumático.
     * 
     * @param maxPressure Presión máxima permitida para el neumático.
     * 
     * @throws IllegalArgumentException Si la descripción es nula o vacía, o si las presiones son negativas o inconsistentes.
     */
    public TyreType(String description, double minPressure, double maxPressure) {
        if (description == null || description.isEmpty() || minPressure < 0 || maxPressure < 0 || minPressure > maxPressure) {
            throw new IllegalArgumentException();
        } else {
            this.description = description;
            this.min_pressure = minPressure;
            this.max_pressure = maxPressure;
        }
    }

    /**
     * Constructor que crea un nuevo objeto TyreType copiando los valores de otro objeto TyreType.
     *
     * @param tt Objeto TyreType del cual copiar los valores.
     */
    public TyreType(TyreType tt) {
        this.description = tt.description;
        this.max_pressure = tt.max_pressure;
        this.min_pressure = tt.min_pressure;
    }

    /**
     * Representación en cadena del objeto TyreType en el formato "TyreType [descripción] [presión mínima, presión máxima]".
     *
     * @return Representación en cadena del objeto TyreType.
     */
    public String toString() {
        return "TyreType " + description + " [" + min_pressure + ", " + max_pressure + "]";
    }

    /**
     * Obtiene la presión mínima permitida para el neumático.
     *
     * @return Presión mínima del neumático.
     */
    public double getMinPressure() {
        return min_pressure;
    }

    /**
     * Obtiene la presión máxima permitida para el neumático.
     *
     * @return Presión máxima del neumático.
     */
    public double getMaxPressure() {
        return max_pressure; //devuelve la máxima presión
    }

    /**
     * Compara el objeto TyreType actual con otro objeto para determinar si son iguales.
     *
     * @param obj Objeto a comparar con el TyreType actual.
     * 
     * @return true si los objetos son iguales, false en caso contrario.
     */
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null || getClass() != obj.getClass())
            return false;
        TyreType other = (TyreType) obj;
        return Objects.equals(description, other.description)
                && Double.doubleToLongBits(max_pressure) == Double.doubleToLongBits(other.max_pressure)
                && Double.doubleToLongBits(min_pressure) == Double.doubleToLongBits(other.min_pressure);
    }
}
