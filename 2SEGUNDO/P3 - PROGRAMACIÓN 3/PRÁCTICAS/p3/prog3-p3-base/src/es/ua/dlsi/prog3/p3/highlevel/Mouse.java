package es.ua.dlsi.prog3.p3.highlevel;

import es.ua.dlsi.prog3.p3.lowlevel.InputDevice;

/**
 * La clase Mouse representa un dispositivo de entrada que extiende la clase InputDevice.
 * Se utiliza para enviar las coordenadas x y y al canal de entrada.
 */
public class Mouse extends InputDevice {

    /**
     * Constructor de la clase Mouse. Llama al constructor de la clase padre InputDevice.
     */
    public Mouse() {
        super();
    }

    /**
     * Envía las coordenadas x y y al canal de entrada.
     *
     * @param x Coordenada x que se enviará al canal de entrada.
     * 
     * @param y Coordenada y que se enviará al canal de entrada.
     * 
     * @throws IllegalStateException Si no hay un canal asociado a este dispositivo.
     */
    public void put(byte x, byte y) throws IllegalStateException {
        if (this.getChannel() == null) {
            throw new IllegalStateException(); // Lanza una excepción si no hay un canal asociado a este dispositivo.
        }

        this.sendToChannel(x); // Envía la coordenada x al canal de entrada.
        this.sendToChannel(y); // Envía la coordenada y al canal de entrada.
    }
}
