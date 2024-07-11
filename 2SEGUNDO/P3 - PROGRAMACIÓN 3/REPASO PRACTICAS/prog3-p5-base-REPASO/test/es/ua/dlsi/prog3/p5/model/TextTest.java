package es.ua.dlsi.prog3.p5.model;

import es.ua.dlsi.prog3.p5.export.markdown.MarkdownExporter;
import es.ua.dlsi.prog3.p5.export.html.HTMLExporter;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * @author David Rizo - drizo@dlsi.ua.es
 * @created 24/10/22
 */
public class TextTest {
    private Text text;

    @Before
    public void setup() {
        text = new Text("DLSI");
    }

    @Test
    public void basicText() {
        assertEquals("DLSI", text.export(new MarkdownExporter()));
        assertEquals("DLSI", text.export(new HTMLExporter()));
    }
    @Test
    public void bold() {
        BoldTextDecorator boldTextDecorator = new BoldTextDecorator(text);
        assertEquals("**DLSI**", boldTextDecorator.export(new MarkdownExporter()));
        assertEquals("<strong>DLSI</strong>", boldTextDecorator.export(new HTMLExporter()));
    }
    @Test
    public void italics() {
        ItalicsTextDecorator italicTextDecorator = new ItalicsTextDecorator(text);
        assertEquals("*DLSI*", italicTextDecorator.export(new MarkdownExporter()));
        assertEquals("<em>DLSI</em>", italicTextDecorator.export(new HTMLExporter()));
    }
    @Test
    public void boldItalics() {
        ItalicsTextDecorator italicTextDecorator = new ItalicsTextDecorator(text);
        BoldTextDecorator boldTextDecorator = new BoldTextDecorator(italicTextDecorator);
        assertEquals("***DLSI***", boldTextDecorator.export(new MarkdownExporter()));
        assertEquals("<strong><em>DLSI</em></strong>", boldTextDecorator.export(new HTMLExporter()));
    }
    @Test
    public void strikeThrough() {
        IText ddecorator = new StrikeThroughDecorator(new Text("Not studying PROG3 theory"));
        assertEquals("~~Not studying PROG3 theory~~", ddecorator.export(new MarkdownExporter()));
        assertEquals("<del>Not studying PROG3 theory</del>", ddecorator.export(new HTMLExporter()));
    }
}
