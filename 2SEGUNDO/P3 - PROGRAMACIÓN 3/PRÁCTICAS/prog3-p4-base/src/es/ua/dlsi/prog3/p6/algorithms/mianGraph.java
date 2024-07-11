package es.ua.dlsi.prog3.p6.algorithms;
import java.io.File;
import java.io.FileNotFoundException;

import es.ua.dlsi.prog3.p6.graph.Edge;
import es.ua.dlsi.prog3.p6.graph.Graph;
import es.ua.dlsi.prog3.p6.graph.Node;
import es.ua.dlsi.prog3.p6.graph.NodeNotFoundException;
public class mianGraph {
	
	public static void main(String [] args) throws NodeNotFoundException, FileNotFoundException {
		Graph<String, Integer> graph = new Graph<String, Integer>();
		
		
		Node<String> n1 = graph.addNode("n1");
		Node<String> n2 = graph.addNode("n2");
		Node<String> n3 = graph.addNode("n3");
		Node<String> n4 = graph.addNode("n4");
		Node<String> n5 = graph.addNode("n5");
		Node<String> n6 = graph.addNode("n6");
		Node<String> n7 = graph.addNode("n7");

		Edge<String, Integer> e1 = graph.addEdge(n1, 2, n2);
		Edge<String, Integer> e2 = graph.addEdge(n1, 3, n3);
		Edge<String, Integer> e3 = graph.addEdge(n1, 4, n4);
		Edge<String, Integer> e4 = graph.addEdge(n2, 1, n4);
		Edge<String, Integer> e5 = graph.addEdge(n4, 12, n3);
		Edge<String, Integer> e7 = graph.addEdge(n4, 23, n5);
		Edge<String, Integer> e8 = graph.addEdge(n5, 23, n6);
		Edge<String, Integer> e9 = graph.addEdge(n6, 23, n4);
		Edge<String, Integer> e10 = graph.addEdge(n5, 23, n7);
		Edge<String, Integer> e11 = graph.addEdge(n2, 23, n1);
		Edge<String, Integer> e12 = graph.addEdge(n2, 23, n3);

		
		System.out.println(graph.getEdges());
		System.out.println("in/out n4: ");
		for(Edge<String, Integer> e : graph.getEdgesConnectedTo(n4)) {
			System.out.println(e.getSource() + " => " + e.getTarget() + " [" + e.getLabel() + "]");
		}
		System.out.println();
		System.out.println("out n1: ");
		for(Edge<String, Integer> e :  graph.getOutEdges(n1)) {
			System.out.println(e.getSource() + " => " + e.getTarget());
		}
		DotExporter<String,Integer> dotExporter = new DotExporter<>();
		dotExporter.export(new File("src/exportado.dot"), graph);	

		DFS<String, Integer> dfs = new DFS<String, Integer>(graph);
		dfs.run(n1);
		System.out.println("Recorrido DFS:");
		for(Node<String> n : dfs.getVisitSequence()) {
			System.out.println(n.getLabel());
		}
	}
}
