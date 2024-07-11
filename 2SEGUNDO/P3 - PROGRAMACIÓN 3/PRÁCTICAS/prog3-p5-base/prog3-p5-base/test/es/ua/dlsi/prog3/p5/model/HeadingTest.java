package es.ua.dlsi.prog3.p5.model;

import es.ua.dlsi.prog3.p5.export.html.HTMLExporter;
import es.ua.dlsi.prog3.p5.export.markdown.MarkdownExporter;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * @author David Rizo - drizo@dlsi.ua.es
 * @created 24/10/22
 */
public class HeadingTest {

    @Test
    public void renderMarkDown() throws EditorException {
        MarkdownExporter markdownExporter = new MarkdownExporter();
        assertEquals("#Heading 1", new Heading("Heading 1", 1).export(markdownExporter));
        assertEquals("##Heading 2", new Heading("Heading 2", 2).export(markdownExporter));
        assertEquals("###Heading 3", new Heading("Heading 3", 3).export(markdownExporter));
    }
    @Test
    public void renderHTML() throws EditorException {
        HTMLExporter htmlExporter = new HTMLExporter();
        assertEquals("<h1>Heading 1</h1>", new Heading("Heading 1", 1).export(htmlExporter));
        assertEquals("<h2>Heading 2</h2>", new Heading("Heading 2", 2).export(htmlExporter));
        assertEquals("<h3>Heading 3</h3>", new Heading("Heading 3", 3).export(htmlExporter));
    }
    @Test(expected = EditorException.class)
    public void invalidHeading() throws EditorException {
        new Heading("Invalid heading", 0);
    }

}
