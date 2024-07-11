package es.ua.dlsi.prog3.p5.model;

import es.ua.dlsi.prog3.p5.modifiers.ITextCaseModifiable;
import es.ua.dlsi.prog3.p5.modifiers.ITextCaseModifier;
/**
 * It is used as a base class for all elements that contain text
 * @author David Rizo - drizo@dlsi.ua.es
 * @created 25/10/22
 */
public abstract class AbstractTextContent implements ITextCaseModifiable{
    /**
     * Text
     */
    protected String text;

    /**
     * Constructor
     * @param text Textual content
     */
    protected AbstractTextContent(String text) {
        this.text = text;
    }

    /**
     * Getter
     * @return The text
     */
    public String getText() {
        return text;
    }
    
    @Override
    public void changeCase(ITextCaseModifier modifier) {
    	this.text = modifier.changeCase(this.text);
    }

}
