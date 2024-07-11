package es.ua.dlsi.prog3.p6.graph;

/**
 * This singleton class is responsible of generating a unique ID
 * @author drizo
 */
public class IDGenerator {
	/**
	 * Unique ID sequence
	 */
	private int nextID; 
	/**
	 * Singleton class variable
	 */
	private static IDGenerator instance;
	
	/**
	 * Private to avoid being instantiated by a client class
	 */
	private IDGenerator() {
		nextID = 1;
	}
	
	/**
	 * Singleton entry method
	 * @return Static instance
	 */
	public static IDGenerator getInstance() {
		if (instance == null) {
			instance = new IDGenerator();
		}
		return instance;
	}
	
	/**
	 * It returns a unique ID
	 * @return Next id
	 */
	public int getNextID() {
		return nextID++;
	}

}
