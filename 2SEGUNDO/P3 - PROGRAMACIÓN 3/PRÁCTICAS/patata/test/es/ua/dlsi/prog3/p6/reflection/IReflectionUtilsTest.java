package es.ua.dlsi.prog3.p6.reflection;

import es.ua.dlsi.prog3.p6.reflection.impl.ReflectionUtils;
import es.ua.dlsi.prog3.p6.reflection.tested.A;
import es.ua.dlsi.prog3.p6.reflection.tested.D;
import es.ua.dlsi.prog3.p6.reflection.tested.F;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * @author David Rizo - drizo@dlsi.ua.es
 * @created 12/11/22
 */
public class IReflectionUtilsTest {
    IReflectionUtils reflectionUtils;
    @Before
    public void setUp() {
        reflectionUtils = new ReflectionUtils();
    }

    @Test
    public void instantiate() throws InstantiationException, IllegalAccessException {
        D d = (D) reflectionUtils.instantiate(D.class);
        assertNotNull(d);
        assertEquals(D.class, d.getClass());
    }

    @Test(expected = InstantiationException.class)
    public void instantiateNoDefaultConstructor() throws InstantiationException, IllegalAccessException {
        reflectionUtils.instantiate(A.class);
    }

    @Test
    public void findClassInPackage() throws ClassNotFoundException {
        Class<?> classF = reflectionUtils.findClassInPackage("es.ua.dlsi.prog3.p6.reflection.tested", "F");
        assertNotNull(classF);
        assertEquals(F.class, classF);
    }

    @Test (expected = ClassNotFoundException.class)
    public void findClassInPackageNoFound() throws ClassNotFoundException {
        reflectionUtils.findClassInPackage("", "F");
    }

    @Test
    public void isImplementingInterface() {
        assertTrue(reflectionUtils.isImplementingInterface(F.class, Comparable.class));
        assertFalse(reflectionUtils.isImplementingInterface(A.class, Comparable.class));
    }
}
