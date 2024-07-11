package es.ua.dlsi.prog3.p5.model;

import es.ua.dlsi.prog3.p5.export.html.HTMLExporter;
import es.ua.dlsi.prog3.p5.export.markdown.MarkdownExporter;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * @author David Rizo - drizo@dlsi.ua.es
 * @created 24/10/22
 */
public class ImageTest {

    @Test
    public void renderMarkdown() {
        Image image = new Image("images/dlsi.png", "DLSI logo");
        assertEquals("![DLSI logo](images/dlsi.png)", image.export(new MarkdownExporter()));
    }
    @Test
    public void renderHTML() {
        Image image = new Image("images/dlsi.png", "DLSI logo");
        assertEquals("<img src='images/dlsi.png' alt='DLSI logo'/>", image.export(new HTMLExporter()));
    }
}
