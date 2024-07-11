package es.ua.dlsi.prog3.p6.graph;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import org.junit.Before;
import org.junit.Test;

/**
 * @author drizo
 */
public class GraphTests {
	/**
	 * This graph is initialized in initGraph before each test
	 */
	private Graph<String, String> graph;
	/**
	 * Initialized in initGraph
	 */
	private Node<String> nodeA;
	/**
	 * Initialized in initGraph
	 */
	private Node<String> nodeB;
	/**
	 * Initialized in initGraph
	 */
	private Node<String> nodeC;
	/**
	 * Initialized in initGraph
	 */
	private Node<String> nodeD;
	/**
	 * Initialized in initGraph
	 */
	private Edge<String, String> edgeAB;
	/**
	 * Initialized in initGraph
	 */
	private Edge<String, String> edgeAD;
	/**
	 * Initialized in initGraph
	 */
	private Edge<String, String> edgeBC;
	/**
	 * Initialized in initGraph
	 */
	private Edge<String, String> edgeCD;		
	
	/**
	 * It creates the graph (read it top down): i.e., a is connected to b and d, and b to c and d, c to d
	 * a -> b
	 *   x
	 * c -> d
	 * @throws NodeNotFoundException  This should never be thrown
	 */
	@Before
	public void initGraph() throws NodeNotFoundException {
		graph = new Graph<String, String>();
		nodeA = graph.addNode("a");
		nodeB = graph.addNode("b");
		nodeC = graph.addNode("c");
		nodeD = graph.addNode("d");
		edgeAB = graph.addEdge(nodeA, null, nodeB);
		edgeAD = graph.addEdge(nodeA, null, nodeD);
		edgeBC = graph.addEdge(nodeB, null, nodeC);
		edgeCD = graph.addEdge(nodeC, null, nodeD);		
	}
	
	@Test
	public void testSize() {
		assertEquals(4, graph.getSize());
	}
	/**
	 * We test that two different nodes have a different hashCode and that the same node, 
	 * after changing the label, has the same hashCode.
	 * We check that two nodes with the same label and a different ID have different hash codes 
	 */
	@Test
	public void testNodeHashCode() {
		Node<String> a = new Node<String>(1, "A");
		Node<String> b = new Node<String>(2, "B");
		Node<String> c = new Node<String>(3, "A");
		assertNotEquals(a.hashCode(), b.hashCode());
		assertNotEquals(a.hashCode(), c.hashCode());
		int hashCode = a.hashCode();
		a.setLabel("A2");
		assertEquals(hashCode, a.hashCode());
	}

	/**
	 * We test that two different labels have a different hashCode and that the same node, 
	 * after changing the label, has the same hashCode.
	 * We check that two nodes with the same label and a different ID have different hash codes 
	 */
	@Test
	public void testEdgeHashCode() {
		Node<String> a = new Node<String>(1, "A");
		Node<String> b = new Node<String>(2, "B");
		Edge<String, String> ab = new Edge<String, String>(1, a, "ab", b);
		Edge<String, String> ba = new Edge<String, String>(2, b, "ba", a);		
		assertNotEquals(ab.hashCode(), ba.hashCode());		
		int hashCode = ab.hashCode();
		ab.setLabel("ab2");
		assertEquals(hashCode, ab.hashCode());
	}	
	
	/**
	 * It tests that addEdge checks the node exists in graph
	 * @throws NodeNotFoundException
	 */
	@Test(expected = NodeNotFoundException.class)
	public void testAddEdgeNodeNotFound() throws NodeNotFoundException {
		Graph<String, String> graph1 = new Graph<String, String>();
		Node<String> graph1A = graph1.addNode("A");
		
		Graph<String, String> graph2 = new Graph<String, String>();
		Node<String> graph2A = graph2.addNode("A");

		graph1.addEdge(graph1A, graph2A); // it must throw an exception, graph2A does not belong to graph1
	}
	
	/**
	 * It tests all connections
	 * @throws NodeNotFoundException It should never be thrown
	 */
	@Test
	public void testGetEdgesConnectedTo() throws NodeNotFoundException {
		Set<Edge<String, String>> edgesConnectingA = graph.getEdgesConnectedTo(nodeA);
		Set<Edge<String, String>> expectedA = new HashSet<>(Arrays.asList(edgeAB, edgeAD));
		assertEquals("Edges connecting A", expectedA, edgesConnectingA);
		Set<Edge<String, String>> edgesConnectingC = graph.getEdgesConnectedTo(nodeC);
		Set<Edge<String, String>> expectedC = new HashSet<>(Arrays.asList(edgeBC, edgeCD));
		assertEquals("Edges connecting C", expectedC, edgesConnectingC);		
	}
	
	/**
	 * It tests that getEdgesConnectedTo checks the node exists in graph
	 */
	@Test(expected = NodeNotFoundException.class)
	public void testGetEdgesConnectedToNodeNotFound() throws NodeNotFoundException {
		Graph<String, String> graph1 = new Graph<String, String>();
		graph1.addNode("A");
		
		Graph<String, String> graph2 = new Graph<String, String>();
		Node<String> graph2A = graph2.addNode("A");

		graph1.getEdgesConnectedTo(graph2A); // it must throw an exception, graph2A does not belong to graph1
	}
	
	/**
	 * It tests the contains method
	 * @throws NodeNotFoundException It should never be thrown
	 */
	@Test
	public void testContains() throws NodeNotFoundException {
		Graph<String, String> graph1 = new Graph<String, String>();
		Node<String> node1A = graph1.addNode("A");
		
		Graph<String, String> graph2 = new Graph<String, String>();
		Node<String> node2A = graph2.addNode("A");

		assertTrue(graph1.contains(node1A));
		assertFalse(graph1.contains(node2A));
		assertTrue(graph2.contains(node2A));
		assertFalse(graph2.contains(node1A));
	}	
	/**
	 * It tests all connections
	 * @throws NodeNotFoundException It should never be thrown
	 */
	@Test
	public void testOutEdges() throws NodeNotFoundException {
		Set<Edge<String, String>> edgesFromA = graph.getOutEdges(nodeA);
		Set<Edge<String, String>> expectedA = new HashSet<>(Arrays.asList(edgeAB, edgeAD));
		assertEquals("Edges leaving A", expectedA, edgesFromA);
		Set<Edge<String, String>> edgesConnectingC = graph.getOutEdges(nodeC);
		Set<Edge<String, String>> expectedC = new HashSet<>(Arrays.asList(edgeCD));
		assertEquals("Edges leaving C", expectedC, edgesConnectingC);		
	}
	
	/**
	 * It tests that getEdgesConnectedTo checks the node exists in graph
	 */
	@Test(expected = NodeNotFoundException.class)
	public void testOutEdgesNodeNotFound() throws NodeNotFoundException {
		Graph<String, String> graph1 = new Graph<String, String>();
		graph1.addNode("A");
		
		Graph<String, String> graph2 = new Graph<String, String>();
		Node<String> graph2A = graph2.addNode("A");

		graph1.getOutEdges(graph2A); // it must throw an exception, graph2A does not belong to graph1
	}	
}
