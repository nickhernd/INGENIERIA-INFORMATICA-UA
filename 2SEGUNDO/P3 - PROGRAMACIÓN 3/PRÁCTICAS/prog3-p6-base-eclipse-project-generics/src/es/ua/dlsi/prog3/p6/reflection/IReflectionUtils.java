package es.ua.dlsi.prog3.p6.reflection;

/**
 * It declares some methods that access to the JVM by using the reflection mechanish
 * @author David Rizo - drizo@dlsi.ua.es
 * @created 12/11/22
 */
public interface IReflectionUtils {
    /**
     * It creates the object of the class using the default constructor. It cannot be created with new because it can be replaced by any other class in the future
     * @param classToBeInstantiated
     * @throws InstantiationException The constructor has thrown an exception
     * @throws IllegalAccessException The visibility of the constructor or the class does not allow to create it
     * @return An object of the parametrized type
     */
	public <T> T instantiate(Class<?> classToBeInstantiated) throws InstantiationException, IllegalAccessException;

    /**
     * It finds the class with the given name in the package with the name provided
     * @param packageName Package name
     * @param name Class name (without package)
     * @return Metaclass
     * @throws ClassNotFoundException When no class is found with these characteristics
     */
    Class<?> findClassInPackage(String packageName, String name) throws ClassNotFoundException;

    /**
     * It tests if a class implements an interface
     * @param clazz Class to be checked
     * @param interfaceClass The metaclass of the interface to be checked to be implemented by clazz
     * @return True If the interface is assignable from clazz, i.e., clazz implements the interface
     */
    public boolean isImplementingInterface(Class<?> clazz, Class<?> interfaceClass);
}
