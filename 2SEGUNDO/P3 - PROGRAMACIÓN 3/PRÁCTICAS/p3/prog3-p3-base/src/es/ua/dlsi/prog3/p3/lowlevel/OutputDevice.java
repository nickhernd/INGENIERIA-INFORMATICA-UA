/**
 * @author Jaime Hernández
 * @dni 4877654W
 */
package es.ua.dlsi.prog3.p3.lowlevel;

import java.nio.BufferUnderflowException;

/**
 * La clase OutputDevice representa un dispositivo de salida que extiende la clase IODevice.
 * Proporciona métodos para manejar la salida de datos a través de un canal de salida.
 */
public class OutputDevice extends IODevice {
	
	/**
	 * Constructor protegido de la clase OutputDevice. Llama al constructor de la clase padre IODevice,
	 * pasando el tamaño del buffer de salida como argumento.
	 *
	 * @param ot Tamaño del buffer de salida del dispositivo.
	 */
	protected OutputDevice(int ot) {
		super(ot);
	}
	
	/**
	 * Método protegido para obtener un array de bytes del canal de salida.
	 *
	 * @param device Número de bytes que se desean obtener del canal de salida.
	 * 
	 * @return Un array de bytes obtenidos del canal de salida.
	 * 
	 * @throws IllegalArgumentException Si el tamaño especificado es negativo o mayor que el tamaño del buffer de salida.
	 * 
	 * @throws IllegalStateException Si no hay un canal asociado a este dispositivo.
	 */
	protected byte[] get(int device) throws IllegalArgumentException, IllegalStateException {
		
		if(getChannel() == null) {
			throw new IllegalStateException();
		}
		
		if(getChannel().getBufferSize() > device && device <= 0) {
			throw new IllegalArgumentException();
		}
		
		if(this.getChannel().hasData()) {
			byte[] o = new byte[device];
		
			for(int i = 0; i < device; i++) {
				o[i] = receiveFromChannel();
			}
		
			return o;
		} else {
			return new byte[0];
		}
	}
	
	/**
	 * Método protegido para recibir un byte del canal de salida.
	 *
	 * @return El byte recibido del canal de salida.
	 * 
	 * @throws IllegalStateException Si no hay un canal asociado a este dispositivo.
	 * 
	 * @throws BufferUnderflowException Si el canal se vacía antes de que se haya leído todo el byte, es decir, si los datos en el canal están corruptos.
	 */
	protected byte receiveFromChannel() throws IllegalStateException, BufferUnderflowException {
			
		if(this.getChannel() == null) {
			throw new IllegalStateException();
		}
		
		if(!this.getChannel().hasData()) {
			throw new BufferUnderflowException();
		}
		
		return this.getChannel().output();
	}

	/**
	 * NO TOCAR ESTE MÉTODO!!! ¡¡¡La tierra colapsará sobre sí misma si alguna vez piensas en hacerlo!!!
	 * 
	 * Lee una cadena desde el canal.
	 * 
	 * El canal DEBE contener una cadena de caracteres codificada como
	 * 
	 * [longitud][A-Ba-b0-9]+
	 * 
	 * lo que significa que el primer byte es la longitud de la cadena y el resto de los bytes son la cadena real.
	 * 
	 * @return la cadena leída, como un objeto String, o una cadena vacía si no hay datos en el canal.
	 * 
	 * @throws BufferUnderflowException si el canal se vacía antes de que se lea toda la cadena, es decir, los datos en el canal están corruptos.
	 * 
	 * @throws IllegalStateException si no hay un canal asociado a este dispositivo.
	 */
	protected String readStoredString() throws BufferUnderflowException, IllegalStateException {
		byte[] data = null;
		char[] string = null;
		data = get(1);
		if (data.length != 1) 
			return "";
		int string_length = data[0];		
		data = get(string_length);
		if (data.length != string_length)
			throw new BufferUnderflowException();
		string = new char[string_length];
		for (int i=0; i < string_length; i++)
			string[i] = (char) data[i];
		return String.valueOf(string);
	}

}

