package es.ua.dlsi.prog3.p6.reflection;

/**
 * Used to unify all exceptions thrown by the reflection mechanism
 * @author David Rizo - drizo@dlsi.ua.es
 * @created 12/11/22
 */
public class ReflectionException extends Exception {
    /**
     * Constructor
     * @param message The message
     */
    public ReflectionException(String message) {
        super(message);
    }

    /**
     * Constructor
     * @param cause Wrapped exception
     */
    public ReflectionException(Throwable cause) {
        super(cause);
    }
}
