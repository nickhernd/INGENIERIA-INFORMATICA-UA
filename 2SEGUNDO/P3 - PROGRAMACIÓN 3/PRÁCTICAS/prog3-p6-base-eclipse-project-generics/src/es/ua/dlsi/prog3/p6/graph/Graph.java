package es.ua.dlsi.prog3.p6.graph;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * Directed graph class
 * @author drizo
 */
public class Graph<NodeLabelType, EdgeLabelType> {
	/**
	 * All the nodes in the graph
	 */
	private Set<Node<NodeLabelType>> nodes;

	/**
	 * All the edges in the graph
	 * Key = source node, key = edge
	 */
	private Map<Node<NodeLabelType>, Set<Edge<NodeLabelType,EdgeLabelType>>> edges;
	
	/**
	 * Default constructor, it initializes the nodes and edges collections
	 */
	public Graph() {
		this.nodes = new HashSet<>();
		this.edges = new HashMap<>();
	}	
	
	/**
	 * It creates a new node and adds it to the set of graph nodes
	 * @param nodeLabel Label of the new node
	 * @return The created node
	 */
	public Node<NodeLabelType> addNode(String nodeLabel) {
		Node<NodeLabelType> node = new Node<>(IDGenerator.getInstance().getNextID(), nodeLabel);
		nodes.add(node);
		edges.put(node, new HashSet<>()); 
		return node;
	}
	
	/**
	 * It creates a new edge and adds it to the set of graph edges
	 * @param source Add edge from this node
	 * @param edgeLabel Label of the new edge
	 * @param target Add edge to this node
	 * @return The created node
	 * @throws NodeNotFoundException It can only happen if one of the provided nodes belongs to other graph
	 */
	public Edge<NodeLabelType, EdgeLabelType> addEdge(Node<NodeLabelType> source, String edgeLabel, Node<NodeLabelType> target) throws NodeNotFoundException {
		
		
		if (!this.edges.containsKey(source)) {
			throw new NodeNotFoundException(source); 
		}

		if (!this.nodes.contains(target)) {
			throw new NodeNotFoundException(target);
		}
		
		Edge<NodeLabelType, EdgeLabelType> edge = new Edge<NodeLabelType, EdgeLabelType>(IDGenerator.getInstance().getNextID(), source, edgeLabel, target);
		Set<Edge<NodeLabelType,EdgeLabelType>> nodeOutEdges = this.edges.get(source);
		nodeOutEdges.add(edge);
		return edge;
	}
		
	/**
	 * It creates a new edge with empty label and adds it to the set of graph edges
	 * @param source Add edge from this node
	 * @param target Add edge to this node
	 * @return The created node
	 * @throws NodeNotFoundException It can only happen if one of the provided nodes belongs to other graph
	 */
	public Edge<NodeLabelType, EdgeLabelType> addEdge(Node<NodeLabelType> source, Node<NodeLabelType> target) throws NodeNotFoundException{
		return addEdge(source, null, target);
	}	
	/**
	 * Getter
	 * @return Defensive copy of the nodes
	 */
	public Set<Node<NodeLabelType>> getNodes() {
		HashSet<Node<NodeLabelType>> result = new HashSet<>();
		for (Node<NodeLabelType> node: nodes) {
			result.add(new Node<NodeLabelType>(node));
		}
		return result;
	}

	
	/**
	 * Getter
	 * @return Defensive / deep copy of the edges
	 */
	public Set<Edge<NodeLabelType,EdgeLabelType>> getEdges() {
		HashSet<Edge<NodeLabelType,EdgeLabelType>> result = new HashSet<>();
		for (Set<Edge<NodeLabelType,EdgeLabelType>> nodeEdges: edges.values()) {
			result.addAll(deepCopy(nodeEdges));
		}
		return result;
	}
	
	/**
	 * It creates a deep copy of the provided set of edges
	 * @param nodeEdges Edge set to be copied
	 * @return Deep clone of the set
	 */
	private Set<Edge<NodeLabelType,EdgeLabelType>> deepCopy(Set<Edge<NodeLabelType, EdgeLabelType>> nodeEdges) {
		HashSet<Edge<NodeLabelType,EdgeLabelType>> result = new HashSet<>();
		for (Edge<NodeLabelType,EdgeLabelType> edge: nodeEdges) {
			result.add(new Edge<NodeLabelType, EdgeLabelType>(edge));
		}
		return result;		
	}
	
	/**
	 * It returns all the edges (defensive copy) that have the node as source or target
	 * @param node Node of the graph
	 * @return Set of edges
	 * @throws NodeNotFoundException When the node does not belong to this graph
	 */
	public Set<Edge<NodeLabelType,EdgeLabelType>> getEdgesConnectedTo(Node<NodeLabelType> node) throws NodeNotFoundException {
		if (!nodes.contains(node)) {
			throw new NodeNotFoundException(node);
		}
		HashSet<Edge<NodeLabelType,EdgeLabelType>> result = new HashSet<>();
		Set<Edge<NodeLabelType,EdgeLabelType>> allEdges = this.getEdges();
		for (Edge<NodeLabelType, EdgeLabelType> edge: allEdges) {
			if (edge.getSource().getUniqueID() == node.getUniqueID() 
					|| edge.getTarget().getUniqueID() == node.getUniqueID()) {
				result.add(new Edge<NodeLabelType, EdgeLabelType>(edge));
			}
		}
		return result;		
	}
	
	/**
	 * It returns all the edges (defensive copy)  that have this node as source
	 * @param node Node of the graph
	 * @return Set of edges, empty if none is found
	 * @throws NodeNotFoundException When the node does not belong to this graph
	 */
	public Set<Edge<NodeLabelType,EdgeLabelType>> getOutEdges(Node<NodeLabelType> node) throws NodeNotFoundException {		
		if (!edges.containsKey(node)) {
			throw new NodeNotFoundException(node);
		}
		
		return deepCopy(edges.get(node));
	}
	
	/**
	 * It returns the number of nodes
	 * @return Number of nodes
	 */
	public int getSize() {
		return nodes.size();
	}

	/**
	 * It checks the node exists in the graph
	 * @param fromNode Node to be searched
	 * @return True if exists
	 */
	public boolean contains(Node<NodeLabelType> fromNode) {
		return nodes.contains(fromNode);
	}
	
	/**
	 * It checks the node has edges
	 * @return False it the edges set is empty
	 */
	public boolean hasEdges() {
		return !edges.isEmpty();
	}	
	
}
