package es.ua.dlsi.prog3.p5.modifiers;

/**
 * It represents the text case modifier behaviour
 * @author David Rizo - drizo@dlsi.ua.es
 * @created 31/10/22
 */
public interface ITextCaseModifier {
    /**
     * It changes the case of the input text
     * @param text Text to be modified
     * @return Modified text
     */	
    String changeCase(String text);
}
