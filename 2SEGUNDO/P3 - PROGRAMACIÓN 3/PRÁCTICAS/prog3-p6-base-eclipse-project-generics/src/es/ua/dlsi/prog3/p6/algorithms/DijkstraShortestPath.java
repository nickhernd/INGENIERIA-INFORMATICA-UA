package es.ua.dlsi.prog3.p6.algorithms;

import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Set;

import es.ua.dlsi.prog3.p6.graph.Edge;
import es.ua.dlsi.prog3.p6.graph.Graph;
import es.ua.dlsi.prog3.p6.graph.Node;
import es.ua.dlsi.prog3.p6.graph.NodeNotFoundException;

/**
 * Dijkstra’s shortest path using a PriorityQueue.
 * Implementation based on https://www.geeksforgeeks.org/dijkstras-shortest-path-algorithm-in-java-using-priorityqueue/
 * Package visibility for force clients using the facade Algorithms class
 * @author drizo
 */
class DijkstraShortestPath<NodeLabelType, EdgeLabelType extends Comparable<EdgeLabelType>> {
	/**
	 * Helper class implementing Comparator interface
	 */
	class Cost implements Comparator<Cost> {	 
		/**
		 * Node
		 */
	    Node<NodeLabelType> node;
	    /**
	     * Cost
	     */
	    int cost;
	 
	    /**
	     * Default constructor
	     */
	    Cost() {	    	
	    }
	 
	    /**
	     * Constructor using fields
	     * @param node Node
	     * @param cost Cost
	     */
	    Cost(Node<NodeLabelType> node, int cost)
	    {
	        this.node = node;
	        this.cost = cost;
	    }
	 
	    @Override 
	    public int compare(Cost node1, Cost node2)
	    {	 
	        if (node1.cost < node2.cost) {
	            return -1;
	        }
	 
	        if (node1.cost > node2.cost) {
	            return 1;
	        }
	 
	        return 0;
	    }
	}
	
	/**
	 * Graph to work on
	 */
	private final Graph<NodeLabelType, EdgeLabelType> graph;
	
	/**
	 * Distances
	 */
	private Map<Node<NodeLabelType>, Integer> dist;

	/**
	 * Settled nodes
	 */
    private Set<Node<NodeLabelType>> settled;

	/**
	 * Priority queue for the implementation of the Dijkstra's algorithm
	 */
	private PriorityQueue<Cost> priorityQueue;
	
	private ICostOperators<EdgeLabelType> costOperators;

	/**
	 * Constructor
	 * @param graph Graph to work on
	 */
	public DijkstraShortestPath(Graph<NodeLabelType, EdgeLabelType> graph, ICostOperators<EdgeLabelType> costOperators) {
		this.graph = graph;
		this.costOperators = costOperators;
	}
	
	/**
	 * It initializes the local structures and computes the shortest path
	 * @param fromNode Source node
	 * @throws NodeNotFoundException 
	 */
	public void compute(Node<NodeLabelType> fromNode) throws NodeNotFoundException {
		if (!graph.contains(fromNode)) {
			throw new NodeNotFoundException(fromNode);
		}
		this.dist = new HashMap<>();
		this.settled = new HashSet<>();
		this.priorityQueue = new PriorityQueue<Cost>(graph.getSize(), new Cost());
		for (Node<NodeLabelType> n: graph.getNodes()) {
			dist.put(n, Integer.MAX_VALUE);
		}
		
        // Add source node to the priority queue
        priorityQueue.add(new Cost(fromNode, (int) costOperators.zero()));
 
        // Distance to the source is 0
        dist.put(fromNode, (Integer) costOperators.zero());
 
        while (settled.size() != graph.getSize()) {
 
            // Terminating condition check when
            // the priority queue is empty, return
            if (priorityQueue.isEmpty()) {
                return;
            }
 
            // Removing the minimum distance node
            // from the priority queue
            Node<NodeLabelType> u = priorityQueue.remove().node;
 
            // Adding the node whose distance is
            // finalized
            if (settled.contains(u)) { 
                // Continue keyword skips execution for
                // following check
                continue;
            }
 
            // We don't have to call e_Neighbors(u)
            // if u is already present in the settled set.
            settled.add(u);
 
            processNeighbours(u);
        }	
	}
	
	/**
	 * It processes all the neighbours of the passed node
	 * @param source Node
	 */
    @SuppressWarnings("unchecked")
	private void processNeighbours(Node<NodeLabelType> source) { 
        // All the neighbors of v
        try {
			for (Edge<NodeLabelType, EdgeLabelType> edge: graph.getOutEdges(source)) {
				Node<NodeLabelType> target = (Node<NodeLabelType>) edge.getTarget();
 
			    // If current node hasn't already been processed
			    if (!settled.contains(target)) {
			    	// If new distance is cheaper in cost
			    	EdgeLabelType edgeDistance = (EdgeLabelType) edge.getLabel();
			    	EdgeLabelType newDistance = costOperators.add((EdgeLabelType) dist.get(source), edgeDistance);
			    	 
			    	// If new distance is cheaper in cost
			    	if (newDistance.compareTo((EdgeLabelType) dist.get(target)) < 0) {
			            dist.put(target, (Integer) newDistance);
			        }
 
			        // Add the current node to the queue
			        priorityQueue.add(new Cost(target, dist.get(target)));
			    }
			}
		} catch (NumberFormatException | NodeNotFoundException e) {
			// the node should always be in the graph
			// The graph should contain the right data at the edges
			throw new RuntimeException(e);
		}
    }
    
    /**
     * It returns the computed distance to the given node
     * @return Defensive copy of the nodes with the corresponding distances
     * @throws GraphAlgorithmException If the compute method has not been invoked
     */
	public Map<Node<NodeLabelType>, Integer> getComputedDistances() throws GraphAlgorithmException {
		if (dist == null) {
			throw new GraphAlgorithmException("Compute has not been invoked first");
		}
		Map<Node<NodeLabelType>, Integer> result = new HashMap<>();
		for (Map.Entry<Node<NodeLabelType>, Integer> entry: dist.entrySet()) {
			result.put(new Node<NodeLabelType>(entry.getKey()), entry.getValue());
		}
		return result;
	}	
    
    
}
