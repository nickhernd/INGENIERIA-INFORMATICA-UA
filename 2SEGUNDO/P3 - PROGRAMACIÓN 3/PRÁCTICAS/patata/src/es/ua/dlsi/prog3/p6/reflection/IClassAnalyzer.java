package es.ua.dlsi.prog3.p6.reflection;

import java.util.Optional;
import java.util.Set;

/**
 * This interface represents the methods required to analyze the class relationshipts
 * @author David Rizo - drizo@dlsi.ua.es
 * @created 12/11/22
 */
public interface IClassAnalyzer {
    /**
     * It returns the parent class of a given class
     * @param c The class to be analyzed
     * @return null if the class has no parent class, or the parent class elsewhere.
     */
    Class<?> findParentClass(Class<?> c);

    /**
     * It checks whether both classes belong to the same package
     * @param a First class
     * @param b Second class
     * @return True if the package of both classes is exactly the same
     */
    boolean haveSamePackage(Class<?> a, Class<?> b);

    /**
     * It browses the declared fields of class to return their classes.
     * @param c The class to be analyzed
     * @return Set (not repeated values) of classes
     */
    Set<Class<?>> findAssociatedClasses(Class<?> c);

    /**
     * It browses the declared methods and constructors of the class to return the classes of the parameters and return values
     * @param c The class to be analyzed
     * @return Set (not repeated values) of classes
     */
    Set<Class<?>> findDependantClasses(Class<?> c);
}
