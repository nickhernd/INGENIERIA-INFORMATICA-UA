package es.ua.dlsi.prog3.p6.reflection.impl;

import es.ua.dlsi.prog3.p6.reflection.IClassAnalyzer;
import es.ua.dlsi.prog3.p6.reflection.IReflectionUtils;

/**
 * To be done by students
 */
public class ReflectionUtils implements IReflectionUtils {

	@Override
	public <T> T instantiate(Class<?> classToBeInstantiated) throws InstantiationException, IllegalAccessException {
		// TODO Auto-generated method stub
		return (T) classToBeInstantiated.newInstance();
	}

	// packageName = p3.pr1
	// name = Perro
	// p3.pr1.Perro
	@Override
	public Class<?> findClassInPackage(String packageName, String name) throws ClassNotFoundException {
		return Class.forName(packageName + "." + name);
	}

	@Override
	public boolean isImplementingInterface(Class<?> clazz, Class<?> interfaceClass) {
		return interfaceClass.isAssignableFrom(clazz);
	}

}
