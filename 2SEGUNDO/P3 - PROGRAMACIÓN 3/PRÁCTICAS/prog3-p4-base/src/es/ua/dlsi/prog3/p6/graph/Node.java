package es.ua.dlsi.prog3.p6.graph;

/**
 * String node 
 * @author drizo
 */
public class Node<NodeLabelType> extends GraphObject<NodeLabelType> {
	/**
	 * Constructor from label. 
	 * Package visibility because only Graph must be able to create it.
	 * @param id Unique identifier of the node
	 * @param label Content for the label
	 */
	Node(int id, NodeLabelType label) {
		super(id, label);
	}
	/**
	 * Copy constructor. Public visibility because algorithms may use it
	 * @param node Node to be copied
	 */	
	public Node(Node<NodeLabelType> node) {
		super(node);
	}
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Node<NodeLabelType> other = (Node<NodeLabelType>) obj;
		if (label == null) {
			if (other.label != null)
				return false;
		} else if (!label.equals(other.label))
			return false;		
		if (uniqueID != other.uniqueID)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "node " + (label != null ? label : "") + "), ID = " + this.uniqueID; 
	}
	
	
}
