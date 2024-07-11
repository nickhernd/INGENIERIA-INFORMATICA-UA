
package es.ua.dlsi.prog3.p3.lowlevel;

import java.nio.BufferUnderflowException;

public class OutputDevice extends IODevice {


	/**
	 * DON'T TOUCH THIS METHOD!!! The earth will collapse on itself if you ever think of doing it!
	 * 
	 * Reads a string from the channel. 
	 * 
	 * The channel MUST contain a string of characteres encoded as
	 * 
	 * 
	 * [length][A-Ba-b0-9]+
	 * 
	 * which means that the first byte is the string length, and the rest of bytes are the actual string 
	 * 
	 * 
	 * @return the string read, as an String object, or an empty if there is no data in the channel 
	 * @throws BufferUnderflowException if the channel becomes empty before the whole string is read, i.e. the data in the channel is corrupted
	 * @throws IllegalStateException if there is no channel associated to this device 
	 */
	protected String readStoredString() {
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
