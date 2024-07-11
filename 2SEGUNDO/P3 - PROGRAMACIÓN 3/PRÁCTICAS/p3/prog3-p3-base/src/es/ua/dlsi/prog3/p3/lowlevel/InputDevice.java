/**
 * @author: Jaime Hernández
 * @dni: 48776654W
 */

package es.ua.dlsi.prog3.p3.lowlevel;
import java.nio.BufferOverflowException;

/**
 * La clase InputDevice representa un dispositivo de entrada que extiende la clase IODevice.
 * Esta clase proporciona métodos para manejar la entrada de datos a través de un canal de entrada.
 */
public class InputDevice extends IODevice {

    /**
     * Constructor protegido de la clase InputDevice. Llama al constructor de la clase padre IODevice.
     */
    protected InputDevice() {
        super();
    }

    /**
     * Método protegido para colocar un array de bytes en el canal de entrada.
     *
     * @param p Array de bytes que se va a colocar en el canal de entrada.
     * 
     * @throws BufferOverflowException Se lanza si el canal está lleno y no puede aceptar más datos.
     */
    protected void put(byte[] p) throws BufferOverflowException {
        for (int i = 0; i < p.length; i++) {
            if (getChannel().isFull()) {
                throw new BufferOverflowException();
            }
            getChannel().input(p[i]);
        }
    }

    /**
     * Método protegido para enviar un byte al canal de entrada.
     *
     * @param s Byte que se va a enviar al canal de entrada.
     * 
     * @throws BufferOverflowException Se lanza si el canal está lleno y no puede aceptar más datos.
     */
    protected void sendToChannel(byte s) throws BufferOverflowException {
        if (getChannel().isFull()) {
            throw new BufferOverflowException();
        }
        getChannel().input(s);
    }
}
