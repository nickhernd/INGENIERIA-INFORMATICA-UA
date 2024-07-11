package es.ua.dlsi.prog3.p6.reflection.impl;

import es.ua.dlsi.prog3.p6.reflection.IClassAnalyzer;
import es.ua.dlsi.prog3.p6.reflection.IReflectionUtils;

/**
 * To be done by students
 */
public class ReflectionUtils implements IReflectionUtils {

	@SuppressWarnings("unchecked")
	@Override
	public <T> T instantiate(Class<?> classToBeInstantiated) throws InstantiationException, IllegalAccessException {
		return (T) classToBeInstantiated.newInstance();
	}

	@Override
	public Class<?> findClassInPackage(String packageName, String name) throws ClassNotFoundException {
		String qualifiedClassName = packageName + "." + name;
	    return Class.forName(qualifiedClassName);
	}

	@Override
	public boolean isImplementingInterface(Class<?> clazz, Class<?> interfaceClass) {
		return interfaceClass.isAssignableFrom(clazz);
	}

}
