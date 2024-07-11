package es.ua.dlsi.prog3.p5.model;

import es.ua.dlsi.prog3.p5.export.html.HTMLExporter;
import es.ua.dlsi.prog3.p5.export.markdown.MarkdownExporter;
import org.junit.Test;

import static junit.framework.TestCase.assertEquals;

/**
 * @author David Rizo - drizo@dlsi.ua.es
 * @created 24/10/22
 */
public class HorizontalRuleTest {
    @Test
    public void renderMarkdown() {
        IBlock hr = new HorizontalRule();
        assertEquals("\n-----\n", hr.export(new MarkdownExporter()));
    }
    @Test
    public void renderHTML() {
        IBlock hr = new HorizontalRule();
        assertEquals("<hr>", hr.export(new HTMLExporter()));
    }
}
