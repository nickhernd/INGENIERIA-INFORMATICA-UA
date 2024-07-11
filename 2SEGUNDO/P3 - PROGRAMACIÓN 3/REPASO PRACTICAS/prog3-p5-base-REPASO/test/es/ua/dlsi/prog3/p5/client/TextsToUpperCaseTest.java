package es.ua.dlsi.prog3.p5.client;

import es.ua.dlsi.prog3.p5.model.Image;
import es.ua.dlsi.prog3.p5.modifiers.ITextCaseModifier;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * @author David Rizo - drizo@dlsi.ua.es
 * @created 31/10/22
 */
public class TextsToUpperCaseTest {

    @Test
    public void changeTextsInImage() {
        Image image = new Image("img/dlsi.png", "Departamento de lenguajes y sistemas informáticos");
        TextsToUpperCase textsToUpperCase = new TextsToUpperCase();
        textsToUpperCase.changeTexts(image);
        assertEquals("DEPARTAMENTO DE LENGUAJES Y SISTEMAS INFORMÁTICOS", image.getAlt());
    }

    @Test
    public void textAnonymousClass() {
        TextsToUpperCase textsToUpperCase = new TextsToUpperCase();
        ITextCaseModifier textModifier = textsToUpperCase.createTextModifier();
        assertTrue("Anonymous class", textModifier.getClass().isAnonymousClass());
    }
}
