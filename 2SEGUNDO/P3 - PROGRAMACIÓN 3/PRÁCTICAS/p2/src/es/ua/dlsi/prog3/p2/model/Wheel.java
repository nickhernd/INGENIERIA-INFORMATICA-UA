/**
 * @author Jaime Hernández
 * 
 * @dni 48776654W
 */

package es.ua.dlsi.prog3.p2.model;
import es.ua.dlsi.prog3.p2.exceptions.NoTyreTypeException;
import es.ua.dlsi.prog3.p2.exceptions.PressureWheelException;

/**
 * La clase Wheel representa una rueda de un vehículo con su presión y tipo de neumático asociados.
 */
public class Wheel {
    private double pressure;
    private TyreType tyreType;

    /**
     * Constructor por defecto que inicializa la presión de la rueda a cero.
     */
    public Wheel() {
        this.pressure = 0;
    }

    /**
     * Constructor que inicializa una rueda con un tipo de neumático específico.
     *
     * @param tt Tipo de neumático para la rueda.
     */
    public Wheel(TyreType tt) {
        this.tyreType = tt;
    }

    /**
     * Constructor que crea una nueva rueda copiando la presión de otra rueda.
     *
     * @param w Rueda de la cual copiar la presión.
     */
    public Wheel(Wheel w) {
        this.pressure = w.pressure;
    }

    /**
     * Establece el tipo de neumático para la rueda.
     *
     * @param tt Tipo de neumático para la rueda.
     */
    public void setTyreType(TyreType tt) {
        this.tyreType = tt;
    }

    /**
     * Obtiene el tipo de neumático asociado a la rueda.
     *
     * @return Tipo de neumático de la rueda.
     */
    public TyreType getTyreType() {
        return this.tyreType;
    }

    /**
     * Infla la rueda a una cierta presión, verificando si la presión está dentro de los límites permitidos por el tipo de neumático.
     *
     * @param inf Presión a la cual se debe inflar la rueda.
     * 
     * @throws NoTyreTypeException Si no se ha especificado un tipo de neumático para la rueda.
     * 
     * @throws PressureWheelException Si la presión está fuera de los límites permitidos por el tipo de neumático.
     */
    public void inflate(double inf) throws NoTyreTypeException, PressureWheelException {
        if (inf < 0) {
            throw new IllegalArgumentException();
        }
        if (tyreType == null) {
            throw new NoTyreTypeException();
        }
        if (tyreType.getMaxPressure() < inf || tyreType.getMinPressure() > inf) {
            throw new PressureWheelException(inf);
        }
        this.pressure = inf;
    }
}

