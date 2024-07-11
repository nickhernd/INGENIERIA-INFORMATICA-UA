package es.ua.dlsi.prog3.p6.reflection;

import es.ua.dlsi.prog3.p6.algorithms.Algorithms;
import es.ua.dlsi.prog3.p6.graph.Graph;
import es.ua.dlsi.prog3.p6.graph.Node;
import es.ua.dlsi.prog3.p6.graph.NodeNotFoundException;
import es.ua.dlsi.prog3.p6.reflection.impl.ReflectionUtils;

import java.io.File;
import java.util.*;

/**
 * This class generates a graph of relations and dependencies starting from a given class
 * to all the classes it's related in the same package
 * @author David Rizo - drizo@dlsi.ua.es
 */
public class Code2Graph {
    /**
     * Source class
     */
    private final Class<?> start;
    /**
     * Graph<class, relationship type>
     */
    private Graph<Class<?>, ERelationship> graph;

    /**
     * Already visited classes
     */
    private HashMap<Class<?>, Node<Class<?>>> visitedClasses;

    /**
     * Class that analyzes the relations of a given class.
     * It will be instantiated using reflection by searching on the classpath
     */
    private IClassAnalyzer classAnalyzer;
    /**
     * The algorithm will work with this starting class
     * @param start Node from which the graph will be built
     */
    public Code2Graph(Class<?> start) {
        this.start = start;
    }

    /**
     * The algorithm will work with this starting class
     * @param packageName The location of the class
     * @param className The name of the class
     * @throws ClassNotFoundException If the class is not found
     */
    public Code2Graph(String packageName, String className) throws ClassNotFoundException {
        IReflectionUtils reflectionUtils = new ReflectionUtils();
        this.start = reflectionUtils.findClassInPackage(packageName, className);
    }

    /**
     * This method is the one that will build the graph
     * @return The graph with nodes representing classes and edges representing relations between them
     * @throws ReflectionException If a reflection problem occurs
     */
    public Graph<Class<?>, ERelationship> createGraph() throws ReflectionException {
        return createGraph("es.ua.dlsi.prog3.p6.reflection.impl");
    }

    /**
     * This method is the one that will build the graph
     * @return The graph with nodes representing classes and edges representing relations between them
     * @throws ReflectionException If a reflection problem occurs
     */
    public Graph<Class<?>, ERelationship> createGraph(String reflectionClassesPackage) throws ReflectionException {
        this.graph = new Graph<>();
        this.visitedClasses = new HashMap<>();
        ClassAnalyzerFactory classAnalyzerFactory = new ClassAnalyzerFactory(reflectionClassesPackage);
        classAnalyzer = classAnalyzerFactory.create();
        if (classAnalyzer == null) {
            throw new ReflectionException("No class analyzer is created");
        }
        browseClass(start);
        return graph;
    }

    /**
     * This recursive method populates the graph for the given class
     * @param c Class to be examined
     * @return The created node in the graph for the given class
     */
    private Node<Class<?>> browseClass(Class<?> c) {
        Node<Class<?>> node = this.visitedClasses.get(c);
        if (node == null) { // it was not visited
            node = graph.addNode(c);
            this.visitedClasses.put(c, node);
            Class<?> parentClass = findParentClass(c);

            if (parentClass != null) {
                addRelatedClass(node, parentClass, ERelationship.inheritance);
            }

            Collection<Class<?>> associations = findAssociatedClasses(c);
            for (Class<?> associatedClass : associations) {
                addRelatedClass(node, associatedClass, ERelationship.association);
            }

            Collection<Class<?>> dependencies = findDependantClasses(c);
            for (Class<?> dependentClass : dependencies) {
                addRelatedClass(node, dependentClass, ERelationship.parameterDependency);
            }
        }

        return node;
    }

    /**
     * Add an edge representing the relationship from two classes
     * @param node Source node
     * @param relatedClass The class it's related to
     * @param relationship The kind of relationship
     */
    private void addRelatedClass(Node<Class<?>> node, Class<?> relatedClass, ERelationship relationship) {
        Node<Class<?>> relatedClassNode = browseClass(relatedClass);
        try {
            graph.addEdge(node, relationship, relatedClassNode);
        } catch (NodeNotFoundException e) {
            throw new RuntimeException(e); // the node will be always found as we are inserting it
        }
    }

    /**
     * This method is to be implemented by students.
     * It returns the parent class of a given class
     * @param c Class to be examined
     * @return null if it has no parent
     */
    private Class<?> findParentClass(Class<?> c) {
        return classAnalyzer.findParentClass(c);
    }

    /**
     * Utility method to check whether the class belongs to the same class as the start class
     * @return True if they belong to the same package (or if both belong to the default one)
     */
    private boolean isSamePackageAsStart(Class<?> c) {
        if (c != start) {
            return classAnalyzer.haveSamePackage(start, c);
        } else {
            return true;
        }
    }

    /**
     * It just returns the classes that belong to the same package as the "start" property
     * @param classes Classes to be filtered
     * @return A new set of classes
     */
    private Set<Class<?>> filterClassesInPackage(Set<Class<?>> classes) {
        HashSet<Class<?>> result = new HashSet<>();
        for (Class<?> possible: classes) {
            if (isSamePackageAsStart(possible)) {
                result.add(possible);
            }
        }
        return result;
    }

    /**
     * This method is to be implemented by students.
     * It returns a collection of classes that are related by a field (associations, compositions, associations) in the class
     * and belong to the same package as the start one
     * @param c Class to be examined
     * @return Set of related classes
     */
    private Set<Class<?>> findAssociatedClasses(Class<?> c) {
        return filterClassesInPackage(classAnalyzer.findAssociatedClasses(c));
    }

    /**
     * This method is to be implemented by students.
     * It returns a collection of classes that are related by a parameter of a method or constructor in the class
     * and belong to the same package as the start one
     * @param c Class to be examined
     * @return Set of related classes
     */
    private Set<Class<?>> findDependantClasses(Class<?> c) {
        return filterClassesInPackage(classAnalyzer.findDependantClasses(c));
    }

    /**
     * Main method
     * @param args Parameters
     */
    public static final void main(String [] args) {
        if (args.length != 3) {
            System.err.println("Use: es.ua.dlsi.prog3.p6.reflection.Code2Graph <package> <source class name> <output DOT file>");
        } else {
            try {
                Code2Graph code2Graph = new Code2Graph(args[0], args[1]);
                Graph<Class<?>, ERelationship> graph = code2Graph.createGraph();
                Algorithms.exportDot(new File(args[2]), graph);
            } catch (Throwable t) {
                System.err.println("Cannot generate the graph from code: " + t.getMessage());
            }
        }

    }
}
