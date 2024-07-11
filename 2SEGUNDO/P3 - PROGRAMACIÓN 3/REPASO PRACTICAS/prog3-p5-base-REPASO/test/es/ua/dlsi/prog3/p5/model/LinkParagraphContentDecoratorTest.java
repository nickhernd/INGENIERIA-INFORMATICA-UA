package es.ua.dlsi.prog3.p5.model;

import es.ua.dlsi.prog3.p5.export.html.HTMLExporter;
import es.ua.dlsi.prog3.p5.export.markdown.MarkdownExporter;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * @author David Rizo - drizo@dlsi.ua.es
 * @created 24/10/22
 */
public class LinkParagraphContentDecoratorTest {

    @Test
    public void textLink() {
        Text singleText = new Text("Click here");
        LinkParagraphContentDecorator linkParagraphContentDecorator = new LinkParagraphContentDecorator(singleText, "https://www.ua.es");
        assertEquals("[Click here](https://www.ua.es)", linkParagraphContentDecorator.export(new MarkdownExporter()));
        assertEquals("<a href='https://www.ua.es'>Click here</a>", linkParagraphContentDecorator.export(new HTMLExporter()));
    }

    @Test
    public void imageLink() {
        Image image = new Image("images/logo.jpg", "DLSI Logo");
        LinkParagraphContentDecorator linkParagraphContentDecorator = new LinkParagraphContentDecorator(image, "https://www.dlsi.ua.es");
        assertEquals("[![DLSI Logo](images/logo.jpg)](https://www.dlsi.ua.es)", linkParagraphContentDecorator.export(new MarkdownExporter()));
        assertEquals("<a href='https://www.dlsi.ua.es'><img src='images/logo.jpg' alt='DLSI Logo'/></a>", linkParagraphContentDecorator.export(new HTMLExporter()));

    }
}
