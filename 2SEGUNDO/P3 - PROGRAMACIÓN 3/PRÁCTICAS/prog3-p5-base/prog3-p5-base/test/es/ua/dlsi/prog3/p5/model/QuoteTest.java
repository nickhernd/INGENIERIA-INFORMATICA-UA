package es.ua.dlsi.prog3.p5.model;

import es.ua.dlsi.prog3.p5.export.html.HTMLExporter;
import es.ua.dlsi.prog3.p5.export.markdown.MarkdownExporter;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * @author David Rizo - drizo@dlsi.ua.es
 * @created 31/10/22
 */
public class QuoteTest {

    @Test
    public void export() {
        String content = "Object-oriented analysis is a method of analysis that examines requirements from the perspective of the classes and objects found in the vocabulary of the problem domain";
        IBlock quote = new Quote(new Text(content));
        String html = quote.export(new HTMLExporter());
        assertEquals("<blockquote>" + content + "</blockquote>", html);
        String markdown = quote.export(new MarkdownExporter());
        assertEquals(">" + content, markdown);
    }
}
