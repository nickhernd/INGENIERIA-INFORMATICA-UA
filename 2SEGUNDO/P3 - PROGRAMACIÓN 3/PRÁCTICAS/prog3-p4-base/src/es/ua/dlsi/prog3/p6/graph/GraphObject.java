package es.ua.dlsi.prog3.p6.graph;

/**
 * Used for generalizing nodes and graphs. Both use labels and a unique ID
 * @author drizo
 */
public abstract class GraphObject<LabelType> implements IIdentifiable {
	/**
	 * Unique identifier of the node. 
	 * At least it must be present in the hashCode for distinguishing between nodes with the same label
	 */
	protected  final int uniqueID;

	/**
	 * Label of the element
	 */
	protected LabelType label;

	/**
	 * Constructor with ID and label
	 * @param uniqueID Unique ID
	 * @param label Label
	 */
	protected GraphObject(int uniqueID, LabelType label) {
		super();
		this.uniqueID = uniqueID;
		this.label = label;
	}
	
	/**
	 * Copy constructor. Used usually for dealing with compositions and defensive copy
	 * @param other Object to be copied
	 */
	protected GraphObject(GraphObject<LabelType> other) {
		this.uniqueID = other.uniqueID;
		this.label = other.label;
	}

	/**
	 * Constructor with empty label
	 * @param uniqueID Unique ID
	 */
	protected GraphObject(int uniqueID) {
		this(uniqueID, null);
	}
	
	/**
	 * Getter
	 * @return id
	 */
	@Override
	public int getUniqueID() {
		return uniqueID;
	}
	
	/**
	 * Getter
	 * @return the label
	 */
	public LabelType getLabel() {
		return label;
	}

	/**
	 * Setter
	 * @param label New label content
	 */
	public void setLabel(LabelType label) {
		this.label = label;
	}
		
	/**
	 * Only the uniqueID is used to avoid problems with label changes
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + uniqueID;
		return result;
	}	
}
