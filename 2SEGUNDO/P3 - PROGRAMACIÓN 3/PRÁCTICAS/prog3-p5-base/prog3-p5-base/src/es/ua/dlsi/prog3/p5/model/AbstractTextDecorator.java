package es.ua.dlsi.prog3.p5.model;

/**
 * Base of any of the text decorators
 * @author David Rizo - drizo@dlsi.ua.es
 * @created 18/10/22
 */
public abstract class AbstractTextDecorator extends AbstractDecorator implements IText {
    /**
     * Constructor
     *
     * @param text Text to decorate
     */
	protected AbstractTextDecorator(IText text) {
        super(text);
    }
}
