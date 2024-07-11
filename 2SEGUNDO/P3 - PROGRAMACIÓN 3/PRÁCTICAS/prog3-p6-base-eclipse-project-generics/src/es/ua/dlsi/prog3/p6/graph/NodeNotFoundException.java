package es.ua.dlsi.prog3.p6.graph;

/**
 * Thrown when a node does not belong to the graph
 * @author drizo
 */
public class NodeNotFoundException extends Exception {
	/**
	 * Avoids Eclipse serialization warning
	 */
	private static final long serialVersionUID = -1565977518844974853L;
	/**
	 * Erroneous node
	 */
	private final Node<?> node;

	/**
	 * It initializes the message using the node
	 * @param node Erroneous node
	 */
	public NodeNotFoundException(Node<?> node) {
		super("Node " + node + " not found");
		this.node = node;
	}

	/**
	 * Getter
	 * @return Erroneous node
	 */
	public Node<?> getNode() {
		return node;
	}
}
