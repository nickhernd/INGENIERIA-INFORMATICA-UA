package es.ua.dlsi.prog3.p5.client;

import es.ua.dlsi.prog3.p5.model.IDocumentElement;
import es.ua.dlsi.prog3.p5.modifiers.ITextCaseModifiable;
import es.ua.dlsi.prog3.p5.modifiers.ITextCaseModifier;

/**
 * This class changes to uppercase all the contents of the document
 * @author David Rizo - drizo@dlsi.ua.es
 * @created 31/10/22
 */
public class TextsToUpperCase {

    /**
     * This utility method changes an input document element just if it is ITextCaseModifiable
     * --- DO NOT CHANGE this method ---
     * @param documentElement The document element to be eventually changed
     */
    public void changeTexts(IDocumentElement documentElement) {
        if (documentElement instanceof ITextCaseModifiable) {
            ((ITextCaseModifiable)documentElement).changeCase(createTextModifier());
        }
    }
    /**
     * This method creates an object that converts the input text into uppercase
     * --- IMPLEMENT this method ---
     * @return anonymous ITextCaseModifiable object
     */
    ITextCaseModifier createTextModifier() {
    	//TODO
    	return null;
    }
}
