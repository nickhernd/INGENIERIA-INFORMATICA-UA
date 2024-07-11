package es.ua.dlsi.prog3.p6.reflection;

import es.ua.dlsi.prog3.p6.reflection.impl.ReflectionUtils;

/**
 * This class uses reflection to find a class that implements IClassAnalyzer that has a default constructor and instantiates it
 * @author David Rizo - drizo@dlsi.ua.es
 * @created 12/11/22
 */
public class ClassAnalyzerFactory {
    /**
     * Package where the classes will be located
     */
    private String packageName;

    /**
     * Reflection utils
     */
    private IReflectionUtils reflectionUtils;

    /**
     * Constructor
     * @param packageName Package where the classes will be located
     */
    public ClassAnalyzerFactory(String packageName) {
        this.packageName = packageName;
        this.reflectionUtils = new ReflectionUtils();
    }


    /**
     * It locates on the given package and creates a class with name ClassAnalyzer that implements IClassAnalyzer.
     * @return The instantiated class
     * @throws ReflectionException When no class is found, it has not a default constructor, or the instantiation has thrown an exception
     */
    public IClassAnalyzer create() throws ReflectionException {
        Class<?> clazz = null;
        try {
            clazz = reflectionUtils.findClassInPackage(packageName, "ClassAnalyzer");
        } catch (ClassNotFoundException e) {
            throw new ReflectionException(e);
        }

        if (!reflectionUtils.isImplementingInterface(clazz, IClassAnalyzer.class)) {
            throw new ReflectionException("The class found with name ClassAnalyzer does not implement IClassAnalyzer");
        }
        try {
            return reflectionUtils.instantiate(clazz);
        } catch (InstantiationException | IllegalAccessException e) {
            throw new ReflectionException(e);
        }
    }


}
