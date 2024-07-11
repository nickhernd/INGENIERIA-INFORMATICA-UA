package es.ua.dlsi.prog3.p3.highlevel;

import java.nio.BufferUnderflowException;

import es.ua.dlsi.prog3.p3.highlevel.exceptions.NoLineForPrintingException;
import es.ua.dlsi.prog3.p3.lowlevel.OutputDevice;

/**
 * La clase LinePrinter representa un dispositivo de salida que extiende la clase OutputDevice.
 * Se utiliza para imprimir líneas de texto almacenadas en el canal de salida.
 */
public class LinePrinter extends OutputDevice {
	private static final int MAX_LINE_LENGTH = 80; // Longitud máxima de la línea de impresión.

    /**
     * Constructor de la clase LinePrinter. Llama al constructor de la clase padre OutputDevice,
     * especificando el tamaño del buffer de salida como la longitud máxima de la línea más uno para el carácter de nueva línea.
     */
    public LinePrinter() {
        super(MAX_LINE_LENGTH + 1);
    }

    /**
     * Imprime una línea de texto almacenada en el canal de salida.
     *
     * @return La línea de texto a imprimir.
     * 
     * @throws NoLineForPrintingException Si no hay ninguna línea disponible para imprimir en el canal de salida.
     */
    public String printLine() throws NoLineForPrintingException {
        if(!getChannel().hasData()) {
        	throw new NoLineForPrintingException();
        }
        return readStoredString();
    }
}


