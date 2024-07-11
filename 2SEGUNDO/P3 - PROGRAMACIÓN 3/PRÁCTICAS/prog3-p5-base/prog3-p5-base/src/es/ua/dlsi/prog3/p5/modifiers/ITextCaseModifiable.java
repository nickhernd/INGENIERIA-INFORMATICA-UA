package es.ua.dlsi.prog3.p5.modifiers;

/**
 * Any element that can be modified using a ITextCaseModifier
 * @author David Rizo - drizo@dlsi.ua.es
 * @created 31/10/22
 */
public interface ITextCaseModifiable {
	/**
	 * It applies the text modifying strategy to the modifiable element
	 * @param modifier Modifier objet
	 */
    void changeCase(ITextCaseModifier modifier);
}
