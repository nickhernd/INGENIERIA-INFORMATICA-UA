package es.ua.dlsi.prog3.p6.reflection;

import es.ua.dlsi.prog3.p6.network.Network;
import es.ua.dlsi.prog3.p6.reflection.impl.ClassAnalyzer;
import es.ua.dlsi.prog3.p6.reflection.tested.*;
import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import static org.junit.Assert.*;

/**
 * @author David Rizo - drizo@dlsi.ua.es
 * @created 12/11/22
 */
public class IClassAnalyzerTest {
    private ClassAnalyzer classAnalyzer;

    @Before
    public void setup() {
        this.classAnalyzer = new ClassAnalyzer();
    }
    @Test
    public void findParentClass() {
        assertNull("Object has no parent", classAnalyzer.findParentClass(Object.class));
        assertEquals(D.class, classAnalyzer.findParentClass(F.class));
    }

    @Test
    public void haveSamePackage() {
        assertTrue(classAnalyzer.haveSamePackage(String.class, Integer.class));
        assertTrue(classAnalyzer.haveSamePackage(A.class, B.class));
        assertFalse(classAnalyzer.haveSamePackage(String.class, B.class));
    }

    @Test
    public void findAssociatedClasses() {
        Set<Class<?>> expected = new HashSet<>(Arrays.asList(String.class, int.class, A.class, B.class));
        assertEquals(expected, classAnalyzer.findAssociatedClasses(A.class));
    }

    @Test
    public void findDependantClasses() {
        Set<Class<?>> expected = new HashSet<>(Arrays.asList(B.class, C.class, D.class, E.class, void.class));
        assertEquals(expected, classAnalyzer.findDependantClasses(A.class));
    }
}
