package es.ua.dlsi.prog3.p6.algorithms;

import static org.junit.Assert.*;

import java.util.Arrays;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import es.ua.dlsi.prog3.p6.graph.Graph;
import es.ua.dlsi.prog3.p6.graph.Node;
import es.ua.dlsi.prog3.p6.graph.NodeNotFoundException;

class StringGraph extends Graph<String, String> {	
}


public class DFSTest {
	/**
	 * This graph is initialized in initGraph before each test
	 */
	private StringGraph graph;
	/**
	 * Initialized in initGraph
	 */
	private Node<String> node0;
	/**
	 * Initialized in initGraph
	 */
	private Node<String> node1;
	/**
	 * Initialized in initGraph
	 */
	private Node<String> node2;
	/**
	 * Initialized in initGraph
	 */
	private Node<String> node3;
	
	/**
	 * It creates the graph of four nodes (0, 1, 2, 3) with this edges:
	 * N0 -> N1, N0 -> N2, N1 -> N2, N2 -> N0, N2 -> N3, N3 -> N3 
	 * @throws NodeNotFoundException  This should never be thrown
	 */
	@Before
	public void initGraph() throws NodeNotFoundException {
		graph = new StringGraph();
		node0 = graph.addNode("N0");
		node1 = graph.addNode("N1");
		node2 = graph.addNode("N2");
		node3 = graph.addNode("N3");
		graph.addEdge(node0, null, node1);
		graph.addEdge(node0, null, node2);
		graph.addEdge(node1, null, node2);
		graph.addEdge(node2, null, node0);
		graph.addEdge(node2, null, node3);
		graph.addEdge(node3, null, node3);
	}
	
	/**
	 * It performs a DFS traversal
	 */
	@Test
	public void testRun() {
		DFS<String, String> dfs = new DFS<>(graph);
		dfs.run(node2);
		List<Node<String>> expected = Arrays.asList(node2, node0, node1, node3);
		List<Node<String>> actual = dfs.getVisitSequence();
		assertEquals("DFS sequence", expected, actual);
	}

}
