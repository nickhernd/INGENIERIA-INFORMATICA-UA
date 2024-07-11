package es.ua.dlsi.prog3.p5.model;

import es.ua.dlsi.prog3.p5.export.html.HTMLExporter;
import es.ua.dlsi.prog3.p5.export.markdown.MarkdownExporter;
import org.junit.Test;

import static junit.framework.TestCase.assertEquals;

/**
 * @author David Rizo - drizo@dlsi.ua.es
 * @created 24/10/22
 */
public class BreakLineTest {
    @Test
    public void renderMarkdown() {
        IBlock hr = new BreakLine();
        assertEquals("\n\n", hr.export(new MarkdownExporter()));
    }
    @Test
    public void renderHTML() {
        IBlock hr = new BreakLine();
        assertEquals("<br>", hr.export(new HTMLExporter()));
    }
}
