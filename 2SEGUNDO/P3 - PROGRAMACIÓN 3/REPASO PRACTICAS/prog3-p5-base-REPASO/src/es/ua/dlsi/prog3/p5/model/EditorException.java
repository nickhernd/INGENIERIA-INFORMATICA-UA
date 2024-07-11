package es.ua.dlsi.prog3.p5.model;

/**
 * Used for any exception of the editor
 * @author David Rizo - drizo@dlsi.ua.es
 * @created 18/10/22
 */
public class EditorException extends Exception {
	/**
	 * Serializable ID
	 */
    private static final long serialVersionUID = 1328665563832893091L;

	/**
     * Constructor
     * @param message Exception message
     */
    public EditorException(String message) {
        super(message);
    }
}
