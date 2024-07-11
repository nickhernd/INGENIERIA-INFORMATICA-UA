package es.ua.dlsi.prog3.p6.reflection.impl;

import es.ua.dlsi.prog3.p6.reflection.IClassAnalyzer;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.HashSet;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;

/**
 * Just findDependantClasses has been included. The other methods have to be done by students. 
 */
public class ClassAnalyzer implements IClassAnalyzer {
    @Override
    public Set<Class<?>> findDependantClasses(Class<?> c) {
        HashSet<Class<?>> result = new HashSet<>();
        for (Method method: c.getDeclaredMethods()) {
            Class<?> returnType = method.getReturnType();
            result.add(returnType);
            for (Class<?> paramType: method.getParameterTypes()) {
                    result.add(paramType);
            }
        }

        for (Constructor<?> method: c.getConstructors()) {
            for (Class<?> paramType : method.getParameterTypes()) {
                result.add(paramType);
            }
        }
        return result;
    }
    
    @Override
	public Class<?> findParentClass(Class<?> c) {
    	return c.getSuperclass();
	}

	@Override
	public boolean haveSamePackage(Class<?> a, Class<?> b) {
		 Package packageA = a.getPackage();
		 Package packageB = b.getPackage();

		 return packageA != null && packageB != null && packageA.equals(packageB);
	}

	@Override
	public Set<Class<?>> findAssociatedClasses(Class<?> c) {
		 HashSet<Class<?>> result = new HashSet<>();

	     for (Field field : c.getDeclaredFields()) {
	        Class<?> fieldType = field.getType();
	        result.add(fieldType);
	     }

	     return result;
	}

}
