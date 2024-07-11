package es.ua.dlsi.prog3.p6.graph;

/**
 * It represents an edge between nodes. Note that there may exist several edges linking the same two nodes
 * @author drizo
 */
public class Edge<NodeLabelType, EdgeLabelType> extends GraphObject {
	/**
	 * The origin of the edge
	 */
	private Node<?> source;
	/**
	 * The end of the edge
	 */
	private Node<?> target;
	/**
	 * Package visibility because only Graph must be able to create it.
	 * @param id Unique id
	 * @param source Origin of the edge
	 * @param label The content that will be stored in the label
	 * @param target Destination of the edge
	 */
	Edge(int id, Node<?> source, String label, Node<?> target) {
		super(id, label);
		if (source == null) {
			throw new IllegalArgumentException("Missing source");
		}
		this.source = source;
		if (target == null) {
			throw new IllegalArgumentException("Missing target");
		}
		this.target = target;
	}
	/**
	 * Copy constructor. Package visibility because only Graph must be able to create it.
	 * @param edge Edge to be copied
	 */	
	Edge(Edge<?, ?> edge) {
		super(edge);
		this.source = edge.source; // it's not required to check of it is null because the other edge is already valid
		this.target = edge.target;
	}	
	/**
	 * Getter
	 * @return Source node
	 */
	public Node<?> getSource() {
		return source;
	}


	/**
	 * Getter
	 * @return Target node
	 */
	public Node<?> getTarget() {
		return target;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Edge<?, ?> other = (Edge<?, ?>) obj;
		if (source == null) {
			if (other.source != null)
				return false;
		} else if (!source.equals(other.source))
			return false;
		if (target == null) {
			if (other.target != null)
				return false;
		} else if (!target.equals(other.target))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return source.toString() + " [" + (label != null ? label : "") + "] " + target.toString();
	}
}
