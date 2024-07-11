package es.ua.dlsi.prog3.p3.highlevel;
import java.nio.BufferOverflowException;

import es.ua.dlsi.prog3.p3.lowlevel.InputDevice;


/**
 * La clase Keyboard representa un dispositivo de entrada que extiende la clase InputDevice.
 * Se utiliza para enviar caracteres al canal de entrada.
 */
public class Keyboard extends InputDevice {

    /**
     * Constructor de la clase Keyboard. Llama al constructor de la clase padre InputDevice.
     */
    public Keyboard() {
        super();
    }

    /**
     * Envia un carácter al canal de entrada.
     *
     * @param c El carácter que se enviará al canal de entrada.
     * 
     * @throws BufferOverflowException Si el canal está lleno después de enviar el carácter.
     */
    public void put(char c) throws BufferOverflowException {
        this.sendToChannel((byte) c); // Envia el carácter al canal de entrada como byte.
        throw new BufferOverflowException(); // Lanza una excepción indicando que el canal está lleno.
    }
}
