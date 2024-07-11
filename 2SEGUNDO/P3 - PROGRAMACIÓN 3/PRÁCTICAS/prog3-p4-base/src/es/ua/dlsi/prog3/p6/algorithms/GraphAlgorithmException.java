package es.ua.dlsi.prog3.p6.algorithms;

/**
 * General purpose exception launched by any library algorithm
 * @author drizo
 */
public class GraphAlgorithmException extends Exception {
	/**
	 * It avoids Eclipse warning
	 */
	private static final long serialVersionUID = 8464139128561353111L;

	/**
	 * Constructor
	 * @param message Exception message
	 */
	public GraphAlgorithmException(String message) {
		super(message);
	}
}
