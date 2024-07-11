package es.ua.dlsi.prog3.p6.algorithms;

import static org.junit.Assert.*;

import java.io.File;
import java.io.FileNotFoundException;

import org.junit.Before;
import org.junit.Test;

import es.ua.dlsi.prog3.p6.graph.Graph;
import es.ua.dlsi.prog3.p6.graph.Node;
import es.ua.dlsi.prog3.p6.graph.NodeNotFoundException;

/**
 * It tests the DijkstraShortestPath class. It uses the IntegerCostGraph class
 * @author drizo
 *
 */
public class DijkstraShortestPathTest {
	/**
	 * This graph is initialized in initGraph before each test
	 */
	private Graph<String, Double> graph;
	/**
	 * The 5 nodes of the graph
	 */
	private Node<String>[] nodes;
	/**
	 * Cost operator on integers
	 */
	private ICostOperators<Double> costOperators;

	/**
	 * It creates the graph of 5 nodes. It prints a .dot file with the graph that can be opened with GraphViz
	 * (e.g. using https://edotor.net or https://dreampuf.github.io/GraphvizOnline)
	 * @throws NodeNotFoundException  This should never be thrown
	 * @throws FileNotFoundException 
	 */
	@SuppressWarnings("unchecked") // for the Node[5] creation
	@Before
	public void initGraph() throws NodeNotFoundException, FileNotFoundException {
		costOperators = new ICostOperators<Double>() {
			
			@Override
			public Double zero() {
				return 0.0;
			}
			
			@Override
			public Double maximumValue() {
				return Double.MAX_VALUE;
			}
			
			@Override
			public Double add(Double a, Double b) {
				return a+b;
			}
		};
		
		graph = new Graph<>();
		nodes = new Node[5];
		for (int i=0; i<5; i++) {
			nodes[i] = graph.addNode("Node " + i);
		}
		graph.addEdge(nodes[0], 9.0, nodes[1]);
		graph.addEdge(nodes[0], 6.0, nodes[2]);
		graph.addEdge(nodes[0], 5.0, nodes[3]);
		graph.addEdge(nodes[0], 3.0, nodes[4]);
		graph.addEdge(nodes[2], 2.0, nodes[1]);
		graph.addEdge(nodes[2], 4.0, nodes[3]);
		
		DotExporter<String, Double> dotExporter = new DotExporter<>();
		// This only works on Mac / Linux - run with
		// File file = File.createTempFile("dijkstra_graph", ".dot"); use this for cross platform
		File file = new File("/tmp/dijkstra_graph.dot");
		dotExporter.export(file, graph);
	}
	
	/**
	 * It forces an exception
	 * @throws GraphAlgorithmException Should be thrown because the compute method has not been inkoved
	 * @throws NodeNotFoundException Should not be thrown 
	 */
	@Test(expected=GraphAlgorithmException.class)
	public void testNotComputed() throws GraphAlgorithmException, NodeNotFoundException {
		DijkstraShortestPath<String, Double> dijkstraShortestPath = new DijkstraShortestPath<>(graph, costOperators);
		dijkstraShortestPath.getComputedDistances();
	}

	/**
	 * It forces an exception on compute
	 * @throws GraphAlgorithmException Thrown because the graph does not have edges 
	 * @throws NodeNotFoundException Should not be thrown 
	 */	
	@Test(expected=NodeNotFoundException.class)
	public void testNoEdges() throws GraphAlgorithmException, NodeNotFoundException {
		Graph<String, Double> g2 = new Graph<>();
		Node<String> n1 = g2.addNode("1");
		DijkstraShortestPath<String, Double> dijkstraShortestPath = new DijkstraShortestPath<>(graph, costOperators);
		dijkstraShortestPath.compute(n1);
	}
	
	/**
	 * It forces an exception on compute
	 * @throws GraphAlgorithmException Should not be thrown
	 * @throws NodeNotFoundException Should be thrown because the node does not belong to the graph 
	 */	
	@Test(expected=NodeNotFoundException.class)
	public void testNodeNotFoundInCompute() throws GraphAlgorithmException, NodeNotFoundException {
		Graph<String, Double> g2 = new Graph<>();
		Node<String> n2 = g2.addNode("Other");
		DijkstraShortestPath<String, Double> dijkstraShortestPath = new DijkstraShortestPath<>(graph, costOperators);
		dijkstraShortestPath.compute(n2);
	}

	/**
	 * It forces an exception on getComputedDistance
	 * @throws GraphAlgorithmException Should not be thrown
	 * @throws NodeNotFoundException Should be thrown because the node does not belong to the graph 
	 */		
	@Test(expected=NodeNotFoundException.class)
	public void testNodeNotFoundInGetDistance() throws GraphAlgorithmException, NodeNotFoundException {
		Graph<String, Double> g2 = new Graph<>();
		Node<String> n2 = g2.addNode("Other");
		Algorithms.shortestPathCost(graph, nodes[1], n2, costOperators);
	}
		
	/**
	 * It evaluates the correct 
	 * @throws NodeNotFoundException
	 * @throws GraphAlgorithmException
	 */
	@Test
	public void testRun() throws NodeNotFoundException, GraphAlgorithmException {
		double [] expectedCosts = new double[] {0, 8, 6, 5, 3};
		for (int i=0; i<nodes.length; i++) {
			double  actualCost = Algorithms.shortestPathCost(graph, nodes[0], nodes[i], costOperators);
			assertEquals("Cost to node " + nodes[i], expectedCosts[i], actualCost, 0.01);
		}
	}
}
