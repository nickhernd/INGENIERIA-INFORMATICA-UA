package es.ua.dlsi.prog3.p5.model;

/**
 * Any of the possible decorators of the paragraph contents
 * @author David Rizo - drizo@dlsi.ua.es
 * @created 18/10/22
 */
public abstract class AbstractParagraphContentDecorator extends AbstractDecorator implements IParagraphContent {
    /**
     * Constructor
     * @param decoratedParagraphContent Paragraph content to decorate
     */
    protected AbstractParagraphContentDecorator(IParagraphContent decoratedParagraphContent) {
        super(decoratedParagraphContent);
    }
}
