package es.ua.dlsi.prog3.p6.algorithms;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import es.ua.dlsi.prog3.p6.graph.Edge;
import es.ua.dlsi.prog3.p6.graph.Graph;
import es.ua.dlsi.prog3.p6.graph.Node;
import es.ua.dlsi.prog3.p6.graph.NodeNotFoundException;

/**
 * Depth-first search algorithm. The algorithm starts at the root node 
 * (selecting some arbitrary node as the root node in the case of a graph) 
 * and explores as far as possible along each branch before backtracking.  
 * çSo the basic idea is to start from the root or any arbitrary node and mark 
 * the node and move to the adjacent unmarked node and continue this loop until 
 * there is no unmarked adjacent node. 
 * Then backtrack and check for other unmarked nodes and traverse them. 
 * Finally, print the nodes in the path.
 * Package visibility for force clients using the facade Algorithms class
 * (see https://www.geeksforgeeks.org/depth-first-search-or-dfs-for-a-graph/)
 * @author drizo
 */
class DFS<NodeLabelType, EdgeLabelType> {
	/**
	 * Graph to perform the DFS
	 */
	private final Graph<NodeLabelType, EdgeLabelType> graph;
	/**
	 * Using a set to speed up search
	 */
	private Set<Node<NodeLabelType>> visited;
	/**
	 * Ordered list of visited nodes 
	 */
	private List<Node<NodeLabelType>> visitSequence;
	
	/**
	 * It initializes the structures required for the DFS algorithm 
	 * @param graph Graph to run the DFS
	 */
	public DFS(Graph<NodeLabelType, EdgeLabelType> graph) {
		this.graph = graph;
		visited = new HashSet<>();
		this.visitSequence = new LinkedList<>();
	}

	/**
	 * It traverses the graph from the given node
	 * @param node Current node
	 */
	public void run(Node<NodeLabelType> node) {
		visited.add(node);
		visitSequence.add(node);
		// Recur for all the vertices adjacent to this
		try {
			Set<Edge<NodeLabelType, EdgeLabelType>> outEdges = graph.getEdgesConnectedTo(node);
				for (Edge<NodeLabelType, EdgeLabelType> edge: outEdges) {
				if (!visited.contains(edge.getTarget())) {
					run(edge.getTarget());
				}
			}
		} catch (NodeNotFoundException e) {
			throw new RuntimeException(e); // this can only happen if the node does not belong to the graph, and we are traversing the nodes of the graph
		}
	}

	/**
	 * Result deep copy of the traversal
	 * @return List of visited nodes
	 */
	public List<Node<NodeLabelType>> getVisitSequence() {
		LinkedList<Node<NodeLabelType>> result = new LinkedList<>();
		for (Node<NodeLabelType> node: visitSequence) {
			result.add(new Node<>(node));
		}
		return result;
	}
	
	
}
