package es.ua.dlsi.prog3.p3.highlevel;

import es.ua.dlsi.prog3.p3.lowlevel.OutputDevice;

/**
 * La clase Display representa un dispositivo de salida que extiende la clase OutputDevice y se utiliza para mostrar imágenes en una matriz bidimensional de píxeles.
 */
public class Display extends OutputDevice {
    private int pixels_row;
    private byte[][] display;

    /**
     * Constructor de la clase Display.
     *
     * @param N Número de píxeles por fila/columna en la pantalla.
     */
    public Display(int N) {
        super(N * N * 2);
        pixels_row = N;
        display = new byte[N][N];
    }

    /**
     * Obtiene el tamaño de la pantalla en términos de número de píxeles por fila/columna.
     *
     * @return Número de píxeles por fila/columna en la pantalla.
     */
    public int getDisplaySize() {
        return pixels_row;
    }

    /**
     * Actualiza la matriz de la pantalla basándose en los datos recibidos del canal de salida.
     *
     * @return Una copia de la matriz de la pantalla después de la actualización.
     */
    public byte[][] refresh() {
  
    	while (super.getChannel().hasData()) {
            byte x = super.receiveFromChannel();
            byte y = super.receiveFromChannel();
            display[x][y] = 1;
        }
        
        return display;
    }
    
    /**
     * Borra la pantalla, estableciendo todos los píxeles en estado inactivo (0).
     */
    public void clear() {
        for (int i = 0; i < pixels_row; i++) {
            for (int j = 0; j < pixels_row; j++) {
                display[i][j] = 0;
            }
        }
    }
}
