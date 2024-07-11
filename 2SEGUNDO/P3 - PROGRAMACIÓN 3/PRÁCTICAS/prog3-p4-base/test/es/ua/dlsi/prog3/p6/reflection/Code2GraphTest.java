package es.ua.dlsi.prog3.p6.reflection;

import es.ua.dlsi.prog3.p6.graph.Graph;
import junit.framework.TestCase;
import org.junit.Test;


/**
 * @author David Rizo - drizo@dlsi.ua.es
 * @created 7/11/22
 */
public class Code2GraphTest extends TestCase {

    @Test
    public void testCreateGraph() throws ReflectionException {
        Code2Graph code2Graph = new Code2Graph(Graph.class);
        Graph<Class<?>, ERelationship> graph = code2Graph.createGraph();
        assertEquals("Graph size", 5, graph.getSize());
        assertEquals("# edges", 9, graph.getEdges().size());
    }


    @Test
    public void testCreateGraphFromName() throws ReflectionException, ClassNotFoundException {
        Code2Graph code2Graph = new Code2Graph("es.ua.dlsi.prog3.p6.graph", "Graph");
        Graph<Class<?>, ERelationship> graph = code2Graph.createGraph();
        assertEquals("Graph size", 5, graph.getSize());
        assertEquals("# edges", 9, graph.getEdges().size());
    }

    @Test
    public void testCreateGraphWrongPackage() throws Exception {
        try {
            Code2Graph code2Graph = new Code2Graph(Graph.class);
            code2Graph.createGraph("fake.package");
            fail("This line should not be reached");
        } catch (ReflectionException e) {
            if (!(e.getCause() instanceof ClassNotFoundException)) {
                throw new Exception("The cause exception should be ClassNotFoundException and it is "+ e.getCause());
            }
        }
    }
}
