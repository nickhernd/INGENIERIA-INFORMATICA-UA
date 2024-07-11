/**
 * @author Jaime Hernández
 * 
 * @dni 48776654W
 */
package es.ua.dlsi.prog3.p2.model;
import java.util.ArrayList;

import es.ua.dlsi.prog3.p2.exceptions.PressureWheelException;
import es.ua.dlsi.prog3.p2.exceptions.TooManyWheelsException;
import es.ua.dlsi.prog3.p2.exceptions.WrongTyreTypeException;

/**
 * La clase Car representa un vehículo con ruedas y proporciona métodos para agregar ruedas y cambiar los neumáticos.
 */
public class Car {
    private final int MAX_WHEEL = 4;
    private ArrayList<Wheel> wheels;
    
    /**
     * Constructor por defecto que inicializa la lista de ruedas del coche.
     */
    public Car() {
        this.wheels = new ArrayList<Wheel>();
    }
    
    /**
     * Constructor que crea un nuevo objeto Car copiando las ruedas de otro objeto Car.
     *
     * @param c Objeto Car del cual copiar las ruedas.
     */
    public Car(Car c) {
        this.wheels = new ArrayList<Wheel>(c.wheels);
    }
    
    /**
     * Agrega una rueda al coche, verificando si hay espacio y si el tipo de neumático es adecuado.
     *
     * @param w Rueda a ser agregada al coche.
     * 
     * @throws TooManyWheelsException Si se intenta agregar más ruedas de las permitidas.
     * 
     * @throws WrongTyreTypeException Si el tipo de neumático de la nueva rueda no coincide con el tipo de las ruedas existentes.
     * 
     */
    public void addWheel(Wheel w) throws TooManyWheelsException, WrongTyreTypeException {
        if (wheels.size() == 0 || wheels == null) {
            wheels.add(w);
        } else {
            if (wheels.size() >= MAX_WHEEL) {
                throw new TooManyWheelsException();
            }
            if (w.getTyreType() != wheels.get(0).getTyreType()) {
                throw new WrongTyreTypeException();
            }
            wheels.add(w);
        }
    }
    
    /**
     * Obtiene una copia de la lista de ruedas del coche.
     *
     * @return Lista de ruedas del coche.
     */
    public ArrayList<Wheel> getWheels() {
        return new ArrayList<Wheel>(this.wheels);
    }
    
    /**
     * Cambia el tipo de neumáticos de todas las ruedas del coche y ajusta la presión de los neumáticos.
     *
     * @param tt Tipo de neumático al que se deben cambiar todas las ruedas.
     * 
     * @param pressure Presión a la que se deben inflar las ruedas.
     * 
     * @throws PressureWheelException Si hay un problema con la presión de las ruedas.
     * 
     */
    public void changeTyres(TyreType tt, double pressure) throws PressureWheelException {
        for (int i = 0; i <= wheels.size(); i++) {
            if (tt != null) {
                wheels.get(i).setTyreType(tt);
            } else {
                throw new IllegalArgumentException();
            }
            try {
                wheels.get(i).inflate(pressure);
            } catch (PressureWheelException e) {
                throw e;
            } catch (Exception e) {
                throw new RuntimeException();
            }
        }
    }
}
